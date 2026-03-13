#!/usr/bin/env node

/**
 * GitHub Actions Failure Log Analyzer
 *
 * This script analyzes failure logs using Google Gemini 2.5 Flash AI and creates
 * GitHub issues with the analysis results in Korean.
 *
 * Required Environment Variables:
 * - GEMINI_API_KEY: Google Gemini API key
 * - GITHUB_TOKEN: GitHub Actions token
 * - GITHUB_CONTEXT: GitHub Actions context (JSON string)
 */

const fs = require('fs').promises;
const path = require('path');

// Configuration
const CONFIG = {
    logFile: 'failure.log',
    geminiModel: 'gemini-2.5-flash',
    geminiApiVersion: 'v1beta',
    issueLabel: 'fix',
    maxLogSize: 100000, // Maximum characters to send to Gemini
    maxJobsToAnalyze: 3,
    maxLogExcerptPerJob: 12000,
};

/**
 * Validates required environment variables
 */
function validateEnvironment() {
    const required = ['GEMINI_API_KEY', 'GITHUB_TOKEN', 'GITHUB_CONTEXT'];
    const missing = required.filter(key => !process.env[key]);

    if (missing.length > 0) {
        throw new Error(`Missing required environment variables: ${missing.join(', ')}`);
    }
}

/**
 * Reads and validates the failure log file
 */
async function readFailureLog() {
    try {
        const logPath = path.join(process.cwd(), CONFIG.logFile);
        console.log(`Reading failure log from: ${logPath}`);

        const content = await fs.readFile(logPath, 'utf-8');

        if (!content || content.trim().length === 0) {
            throw new Error('Failure log is empty');
        }

        // Truncate if too large
        const truncated = content.length > CONFIG.maxLogSize
            ? content.slice(-CONFIG.maxLogSize)
            : content;

        if (truncated.length < content.length) {
            console.log(`Log truncated from ${content.length} to ${truncated.length} characters`);
        }

        return truncated;
    } catch (error) {
        if (error.code === 'ENOENT') {
            throw new Error(`Failure log file not found: ${CONFIG.logFile}`);
        }
        throw error;
    }
}

function parseGitHubContext() {
    return JSON.parse(process.env.GITHUB_CONTEXT);
}

function getRepoInfo(context) {
    const { repository } = context;
    if (!repository) {
        throw new Error('Repository information not found in GITHUB_CONTEXT');
    }

    const [owner, repo] = repository.split('/');
    return { owner, repo };
}

async function githubApiRequest(url, options = {}) {
    const token = process.env.GITHUB_TOKEN;
    const response = await fetch(url, {
        ...options,
        headers: {
            'Authorization': `Bearer ${token}`,
            'Accept': 'application/vnd.github+json',
            'User-Agent': 'GitHub-Actions-Failure-Analyzer',
            ...(options.headers || {}),
        },
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`GitHub API error (${response.status}): ${errorText}`);
    }

    return response;
}

function stripAnsi(text) {
    return text.replace(/\u001b\[[0-9;]*m/g, '');
}

function summarizeFailedSteps(steps = []) {
    return steps
        .filter(step => step.conclusion === 'failure')
        .map(step => `${step.number}. ${step.name}`)
        .join(', ');
}

async function fetchFailedJobs(context) {
    const { owner, repo } = getRepoInfo(context);
    const runId = context.run_id;
    const runAttempt = context.run_attempt || 1;
    const url = `https://api.github.com/repos/${owner}/${repo}/actions/runs/${runId}/attempts/${runAttempt}/jobs?per_page=100`;

    console.log(`Fetching failed jobs for run ${runId} (attempt ${runAttempt})...`);
    const response = await githubApiRequest(url);
    const data = await response.json();

    if (!data.jobs || data.jobs.length === 0) {
        throw new Error('No workflow jobs returned from GitHub Actions API');
    }

    const failedConclusions = new Set(['failure', 'timed_out', 'startup_failure', 'action_required']);
    const failedJobs = data.jobs
        .filter(job => failedConclusions.has(job.conclusion))
        .slice(0, CONFIG.maxJobsToAnalyze);

    console.log(`Found ${failedJobs.length} failed job(s)`);
    return failedJobs;
}

async function fetchJobLog(owner, repo, jobId) {
    const url = `https://api.github.com/repos/${owner}/${repo}/actions/jobs/${jobId}/logs`;
    const response = await githubApiRequest(url);
    const rawLog = await response.text();

    return stripAnsi(rawLog).trim();
}

function buildFailureLog(context, failedJobs, jobLogs) {
    const workflowUrl = `https://github.com/${context.repository}/actions/runs/${context.run_id}`;
    const lines = [
        '=== GitHub Actions Failure Context ===',
        `Repository: ${context.repository}`,
        `Workflow: ${context.workflow}`,
        `Run ID: ${context.run_id}`,
        `Run Attempt: ${context.run_attempt || 1}`,
        `Branch: ${context.ref_name}`,
        `Commit: ${context.sha}`,
        `Actor: ${context.actor}`,
        `Event: ${context.event_name}`,
        `Workflow URL: ${workflowUrl}`,
        '',
        '=== Failed Jobs Summary ===',
    ];

    if (failedJobs.length === 0) {
        lines.push('No failed jobs were identified by the GitHub Actions API.');
    }

    for (const job of failedJobs) {
        const failedSteps = summarizeFailedSteps(job.steps);
        lines.push(`- Job: ${job.name}`);
        lines.push(`  Conclusion: ${job.conclusion}`);
        lines.push(`  Started: ${job.started_at}`);
        lines.push(`  Completed: ${job.completed_at}`);
        lines.push(`  Failed steps: ${failedSteps || 'No failed step information available'}`);
    }

    lines.push('');
    lines.push('=== Failed Job Logs ===');

    for (const job of failedJobs) {
        const logText = jobLogs[job.id] || 'Failed to retrieve job log.';
        const excerpt = logText.length > CONFIG.maxLogExcerptPerJob
            ? logText.slice(-CONFIG.maxLogExcerptPerJob)
            : logText;

        lines.push('');
        lines.push(`--- Job: ${job.name} (ID: ${job.id}) ---`);
        lines.push(excerpt);
    }

    return lines.join('\n');
}

async function collectFailureLog() {
    const context = parseGitHubContext();
    const { owner, repo } = getRepoInfo(context);
    const failedJobs = await fetchFailedJobs(context);
    const jobLogs = {};

    for (const job of failedJobs) {
        console.log(`Fetching log for failed job: ${job.name} (${job.id})`);

        try {
            jobLogs[job.id] = await fetchJobLog(owner, repo, job.id);
        } catch (error) {
            jobLogs[job.id] = `Failed to retrieve log for job ${job.name}: ${error.message}`;
        }
    }

    const failureLog = buildFailureLog(context, failedJobs, jobLogs);
    const logPath = path.join(process.cwd(), CONFIG.logFile);
    await fs.writeFile(logPath, failureLog, 'utf-8');

    console.log(`✓ Wrote enriched failure log to ${logPath}`);
}

/**
 * Calls Google Gemini API to analyze the failure log
 */
async function analyzeWithGemini(logContent) {
    const apiKey = process.env.GEMINI_API_KEY;
    const url = `https://generativelanguage.googleapis.com/${CONFIG.geminiApiVersion}/models/${CONFIG.geminiModel}:generateContent?key=${apiKey}`;

    const prompt = `You are a DevOps expert analyzing a CI/CD failure log.
Analyze this failure log and provide a structured response in STRICT JSON format.
All response values MUST be in Korean language.

CRITICAL RULES:
1. Use only evidence that explicitly appears in the log.
2. Do NOT guess or list generic possibilities when the error is not shown.
3. If the log is insufficient, clearly say that the exact root cause is not confirmed.
4. Mention the failed job and failed step names when they exist.
5. The solution must be concrete and tied to the observed error.

CRITICAL: Your response must be ONLY valid JSON. Do not include any text before or after the JSON object.

Required JSON structure:
{
  "title": "Korean issue title (concise, max 80 chars)",
  "summary": "What failed (Korean, 2-3 sentences, include failed job/step if known)",
  "cause": "Root cause analysis (Korean, based only on explicit evidence)",
  "solution": "How to fix this issue (Korean, actionable steps based on evidence)"
}

Failure Log:
${logContent}

Remember: Respond with ONLY the JSON object, nothing else.`;

    console.log('Calling Gemini API...');

    const response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            contents: [{
                parts: [{
                    text: prompt
                }]
            }],
            generationConfig: {
                temperature: 0.3,
                topK: 40,
                topP: 0.95,
                maxOutputTokens: 8192,
                responseMimeType: 'application/json',
            }
        })
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Gemini API error (${response.status}): ${errorText}`);
    }

    const data = await response.json();

    // Validate response structure
    if (!data.candidates || data.candidates.length === 0) {
        throw new Error('Gemini API returned no candidates. Response: ' + JSON.stringify(data));
    }

    const candidate = data.candidates[0];

    if (!candidate.content || !candidate.content.parts || candidate.content.parts.length === 0) {
        throw new Error('Gemini API returned empty content');
    }

    const rawText = candidate.content.parts[0].text;
    console.log(`✓ Gemini response received (${rawText.length} characters)`);

    // Check for finish reason
    if (candidate.finishReason && candidate.finishReason !== 'STOP') {
        console.warn(`⚠️  Warning: Unusual finish reason: ${candidate.finishReason}`);
        if (candidate.finishReason === 'MAX_TOKENS') {
            console.warn('Response was truncated due to token limit. Increase maxOutputTokens.');
        }
    }

    return rawText;
}

/**
 * Parses JSON response from Gemini, handling code block wrapping
 */
function parseGeminiResponse(rawText) {
    console.log('Parsing Gemini response...');
    console.log(`Raw response length: ${rawText.length} characters`);

    let jsonText = rawText.trim();

    // Remove markdown code block wrapping if present
    const codeBlockPattern = /```(?:json)?\s*\n?([\s\S]*?)\n?```/;
    const match = jsonText.match(codeBlockPattern);

    if (match) {
        jsonText = match[1].trim();
        console.log('Removed markdown code block wrapper');
    }

    // Check if response appears to be truncated
    if (!jsonText.endsWith('}')) {
        console.warn('⚠️  Warning: Response may be truncated (does not end with })');
        console.log('Last 200 chars:', jsonText.slice(-200));
    }

    // Parse JSON
    let parsed;
    try {
        parsed = JSON.parse(jsonText);
    } catch (error) {
        console.error('JSON Parse Error:', error.message);
        console.error('First 500 chars:', jsonText.substring(0, 500));
        console.error('Last 500 chars:', jsonText.slice(-500));
        throw new Error(`Failed to parse JSON response: ${error.message}\nThis may be due to incomplete API response. Try increasing maxOutputTokens or simplifying the log.`);
    }

    // Validate required fields
    const requiredFields = ['title', 'summary', 'cause', 'solution'];
    const missingFields = requiredFields.filter(field => !parsed[field]);

    if (missingFields.length > 0) {
        throw new Error(`Missing required fields in Gemini response: ${missingFields.join(', ')}`);
    }

    console.log('✓ Successfully parsed Gemini response');
    return parsed;
}

/**
 * Creates a GitHub issue with the analysis
 */
async function createGitHubIssue(analysis) {
    const context = parseGitHubContext();
    const token = process.env.GITHUB_TOKEN;
    const { owner, repo } = getRepoInfo(context);
    const workflowUrl = `https://github.com/${context.repository}/actions/runs/${context.run_id}`;

    const issueBody = `## 🔍 요약 (Summary)

${analysis.summary}

## 🐛 원인 (Root Cause)

${analysis.cause}

## ✅ 해결 방법 (Solution)

${analysis.solution}

## 🔗 참고 링크

- Workflow Run: ${workflowUrl}
- Commit: ${context.sha}

---
*이 이슈는 자동으로 생성되었습니다 / This issue was automatically generated*`;

    const url = `https://api.github.com/repos/${owner}/${repo}/issues`;

    console.log(`Creating GitHub issue in ${owner}/${repo}...`);

    const response = await fetch(url, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Accept': 'application/vnd.github.v3+json',
            'Content-Type': 'application/json',
            'User-Agent': 'GitHub-Actions-Failure-Analyzer',
        },
        body: JSON.stringify({
            title: analysis.title,
            body: issueBody,
            labels: [CONFIG.issueLabel],
        })
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`GitHub API error (${response.status}): ${errorText}`);
    }

    const issue = await response.json();
    console.log(`✓ Issue created successfully: #${issue.number}`);

    return issue;
}

/**
 * Main execution function
 */
async function main() {
    try {
        console.log('=== GitHub Actions Failure Analyzer ===\n');

        // Step 1: Validate environment
        validateEnvironment();
        console.log('✓ Environment validated\n');

        // Step 2: Collect and read failure log
        await collectFailureLog();
        const logContent = await readFailureLog();
        console.log(`✓ Read ${logContent.length} characters from failure log\n`);

        // Step 3: Analyze with Gemini
        const geminiResponse = await analyzeWithGemini(logContent);

        // Step 4: Parse response
        const analysis = parseGeminiResponse(geminiResponse);
        console.log(`✓ Analysis completed\n`);

        // Step 5: Create GitHub issue
        const issue = await createGitHubIssue(analysis);

        // Step 6: Output issue URL
        console.log(`\nISSUE_URL=${issue.html_url}`);

        // Set output for GitHub Actions
        if (process.env.GITHUB_OUTPUT) {
            await fs.appendFile(
                process.env.GITHUB_OUTPUT,
                `issue_url=${issue.html_url}\n`,
                'utf-8'
            );
        }

        process.exit(0);
    } catch (error) {
        console.error('\n❌ Error:', error.message);

        if (error.stack) {
            console.error('\nStack trace:');
            console.error(error.stack);
        }

        process.exit(1);
    }
}

// Execute main function
if (require.main === module) {
    main();
}

module.exports = { main, analyzeWithGemini, parseGeminiResponse };

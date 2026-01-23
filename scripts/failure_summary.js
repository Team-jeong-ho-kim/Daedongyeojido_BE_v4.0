import fs from "fs";
import fetch from "node-fetch";

const GEMINI_API_KEY = process.env.GEMINI_API_KEY;
const GITHUB_TOKEN = process.env.GITHUB_TOKEN;
const GITHUB_CONTEXT = JSON.parse(process.env.GITHUB_CONTEXT);

const LOG_PATH = "failure.log";

async function summarizeWithGemini(logText) {
    const prompt = `
You are a senior DevOps engineer.

Analyze the following CI/CD deployment failure log.
Respond strictly in JSON format.
All field values must be written in Korean.

Response format:
{
  "title": "이슈 제목 (한국어)",
  "summary": "무엇이 실패했는지 요약 (한국어)",
  "cause": "근본 원인 (한국어)",
  "solution": "해결 방법 또는 조치 가이드 (한국어)"
}

Log:
${logText.slice(-12000)}
`;

    const res = await fetch(
        `https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=${GEMINI_API_KEY}`,
        {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                contents: [{ parts: [{ text: prompt }] }],
            }),
        }
    );

    const data = await res.json();
    const raw = data.candidates[0].content.parts[0].text;
    const cleaned = raw.replace(/```json|```/g, "").trim();

    return JSON.parse(cleaned);
}

async function createIssue(summaryData) {
    const { owner, repo } = GITHUB_CONTEXT.repository;

    const body = `
## 🚧Summary
${summaryData.summary}

## 🚨Root Cause
${summaryData.cause}

## 💡Suggested Solution
${summaryData.solution}

---

**Repository:** ${GITHUB_CONTEXT.repository.full_name}  
**Branch:** ${GITHUB_CONTEXT.ref_name}  
**Commit:** ${GITHUB_CONTEXT.sha}  
**Actor:** ${GITHUB_CONTEXT.actor}  
**Workflow:** https://github.com/${GITHUB_CONTEXT.repository.full_name}/actions/runs/${GITHUB_CONTEXT.run_id}
`;

    const res = await fetch(`https://api.github.com/repos/${owner.login}/${repo.name}/issues`, {
        method: "POST",
        headers: {
            Authorization: `Bearer ${GITHUB_TOKEN}`,
            Accept: "application/vnd.github+json",
        },
        body: JSON.stringify({
            title: summaryData.title,
            body,
            labels: ["fix"]
        }),
    });

    const issue = await res.json();

    console.log(`ISSUE_URL=${issue.html_url}`);
}

async function main() {
    if (GITHUB_CONTEXT.event_name !== "push") {
        console.log("Not a deploy event. Skip issue creation.");
        return;
    }

    if (!fs.existsSync(LOG_PATH)) {
        console.log("No failure log found");
        return;
    }

    const logText = fs.readFileSync(LOG_PATH, "utf-8");

    const summary = await summarizeWithGemini(logText);
    await createIssue(summary);

    console.log("Deploy failure issue (label: fix, Korean summary) created successfully");
}

main().catch((e) => {
    console.error(e);
    process.exit(1);
});

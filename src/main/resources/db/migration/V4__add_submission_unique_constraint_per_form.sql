CREATE TEMPORARY TABLE `tmp_submission_ranked` AS
SELECT
    s.id,
    ROW_NUMBER() OVER (
        PARTITION BY s.account_id, s.application_form_id
        ORDER BY
            CASE s.user_application_status
                WHEN 'ACCEPTED' THEN 4
                WHEN 'REJECTED' THEN 3
                WHEN 'SUBMITTED' THEN 2
                WHEN 'WRITING' THEN 1
                ELSE 0
            END DESC,
            s.id DESC
    ) AS rn
FROM `tbl_submission` s;

CREATE TEMPORARY TABLE `tmp_submission_delete_ids` AS
SELECT id
FROM `tmp_submission_ranked`
WHERE rn > 1;

DELETE aa
FROM `tbl_application_answer` aa
         JOIN `tmp_submission_delete_ids` d ON aa.submission_id = d.id;

DELETE s
FROM `tbl_submission` s
         JOIN `tmp_submission_delete_ids` d ON s.id = d.id;

DROP TEMPORARY TABLE IF EXISTS `tmp_submission_delete_ids`;
DROP TEMPORARY TABLE IF EXISTS `tmp_submission_ranked`;

ALTER TABLE `tbl_submission`
    ADD CONSTRAINT `unique_idx_submission_account_form`
        UNIQUE (`account_id`, `application_form_id`);

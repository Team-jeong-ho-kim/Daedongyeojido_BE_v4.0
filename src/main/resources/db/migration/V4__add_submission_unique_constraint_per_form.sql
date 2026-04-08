ALTER TABLE `tbl_submission`
    ADD CONSTRAINT `unique_idx_submission_account_form`
        UNIQUE (`account_id`, `application_form_id`);

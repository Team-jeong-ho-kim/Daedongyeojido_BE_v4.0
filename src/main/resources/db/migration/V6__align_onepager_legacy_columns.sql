-- Idempotently drop legacy columns left over from earlier Hibernate-DDL state.
-- Stag has already dropped these via V5; this migration is a no-op there.
-- Prod (and any environment without DROP COLUMN IF EXISTS support) will use
-- the information_schema lookup below to drop the columns when present.

-- tbl_onepager.teacher_name
SET @tbl_onepager_teacher_name_exists = (
    SELECT COUNT(*)
    FROM information_schema.columns
    WHERE table_schema = DATABASE()
      AND table_name = 'tbl_onepager'
      AND column_name = 'teacher_name'
);
SET @sql = IF(
    @tbl_onepager_teacher_name_exists = 1,
    'ALTER TABLE `tbl_onepager` DROP COLUMN `teacher_name`',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- tbl_onepager.state
SET @tbl_onepager_state_exists = (
    SELECT COUNT(*)
    FROM information_schema.columns
    WHERE table_schema = DATABASE()
      AND table_name = 'tbl_onepager'
      AND column_name = 'state'
);
SET @sql = IF(
    @tbl_onepager_state_exists = 1,
    'ALTER TABLE `tbl_onepager` DROP COLUMN `state`',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- tbl_onepager.reason
SET @tbl_onepager_reason_exists = (
    SELECT COUNT(*)
    FROM information_schema.columns
    WHERE table_schema = DATABASE()
      AND table_name = 'tbl_onepager'
      AND column_name = 'reason'
);
SET @sql = IF(
    @tbl_onepager_reason_exists = 1,
    'ALTER TABLE `tbl_onepager` DROP COLUMN `reason`',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- tbl_rejected_onepager_comment.onepager_id
SET @rejected_comment_old_col_exists = (
    SELECT COUNT(*)
    FROM information_schema.columns
    WHERE table_schema = DATABASE()
      AND table_name = 'tbl_rejected_onepager_comment'
      AND column_name = 'onepager_id'
);
SET @sql = IF(
    @rejected_comment_old_col_exists = 1,
    'ALTER TABLE `tbl_rejected_onepager_comment` DROP COLUMN `onepager_id`',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

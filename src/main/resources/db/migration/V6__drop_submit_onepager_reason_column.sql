-- 제출 사유(reason)를 별도 컬럼이 아닌 첫 번째 댓글(tbl_rejected_onepager_comment)로 관리하도록 변경.
-- 더 이상 사용하지 않는 tbl_submit_onepager.reason 컬럼을 제거한다.
SET @tbl_submit_onepager_reason_exists = (
    SELECT COUNT(*)
    FROM information_schema.columns
    WHERE table_schema = DATABASE()
      AND table_name = 'tbl_submit_onepager'
      AND column_name = 'reason'
);
SET @sql = IF(
    @tbl_submit_onepager_reason_exists = 1,
    'ALTER TABLE `tbl_submit_onepager` DROP COLUMN `reason`',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

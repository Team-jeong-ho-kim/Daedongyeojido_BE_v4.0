-- 같은 원본 파일명으로도 여러 파일 업로드가 가능하도록 file_name unique 제약을 제거한다.
SET @tbl_file_file_name_unique_exists = (
    SELECT COUNT(*)
    FROM information_schema.statistics
    WHERE table_schema = DATABASE()
      AND table_name = 'tbl_file'
      AND index_name = 'UK_ngx7oesqusouskrfa479fadcv'
);

SET @sql = IF(
    @tbl_file_file_name_unique_exists = 1,
    'ALTER TABLE `tbl_file` DROP INDEX `UK_ngx7oesqusouskrfa479fadcv`',
    'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

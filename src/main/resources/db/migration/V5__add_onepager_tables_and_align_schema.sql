-- Add onepager-related schema for the current develop entities.
-- Main does not have these tables yet, but some environments may already have
-- partially-created tables from Hibernate DDL. This migration therefore:
-- 1) creates the final tables when they do not exist
-- 2) removes only known legacy columns/constraints when they already exist
-- 3) adds required indexes/FKs when missing

-- ========================================
-- Table: tbl_onepager
-- ========================================
CREATE TABLE IF NOT EXISTS `tbl_onepager` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `description` varchar(500) NOT NULL,
    `form_url` varchar(255) DEFAULT NULL,
    `one_pager_duration` datetime(6) DEFAULT NULL,
    `one_pager_duration_type` enum('DATE','INFINITY') NOT NULL,
    `title` varchar(50) NOT NULL,
    `file_id` bigint DEFAULT NULL,
    `one_pager_teacher_id` bigint NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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

SET @tbl_onepager_file_uk_exists = (
    SELECT COUNT(*)
    FROM information_schema.statistics
    WHERE table_schema = DATABASE()
      AND table_name = 'tbl_onepager'
      AND index_name = 'UK_flhkbskiwvbblkggym9oqr9qt'
);
SET @sql = IF(
    @tbl_onepager_file_uk_exists = 0,
    'ALTER TABLE `tbl_onepager` ADD UNIQUE KEY `UK_flhkbskiwvbblkggym9oqr9qt` (`file_id`)',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @tbl_onepager_teacher_idx_exists = (
    SELECT COUNT(*)
    FROM information_schema.statistics
    WHERE table_schema = DATABASE()
      AND table_name = 'tbl_onepager'
      AND index_name = 'FKpr38aiqgcjl6o0o4gi4oybt75'
);
SET @sql = IF(
    @tbl_onepager_teacher_idx_exists = 0,
    'ALTER TABLE `tbl_onepager` ADD KEY `FKpr38aiqgcjl6o0o4gi4oybt75` (`one_pager_teacher_id`)',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @tbl_onepager_file_fk_exists = (
    SELECT COUNT(*)
    FROM information_schema.table_constraints
    WHERE constraint_schema = DATABASE()
      AND table_name = 'tbl_onepager'
      AND constraint_name = 'FKiv34sojj4c7eoi3wpjnkhg8cf'
);
SET @sql = IF(
    @tbl_onepager_file_fk_exists = 0,
    'ALTER TABLE `tbl_onepager` ADD CONSTRAINT `FKiv34sojj4c7eoi3wpjnkhg8cf` FOREIGN KEY (`file_id`) REFERENCES `tbl_file` (`id`)',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @tbl_onepager_teacher_fk_exists = (
    SELECT COUNT(*)
    FROM information_schema.table_constraints
    WHERE constraint_schema = DATABASE()
      AND table_name = 'tbl_onepager'
      AND constraint_name = 'FKpr38aiqgcjl6o0o4gi4oybt75'
);
SET @sql = IF(
    @tbl_onepager_teacher_fk_exists = 0,
    'ALTER TABLE `tbl_onepager` ADD CONSTRAINT `FKpr38aiqgcjl6o0o4gi4oybt75` FOREIGN KEY (`one_pager_teacher_id`) REFERENCES `tbl_teacher` (`id`)',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- ========================================
-- Table: tbl_submit_onepager
-- ========================================
CREATE TABLE IF NOT EXISTS `tbl_submit_onepager` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `one_pager_state` enum('APPROVED','REJECTED','SUBMITTED','CANCELED') NOT NULL,
    `reason` varchar(100) DEFAULT NULL,
    `submit_date` date NOT NULL,
    `submit_club_id` bigint NOT NULL,
    `form_onepager_id` bigint NOT NULL,
    `submit_file_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET @tbl_submit_onepager_file_uk_exists = (
    SELECT COUNT(*)
    FROM information_schema.statistics
    WHERE table_schema = DATABASE()
      AND table_name = 'tbl_submit_onepager'
      AND index_name = 'UK_lr3owsjhsrlkmq8e205a764qr'
);
SET @sql = IF(
    @tbl_submit_onepager_file_uk_exists = 0,
    'ALTER TABLE `tbl_submit_onepager` ADD UNIQUE KEY `UK_lr3owsjhsrlkmq8e205a764qr` (`submit_file_id`)',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @tbl_submit_onepager_club_idx_exists = (
    SELECT COUNT(*)
    FROM information_schema.statistics
    WHERE table_schema = DATABASE()
      AND table_name = 'tbl_submit_onepager'
      AND index_name = 'FK6sr7sqyaxgbrl6xdgpv5xkrxm'
);
SET @sql = IF(
    @tbl_submit_onepager_club_idx_exists = 0,
    'ALTER TABLE `tbl_submit_onepager` ADD KEY `FK6sr7sqyaxgbrl6xdgpv5xkrxm` (`submit_club_id`)',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @tbl_submit_onepager_form_idx_exists = (
    SELECT COUNT(*)
    FROM information_schema.statistics
    WHERE table_schema = DATABASE()
      AND table_name = 'tbl_submit_onepager'
      AND index_name = 'FKniq8qcwsr51nx7xg6053qgx75'
);
SET @sql = IF(
    @tbl_submit_onepager_form_idx_exists = 0,
    'ALTER TABLE `tbl_submit_onepager` ADD KEY `FKniq8qcwsr51nx7xg6053qgx75` (`form_onepager_id`)',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @tbl_submit_onepager_file_fk_exists = (
    SELECT COUNT(*)
    FROM information_schema.table_constraints
    WHERE constraint_schema = DATABASE()
      AND table_name = 'tbl_submit_onepager'
      AND constraint_name = 'FKjet8s3aqwqt31h1hulaue2lev'
);
SET @sql = IF(
    @tbl_submit_onepager_file_fk_exists = 0,
    'ALTER TABLE `tbl_submit_onepager` ADD CONSTRAINT `FKjet8s3aqwqt31h1hulaue2lev` FOREIGN KEY (`submit_file_id`) REFERENCES `tbl_file` (`id`)',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @tbl_submit_onepager_club_fk_exists = (
    SELECT COUNT(*)
    FROM information_schema.table_constraints
    WHERE constraint_schema = DATABASE()
      AND table_name = 'tbl_submit_onepager'
      AND constraint_name = 'FK6sr7sqyaxgbrl6xdgpv5xkrxm'
);
SET @sql = IF(
    @tbl_submit_onepager_club_fk_exists = 0,
    'ALTER TABLE `tbl_submit_onepager` ADD CONSTRAINT `FK6sr7sqyaxgbrl6xdgpv5xkrxm` FOREIGN KEY (`submit_club_id`) REFERENCES `tbl_club` (`id`)',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @tbl_submit_onepager_form_fk_exists = (
    SELECT COUNT(*)
    FROM information_schema.table_constraints
    WHERE constraint_schema = DATABASE()
      AND table_name = 'tbl_submit_onepager'
      AND constraint_name = 'FKniq8qcwsr51nx7xg6053qgx75'
);
SET @sql = IF(
    @tbl_submit_onepager_form_fk_exists = 0,
    'ALTER TABLE `tbl_submit_onepager` ADD CONSTRAINT `FKniq8qcwsr51nx7xg6053qgx75` FOREIGN KEY (`form_onepager_id`) REFERENCES `tbl_onepager` (`id`)',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- ========================================
-- Table: tbl_rejected_onepager_comment
-- ========================================
CREATE TABLE IF NOT EXISTS `tbl_rejected_onepager_comment` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `comment` varchar(255) NOT NULL,
    `comment_writer` varchar(4) NOT NULL,
    `submit_onepager_id` bigint NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET @rejected_comment_old_fk_exists = (
    SELECT COUNT(*)
    FROM information_schema.table_constraints
    WHERE constraint_schema = DATABASE()
      AND table_name = 'tbl_rejected_onepager_comment'
      AND constraint_name = 'FK77qovmk6i2aqhlohwkwc7e27q'
);
SET @sql = IF(
    @rejected_comment_old_fk_exists = 1,
    'ALTER TABLE `tbl_rejected_onepager_comment` DROP FOREIGN KEY `FK77qovmk6i2aqhlohwkwc7e27q`',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @rejected_comment_old_idx_exists = (
    SELECT COUNT(*)
    FROM information_schema.statistics
    WHERE table_schema = DATABASE()
      AND table_name = 'tbl_rejected_onepager_comment'
      AND index_name = 'FK77qovmk6i2aqhlohwkwc7e27q'
);
SET @sql = IF(
    @rejected_comment_old_idx_exists = 1,
    'ALTER TABLE `tbl_rejected_onepager_comment` DROP INDEX `FK77qovmk6i2aqhlohwkwc7e27q`',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

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

SET @rejected_comment_submit_idx_exists = (
    SELECT COUNT(*)
    FROM information_schema.statistics
    WHERE table_schema = DATABASE()
      AND table_name = 'tbl_rejected_onepager_comment'
      AND index_name = 'FK3012yv8jtaje61igk9h26r47o'
);
SET @sql = IF(
    @rejected_comment_submit_idx_exists = 0,
    'ALTER TABLE `tbl_rejected_onepager_comment` ADD KEY `FK3012yv8jtaje61igk9h26r47o` (`submit_onepager_id`)',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @rejected_comment_submit_fk_exists = (
    SELECT COUNT(*)
    FROM information_schema.table_constraints
    WHERE constraint_schema = DATABASE()
      AND table_name = 'tbl_rejected_onepager_comment'
      AND constraint_name = 'FK3012yv8jtaje61igk9h26r47o'
);
SET @sql = IF(
    @rejected_comment_submit_fk_exists = 0,
    'ALTER TABLE `tbl_rejected_onepager_comment` ADD CONSTRAINT `FK3012yv8jtaje61igk9h26r47o` FOREIGN KEY (`submit_onepager_id`) REFERENCES `tbl_submit_onepager` (`id`)',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

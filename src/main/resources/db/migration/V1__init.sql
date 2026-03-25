-- ========================================
-- Table: tbl_admin_alarm
-- ========================================
CREATE TABLE `tbl_admin_alarm` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `alarm_category` enum('COMMON','CLUB_ACCEPTED','CLUB_MEMBER_APPLY') NOT NULL,
    `alarm_type` enum('CREATE_CLUB_APPLY','DISSOLVE_CLUB_APPLY','CLUB_FINAL_ACCEPTED','CLUB_FINAL_REJECTED','CLUB_MEMBER_APPLY','CLUB_CREATION_ACCEPTED','CLUB_CREATION_REJECTED','CLUB_DISSOLUTION_ACCEPTED','CLUB_DISSOLUTION_REJECTED','USER_JOINED_CLUB','USER_REFUSED_CLUB','USER_SUBMIT_APPLICATION','USER_CANCEL_APPLICATION','DELETE_CLUB_MEMBER','INTERVIEW_SCHEDULE_CREATED','INTERVIEW_SCHEDULE_CHANGED','REQUEST_CLUB_CREATION','REQUEST_CLUB_DISSOLUTION') NOT NULL,
    `content` varchar(300) NOT NULL,
    `title` varchar(50) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_teacher
-- ========================================
CREATE TABLE `tbl_teacher` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `account_id` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    `teacher_name` varchar(20) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_cf3gdp8f5vuuob9836umgg3b3` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_user
-- ========================================
CREATE TABLE `tbl_user` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `account_id` varchar(255) NOT NULL,
    `class_number` varchar(4) NOT NULL,
    `introduction` varchar(30) DEFAULT NULL,
    `password` varchar(255) NOT NULL,
    `phone_number` varchar(11) DEFAULT NULL,
    `profile_image` varchar(300) DEFAULT NULL,
    `role` enum('STUDENT','ADMIN','TEACHER','CLUB_MEMBER','CLUB_LEADER') NOT NULL,
    `user_name` varchar(4) NOT NULL,
    `club_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_fluav7nuwmv5hni5guxjrdwbw` (`account_id`),
    UNIQUE KEY `UK_crr34qvg2qqdfhc1sv0eg5km2` (`phone_number`),
    KEY `FK7vvnjl5gmaufavexhta4iokoc` (`club_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_club
-- ========================================
CREATE TABLE `tbl_club` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `club_creation_form` varchar(255) NOT NULL,
    `club_image` varchar(200) NOT NULL,
    `club_name` varchar(20) NOT NULL,
    `club_status` enum('OPEN','CLOSE','REJECTED') NOT NULL,
    `introduction` varchar(500) NOT NULL,
    `one_liner` varchar(30) NOT NULL,
    `account_id` bigint NOT NULL,
    `teacher_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_idx_club` (`club_name`,`account_id`),
    UNIQUE KEY `UK_bk3dht7s6afbb59lj5gicnbv9` (`account_id`),
    UNIQUE KEY `UK_lxmggy4ym4x9e9my2onkphw8f` (`teacher_id`),
    KEY `FK8ux2p46lys4gfrr2xa4f66kcs` (`account_id`),
    KEY `FKlnewf7jgb7j5l9xagnrxihdlv` (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_application_form
-- ========================================
CREATE TABLE `tbl_application_form` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `application_form_title` varchar(30) NOT NULL,
    `submission_duration` date NOT NULL,
    `club_id` bigint NOT NULL,
    `account_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK7why0e9pg51f6yb207hrg346g` (`club_id`),
    KEY `FK6wa0mpp1aq0owugion9qbebw6` (`account_id`),
    CONSTRAINT `FK6wa0mpp1aq0owugion9qbebw6` FOREIGN KEY (`account_id`) REFERENCES `tbl_user` (`id`),
    CONSTRAINT `FK7why0e9pg51f6yb207hrg346g` FOREIGN KEY (`club_id`) REFERENCES `tbl_club` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_announcement
-- ========================================
CREATE TABLE `tbl_announcement` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `assignment` varchar(150) DEFAULT NULL,
    `deadline` date NOT NULL,
    `introduction` varchar(200) NOT NULL,
    `phone_number` varchar(11) DEFAULT NULL,
    `status` enum('OPEN','CLOSED') NOT NULL,
    `talent_description` varchar(100) NOT NULL,
    `title` varchar(30) NOT NULL,
    `application_form_id` bigint DEFAULT NULL,
    `club_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK43ggb51iqpcqldmgue5sj7bm5` (`application_form_id`),
    KEY `FKeg0crto9xbessbnunym4chten` (`club_id`),
    CONSTRAINT `FK43ggb51iqpcqldmgue5sj7bm5` FOREIGN KEY (`application_form_id`) REFERENCES `tbl_application_form` (`id`),
    CONSTRAINT `FKeg0crto9xbessbnunym4chten` FOREIGN KEY (`club_id`) REFERENCES `tbl_club` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_announcement_major
-- ========================================
CREATE TABLE `tbl_announcement_major` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `major` enum('BE','FE','DEVOPS','IOS','ANDROID','FLUTTER','EMBEDDED','AI','DESIGN','SECURITY','GAME') NOT NULL,
    `announcement_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKgorcm2wwrdmv72t01g8bbohu0` (`announcement_id`),
    CONSTRAINT `FKgorcm2wwrdmv72t01g8bbohu0` FOREIGN KEY (`announcement_id`) REFERENCES `tbl_announcement` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_application_question
-- ========================================
CREATE TABLE `tbl_application_question` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `content` varchar(150) NOT NULL,
    `application_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK7phv6wx54b4une1hng798uqg1` (`application_id`),
    CONSTRAINT `FK7phv6wx54b4une1hng798uqg1` FOREIGN KEY (`application_id`) REFERENCES `tbl_application_form` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_application_major
-- ========================================
CREATE TABLE `tbl_application_major` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `major` enum('BE','FE','DEVOPS','IOS','ANDROID','FLUTTER','EMBEDDED','AI','DESIGN','SECURITY','GAME') NOT NULL,
    `application_form_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKq4h9d54x89mycf7v007i4cvaj` (`application_form_id`),
    CONSTRAINT `FKq4h9d54x89mycf7v007i4cvaj` FOREIGN KEY (`application_form_id`) REFERENCES `tbl_application_form` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_club_alarm
-- ========================================
CREATE TABLE `tbl_club_alarm` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6) NOT NULL,
    `updated_at` datetime(6) NOT NULL,
    `alarm_category` enum(
        'COMMON',
        'CLUB_ACCEPTED',
        'CLUB_MEMBER_APPLY'
    ) NOT NULL,
    `alarm_type` enum(
        'CREATE_CLUB_APPLY',
        'DISSOLVE_CLUB_APPLY',
        'CLUB_FINAL_ACCEPTED',
        'CLUB_FINAL_REJECTED',
        'CLUB_MEMBER_APPLY',
        'CLUB_CREATION_ACCEPTED',
        'CLUB_CREATION_REJECTED',
        'CLUB_DISSOLUTION_ACCEPTED',
        'CLUB_DISSOLUTION_REJECTED',
        'USER_JOINED_CLUB',
        'USER_REFUSED_CLUB',
        'USER_SUBMIT_APPLICATION',
        'USER_CANCEL_APPLICATION',
        'DELETE_CLUB_MEMBER',
        'INTERVIEW_SCHEDULE_CREATED',
        'INTERVIEW_SCHEDULE_CHANGED',
        'REQUEST_CLUB_CREATION',
        'REQUEST_CLUB_DISSOLUTION'
    ) NOT NULL,
    `content` varchar(300) NOT NULL,
    `is_executed` bit(1) NOT NULL,
    `title` varchar(50) NOT NULL,
    `club_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKnt9ihkqyi8cckvrl9rrkllh6w` (`club_id`),
    CONSTRAINT `FKnt9ihkqyi8cckvrl9rrkllh6w` FOREIGN KEY (`club_id`) REFERENCES `tbl_club` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_club_creation_application
-- ========================================
CREATE TABLE `tbl_club_creation_application` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6) NOT NULL,
    `updated_at` datetime(6) NOT NULL,
    `approved_at` datetime(6) DEFAULT NULL,
    `club_creation_form` varchar(255) NOT NULL,
    `club_image` varchar(200) NOT NULL,
    `club_name` varchar(20) NOT NULL,
    `introduction` varchar(500) NOT NULL,
    `last_submitted_at` datetime(6) DEFAULT NULL,
    `one_liner` varchar(30) NOT NULL,
    `rejected_at` datetime(6) DEFAULT NULL,
    `revision` int NOT NULL,
    `status` enum('DRAFT','SUBMITTED','UNDER_REVIEW','CHANGES_REQUESTED','APPROVED','REJECTED') NOT NULL,
    `submitted_at` datetime(6) DEFAULT NULL,
    `applicant_id` bigint NOT NULL,
    `teacher_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK5lejj2eaawgbou192aors5wah` (`applicant_id`),
    KEY `FKlvrcq5aowo6hqpxfm0gac5mkc` (`teacher_id`),
    CONSTRAINT `FK5lejj2eaawgbou192aors5wah` FOREIGN KEY (`applicant_id`) REFERENCES `tbl_user` (`id`),
    CONSTRAINT `FKlvrcq5aowo6hqpxfm0gac5mkc` FOREIGN KEY (`teacher_id`) REFERENCES `tbl_teacher` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_club_creation_application_link
-- ========================================
CREATE TABLE `tbl_club_creation_application_link` (
    `application_id` bigint NOT NULL,
    `link_value` varchar(255) NOT NULL,
    KEY `FK9q8a52v7g03vyjk6vmsoix6i4` (`application_id`),
    CONSTRAINT `FK9q8a52v7g03vyjk6vmsoix6i4` FOREIGN KEY (`application_id`) REFERENCES `tbl_club_creation_application` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_club_creation_application_major
-- ========================================
CREATE TABLE `tbl_club_creation_application_major` (
    `application_id` bigint NOT NULL,
    `major` enum('BE','FE','DEVOPS','IOS','ANDROID','FLUTTER','EMBEDDED','AI','DESIGN','SECURITY','GAME') NOT NULL,
    KEY `FK9cthkv3pdv197h6kqy8ryfqyf` (`application_id`),
    CONSTRAINT `FK9cthkv3pdv197h6kqy8ryfqyf` FOREIGN KEY (`application_id`) REFERENCES `tbl_club_creation_application` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_club_creation_review
-- ========================================
CREATE TABLE `tbl_club_creation_review` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6) NOT NULL,
    `updated_at` datetime(6) NOT NULL,
    `decision` enum('APPROVED','CHANGES_REQUESTED','REJECTED') NOT NULL,
    `feedback` varchar(1000) DEFAULT NULL,
    `reviewer_id` bigint NOT NULL,
    `reviewer_name` varchar(20) NOT NULL,
    `reviewer_type` enum('ADMIN','TEACHER') NOT NULL,
    `revision` int NOT NULL,
    `application_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_idx_club_creation_review` (`application_id`,`reviewer_type`,`reviewer_id`,`revision`),
    CONSTRAINT `FK9uc8offvqkhnl4gd0ga66ckn9` FOREIGN KEY (`application_id`) REFERENCES `tbl_club_creation_application` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_club_link
-- ========================================
CREATE TABLE `tbl_club_link` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `link` varchar(100) NOT NULL,
    `club_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKj837to5xechdknrmuq46j06ll` (`club_id`),
    CONSTRAINT `FKj837to5xechdknrmuq46j06ll` FOREIGN KEY (`club_id`) REFERENCES `tbl_club` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_club_major
-- ========================================
CREATE TABLE `tbl_club_major` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `major` enum('BE','FE','DEVOPS','IOS','ANDROID','FLUTTER','EMBEDDED','AI','DESIGN','SECURITY','GAME') NOT NULL,
    `club_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKjcflud7w8rtrnoxaocg0dv3ef` (`club_id`),
    CONSTRAINT `FKjcflud7w8rtrnoxaocg0dv3ef` FOREIGN KEY (`club_id`) REFERENCES `tbl_club` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_file
-- ========================================
CREATE TABLE `tbl_file` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `file_name` varchar(255) NOT NULL,
    `file_url` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_ngx7oesqusouskrfa479fadcv` (`file_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_result_duration
-- ========================================
CREATE TABLE `tbl_result_duration` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `alarm_status` enum('PENDING','REQUESTED','COMPLETED') DEFAULT NULL,
    `result_duration_time` datetime(6) DEFAULT NULL,
    `sms_status` enum('PENDING','REQUESTED','COMPLETED') DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_schedule
-- ========================================
CREATE TABLE `tbl_schedule` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `has_interview_schedule` bit(1) NOT NULL,
    `interview_schedule` date NOT NULL,
    `interview_time` time(6) NOT NULL,
    `place` varchar(20) NOT NULL,
    `account_id` bigint DEFAULT NULL,
    `club_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKb9sjhvvmsywel8kattyio4kjk` (`account_id`),
    KEY `FKcjunfa1m5wmjt8snc3b6urqmn` (`club_id`),
    CONSTRAINT `FKb9sjhvvmsywel8kattyio4kjk` FOREIGN KEY (`account_id`) REFERENCES `tbl_user` (`id`),
    CONSTRAINT `FKcjunfa1m5wmjt8snc3b6urqmn` FOREIGN KEY (`club_id`) REFERENCES `tbl_club` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_sms_history
-- ========================================
CREATE TABLE `tbl_sms_history` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6) NOT NULL,
    `updated_at` datetime(6) NOT NULL,
    `class_number` varchar(4) NOT NULL,
    `club_name` varchar(30) NOT NULL,
    `failure_reason` varchar(1000) DEFAULT NULL,
    `message_type` varchar(50) NOT NULL,
    `phone_number` varchar(30) NOT NULL,
    `reference_id` bigint NOT NULL,
    `reference_type` enum('INTERVIEW_SCHEDULE','CLUB_RESULT') NOT NULL,
    `scheduled_at` datetime(6) DEFAULT NULL,
    `sent_at` datetime(6) DEFAULT NULL,
    `status` enum('QUEUED','REQUESTED','SENT','FAILED') NOT NULL,
    `user_name` varchar(4) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_submission
-- ========================================
CREATE TABLE `tbl_submission` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `class_number` varchar(4) NOT NULL,
    `club_application_status` enum('SUBMITTED','WRITING','ACCEPTED','REJECTED') NOT NULL,
    `introduction` varchar(300) DEFAULT NULL,
    `is_interview_completed` bit(1) NOT NULL,
    `major` enum('BE','FE','DEVOPS','IOS','ANDROID','FLUTTER','EMBEDDED','AI','DESIGN','SECURITY','GAME') NOT NULL,
    `user_application_status` enum('SUBMITTED','WRITING','ACCEPTED','REJECTED') NOT NULL,
    `user_name` varchar(4) NOT NULL,
    `application_form_id` bigint NOT NULL,
    `account_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKh1acan08sgvvlbd67jd4r5k9t` (`application_form_id`),
    KEY `FKl8jskia8pd4xfvwsj6tw3f3v0` (`account_id`),
    CONSTRAINT `FKh1acan08sgvvlbd67jd4r5k9t` FOREIGN KEY (`application_form_id`) REFERENCES `tbl_application_form` (`id`),
    CONSTRAINT `FKl8jskia8pd4xfvwsj6tw3f3v0` FOREIGN KEY (`account_id`) REFERENCES `tbl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_application_answer
-- ========================================
CREATE TABLE `tbl_application_answer` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `content` varchar(200) DEFAULT NULL,
    `application_question_id` bigint NOT NULL,
    `submission_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKra4h78d7j1j7u1tf6l1odr8ec` (`application_question_id`),
    KEY `FK2yvf456u48uf7lg5ru7vh3fso` (`submission_id`),
    CONSTRAINT `FK2yvf456u48uf7lg5ru7vh3fso` FOREIGN KEY (`submission_id`) REFERENCES `tbl_submission` (`id`),
    CONSTRAINT `FKra4h78d7j1j7u1tf6l1odr8ec` FOREIGN KEY (`application_question_id`) REFERENCES `tbl_application_question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_user_alarm
-- ========================================
CREATE TABLE `tbl_user_alarm` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6) NOT NULL,
    `updated_at` datetime(6) NOT NULL,
    `alarm_category` enum('COMMON','CLUB_ACCEPTED','CLUB_MEMBER_APPLY') NOT NULL,
    `alarm_type` enum(
        'CREATE_CLUB_APPLY',
        'DISSOLVE_CLUB_APPLY',
        'CLUB_FINAL_ACCEPTED',
        'CLUB_FINAL_REJECTED',
        'CLUB_MEMBER_APPLY',
        'CLUB_CREATION_ACCEPTED',
        'CLUB_CREATION_REJECTED',
        'CLUB_DISSOLUTION_ACCEPTED',
        'CLUB_DISSOLUTION_REJECTED',
        'USER_JOINED_CLUB',
        'USER_REFUSED_CLUB',
        'USER_SUBMIT_APPLICATION',
        'USER_CANCEL_APPLICATION',
        'DELETE_CLUB_MEMBER',
        'INTERVIEW_SCHEDULE_CREATED',
        'INTERVIEW_SCHEDULE_CHANGED',
        'REQUEST_CLUB_CREATION',
        'REQUEST_CLUB_DISSOLUTION'
    ) NOT NULL,
    `content` varchar(300) NOT NULL,
    `is_executed` bit(1) NOT NULL,
    `title` varchar(50) NOT NULL,
    `club_id` bigint DEFAULT NULL,
    `user_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK423irefka6ov1fkacc8koa3qw` (`club_id`),
    KEY `FK5w8epuesbb8c6d34pty0opcdp` (`user_id`),
    CONSTRAINT `FK423irefka6ov1fkacc8koa3qw` FOREIGN KEY (`club_id`) REFERENCES `tbl_club` (`id`),
    CONSTRAINT `FK5w8epuesbb8c6d34pty0opcdp` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_user_application
-- ========================================
CREATE TABLE `tbl_user_application` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `is_approved` bit(1) NOT NULL,
    `club_id` bigint NOT NULL,
    `user_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK4nhcjeddj7goruref3vhdv7mm` (`club_id`),
    KEY `FKs1i3mjwf1i7haoljhec1l4em` (`user_id`),
    CONSTRAINT `FK4nhcjeddj7goruref3vhdv7mm` FOREIGN KEY (`club_id`) REFERENCES `tbl_club` (`id`),
    CONSTRAINT `FKs1i3mjwf1i7haoljhec1l4em` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_user_link
-- ========================================
CREATE TABLE `tbl_user_link` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `link` varchar(100) NOT NULL,
    `user_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK196tfkjn45tpi3lktsbl7ob1j` (`user_id`),
    CONSTRAINT `FK196tfkjn45tpi3lktsbl7ob1j` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Table: tbl_user_major
-- ========================================
CREATE TABLE `tbl_user_major` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `major` enum('BE','FE','DEVOPS','IOS','ANDROID','FLUTTER','EMBEDDED','AI','DESIGN','SECURITY','GAME','ETC') DEFAULT NULL,
    `user_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKk3jkqs669y9is95qa4fysd3c9` (`user_id`),
    CONSTRAINT `FKk3jkqs669y9is95qa4fysd3c9` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ========================================
-- Delayed FK for cyclic dependency: tbl_user <-> tbl_club
-- ========================================
ALTER TABLE `tbl_club`
    ADD CONSTRAINT `FK8ux2p46lys4gfrr2xa4f66kcs` FOREIGN KEY (`account_id`) REFERENCES `tbl_user` (`id`),
    ADD CONSTRAINT `FKlnewf7jgb7j5l9xagnrxihdlv` FOREIGN KEY (`teacher_id`) REFERENCES `tbl_teacher` (`id`);

ALTER TABLE `tbl_user`
    ADD CONSTRAINT `FK7vvnjl5gmaufavexhta4iokoc` FOREIGN KEY (`club_id`) REFERENCES `tbl_club` (`id`);
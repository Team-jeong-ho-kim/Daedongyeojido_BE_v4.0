-- tbl_announcement_major
ALTER TABLE tbl_announcement_major
MODIFY COLUMN major ENUM(
    'BE','FE','DEVOPS','IOS','ANDROID','FLUTTER','EMBEDDED','AI','DESIGN','SECURITY','GAME','ETC'
) NOT NULL;

-- tbl_application_major
ALTER TABLE tbl_application_major
MODIFY COLUMN major ENUM(
    'BE','FE','DEVOPS','IOS','ANDROID','FLUTTER','EMBEDDED','AI','DESIGN','SECURITY','GAME','ETC'
) NOT NULL;

-- tbl_club_creation_application_major
ALTER TABLE tbl_club_creation_application_major
MODIFY COLUMN major ENUM(
    'BE','FE','DEVOPS','IOS','ANDROID','FLUTTER','EMBEDDED','AI','DESIGN','SECURITY','GAME','ETC'
) NOT NULL;

-- tbl_club_major
ALTER TABLE tbl_club_major
MODIFY COLUMN major ENUM(
    'BE','FE','DEVOPS','IOS','ANDROID','FLUTTER','EMBEDDED','AI','DESIGN','SECURITY','GAME','ETC'
) NOT NULL;

-- tbl_submission
ALTER TABLE tbl_submission
MODIFY COLUMN major ENUM(
    'BE','FE','DEVOPS','IOS','ANDROID','FLUTTER','EMBEDDED','AI','DESIGN','SECURITY','GAME','ETC'
) NOT NULL;

-- tbl_user_major (여긴 기존에 ETC 있었지만 통일 차원에서 명시 추천)
ALTER TABLE tbl_user_major
MODIFY COLUMN major ENUM(
    'BE','FE','DEVOPS','IOS','ANDROID','FLUTTER','EMBEDDED','AI','DESIGN','SECURITY','GAME','ETC'
);
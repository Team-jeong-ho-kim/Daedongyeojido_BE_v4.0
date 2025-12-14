package team.jeonghokim.daedongyeojido.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // jwt
    EXPIRED_TOKEN(401, "만료된 토큰입니다."),
    INVALID_TOKEN(401, "검증 되지 않은 토큰 입니다."),
    REFRESH_TOKEN_NOT_FOUND(404, "일치 하는 RefreshToken이 존재 하지 않습니다."),
    INVALID_ROLE(401,"유효 하지 않은 역할입니다."),

    // user
    USER_NOT_FOUND(404, "해당 유저가 존재 하지 않습니다."),
    USER_MISMATCH(401, "유저가 일치 하지 않습니다."),
    PASSWORD_MISMATCH(401, "비밀 번호가 일치 하지 않습니다."),
    INVALID_USER(401, "유효 하지 않은 사용자입니다."),
    USER_APPLICATION_NOT_FOUND(404, "팀원 신청 기록을 찾을 수 없습니다."),

    // application
    APPLICATION_FORM_NOT_FOUND(404, "지원서 폼을 찾을 수 없습니다."),
    APPLICATION_FORM_ACCESS_DENIED(403, "해당 지원서 폼에 대한 수정 권한이 없습니다."),
    INVALID_APPLICATION_QUESTION(400, "유효하지 않은 질문입니다."),
    APPLICATION_NOT_FOUND(404, "지원서를 찾을 수 없습니다."),
    APPLICATION_ACCESS_DENIED(403, "지원서 관련 권한이 없습니다."),
    CANNOT_MODIFY_APPLICATION(400, "지원서를 수정할 수 없습니다."),
    CANNOT_DELETE_APPLICATION(400, "지원서를 삭제할 수 없습니다."),
    APPLICATION_NOT_SUBMITTED(400, "제출하지 않은 지원서입니다."),
    APPLICATION_NOT_ACCEPTED(400, "합격되지 않은 지원서입니다."),

    // club
    ALREADY_EXISTS_CLUB(409, "해당 동아리가 이미 존재합니다."),
    ALREADY_JOIN_CLUB(409, "이미 다른 동아리에 소속되어있습니다."),
    ALREADY_APPLY_CLUB(409, "이미 동아리 개설 신청을 하였습니다."),
    CLUB_NOT_FOUND(404, "동아리를 찾을 수 없습니다."),
    CLUB_NOT_OPEN(403, "개설되지 않은 동아리입니다."),
    USER_NOT_IN_CLUB(404, "소속된 동아리가 없습니다."),
    CLUB_MISMATCH(403, "동아리장이 속한 동아리와 동아리원이 속한 동아리가 일치하지 않습니다."),
    ALREADY_APPLICANT_IN_CLUB_EXCEPTION(409, "지원자가 이미 동아리에 소속되어있습니다."),
    CLUB_ACCESS_DENIED(403, "해당 동아리에 대한 권한이 없습니다."),

    // alarm
    ALARM_NOT_FOUND(404, "알람을 찾을 수 없습니다."),

    // announcement
    ANNOUNCEMENT_NOT_FOUND(404, "공고를 찾을 수 없습니다."),
    ANNOUNCEMENT_ACCESS_DENIED(403, "해당 공고에 대한 수정 권한이 없습니다."),

    // submission
    SUBMISSION_NOT_FOUND(404, "제출 내역을 찾을 수 없습니다."),

    // schedule
    ALREADY_INTERVIEW_SCHEDULE_EXISTS(409, "이미 지원자 면접 일정이 존재합니다."),
    INTERVIEW_SCHEDULE_NOT_FOUND(404, "면접 일정을 찾을 수 없습니다."),
    INTERVIEW_SCHEDULE_ACCESS_DENIED(403, "해당 면접 일정 관련 권한이 없습니다."),

    // resultDuration
    RESULT_DURATION_NOT_FOUND(404, "발표시간이 설정되지 않았습니다."),
    RESULT_DURATION_ALREADY_SET(409, "이미 발표시간이 설정되어있습니다."),
    RESULT_DURATION_ALREADY_EXECUTED(400, "이미 결과 처리가 완료되었습니다."),

    // s3
    IMAGE_NOT_FOUND(404, "이미지를 찾을 수 없음"),
    FAILED_UPLOAD(500, "업로드 실패"),
    FAILED_DELETE(500, "삭제 실패"),
    INVALID_EXTENSION(400, "유효하지 않은 파일 확장자입니다."),

    // feign
    FEIGN_BAD_REQUEST(400, "Feign Bad Request"),
    FEIGN_UNAUTHORIZED_EXCEPTION(401, "Feign Unauthorized Exception"),
    FEIGN_FORBIDDEN_EXCEPTION(403, "Feign Forbidden Exception"),

    // sms
    SIGNATURE_GENERATION_EXCEPTION(500, "HMAC 시그니처 생성에 실패했습니다."),

    // general
    BAD_REQUEST(400, "front fault"),
    INTERNAL_SERVER_ERROR(500, "server fault");

    private final int statusCode;
    private final String ErrorMessage;
}
package team.jeonghokim.daedongyeojido.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    //jwt
    EXPIRED_TOKEN(401, "만료된 토큰입니다."),
    INVALID_TOKEN(401, "검증 되지 않은 토큰 입니다."),
    REFRESH_TOKEN_NOT_FOUND(404, "일치 하는 RefreshToken이 존재 하지 않습니다."),
    INVALID_ROLE(401,"유효 하지 않은 역할입니다."),

    //user
    USER_NOT_FOUND(404, "해당 유저가 존재 하지 않습니다."),
    USER_MISMATCH(401, "유저가 일치 하지 않습니다."),
    PASSWORD_MISMATCH(401, "비밀 번호가 일치 하지 않습니다."),
    INVALID_USER(401, "유효 하지 않은 사용자입니다."),
    USER_APPLICATION_NOT_FOUND(404, "팀원 신청 기록을 찾을 수 없습니다."),

    //s3
    IMAGE_NOT_FOUND(404, "이미지를 찾을 수 없음"),
    FAILED_UPLOAD(500, "업로드 실패"),
    FAILED_DELETE(500, "삭제 실패"),
    INVALID_EXTENSION(400, "유효하지 않은 파일 확장자입니다."),

    //feign
    FEIGN_BAD_REQUEST(400, "Feign Bad Request"),
    FEIGN_UNAUTHORIZED_EXCEPTION(401, "Feign Unauthorized Exception"),
    FEIGN_FORBIDDEN_EXCEPTION(403, "Feign Forbidden Exception"),

    // club
    ALREADY_EXISTS_CLUB(409, "해당 동아리가 이미 존재합니다."),
    ALREADY_JOIN_CLUB(409, "이미 다른 동아리에 소속되어있습니다."),
    ALREADY_APPLY_CLUB(409, "이미 동아리 개설 신청을 하였습니다."),
    CLUB_NOT_FOUND(404, "동아리를 찾을 수 없습니다."),
    CLUB_NOT_OPEN(403, "개설되지 않은 동아리입니다."),
    USER_NOT_IN_CLUB(404, "소속된 동아리가 없습니다."),
    CLUB_MISMATCH(403, "동아리장이 속한 동아리와 동아리원이 속한 동아리가 일치하지 않습니다."),

    // alarm
    ALARM_NOT_FOUND(404, "알람을 찾을 수 없습니다."),

    // general
    BAD_REQUEST(400, "front fault"),
    INTERNAL_SERVER_ERROR(500, "server fault");

    private final int statusCode;
    private final String ErrorMessage;
}
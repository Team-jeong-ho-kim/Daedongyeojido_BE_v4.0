package team.jeonghokim.daedongyeojido.infrastructure.sms.auth;

import org.springframework.stereotype.Component;
import team.jeonghokim.daedongyeojido.infrastructure.sms.exception.SignatureGenerationException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class SignatureProvider {
    private static final String HMAC = "hmac-sha256";

    public String generateSignature(String secretKey, String data) {
        try {
            // HMAC-SHA256로 MAC(Message Authentication Code) 메시지 인증 코드 생성
            Mac mac = Mac.getInstance(HMAC);

            // Mac 객체에 SecretKey를 등록
            mac.init(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), HMAC));

            // 전달받은 data를 HMAC-SHA256 알고리즘으로 Mac 생성
            byte[] result = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            /*
                byte 형태로는 HTTP 헤더, JSON, URL에 담을 수 없기 때문에
                byte를 Base64 문자열로 변환해서 반환
             */
            return Base64.getEncoder().encodeToString(result);

        } catch (Exception e) {
            throw SignatureGenerationException.EXCEPTION;
        }
    }
}

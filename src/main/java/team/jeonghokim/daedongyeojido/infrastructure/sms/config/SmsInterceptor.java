package team.jeonghokim.daedongyeojido.infrastructure.sms.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.infrastructure.sms.auth.SignatureProvider;

import java.util.UUID;

@RequiredArgsConstructor
public class SmsInterceptor implements RequestInterceptor {

    private static final String HEADER_CONTENT_TYPE = "application/json";
    private static final String HEADER_AUTH = "Authorization";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String HMAC = "hmac-sha256";
    private static final String FORMAT = "%s apiKey=%s, date=%d, salt=%s, signature=%s";

    private final SignatureProvider signatureProvider;
    private final String accessKey;
    private final String secretKey;

    @Override
    public void apply(RequestTemplate template) {

        long timestamp = System.currentTimeMillis();
        String salt = UUID.randomUUID().toString();

        String signature = createSignature(timestamp, salt);

        template.header(HEADER_AUTH, authorizationHeader(timestamp, salt, signature));
        template.header(CONTENT_TYPE, HEADER_CONTENT_TYPE);
    }

    private String createSignature(long timestamp, String salt) {
        String data = timestamp + salt;

        return signatureProvider.generateSignature(secretKey, data);
    }

    private String authorizationHeader(long timestamp, String salt, String signature) {
        return String.format(
                FORMAT, HMAC, accessKey, timestamp, salt, signature
        );
    }
}

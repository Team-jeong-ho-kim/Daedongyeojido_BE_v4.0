package team.jeonghokim.daedongyeojido.infrastructure.sms.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.infrastructure.sms.auth.SignatureProvider;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RequiredArgsConstructor
public class SmsInterceptor implements RequestInterceptor {

    private static final String HEADER_CONTENT_TYPE = "application/json";
    private static final String HEADER_AUTH = "Authorization";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String HMAC = "hmac-sha256";
    private static final String FORMAT = "%s apiKey=%s, date=%s, salt=%s, signature=%s";
    private static final DateTimeFormatter ISO8601 =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
                    .withZone(ZoneOffset.UTC);

    private final SignatureProvider signatureProvider;
    private final String accessKey;
    private final String secretKey;

    @Override
    public void apply(RequestTemplate template) {

        String date = ISO8601.format(Instant.now());;
        String salt = UUID.randomUUID().toString();

        String signature = createSignature(date, salt);

        template.header(HEADER_AUTH, authorizationHeader(date, salt, signature));
        template.header(CONTENT_TYPE, HEADER_CONTENT_TYPE);
    }

    private String createSignature(String date, String salt) {
        String data = date + salt;

        return signatureProvider.generateSignature(secretKey, data);
    }

    private String authorizationHeader(String date, String salt, String signature) {
        return String.format(
                FORMAT, HMAC, accessKey, date, salt, signature
        );
    }
}

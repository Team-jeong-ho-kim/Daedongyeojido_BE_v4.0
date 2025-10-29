package team.jeonghokim.daedongyeojido.infrastructure.s3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import team.jeonghokim.daedongyeojido.infrastructure.s3.exception.FailedDeleteException;
import team.jeonghokim.daedongyeojido.infrastructure.s3.exception.FailedUploadException;
import team.jeonghokim.daedongyeojido.infrastructure.s3.exception.ImageNotFoundException;

import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final Set<String> FILE_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp");

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.url-prefix}")
    private String urlPrefix;

    public String upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        validate(fileName);

        String key = UUID.randomUUID() + "." + getExtension(fileName);

        try {
            PutObjectRequest object = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(object, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            return urlPrefix + key;
        } catch (Exception e) {
            throw FailedUploadException.EXCEPTION;
        }
    }

    public void delete(String s3Url) {
        try {
            URL url = new URI(s3Url).toURL();
            String decodedKey = URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8);
            String key = decodedKey.startsWith("/") ? decodedKey.substring(1) : decodedKey;

            DeleteObjectRequest object = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            s3Client.deleteObject(object);
        } catch (Exception e) {
            throw FailedDeleteException.EXCEPTION;
        }
    }

    private void validate(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw ImageNotFoundException.EXCEPTION;
        }

        String extension = getExtension(fileName);
        if (!FILE_EXTENSIONS.contains(extension)) {
            throw FailedUploadException.EXCEPTION;
        }
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }
}


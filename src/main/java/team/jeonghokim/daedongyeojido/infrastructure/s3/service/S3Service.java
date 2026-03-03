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
import team.jeonghokim.daedongyeojido.infrastructure.s3.exception.InvalidExtensionException;
import team.jeonghokim.daedongyeojido.infrastructure.s3.type.FileType;

import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private static final Set<String> IMAGE_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp");

    private static final Set<String> DOCUMENT_EXTENSIONS = Set.of("hwp", "pdf");

    private static final String DOWNLOAD_CONTENT_TYPE = "application/octet-stream";

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.url-prefix}")
    private String urlPrefix;

    public String upload(MultipartFile file, FileType fileType) {

        String fileName = file.getOriginalFilename();
        validate(fileName, fileType);

        String extension = getExtension(fileName);
        String key = UUID.randomUUID() + "." + extension;

        try {
            PutObjectRequest object = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(resolveContentType(file))
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(object,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

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

    public String update(String oldFile, MultipartFile file, FileType fileType) {

        if (file == null || file.isEmpty()) {
            return oldFile;
        }

        if (oldFile != null) {
            try {
                delete(oldFile);
            } catch (Exception e) {
                System.err.println("파일 삭제에 실패했습니다: " + e.getMessage());
            }
        }

        return upload(file, fileType);
    }

    private void validate(String fileName, FileType fileType) {

        if (fileName == null || fileName.isEmpty()) {
            throw ImageNotFoundException.EXCEPTION;
        }

        String extension = getExtension(fileName);

        if (fileType == FileType.IMAGE) {
            if (!IMAGE_EXTENSIONS.contains(extension)) {
                throw InvalidExtensionException.EXCEPTION;
            }
        }

        if (fileType == FileType.DOCUMENT) {
            if (!DOCUMENT_EXTENSIONS.contains(extension)) {
                throw InvalidExtensionException.EXCEPTION;
            }
        }
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    private String resolveContentType(MultipartFile file) {
        return file.getContentType() != null ? file.getContentType() : DOWNLOAD_CONTENT_TYPE;
    }
}


package team.jeonghokim.daedongyeojido.infrastructure.s3.service;

import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
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
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    private static final Set<String> IMAGE_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp");

    private static final Set<String> DOCUMENT_EXTENSIONS = Set.of("hwp", "pdf");

    private static final Set<String> IMAGE_MIME_TYPES = Set.of("image/jpeg", "image/png", "image/webp");

    private static final Set<String> DOCUMENT_MIME_TYPES = Set.of(
            "application/pdf",
            "application/x-hwp",
            "application/x-hwp-v5"
    );

    private static final String DOWNLOAD_CONTENT_TYPE = "application/octet-stream";

    private static final Tika TIKA = new Tika();

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.url-prefix}")
    private String urlPrefix;

    public String upload(MultipartFile file, FileType fileType) {
        validateFile(file, fileType);

        String fileName = file.getOriginalFilename();
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

    private void validateFile(MultipartFile file, FileType fileType) {
        if (file == null || file.isEmpty()) {
            throw ImageNotFoundException.EXCEPTION;
        }

        validateFileSize(file);

        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            throw ImageNotFoundException.EXCEPTION;
        }

        String extension = getExtension(fileName);
        validateExtension(extension, fileType);
        validateMimeType(file.getContentType(), fileType);
        validateDetectedMimeType(file, fileType);
    }

    private void validateFileSize(MultipartFile file) {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw FailedUploadException.EXCEPTION;
        }
    }

    private void validateExtension(String extension, FileType fileType) {
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

    private void validateMimeType(String contentType, FileType fileType) {
        Set<String> allowedMimeTypes = getAllowedMimeTypes(fileType);
        String normalizedMimeType = normalizeMimeType(contentType);

        if (!allowedMimeTypes.contains(normalizedMimeType)) {
            throw InvalidExtensionException.EXCEPTION;
        }
    }

    private void validateDetectedMimeType(MultipartFile file, FileType fileType) {
        Set<String> allowedMimeTypes = getAllowedMimeTypes(fileType);

        try {
            String detectedMimeType = normalizeMimeType(TIKA.detect(file.getInputStream()));
            if (!allowedMimeTypes.contains(detectedMimeType)) {
                throw InvalidExtensionException.EXCEPTION;
            }
        } catch (InvalidExtensionException e) {
            throw e;
        } catch (Exception e) {
            throw FailedUploadException.EXCEPTION;
        }
    }

    private Set<String> getAllowedMimeTypes(FileType fileType) {
        if (fileType == FileType.IMAGE) {
            return IMAGE_MIME_TYPES;
        }

        return DOCUMENT_MIME_TYPES;
    }

    private String normalizeMimeType(String mimeType) {
        if (mimeType == null || mimeType.isBlank()) {
            return "";
        }

        return mimeType.split(";")[0].trim().toLowerCase(Locale.ROOT);
    }

    private String getExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex < 0 || lastDotIndex == fileName.length() - 1) {
            throw InvalidExtensionException.EXCEPTION;
        }
        return fileName.substring(lastDotIndex + 1).toLowerCase(Locale.ROOT);
    }

    private String resolveContentType(MultipartFile file) {
        return file.getContentType() != null ? file.getContentType() : DOWNLOAD_CONTENT_TYPE;
    }
}

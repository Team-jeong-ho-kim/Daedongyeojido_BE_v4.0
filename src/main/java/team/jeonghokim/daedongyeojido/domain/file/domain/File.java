package team.jeonghokim.daedongyeojido.domain.file.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity
@Table(name = "tbl_file")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends BaseIdEntity {

    @Column(nullable = false, unique = true)
    private String fileName;

    @Column(nullable = false)
    private String fileUrl;

    @Builder
    public File(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}

package team.jeonghokim.daedongyeojido.domain.clubcreationform.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubCreationForm extends BaseIdEntity {

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileUrl;

    @Builder
    public ClubCreationForm(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}

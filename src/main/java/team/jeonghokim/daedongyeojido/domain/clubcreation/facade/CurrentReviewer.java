package team.jeonghokim.daedongyeojido.domain.clubcreation.facade;

import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ReviewerType;

public record CurrentReviewer(
        ReviewerType reviewerType,
        Long reviewerId,
        String reviewerName
) {
}

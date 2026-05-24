package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.RejectedOnePagerCommentRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.SubmitOnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.OnePagerFormDetailResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.RejectedOnePagerCommentResponse;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryOnePagerFormDetailService {
    private final OnePagerRepository onePagerRepository;
    private final SubmitOnePagerRepository submitOnePagerRepository;
    private final RejectedOnePagerCommentRepository rejectedOnePagerCommentRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public OnePagerFormDetailResponse execute(Long formId) {
        OnePager form = onePagerRepository.findById(formId)
                .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);

        Club club = userFacade.getCurrentUser().getClub();
        if (club == null) {
            return OnePagerFormDetailResponse.ofFormOnly(form);
        }

        SubmitOnePager submission = submitOnePagerRepository
                .findByClubAndFormOnePager(club, form)
                .orElse(null);
        if (submission == null) {
            return OnePagerFormDetailResponse.ofFormOnly(form);
        }

        List<RejectedOnePagerCommentResponse> comments = rejectedOnePagerCommentRepository
                .findAllByOnePagerId(submission.getId()).stream()
                .map(RejectedOnePagerCommentResponse::from)
                .toList();

        return OnePagerFormDetailResponse.of(form, submission, comments);
    }
}

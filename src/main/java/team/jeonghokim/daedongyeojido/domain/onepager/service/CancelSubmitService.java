package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.RejectedOnePagerComment;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.RejectedOnePagerCommentRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.SubmitOnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.SubmitOnePagerAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.SubmitOnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CancelSubmitService {
    private final SubmitOnePagerRepository submitOnePagerRepository;
    private final RejectedOnePagerCommentRepository rejectedOnePagerCommentRepository;
    private final FileRepository fileRepository;
    private final UserFacade userFacade;
    private final S3Service s3Service;

    @Transactional
    public void execute(Long submissionId) {
        SubmitOnePager submission = submitOnePagerRepository.findById(submissionId)
                .orElseThrow(() -> SubmitOnePagerNotFoundException.EXCEPTION);

        User user = userFacade.getCurrentUser();
        if (!submission.getClub().getId().equals(user.getClub().getId())) {
            throw SubmitOnePagerAccessDeniedException.EXCEPTION;
        }

        // 제출 취소 = 제출 row·댓글·파일을 완전 삭제 (FK 순서: 댓글 → 제출 → 파일)
        List<RejectedOnePagerComment> comments = rejectedOnePagerCommentRepository.findByOnePager(submission);
        rejectedOnePagerCommentRepository.deleteAll(comments);
        rejectedOnePagerCommentRepository.flush();

        String submitFileUrl = submission.getSubmitFileUrl();
        File submitFile = submission.getSubmitFile();

        submitOnePagerRepository.delete(submission);
        submitOnePagerRepository.flush();

        if (submitFile != null) {
            fileRepository.delete(submitFile);
            fileRepository.flush();
        }

        if (submitFileUrl != null) {
            s3Service.delete(submitFileUrl);
        }
    }
}

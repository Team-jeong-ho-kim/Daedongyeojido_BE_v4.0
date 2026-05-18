package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.RejectedOnePagerComment;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.RejectedOnePagerCommentRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.SubmitOnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerInvalidException;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.QueryListSubmitOnePagerResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.SubmitCommentResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.SubmitOnePagerResponse;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;
import team.jeonghokim.daedongyeojido.domain.teacher.facade.TeacherFacade;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuerySubmitOnePagerService {
    private final OnePagerRepository onePagerRepository;
    private final SubmitOnePagerRepository submitOnePagerRepository;
    private final RejectedOnePagerCommentRepository rejectedOnePagerCommentRepository;
    private final TeacherFacade teacherFacade;

    @Transactional(readOnly = true)
    public QueryListSubmitOnePagerResponse execute(Long onePagerId) {
        OnePager onePager = onePagerRepository.findById(onePagerId)
            .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);

        String fileUrl = onePager.getFormFile().getFileUrl();

        Teacher currentTeacher = teacherFacade.getCurrentTeacher();
        if (!onePager.getTeacher().getId().equals(currentTeacher.getId())) {
            return QueryListSubmitOnePagerResponse.of(onePager, fileUrl, List.of());
        }

        List<SubmitOnePager> submitOnePagers = submitOnePagerRepository.findByFormOnePager(onePager);

        List<RejectedOnePagerComment> allComments = rejectedOnePagerCommentRepository.findByOnePagerIn(submitOnePagers);

        Map<Long, List<SubmitCommentResponse>> commentsBySubmitId = allComments.stream()
            .collect(Collectors.groupingBy(
                comment -> comment.getOnePager().getId(),
                Collectors.mapping(SubmitCommentResponse::from, Collectors.toList())
            ));

        List<SubmitOnePagerResponse> responses = submitOnePagers.stream()
            .filter(submit -> submit.getSubmitFile() != null)
            .map(submit -> SubmitOnePagerResponse.of(
                submit, commentsBySubmitId.getOrDefault(submit.getId(), List.of())
            ))
            .toList();


        return QueryListSubmitOnePagerResponse.of(onePager, fileUrl, responses);
    }
}

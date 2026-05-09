package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.RejectedOnePagerComment;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.RejectedOnePagerCommentRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.SubmitOnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.QueryListSubmitOnePagerResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.SubmitCommentResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.SubmitOnePagerResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuerySubmitOnePagerService {
    private final OnePagerRepository onePagerRepository;
    private final SubmitOnePagerRepository submitOnePagerRepository;
    private final RejectedOnePagerCommentRepository rejectedOnePagerCommentRepository;

    public QueryListSubmitOnePagerResponse execute(Long onePagerId) {
        OnePager onePager = onePagerRepository.findById(onePagerId)
            .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);

        List<SubmitOnePager> submitOnePagers = submitOnePagerRepository.findByFormOnePager(onePager);

        List<RejectedOnePagerComment> allComments = rejectedOnePagerCommentRepository.findByOnePagerIn(submitOnePagers);

        Map<Long, List<SubmitCommentResponse>> commentsBySubmitId = allComments.stream()
            .collect(Collectors.groupingBy(
                comment -> comment.getOnePager().getId(),
                Collectors.mapping(SubmitCommentResponse::from, Collectors.toList())
            ));

        List<SubmitOnePagerResponse> responses = submitOnePagers.stream()
            .map(submit -> SubmitOnePagerResponse.of(
                submit, commentsBySubmitId.getOrDefault(submit.getId(), List.of())
            ))
            .toList();

        String fileUrl = onePager.getFormFile() != null ? onePager.getFormFile().getFileUrl() : onePager.getFormUrl();

        return QueryListSubmitOnePagerResponse.of(onePager, fileUrl, responses);
    }
}

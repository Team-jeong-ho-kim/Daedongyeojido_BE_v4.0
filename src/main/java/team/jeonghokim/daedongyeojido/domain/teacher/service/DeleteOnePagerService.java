package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.RejectedOnePagerCommentRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.SubmitOnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteOnePagerService {
    private final OnePagerRepository onePagerRepository;
    private final SubmitOnePagerRepository submitOnePagerRepository;
    private final RejectedOnePagerCommentRepository rejectedOnePagerCommentRepository;
    private final FileRepository fileRepository;
    private final S3Service s3Service;

    @Transactional
    public void execute(Long onePagerId){
        OnePager onePager = onePagerRepository.findById(onePagerId)
            .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);

        // S3 객체는 DB 정리가 모두 성공한 뒤 마지막에 삭제 (롤백 시 유실 방지)
        List<String> s3UrlsToDelete = new ArrayList<>();

        // FK 순서대로 자식부터 정리: 댓글 → 제출 → 제출 파일 → 폼 → 폼 파일
        List<SubmitOnePager> submissions = submitOnePagerRepository.findByFormOnePager(onePager);

        // 1) 제출들의 댓글 삭제
        for (SubmitOnePager submission : submissions) {
            rejectedOnePagerCommentRepository.deleteAll(
                rejectedOnePagerCommentRepository.findByOnePager(submission));
        }
        rejectedOnePagerCommentRepository.flush();

        // 2) 제출 row 삭제 (제출 파일 참조·URL은 먼저 수집)
        List<File> submitFiles = new ArrayList<>();
        for (SubmitOnePager submission : submissions) {
            File submitFile = submission.getSubmitFile();
            if (submitFile != null) {
                submitFiles.add(submitFile);
                if (submission.getSubmitFileUrl() != null) {
                    s3UrlsToDelete.add(submission.getSubmitFileUrl());
                }
            }
            submitOnePagerRepository.delete(submission);
        }
        submitOnePagerRepository.flush();

        // 3) 제출 파일 row 삭제
        for (File submitFile : submitFiles) {
            fileRepository.delete(submitFile);
        }
        fileRepository.flush();

        // 4) 폼 삭제 (OnePager 먼저 → 폼 파일 나중: tbl_onepager.file_id FK 때문)
        File formFile = onePager.getFormFile();
        String formFileUrl = onePager.getFormFileUrl();

        onePagerRepository.delete(onePager);
        onePagerRepository.flush();

        if (formFile != null) {
            fileRepository.delete(formFile);
            fileRepository.flush();
            if (formFileUrl != null) {
                s3UrlsToDelete.add(formFileUrl);
            }
        }

        // 5) DB 정리 성공 후 S3 객체 삭제
        s3UrlsToDelete.forEach(s3Service::delete);
    }
}

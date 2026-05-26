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

        List<String> s3UrlsToDelete = new ArrayList<>();

        List<SubmitOnePager> submissions = submitOnePagerRepository.findByFormOnePager(onePager);

        for (SubmitOnePager submission : submissions) {
            rejectedOnePagerCommentRepository.deleteAll(
                rejectedOnePagerCommentRepository.findByOnePager(submission));
        }
        rejectedOnePagerCommentRepository.flush();

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

        for (File submitFile : submitFiles) {
            fileRepository.delete(submitFile);
        }
        fileRepository.flush();

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

        s3UrlsToDelete.forEach(s3Service::delete);
    }
}

package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.RejectedOnePagerComment;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.RejectedOnePagerCommentRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.SubmitOnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.InvalidUserException;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;
import team.jeonghokim.daedongyeojido.infrastructure.s3.type.FileType;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmitOnePagerFileUploadService {
    private final SubmitOnePagerRepository submitOnePagerRepository;
    private final RejectedOnePagerCommentRepository rejectedOnePagerCommentRepository;
    private final FileRepository fileRepository;
    private final UserFacade userFacade;
    private final S3Service s3Service;

    @Transactional
    public void execute(MultipartFile submitFile, OnePager onePager) {
        User submitUser = userFacade.getCurrentUser();

        Club club = submitUser.getClub();
        if (club == null) {
            throw InvalidUserException.EXCEPTION;
        }

        // 동아리당 폼 1제출 보장: 기존 제출이 있으면 삭제 후 재제출
        String oldSubmitFileUrl = deleteExistingSubmission(club, onePager);

        String fileName = submitFile.getOriginalFilename();
        String fileUrl = s3Service.upload(submitFile, FileType.DOCUMENT);

        try {
            File file = File.builder()
                .fileUrl(fileUrl)
                .fileName(fileName)
                .build();

            fileRepository.save(file);

            SubmitOnePager submitOnePager = SubmitOnePager.builder()
                .club(club)
                .onePagerState(OnePagerState.SUBMITTED)
                .submitFile(file)
                .submitDate(LocalDate.now())
                .formOnePager(onePager)
                .build();

            submitOnePagerRepository.save(submitOnePager);
        } catch (Exception e) {
            s3Service.delete(fileUrl);
            throw e;
        }

        // 새 제출 저장 성공 후 기존 제출 파일의 S3 객체 정리 (롤백 시 유실 방지)
        if (oldSubmitFileUrl != null) {
            s3Service.delete(oldSubmitFileUrl);
        }
    }

    private String deleteExistingSubmission(Club club, OnePager onePager) {
        SubmitOnePager existing = submitOnePagerRepository
            .findByClubAndFormOnePager(club, onePager)
            .orElse(null);
        if (existing == null) {
            return null;
        }

        // FK 순서: 댓글 → 제출 → 파일
        List<RejectedOnePagerComment> comments = rejectedOnePagerCommentRepository.findByOnePager(existing);
        rejectedOnePagerCommentRepository.deleteAll(comments);
        rejectedOnePagerCommentRepository.flush();

        String oldSubmitFileUrl = existing.getSubmitFileUrl();
        File oldSubmitFile = existing.getSubmitFile();

        submitOnePagerRepository.delete(existing);
        submitOnePagerRepository.flush();

        if (oldSubmitFile != null) {
            fileRepository.delete(oldSubmitFile);
            fileRepository.flush();
        }

        return oldSubmitFileUrl;
    }
}

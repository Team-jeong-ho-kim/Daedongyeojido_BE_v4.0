package team.jeonghokim.daedongyeojido.domain.announcement.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.request.AnnouncementRequest;
import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response.QueryAnnouncementDetailResponse;
import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.response.QueryAnnouncementListResponse;
import team.jeonghokim.daedongyeojido.domain.announcement.service.CreateAnnouncementService;
import team.jeonghokim.daedongyeojido.domain.announcement.service.DeleteAnnouncementService;
import team.jeonghokim.daedongyeojido.domain.announcement.service.QueryAnnouncementDetailService;
import team.jeonghokim.daedongyeojido.domain.announcement.service.QueryAnnouncementListService;
import team.jeonghokim.daedongyeojido.domain.announcement.service.UpdateAnnouncementService;

@RestController
@RequestMapping("/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    private final CreateAnnouncementService createAnnouncementService;
    private final QueryAnnouncementListService queryAnnouncementListService;
    private final QueryAnnouncementDetailService queryAnnouncementDetailService;
    private final UpdateAnnouncementService updateAnnouncementService;
    private final DeleteAnnouncementService deleteAnnouncementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAnnouncement(@RequestBody @Valid AnnouncementRequest request) {
        createAnnouncementService.execute(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public QueryAnnouncementListResponse queryAnnouncementList() {
        return queryAnnouncementListService.execute();
    }

    @GetMapping("/{announcement-id}")
    @ResponseStatus(HttpStatus.OK)
    public QueryAnnouncementDetailResponse queryAnnouncementDetail(@PathVariable("announcement-id") Long announcementId) {
        return queryAnnouncementDetailService.execute(announcementId);
    }

    @PatchMapping("/{announcement-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAnnouncement(@PathVariable("announcement-id") Long announcementId, @RequestBody @Valid AnnouncementRequest request) {
        updateAnnouncementService.execute(announcementId, request);
    }

    @DeleteMapping("/{announcement-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnnouncement(@PathVariable("announcement-id") Long announcementId) {
        deleteAnnouncementService.execute(announcementId);
    }
}

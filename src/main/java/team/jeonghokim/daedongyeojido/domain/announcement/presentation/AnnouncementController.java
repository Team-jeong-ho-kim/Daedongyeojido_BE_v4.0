package team.jeonghokim.daedongyeojido.domain.announcement.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.request.CreateAnnouncementRequest;
import team.jeonghokim.daedongyeojido.domain.announcement.service.CreateAnnouncementService;

@RestController
@RequestMapping("/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    private final CreateAnnouncementService createAnnouncementService;

    @PostMapping
    public void createAnnouncement(@RequestBody @Valid CreateAnnouncementRequest request) {
        createAnnouncementService.execute(request);
    }
}

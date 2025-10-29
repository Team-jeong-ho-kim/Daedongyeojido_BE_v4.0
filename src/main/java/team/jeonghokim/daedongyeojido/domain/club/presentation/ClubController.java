package team.jeonghokim.daedongyeojido.domain.club.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.CreateClubRequest;
import team.jeonghokim.daedongyeojido.domain.club.service.CreateClubService;

@RestController
@RequestMapping("/club")
@RequiredArgsConstructor
public class ClubController {

    private final CreateClubService createClubService;

    @PostMapping("/create/apply")
    @ResponseStatus(HttpStatus.CREATED)
    public void createClub(@RequestBody @Valid CreateClubRequest request) {
        createClubService.execute(request);
    }
}

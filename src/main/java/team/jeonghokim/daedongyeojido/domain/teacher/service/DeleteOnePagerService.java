package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.file.domain.repository.FileRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;

@Service
@RequiredArgsConstructor
public class DeleteOnePagerService {
    private final OnePagerRepository onePagerRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void execute(Long onePagerId){
        OnePager onePager = onePagerRepository.findById(onePagerId)
            .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);

        if(onePager.getFormFile() != null) {
            fileRepository.delete(onePager.getFormFile());
        }

        onePagerRepository.delete(onePager);
    }
}

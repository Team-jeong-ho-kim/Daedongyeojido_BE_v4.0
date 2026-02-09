package team.jeonghokim.daedongyeojido.domain.club.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import static team.jeonghokim.daedongyeojido.global.cache.CacheNames.CLUB_DETAIL;

@Component
public class ClubCacheEvictor {

    @CacheEvict(value = CLUB_DETAIL, key = "#clubId")
    public void evictClubDetail(Long clubId) {
    }
}

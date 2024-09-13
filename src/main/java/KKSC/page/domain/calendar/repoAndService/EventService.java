package KKSC.page.domain.calendar.repoAndService;

import org.springframework.security.access.prepost.PreAuthorize;

import KKSC.page.domain.calendar.dto.EventRequest;
import KKSC.page.domain.calendar.dto.EventResponse;
import KKSC.page.domain.calendar.dto.EventResponseInterface;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface EventService {
    
    // 일정 목록 조회
    @PreAuthorize("hasRole('permission_level1')")
    List<EventResponseInterface> getEventList(int year, int month);

    // 일정 생성
    @PreAuthorize("hasRole('permission_level0')")
    Long createEvent(EventRequest eventRequest);

    // 일정 수정
    @PreAuthorize("hasRole('permission_level0')")
    EventResponse updateEvent(Long evetnId, EventRequest eventRequest);

    // 일정 삭제
    @PreAuthorize("hasRole('permission_level0')")
    void deleteEvent(Long evetnId);


    // 일정 참가
    @PreAuthorize("hasRole('permission_level1')")
    Long joinEvent(Long evetnId, String userName);

    // 일정 참가 취소
    @PreAuthorize("hasRole('permission_level1')")
    String cancelEvent (Long evetnId, String userName);

}

package KKSC.page.domain.calendar.repoAndService.impl;

import KKSC.page.domain.calendar.dto.EventRequest;
import KKSC.page.domain.calendar.dto.EventResponse;
import KKSC.page.domain.calendar.dto.EventResponseInterface;
import KKSC.page.domain.calendar.entity.Event;
import KKSC.page.domain.calendar.exception.EventException;
import KKSC.page.domain.calendar.repoAndService.EventRepository;
import KKSC.page.domain.calendar.repoAndService.EventService;
import KKSC.page.domain.calendar.repoAndService.ParticipantRepository;
import KKSC.page.domain.member.exception.MemberException;
import KKSC.page.domain.member.repository.MemberRepository;
import KKSC.page.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    
    private final EventRepository eventRepository;

    private final ParticipantRepository participantRepository;

    private final MemberRepository memberRepository;

    /*  만들어야하는 쿼리문 목록
    getEventList : year , month 파라미터 적용 ( eventRepositoryCustom )
    deleteEvent : 참여자 정보 삭제 ( ParticipantRepositoryCustom )
    getPersonalEventList : 사용자에 대한 일정 참가 목록 ( ParticipantRepositoryCustom )
    */

    // 일정 목록 조회
    @Override
    public List<EventResponseInterface> getEventList(int year, int month) {
        
        return eventRepository.EventList(year, month);
    };
    
    // 일정 생성
    // 파라미터 : 스케쥴 생성 값 
    @Override
    public Long createEvent(EventRequest eventRequest) {
        Event event = eventRequest.toEntity();
        return eventRepository.save(event).getId();
    };
    
    // 일정 삭제
    // 파라미터 : 스케줄 아이디
    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.findById(eventId)
        .orElseThrow(() -> new EventException(ErrorCode.NOT_FOUND_SCHEDULE));
        // 참여자 정보 전부 삭제 
        // 일정 삭제
        eventRepository.deleteById(eventId);
    };
    
    // 일정 수정
    // 파라미터 : 스케줄 수정 값
    @Override
    public EventResponse updateEvent(Long eventId, EventRequest eventRequest) {

        eventRepository.findById(eventId)
         .orElseThrow(() -> new EventException(ErrorCode.NOT_FOUND_SCHEDULE));

        Event event = new Event();

        event.update(eventId, eventRequest);

        eventRepository.save(event);
        
        return EventResponse.from(event);
    };
    
    // 일정 참가
    // 파라미터 : 스케줄 아이디, 유저아이디
    @Override
    public Long joinEvent(Long eventId, String userName) {
        eventRepository.findById(eventId)
                        .orElseThrow(() -> new EventException(ErrorCode.NOT_FOUND_SCHEDULE));
        Long userId = memberRepository.findByEmail(userName)
        .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_MEMBER)).getId();

        participantRepository.save(null);
        return null;
    };
    
    // 일정 참가 취소
    // 파라미터 : 스케줄 아이디, 유저아이디
    @Override
    public String cancelEvent(Long eventId,String userName) {
        eventRepository.findById(eventId)
                        .orElseThrow(() -> new EventException(ErrorCode.NOT_FOUND_SCHEDULE));
        Long userId = memberRepository.findByEmail(userName)
        .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_MEMBER)).getId();
        
        eventRepository.delete(null);
        return null;
    };
}

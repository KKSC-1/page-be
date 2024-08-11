package KKSC.page.domain.calendar.controller;

import KKSC.page.domain.calendar.dto.EventRequest;
import KKSC.page.domain.calendar.dto.EventResponse;
import KKSC.page.domain.calendar.exception.CalendarException;
import KKSC.page.domain.calendar.repoAndService.EventService;
import KKSC.page.global.auth.service.JwtService;
import KKSC.page.global.exception.ErrorCode;
import KKSC.page.global.exception.dto.ResponseVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class EventController {

    private final EventService eventService;
    private final JwtService jwtService;

    // 캘린더 일정 추가
    @PostMapping("/")
    public ResponseVO<Long> addEvent(@RequestBody EventRequest eventRequest) {
        return new ResponseVO<>(eventService.createSchedule(eventRequest));
    }

    // 캘린더 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseVO<String> deleteEvent(@PathVariable Long id) {
        eventService.deleteSchedule(id);

        return new ResponseVO<>("Delete Success");
    }

    // 캘린더 일정 수정
    @PutMapping("/{id}")
    public ResponseVO<EventResponse> updateEvent(@PathVariable Long id, @RequestBody EventRequest eventRequest) {
        return new ResponseVO<>(eventService.updateSchedule(id, eventRequest));
    }

    // 캘린더 일정 참가
    @PostMapping("/{id}/join")
    public ResponseVO<Long> joinEvent(HttpServletRequest request, @PathVariable Long id) {
        String username = jwtService.extractUsername(request)
                .orElseThrow(() -> new CalendarException(ErrorCode.ACCESS_DENIED));

        return new ResponseVO<>(eventService.joinSchedule(id, username));
    }

    // 캘린더 일정 참가 취소
    @DeleteMapping("/cancel/{id}")
    public ResponseVO<String> cancelEvent(@PathVariable Long id) {
        eventService.cancelSchedule(id);

        return new ResponseVO<>("Cancel Success");
    }

    // 캘린더 일정 목록 조회
    @GetMapping("/")
    public ResponseVO<List<EventResponse>> getEventList(@RequestParam Long year, @RequestParam Long month) {
        return new ResponseVO<>(eventService.getScheduleList(year, month));
    }
}

package KKSC.page.domain.calendar.controller;

import KKSC.page.domain.calendar.dto.EventRequest;
import KKSC.page.domain.calendar.dto.EventResponse;
import KKSC.page.domain.calendar.dto.EventResponseInterface;
import KKSC.page.domain.calendar.exception.EventException;
import KKSC.page.domain.calendar.repoAndService.EventService;
import KKSC.page.global.auth.service.JwtService;
import KKSC.page.global.exception.ErrorCode;
import KKSC.page.global.exception.dto.ErrorResponseVO;
import KKSC.page.global.exception.dto.ResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Event", description = "일정관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
@Slf4j
public class EventController {

    private final EventService eventService;
    private final JwtService jwtService;

    // 캘린더 일정 추가
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API 정상 작동",content = @Content(schema = @Schema(implementation = ResponseVO.class))),
            @ApiResponse(responseCode = "???", description = "서버 에러",content = @Content(schema = @Schema(implementation = ErrorResponseVO.class)))}
    )
    @Operation(summary = "일정 추가", description = " 일정 추가 API ")
    @PostMapping("/")
    public ResponseVO<Long> addEvent(@RequestBody EventRequest eventRequest) {
        return new ResponseVO<>(eventService.createEvent(eventRequest));
    }

    // 캘린더 일정 수정
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "API 정상 작동",content = @Content(schema = @Schema(implementation = ResponseVO.class))),
        @ApiResponse(responseCode = "???", description = "서버 에러",content = @Content(schema = @Schema(implementation = ErrorResponseVO.class)))}
    )
    @Parameter(name = "eventId", description = "일정 번호")
    @Operation(summary = " 일정 수정 ", description = " 일정 수정 API ")
    @PutMapping("/{eventId}")
    public ResponseVO<EventResponse> updateEvent(@PathVariable Long eventId, @RequestBody EventRequest eventRequest) {
        return new ResponseVO<>(eventService.updateEvent(eventId, eventRequest));
    }

    // 캘린더 일정 삭제
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "API 정상 작동",content = @Content(schema = @Schema(implementation = ResponseVO.class))),
        @ApiResponse(responseCode = "???", description = "서버 에러",content = @Content(schema = @Schema(implementation = ErrorResponseVO.class)))}
    )
    @Parameter(name = "eventId", description = "일정 번호")
    @Operation(summary = " 일정 삭제 ", description = " 일정 삭제 API ")
    @DeleteMapping("/{eventId}")
    public ResponseVO<String> deleteEvent(@PathVariable Long eventId) {
        return new ResponseVO<>("Delete success");
    }

    // 캘린더 일정 목록 조회 완성
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "API 정상 작동",content = @Content(schema = @Schema(implementation = ResponseVO.class))),
        @ApiResponse(responseCode = "???", description = "서버 에러",content = @Content(schema = @Schema(implementation = ErrorResponseVO.class)))}
    )
    @Parameters({
        @Parameter(name = "year", description = "일정 검색 년도"),
        @Parameter(name = "month", description = "일정 검색 월"),
    })
    @Operation(summary = " 일정 조회 ", description = " 일정 조회 API ")
    @GetMapping("")
    public ResponseVO<List<EventResponseInterface>> getEventList(@RequestParam int year, @RequestParam int month) {
        return new ResponseVO<>(eventService.getEventList(year, month));
    }

    // 캘린더 일정 참가
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "API 정상 작동",content = @Content(schema = @Schema(implementation = ResponseVO.class))),
        @ApiResponse(responseCode = "???", description = "서버 에러",content = @Content(schema = @Schema(implementation = ErrorResponseVO.class)))}
    )
    @Parameter(name = "eventId", description = "일정 번호")
    @Operation(summary = " 일정 참가 ", description = " 일정 참가 API ")
    @PostMapping("/join/{eventId}")
    public ResponseVO<Long> joinEvent(HttpServletRequest request, @PathVariable Long eventId) {
        String username = jwtService.extractUsername(request)
                .orElseThrow(() -> new EventException(ErrorCode.ACCESS_DENIED));
    
        return new ResponseVO<>(eventService.joinEvent(eventId, username));
    }
    
    // 캘린더 일정 참가 취소
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "API 정상 작동",content = @Content(schema = @Schema(implementation = ResponseVO.class))),
        @ApiResponse(responseCode = "???", description = "서버 에러",content = @Content(schema = @Schema(implementation = ErrorResponseVO.class)))}
    )
    @Parameter(name = "eventId", description = "일정 번호")
    @Operation(summary = " 일정 참가 취소 ", description = " 일정 참가 취소 API ")
    @DeleteMapping("/cancel/{eventId}")
    public ResponseVO<String> cancelEvent(HttpServletRequest request, @PathVariable Long eventId) {
        String username = jwtService.extractUsername(request)
        .orElseThrow(() -> new EventException(ErrorCode.ACCESS_DENIED));

        return new ResponseVO<>(eventService.cancelEvent(eventId, username));
    }
}

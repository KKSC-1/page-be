package KKSC.page.domain.calendar.entity;

import KKSC.page.domain.calendar.dto.EventRequest;
import KKSC.page.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    // 참가자 리스트
    @OneToMany(mappedBy = "event")
    private List<Participant> participants;

    // 일정 제목
    private String title;

    // 일정 카테고리(분류)
    @Enumerated(value = EnumType.STRING)
    private Category category;

    // 참가 인원 추가
    private Long maxParticipant;

    // 일정 시작 날짜
    private LocalDateTime startDate;

    // 일정 종료 날짜
    private LocalDateTime endDate;

    // 일정 세부사항
    private String detail;

    // 일정 수정(시작 날짜, 종료 날짜, 세부 사항 수정)
    public void update(Long eventId, EventRequest eventRequest) {
        this.id = eventId;
        this.title = eventRequest.title();
        this.detail = eventRequest.detail();
        this.category = eventRequest.category();
        this.startDate = eventRequest.startDate();
        this.endDate = eventRequest.endDate();
        this.maxParticipant = eventRequest.maxParticipant();
    }
}

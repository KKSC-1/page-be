package KKSC.page.domain.calendar.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

import KKSC.page.domain.calendar.entity.Category;

public interface EventResponseInterface {
    
    Long getId();
    String getTitle();
    String getDetail();

    Category getCategory();
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime getStartDate();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime getEndDate();
    
    Long getMaxParticipant();

}

package KKSC.page.domain.calendar.exception;

import KKSC.page.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EventException extends RuntimeException {

    private final ErrorCode errorCode;
}

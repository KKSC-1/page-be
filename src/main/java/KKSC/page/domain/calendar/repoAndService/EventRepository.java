package KKSC.page.domain.calendar.repoAndService;

import KKSC.page.domain.calendar.dto.EventResponseInterface;
import KKSC.page.domain.calendar.entity.Event;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {
    @Query(value = "SELECT "+
        "event_id as id,"+
        "title as title,"+
        "detail as detail,"+
        "category as category,"+
        "start_date as startDate,"+
        "end_date as endDate ,"+
        "max_participant as maxParticipant "+
        "FROM event "+
        "WHERE YEAR(end_date) = :Year "+
        "AND MONTH(end_date) = :Month "
        , nativeQuery = true)
    List<EventResponseInterface> EventList(@Param("Year") int Year,@Param("Month") int Month);
}

package KKSC.page.domain.calendar.repoAndService;

import org.springframework.data.jpa.repository.JpaRepository;

import KKSC.page.domain.calendar.entity.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    
}

package eventmanagementinternyte.eventmanagementsystembackend.repository;

import eventmanagementinternyte.eventmanagementsystembackend.entity.Meetup;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MeetupRepository extends JpaRepository<Meetup, Long> {
}

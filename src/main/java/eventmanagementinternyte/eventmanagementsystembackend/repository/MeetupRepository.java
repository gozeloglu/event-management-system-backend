package eventmanagementinternyte.eventmanagementsystembackend.repository;

import eventmanagementinternyte.eventmanagementsystembackend.entity.Meetup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeetupRepository extends JpaRepository<Meetup, Long> {

    Optional<Meetup> findByMeetupID(String meetupID);

    void deleteByMeetupID(String meetupID);
}

package eventmanagementinternyte.eventmanagementsystembackend.repository;

import eventmanagementinternyte.eventmanagementsystembackend.entity.Meetup;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface MeetupRepository extends JpaRepository<Meetup, Long> {

    // Optional<Meetup> findById(Long id);

    //@Transactional
    //void deleteById(String meetupID);
}

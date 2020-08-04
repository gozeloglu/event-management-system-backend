package eventmanagementinternyte.eventmanagementsystembackend.repository;

import eventmanagementinternyte.eventmanagementsystembackend.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

   /* @Query("select p.meetups from Participant p where p.username = :username")
    Optional<Meetup> getMeetupListByUsername(String username);
*/
   Optional<Participant> findByUsername(String username);

   @Transactional
   void deleteByUsername(String username);
}
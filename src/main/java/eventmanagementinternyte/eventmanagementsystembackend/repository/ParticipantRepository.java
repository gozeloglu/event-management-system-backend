package eventmanagementinternyte.eventmanagementsystembackend.repository;

import eventmanagementinternyte.eventmanagementsystembackend.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

   Optional<Participant> findByUsername(String username);
   Optional<Participant> findByIdentityNumber(String identityNumber);

   @Transactional
   void deleteByUsername(String username);
}
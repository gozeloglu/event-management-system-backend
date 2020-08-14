package eventmanagementinternyte.eventmanagementsystembackend.repository;

import eventmanagementinternyte.eventmanagementsystembackend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Question findTopByOrderByIdDesc();
}

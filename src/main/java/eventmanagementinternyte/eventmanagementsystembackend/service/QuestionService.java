package eventmanagementinternyte.eventmanagementsystembackend.service;

import eventmanagementinternyte.eventmanagementsystembackend.entity.Meetup;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Question;
import eventmanagementinternyte.eventmanagementsystembackend.mapper.QuestionMapper;
import eventmanagementinternyte.eventmanagementsystembackend.repository.MeetupRepository;
import eventmanagementinternyte.eventmanagementsystembackend.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final MeetupRepository meetupRepository;

    /**
     * List all questions in given meetup
     *
     * @param meetupID represents the id of the meetup
     * @return List of Question
     */
    public List<Question> listAllQuestions(String meetupID) {
        return questionRepository.findAll();
    }

    /**
     * Saves the question on the database
     *
     * @param meetupID is the id of the meetup that we want to save
     * @param question is a Question object which includes question and isAnswered
     * @return String message after operations
     * If question is saved successfully, it returns success message
     * If question is not saved successfully, it returns fail message
     */
    public String saveNewQuestion(String meetupID, Question question) {
        Optional<Meetup> optionalMeetup = meetupRepository.findByMeetupID(meetupID);

        if (optionalMeetup.isPresent()) {
            Meetup meetup = optionalMeetup.get();
            Set<Question> questionSet = meetup.getQuestionSet();
            meetup.setQuestionSet(questionSet);
            meetupRepository.save(meetup);
            questionRepository.save(question);
            return "Question is saved";
        } else {
            return "Question is not saved";
        }
    }

    /**
     * Fetches the question which is given as a parameter
     *
     * @param questionId is the question questionId for fetching question
     * @return Question object
     * @throws EntityNotFoundException if question could not find
     */
    public Question getQuestion(Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            return question;
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Deletes the question if it is answered
     *
     * @param questionID is the question's id that we want to delete
     * @return String message after deleted
     * If question is saved successfully, it returns success message
     * If question is not found, it returns question could not found
     */
    @Transactional
    public String deleteQuestion(Long questionID) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionID);

        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            questionRepository.deleteById(questionID);
            return "Question is deleted";
        } else {
            return "Question could not find";

        }
    }
}

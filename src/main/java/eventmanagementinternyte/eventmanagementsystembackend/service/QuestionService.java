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
import java.util.ArrayList;
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

        List<Question> questionList = questionRepository.findAll();
        List<Question> questions = new ArrayList<>();
        for (Question question : questionList) {
            if (question.getMeetupID().equals(meetupID)) {
                questions.add(question);
            }
        }
        return questions;
        /*Optional<Meetup> optionalMeetup = meetupRepository.findByMeetupID(meetupID);

        if (optionalMeetup.isPresent()) {
            Meetup meetup = optionalMeetup.get();
            // Set<Question> questionSet = meetup.getQuestions();
            System.out.println("--------------");
            System.out.println(questionSet.size());
            List<Question> questionList = new ArrayList<>(questionSet);
            return questionList;
        } else {
            throw new EntityNotFoundException();
        }*/
    }

    /**
     * Saves the question on the database
     *
     * @param meetupID is the id of the meetup that we want to save
     * @param id is a Question object which includes question and isAnswered
     * @return String message after operations
     * If question is saved successfully, it returns success message
     * If question is not saved successfully, it returns fail message
     */
    public String saveNewQuestion(Long meetupID, Long id) {
        // questionRepository.save(question);
        Optional<Meetup> optionalMeetup = meetupRepository.findById(meetupID);
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalMeetup.isPresent()) {
            Meetup meetup = optionalMeetup.get();
            Question question = optionalQuestion.get();

            questionRepository.save(question);
           /* Meetup meetup = optionalMeetup.get();
            Question question = optionalQuestion.get();
            System.out.println(question.getId());
            System.out.println(question.getAskedQuestion());
            System.out.println(meetup.getMeetupID());

            Set<Question> questionSet = meetup.getQuestions();
            meetup.getQuestions().add(question);
            System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-");
            System.out.println(meetup.getQuestions().size());
            System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-");
            meetup.setQuestions(questionSet);
            System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-");
            System.out.println(meetup.getQuestions().size());
            System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-");

            question.getMeetups().add(meetup);
            questionRepository.save(question);
            meetupRepository.save(meetup);*/
            //int before = questionSet.size();

            //questionSet.add(question);

            /*if (before == questionSet.size()) {
                return "You already asked that question";
            }
            meetup.setQuestionSet(questionSet);
            meetupRepository.save(meetup);
            Optional<Meetup> optionalMeetup2 = meetupRepository.findByMeetupID(meetupID);

            Meetup meetup1 = optionalMeetup2.get();

            Set<Question> questionSet1 = meetup1.getQuestionSet();*/

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

    public Long saveQuestion(Question question) {
        questionRepository.save(question);
        Question question1 = questionRepository.findTopByOrderByIdDesc();
        System.out.println("ID:--------\t" + question1.getId() + "\t" + question.getId());
        return question1.getId();
       // return "Question is saved";
    }
}

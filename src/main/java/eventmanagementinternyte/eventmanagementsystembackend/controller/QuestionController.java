package eventmanagementinternyte.eventmanagementsystembackend.controller;

import eventmanagementinternyte.eventmanagementsystembackend.entity.Question;
import eventmanagementinternyte.eventmanagementsystembackend.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/question")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping(value = "/all-questions/{meetupID}")
    public List<Question> listAllQuestions(@PathVariable Long meetupID) {
        return questionService.listAllQuestions(meetupID);
    }

    @PostMapping(value = "/add-new-question/{meetupID}")
    public String saveNewQuestion(@PathVariable Long meetupID, @Valid @RequestBody Question question) {
        Long id = questionService.saveQuestion(question);
        return questionService.saveNewQuestion(meetupID, id);
    }

    @GetMapping(value = "/{questionID}")
    public Question getQuestion(@PathVariable Long questionID) {
        return questionService.getQuestion(questionID);
    }

    @DeleteMapping(value = "/delete-question/{questionID}")
    public String deleteQuestion(@PathVariable Long questionID) {
        return questionService.deleteQuestion(questionID);
    }
}

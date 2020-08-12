package eventmanagementinternyte.eventmanagementsystembackend.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
@Builder
public class QuestionDTO {

    @Size(min = 1, max = 240, message = "Your question should contain max 240 characters")
    public final String askedQuestion;

    public final int isAnswered;
}

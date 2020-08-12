package eventmanagementinternyte.eventmanagementsystembackend.mapper;

import eventmanagementinternyte.eventmanagementsystembackend.dto.QuestionDTO;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionDTO mapToDto(Question question);

    Question mapToEntity(QuestionDTO questionDTO);

    List<QuestionDTO> mapToDto(List<Question> questionList);

    List<Question> mapToEntity(List<QuestionDTO> questionDTOList);
}

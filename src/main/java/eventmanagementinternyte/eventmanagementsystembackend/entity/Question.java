package eventmanagementinternyte.eventmanagementsystembackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "idgen", sequenceName = "QUESTION_SEQ")
@AllArgsConstructor
@NoArgsConstructor
public class Question extends BaseEntity {

    @Column(name = "ASKED_QUESTION")
    private String askedQuestion;

    @Column(name = "MEETUP_ID")
    private Long meetupID;

    @Column(name = "IS_ANSWERED")
    private int isAnswered;
}

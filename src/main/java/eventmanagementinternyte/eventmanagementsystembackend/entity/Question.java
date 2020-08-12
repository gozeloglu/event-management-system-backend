package eventmanagementinternyte.eventmanagementsystembackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question extends BaseEntity {

    @Column(name = "ASKED_QUESTION")
    private String askedQuestion;

    @Column(name = "IS_ANSWERED")
    private int isAnswered;

    @ManyToOne
    @JoinColumn(name = "question_meetup",
            insertable = false, updatable = false)
    private Meetup meetup;
}

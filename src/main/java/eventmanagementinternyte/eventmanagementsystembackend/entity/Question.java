package eventmanagementinternyte.eventmanagementsystembackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

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
    private String meetupID;

    @Column(name = "IS_ANSWERED")
    private int isAnswered;

   /* @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "question_meetup",
            joinColumns = {@JoinColumn(name = "fk_question")},
            inverseJoinColumns = {@JoinColumn(name = "fk_meetup2")}
    )
    private Set<Meetup> meetups = new HashSet<>();

    public Set<Meetup> getMeetups() {
        return this.meetups;
    }*/
}

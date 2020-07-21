package eventmanagementinternyte.eventmanagementsystembackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.FutureOrPresent;
import java.sql.Time;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event extends BaseEntity{

    @Column(name = "EVENT_NAME")
    private String eventName;

    @Column(name = "DETAILS")
    private String details;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PLACE_NAME")
    private String placeName;

    @Column(name = "START_DATE")
    @FutureOrPresent
    private LocalDate startDate;

    @Column(name = "END_DATE")
    @FutureOrPresent
    private LocalDate endDate;

    @Column(name = "START_TIME")
    @FutureOrPresent
    private Time startTime;

    @Column(name = "END_TIME")
    @FutureOrPresent
    private Time endTime;

    @Column(name = "QUOTA")
    private int quota;

    @Column(name = "REGISTERED_USER_COUNT")
    private int registeredUserCount;
}

package eventmanagementinternyte.eventmanagementsystembackend.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Builder
public class EventDTO {

    @Size(max = 255, message = "Event name cannot be larger than 255 characters!")
    public final String eventName;

    @Size(max = 1024, message = "Event details cannot be larger than 1024 characters!")
    public final String details;

    @Size(max = 255, message = "Event address cannot be larger than 255 characters!")
    public final String address;

    @Size(max = 255, message = "Place name cannot be larger than 255 characters!")
    public final String placeName;

    @FutureOrPresent(message = "Event start date cannot be in the past!")
    public final LocalDate startDate;

    @FutureOrPresent(message = "Event finish date cannot be in the past!")
    public final LocalDate endDate;

    public final int quota;

    public final int registeredUserCount;

}
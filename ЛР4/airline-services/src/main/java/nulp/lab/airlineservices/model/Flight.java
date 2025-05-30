package nulp.lab.airlineservices.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Flight {
    private Long id;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
}
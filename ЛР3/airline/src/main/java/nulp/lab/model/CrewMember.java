package nulp.lab.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CrewMember {
    private Long id;
    private String name;
    private Role role;

    public enum Role { PILOT, NAVIGATOR, RADIO_OPERATOR, STEWARDESS }
}

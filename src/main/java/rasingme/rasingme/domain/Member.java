package rasingme.rasingme.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "\\d{8}", message = "Birth date must be 8 digits")
    private String birthDate;

    private String email;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Characters character;

    //사용자 이름 추가
    private String name;



}
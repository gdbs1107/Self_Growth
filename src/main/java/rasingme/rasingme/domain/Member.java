package rasingme.rasingme.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE) // Date 타입으로 변경됨
    private Date birthDate;

    private String email;
    @Column(unique = true, nullable = false) // username을 고유하게 설정
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Characters characterType;

    //사용자 이름 추가
    private String name;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diary> diaries = new ArrayList<>();

}
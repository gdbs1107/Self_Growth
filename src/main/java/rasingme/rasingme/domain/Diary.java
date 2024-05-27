package rasingme.rasingme.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private Weather weather;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false) // 외래 키 설정
    private Member member;
}
package kr.co.inhatc.lms.study;

import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.lecture.Lecture;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@Setter @EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Study {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Lecture lecture;

    @ManyToOne
    private Account createdBy;

    @Column(nullable = false)
    private  String title;

    private LocalDateTime createdDateTime;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;
    @Lob
    private String post;
}

package kr.co.inhatc.lms.register;

import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.lecture.Lecture;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Register {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Lecture lecture;

    @ManyToOne
    private Account account;

    private LocalDateTime registeredAt;

    private boolean accepted;

}

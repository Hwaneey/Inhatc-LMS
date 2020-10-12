package kr.co.inhatc.lms.attend;

import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.study.Study;
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
public class Attend {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Study study;

    @ManyToOne
    private Account account;

    private int residenceTime;

    private LocalDateTime attendTime;

    private boolean attend;

    public void checkAttend(Attend attend){
        attend.setAttendTime(LocalDateTime.now());
        attend.setAttend(true);
    }
}

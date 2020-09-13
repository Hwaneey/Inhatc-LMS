package kr.co.inhatc.lms.study;

import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.lecture.Lecture;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter
@Setter
public class Study {

    @Id
    @GeneratedValue
    private String id;

    @ManyToOne
    private Lecture lecture;

    @ManyToOne
    private Account account;

    private  String title;

    @Lob
    private String post;
}

package kr.co.inhatc.lms.lecture;

import kr.co.inhatc.lms.account.Account;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lecture {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    private Set<Account> lecturer = new HashSet<>();

    @ManyToMany
    private Set<Account> student = new HashSet<>();

    @Column(unique = true)
    private String path;

    private String subjectTitle;


    public void addLecturer(Account account) {
        this.lecturer.add(account);
    }


}

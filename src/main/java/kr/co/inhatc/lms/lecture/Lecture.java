package kr.co.inhatc.lms.lecture;

import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.account.UserAccount;
import lombok.*;

import javax.persistence.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor @EqualsAndHashCode(of = "id")
public class Lecture {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    private Set<Account> lecturer = new HashSet<>();

    @ManyToMany
    private Set<Account> student = new HashSet<>();

    private LocalDateTime createdDateTime;

    @Column(unique = true)
    private String path;

    private String subjectTitle;

    private int studentCount;


    public void addLecturer(Account account) {
        this.lecturer.add(account);
    }

    public void addStudent(Account account) {
        this.getStudent().add(account);
        this.studentCount++;
    }

    public void oddStudent(Account account) {
        this.getStudent().remove(account);
        this.studentCount--;
    }

    public String getEncodedPath() {
        return URLEncoder.encode(this.path, StandardCharsets.UTF_8);
    }

    public boolean isLecturer(UserAccount userAccount) {
        return this.lecturer.contains(userAccount.getAccount());
    }

    public boolean isStudent(UserAccount userAccount){
        return this.student.contains(userAccount.getAccount());
    }

    public boolean isEnable(UserAccount userAccount) {
        Account account = userAccount.getAccount();
        return !this.student.contains(account) && !this.lecturer.contains(account);

    }
}

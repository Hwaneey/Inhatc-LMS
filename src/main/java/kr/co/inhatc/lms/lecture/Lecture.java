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

    public void addLecturer(Account account) {
        this.lecturer.add(account);
    }

    public String getEncodedPath() {
        return URLEncoder.encode(this.path, StandardCharsets.UTF_8);
    }
    public boolean isLecturer(UserAccount userAccount) {
        return this.lecturer.contains(userAccount.getAccount());
    }

    public boolean isManagedBy(Account account) {
        return this.getLecturer().contains(account);
    }

}

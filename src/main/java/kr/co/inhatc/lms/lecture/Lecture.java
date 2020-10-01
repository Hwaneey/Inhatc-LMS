package kr.co.inhatc.lms.lecture;

import kr.co.inhatc.lms.Register.Register;
import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.account.UserAccount;
import lombok.*;

import javax.persistence.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor @EqualsAndHashCode(of = "id")
public class Lecture {

    @Id @GeneratedValue
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

    @OneToMany(mappedBy = "lecture")
    private List<Register> registers;

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

    public void addRegister(Register register) {
        this.registers.add(register);
        register.setLecture(this);
    }

    public void removeEnrollment(Register register) {
        this.registers.remove(register);
        register.setLecture(null);
    }
    public boolean isAttended(UserAccount userAccount) {
        Account account = userAccount.getAccount();
        for (Register e : this.registers) {
            if (e.getAccount().equals(account) && e.isAttended()) {
                return true;
            }
        }
        return false;
    }
    private boolean isAlreadyRegister(UserAccount userAccount) {
        Account account = userAccount.getAccount();
        for (Register e : this.registers) {
            if (e.getAccount().equals(account)) {
                return true;
            }
        }
        return false;
    }

    public boolean isRegisterFor(UserAccount userAccount) {
        return !isAlreadyRegister(userAccount);
    }

    public boolean isDisRegisterFor(UserAccount userAccount) {
        return isAlreadyRegister(userAccount);
    }

    public void accept(Register register) {
        register.setAccepted(true);
    }

    public void reject(Register register) {
        register.setAccepted(false);
    }

    public boolean canAccept(Register register) {
        return this.registers.contains(register) && !register.isAccepted();
    }

    public boolean canReject(Register register) {
        return this.registers.contains(register) && register.isAccepted();
    }
}

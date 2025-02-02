package kr.co.inhatc.lms.comment;

import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.study.Study;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne @JoinColumn(name="study_id")
    private Study study;

    @ManyToOne @JoinColumn(name="account_id")
    private Account writer;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    private int level;

    private boolean isLive;

    public boolean isWriter(Account account) {
        return this.writer.equals(account);
    }

}

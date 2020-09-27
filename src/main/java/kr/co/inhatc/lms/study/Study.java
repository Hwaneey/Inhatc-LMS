package kr.co.inhatc.lms.study;

import kr.co.inhatc.lms.Comment.Comment;
import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.lecture.Lecture;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name ="account_id")
    private Account writer;

    @Column(nullable = false)
    private  String title;

    private LocalDateTime createdDateTime;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Lob
    private String post;

    //Comment의 ProblemId와 연결을 의미함
    @OneToMany
    private List<Comment> comments = new ArrayList<Comment>();

    public boolean isWriter (Account account) {
        return this.writer.equals(account);
    }

    public void addComment (Comment comment) {
        this.comments.add(comment);
    }

    public void removeComment (Comment comment) {
        this.comments.remove(comment);
    }

}

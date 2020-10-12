package kr.co.inhatc.lms.study;

import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.attend.Attend;
import kr.co.inhatc.lms.comment.Comment;
import kr.co.inhatc.lms.lecture.Lecture;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
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

    @ManyToOne @JoinColumn(name ="account_lecturer")
    private Account writer;

    @Column(nullable = false)
    private  String title;

    @Column(updatable = false)
    private LocalDateTime createdDateTime;

    @Column(updatable = false)
    private LocalDateTime startDateTime;

    @Column(updatable = false)
    private LocalDateTime endDateTime;

    @Column(nullable = false) @Min(5)
    private int classTime;

    @Lob
    private String post;

    @OneToMany
    private List<Attend> attendList = new ArrayList<>();

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

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

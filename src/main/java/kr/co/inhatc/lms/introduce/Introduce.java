//package kr.co.inhatc.lms.introduce;
//
//import kr.co.inhatc.lms.account.Account;
//import kr.co.inhatc.lms.lecture.Lecture;
//import lombok.*;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Setter @NoArgsConstructor @AllArgsConstructor
//@EqualsAndHashCode(of = "id") @Builder
//public class Introduce {
//
//    @Id
//    @GeneratedValue
//    private Long id;
//
//    @OneToMany
//    @JoinColumn(name="lecture_id")
//    private Lecture lecture;
//
//    @ManyToOne @JoinColumn(name="account_id")
//    private Account introducer;
//
//    @Column(updatable = false)
//    private LocalDateTime createdDateTime;
//
//    private String title;
//
//    @Lob
//    private String introducePost;
//}

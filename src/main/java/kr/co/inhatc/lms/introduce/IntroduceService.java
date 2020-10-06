//package kr.co.inhatc.lms.introduce;
//
//import kr.co.inhatc.lms.account.Account;
//import kr.co.inhatc.lms.lecture.Lecture;
//import kr.co.inhatc.lms.lecture.LectureRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.time.LocalDateTime;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class IntroduceService {
//
//    private final LectureRepository lectureRepository;
//
//    private final IntroduceRepository introduceRepository;
//
//    public Lecture getLecture(Long lectureId) {
//        return lectureRepository.findById(lectureId).get();
//    }
//
//    public Introduce createIntroduce(IntroduceForm introduceForm, Account account) {
//
////        Lecture lecture = getLecture(introduceForm.getLectureId());
//
//        Introduce introduce = Introduce.builder()
//                .title(introduceForm.getTitle())
//                .introducePost(introduceForm.getIntroducePost())
//                .createdDateTime(LocalDateTime.now())
////                .lecture(lecture)
//                .introducer(account)
//                .build();
////        lecture.createIntroduce(introduce);
//        return introduceRepository.save(introduce);
//    }
//}

package kr.co.inhatc.lms.lecture;

import kr.co.inhatc.lms.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;

    public Lecture createLecture(Lecture lecture , Account account) {
        Lecture createLecture = lectureRepository.save(lecture);
        createLecture.addLecturer(account);
        return createLecture;
    }

}

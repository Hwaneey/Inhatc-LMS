package kr.co.inhatc.lms.study;

import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.lecture.Lecture;
import kr.co.inhatc.lms.lecture.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final LectureRepository lectureRepository;


    public Study createStudy(Study study, Lecture lecture, Account account) {
        study.setCreatedBy(account);
        study.setLecture(lecture);
        study.setCreatedDateTime(LocalDateTime.now());
        return studyRepository.save(study);
    }

    public Lecture getStudy(String path) {
        Lecture lecture = this.lectureRepository.findByPath(path);
        if (lecture == null) {
            throw new IllegalArgumentException(path + "에 해당하는 스터디가 없습니다.");
        }
        return lecture;
    }
}

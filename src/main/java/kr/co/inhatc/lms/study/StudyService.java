package kr.co.inhatc.lms.study;

import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.lecture.Lecture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;


    public Study createStudy(Study study, Lecture lecture, Account account) {
        study.setCreatedBy(account);
        study.setLecture(lecture);
        return studyRepository.save(study);
    }
}

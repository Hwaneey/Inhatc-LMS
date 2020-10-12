package kr.co.inhatc.lms.study;

import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.attend.Attend;
import kr.co.inhatc.lms.lecture.Lecture;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final ModelMapper modelMapper;

    public Study createStudy(Study study, Lecture lecture, Account account) {
        Attend attend = new Attend();
        study.setCreatedBy(account);
        study.setLecture(lecture);
        study.setCreatedDateTime(LocalDateTime.now());
        return studyRepository.save(study);
    }

    public void editStudy(Study study, StudyForm studyForm) {
        modelMapper.map(studyForm, study);
    }

    public void deleteStudy(Study study) {
        studyRepository.delete(study);
    }

}

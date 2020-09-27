package kr.co.inhatc.lms.study;

import kr.co.inhatc.lms.Comment.CommentService;
import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.lecture.Lecture;
import kr.co.inhatc.lms.lecture.LectureRepository;
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
    private final LectureRepository lectureRepository;
    private final ModelMapper modelMapper;
    private final CommentService commentService;
    public Study createStudy(Study study, Lecture lecture, Account account) {
        study.setCreatedBy(account);
        study.setLecture(lecture);
        study.setCreatedDateTime(LocalDateTime.now());
        return studyRepository.save(study);
    }

    public Lecture getStudy(String path) {
        Lecture lecture = this.lectureRepository.findByPath(path);
        if (lecture == null) {
            throw new IllegalArgumentException(path + "에 해당하는 강의가 없습니다.");
        }
        return lecture;
    }

    public void editStudy(Study study, StudyDto studyDto) {
        modelMapper.map(studyDto, study);
    }

    public void deleteStudy(Study study) {
        studyRepository.delete(study);
    }
}

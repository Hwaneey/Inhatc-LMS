package kr.co.inhatc.lms.lecture;

import kr.co.inhatc.lms.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;

    public Lecture createLecture(Lecture lecture , Account account) {
        Lecture createLecture = lectureRepository.save(lecture);
        createLecture.addLecturer(account);
        createLecture.setCreatedDateTime(LocalDateTime.now());
        return createLecture;
    }

    public Lecture getStudyToUpdateStatus(String path) {
        Lecture lecture = lectureRepository.findStudyWithManagersByPath(path);
        checkIfExistingStudy(path, lecture);
        return lecture;
    }

    private void checkIfExistingStudy(String path, Lecture lecture) {
        if (lecture == null) {
            throw new IllegalArgumentException(path + "에 해당하는 강의가 없습니다.");
        }
    }

    public Lecture getStudy(String path) {
        Lecture lecture = this.lectureRepository.findByPath(path);
        if (lecture == null) {
            throw new IllegalArgumentException(path + "에 해당하는 강의가 없습니다.");
        }
        return lecture;
    }
}

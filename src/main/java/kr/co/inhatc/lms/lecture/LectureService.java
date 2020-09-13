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


    public Lecture getStudyToUpdateStatus(Account account, String path) {
        Lecture lecture = lectureRepository.findStudyWithManagersByPath(path);
        checkIfExistingStudy(path, lecture);
        checkIfManager(account, lecture);
        return lecture;
    }
    private void checkIfExistingStudy(String path, Lecture lecture) {
        if (lecture == null) {
            throw new IllegalArgumentException(path + "에 해당하는 스터디가 없습니다.");
        }
    }

    private void checkIfManager(Account account, Lecture lecture) {
//        if (!lecture.isManagedBy(account)) {
//            throw new AccessDeniedException("해당 기능을 사용할 수 없습니다.");
//        }
    }

    public Lecture getStudy(String path) {
        Lecture lecture = this.lectureRepository.findByPath(path);
        if (lecture == null) {
            throw new IllegalArgumentException(path + "에 해당하는 스터디가 없습니다.");
        }
        return lecture;
    }
}

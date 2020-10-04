package kr.co.inhatc.lms.lecture;

import kr.co.inhatc.lms.Register.Register;
import kr.co.inhatc.lms.Register.RegisterRepository;
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
    private final RegisterRepository registerRepository;

    public Lecture createLecture(Lecture lecture , Account account) {
        Lecture createLecture = lectureRepository.save(lecture);
        createLecture.addLecturer(account);
        createLecture.setCreatedDateTime(LocalDateTime.now());
        return createLecture;
    }

    public Lecture getStudyToUpdateStatus(String path) {
        Lecture lecture = lectureRepository.findStudyWithLecturerByPath(path);
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

    public Lecture getLectureToRegister(String path) {
        Lecture lecture = lectureRepository.findLectureOnlyByPath(path);
        checkIfExistingStudy(path, lecture);
        return lecture;
    }

    public void newRegister(Lecture lecture, Account account) {
        if (!registerRepository.existsByLectureAndAccount(lecture, account)) {
            Register register = new Register();
            register.setRegisteredAt(LocalDateTime.now());
            register.setAccount(account);
            lecture.addRegister(register);
            registerRepository.save(register);
        }
    }

    public void cancelRegister(Lecture lecture, Account account) {
        Register register = registerRepository.findByLectureAndAccount(lecture, account);
        lecture.removeRegister(register);
        registerRepository.delete(register);
    }

    public void acceptRegister(Account account,Lecture lecture, Register register) {
        lecture.accept(register,account);
    }

    public void rejectRegister(Account account, Lecture lecture, Register register) {
        lecture.reject(register,account);
    }

}

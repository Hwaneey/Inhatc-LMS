package kr.co.inhatc.lms.Register;

import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RegisterRepository extends JpaRepository<Register,Long> {

    boolean existsByLectureAndAccount(Lecture lecture, Account account);

    Register findByLectureAndAccount(Lecture lecture, Account account);

}

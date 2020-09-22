package kr.co.inhatc.lms.study;

import kr.co.inhatc.lms.lecture.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface StudyRepository extends JpaRepository<Study , Long> {

    List<Study> findAllByLectureOrderByStartDateTime(Lecture lecture);

    List<Study> findByLectureOrderByStartDateTime(Lecture lecture);

    Page<Study> findByLectureOrderByStartDateTime(Lecture lecture, Pageable pageable);

}

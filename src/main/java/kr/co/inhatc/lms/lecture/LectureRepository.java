package kr.co.inhatc.lms.lecture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface LectureRepository extends JpaRepository<Lecture,Long> {
    boolean existsByPath(String path);

    Lecture findByPath(String path);
}

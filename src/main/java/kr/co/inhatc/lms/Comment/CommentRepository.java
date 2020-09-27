package kr.co.inhatc.lms.Comment;

import kr.co.inhatc.lms.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CommentRepository extends JpaRepository<Comment,Long>{

    void deleteAllByStudy(Study study);
    
    List<Comment> findAllByStudy(Study study);

    Comment findOrderBycreatedDate(Comment comment);
}
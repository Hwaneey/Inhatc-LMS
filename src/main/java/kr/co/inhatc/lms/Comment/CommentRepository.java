package kr.co.inhatc.lms.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CommentRepository extends JpaRepository<Comment,Long>{

}
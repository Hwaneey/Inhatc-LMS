package kr.co.inhatc.lms.comment;

import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.study.Study;
import kr.co.inhatc.lms.study.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final StudyRepository studyRepository;
    private final CommentRepository commentRepository;

    public Study getStudy(Long studyId){
        return studyRepository.findById(studyId).get();
    }

    public Comment saveComment(CommentForm commentForm, Account account){

        Study study = getStudy(commentForm.getStudyId());

        Comment comment = Comment.builder()
                .content(commentForm.getContent())
                .createdDate(LocalDateTime.now())
                .study(study)
                .writer(account)
                .build();
        study.addComment(comment);
        return commentRepository.save(comment);
    }

}

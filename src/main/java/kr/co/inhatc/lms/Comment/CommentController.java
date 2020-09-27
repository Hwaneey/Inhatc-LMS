package kr.co.inhatc.lms.Comment;

import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.account.CurrentUser;
import kr.co.inhatc.lms.lecture.Lecture;
import kr.co.inhatc.lms.study.Study;
import kr.co.inhatc.lms.study.StudyRepository;
import kr.co.inhatc.lms.study.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;
    private final StudyService studyService;
    private final StudyRepository studyRepository;

    // 강의 댓글
    @PostMapping("/study/{path}/events/{id}/comment")
    public String write(@CurrentUser Account account, @PathVariable String path, @PathVariable Long id, CommentForm CommentForm) {

        Lecture lecture = studyService.getStudy(path);
        Study study = studyRepository.findById(id).orElseThrow();

        commentService.saveComment(CommentForm, account);
//        commentList commentList = commentRepository.
        return "redirect:/study/" + lecture.getEncodedPath() + "/events/" + study.getId();
    }


}

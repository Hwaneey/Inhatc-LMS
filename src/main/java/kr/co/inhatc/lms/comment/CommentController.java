package kr.co.inhatc.lms.comment;

import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.account.CurrentUser;
import kr.co.inhatc.lms.lecture.Lecture;
import kr.co.inhatc.lms.lecture.LectureService;
import kr.co.inhatc.lms.study.Study;
import kr.co.inhatc.lms.study.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final LectureService lectureService;
    private final StudyRepository studyRepository;

    @PostMapping("/study/{path}/events/{id}/comment")
    public String commentWrite(@CurrentUser Account account, @PathVariable String path, @PathVariable Long id, CommentForm CommentForm) {

        Lecture lecture = lectureService.getLecture(path);
        Study study = studyRepository.findById(id).orElseThrow();

        commentService.saveComment(CommentForm, account);
        return "redirect:/study/" + lecture.getEncodedPath() + "/events/" + study.getId();
    }

    @PostMapping("/study/{path}/events/{id}/comment/{commentId}")
    public String commentDelete(@CurrentUser Account account, @PathVariable String path,
                                @PathVariable Long id,@PathVariable("commentId") Long commentId){

        Lecture lecture = lectureService.getLecture(path);
        Study study = studyRepository.findById(id).orElseThrow();

        commentService.deleteComment(commentId, account);
        return "redirect:/study/" + lecture.getEncodedPath() + "/events/" + study.getId();
    }

}

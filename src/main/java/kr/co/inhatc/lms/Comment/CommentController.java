//package kr.co.inhatc.lms.Comment;
//
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import javax.validation.Valid;
//
//public class CommentController {
//    // 코멘트 작성
//    @PostMapping("/problem-share/comment")
//    public String write(@PathVariable("nickname") String nickname, @PathVariable("title") String title,
//                        Model model, @CurrentUser Account account, @Valid CommentForm commentForm) {
//
//        Project project = projectService.getProject(nickname, title);
//
//        // 입력받은 comment 내용, 커멘트가 달린 문제공유글 id, 해당 코멘트를 작성한 유저
//        problemShareService.saveComment(commentForm, account);
//        return "redirect:/project/" + nickname + "/" + project.getEncodedTitle() + "/problem-share";
//    }
//
//}

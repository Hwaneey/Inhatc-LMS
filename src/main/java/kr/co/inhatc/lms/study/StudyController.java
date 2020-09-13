package kr.co.inhatc.lms.study;

import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.account.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class StudyController {

    @GetMapping("/lecture/{path}/createStudy")
    public String createStudy(@CurrentUser Account account, @PathVariable String path, Model model){
        model.addAttribute(account);
        model.addAttribute(path);
        model.addAttribute(new StudyDto());
        return "study/form";
    }
}

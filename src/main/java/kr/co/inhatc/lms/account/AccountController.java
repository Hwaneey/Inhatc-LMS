package kr.co.inhatc.lms.account;

import kr.co.inhatc.lms.lecture.LectureRepository;
import kr.co.inhatc.lms.signup.SignUpForm;
import kr.co.inhatc.lms.signup.SignUpFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller @RequiredArgsConstructor
public class AccountController {

    private final UserRepository userRepository;
    private final AccountService accountService;
    private final LectureRepository lectureRepository;
    private final SignUpFormValidator signUpFormValidator;

    @GetMapping("/")
    public String home(@CurrentUser Account account, Model model) {
        if (account != null) {
            model.addAttribute(account);
//            model.addAttribute("studyManagerOf",
//                    lectureRepository.findBySubjectTitle(account));
        }
        return "index";
    }
    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("signUpForm",new SignUpForm());
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid SignUpForm signUpForm, Errors errors){
        if(errors.hasErrors()){
            return "account/sign-up";
        }
        signUpFormValidator.validate(signUpForm,errors);
        if (errors.hasErrors()){
            return "account/sign-up";
        }
        accountService.createAccount(signUpForm);
        return "redirect:/";
    }

    @GetMapping("/check-email-token")
    public String checkEmailToken (String token, String email, Model model){
        Account account = userRepository.findByEmail(email);
        if (account == null){
            model.addAttribute("error","wrong.email");
            return "account/checkedEmail";
        }
        if(!account.getEmailCheckToken().equals(token)){
            model.addAttribute("error","wrong.token");
            return "account/checkedEmail";
        }
        accountService.checkedMail(account);
        model.addAttribute("username",account.getUsername());
        return "account/checkedEmail";
    }

    @GetMapping("/check-email")
    public String checkEmail(@CurrentUser Account account,Model model){
        model.addAttribute("email",account.getEmail());
        return "account/check-email";
    }

    @GetMapping("/resend-confirm-email")
    public String resendEmail(@CurrentUser Account account){
        accountService.sendEmail(account);
        return "redirect:/";
    }

}

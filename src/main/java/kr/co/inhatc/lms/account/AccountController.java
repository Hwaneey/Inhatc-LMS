package kr.co.inhatc.lms.account;

import kr.co.inhatc.lms.signup.SignUpForm;
import kr.co.inhatc.lms.signup.SignUpFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller @RequiredArgsConstructor
public class AccountController {

    private final JavaMailSender javaMailSender;
    private final AccountRepository accountRepository;
    private final SignUpFormValidator signUpFormValidator;
    private final PasswordEncoder passwordEncoder;

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

        Account account = Account.builder()
                .email(signUpForm.getEmail())
                .username(signUpForm.getUsername())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .build();

        Account CreateAccount = accountRepository.save(account);

        CreateAccount.generateEmailCheckToken();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(CreateAccount.getEmail());
        mailMessage.setSubject("Inha Technical College 회원가입 인증");
        mailMessage.setText("/check-email-token?token=" + CreateAccount.getEmailCheckToken() +"&email = "+ CreateAccount.getEmail() );
        javaMailSender.send(mailMessage);

        return "redirect:/";
    }

}

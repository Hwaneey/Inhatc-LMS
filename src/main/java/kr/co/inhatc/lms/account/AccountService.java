package kr.co.inhatc.lms.account;

import kr.co.inhatc.lms.mail.AppProperties;
import kr.co.inhatc.lms.mail.EmailMessage;
import kr.co.inhatc.lms.mail.EmailService;
import kr.co.inhatc.lms.role.Role;
import kr.co.inhatc.lms.role.RoleRepository;
import kr.co.inhatc.lms.signup.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service @Transactional @RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ITemplateEngine templateEngine;
    private final AppProperties appProperties;

    public Account createAccount(SignUpForm signUpForm) {

        Role role = roleRepository.findByRoleName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        Account account = Account.builder()
                .email(signUpForm.getEmail())
                .username(signUpForm.getUsername())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .userRoles(roles)
                .build();
        Account CreateAccount = userRepository.save(account);
        sendEmail(account);
        return CreateAccount;
    }

    public void sendEmail(Account Account) {
        Account.generateEmailCheckToken();
        Context context = new Context();
        context.setVariable("link", "/check-email-token?token=" + Account.getEmailCheckToken() +
                "&email=" + Account.getEmail());
        context.setVariable("username", Account.getUsername());
        context.setVariable("linkName", "이메일 인증하기");
        context.setVariable("message", "Inha Technical College LMS 서비스를 사용하려면 링크를 클릭하세요.");
        context.setVariable("host", appProperties.getHost());
        String message = templateEngine.process("mail/simple-link", context);

        EmailMessage emailMessage = EmailMessage.builder()
                .to(Account.getEmail())
                .subject("Inha Technical College LMS , 회원 가입 인증")
                .message(message)
                .build();

        emailService.sendEmail(emailMessage);
    }



    public void checkedMail(Account account) {
        account.checkEmail();
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = userRepository.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException(email);
        }
        Set<String> userRoles = account.getUserRoles()
                .stream()
                .map(userRole -> userRole.getRoleName())
                .collect(Collectors.toSet());

        List<GrantedAuthority> collect = userRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new UserAccount(account, collect);
    }
}

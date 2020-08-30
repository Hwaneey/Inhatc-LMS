package kr.co.inhatc.lms.signup;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Data
public class SignUpForm {
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{2,10}$")
    @Length(min = 2, max = 10)
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 8, max = 50)
    private String password;
}

package kr.co.inhatc.lms.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private String id;
//    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{2,10}$")
//    @Length(min = 2, max = 10)
    private String username;

//    @Email
//    @NotBlank
    private String email;

//    @NotBlank
//    @Length(min = 8, max = 50)
    private String password;

    private List<String> roles;
}



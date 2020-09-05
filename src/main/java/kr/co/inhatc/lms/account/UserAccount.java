package kr.co.inhatc.lms.account;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserAccount extends User {

    private Account account;

    public UserAccount(Account account, List<GrantedAuthority> roles) {
        super(account.getUsername(), account.getPassword(), roles);
        this.account = account;
    }

}

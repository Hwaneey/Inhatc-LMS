package kr.co.inhatc.lms.account;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class UserAccount extends User implements UserDetailsService {
    private UserRepository userRepository;
    private Account account;

    public UserAccount(Account account, List<GrantedAuthority> roles) {
        super(account.getUsername(), account.getPassword(), roles);
        this.account = account;
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

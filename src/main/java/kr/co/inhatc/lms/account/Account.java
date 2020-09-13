package kr.co.inhatc.lms.account;

import kr.co.inhatc.lms.role.Role;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ToString(exclude = {"userRoles"})
@EqualsAndHashCode(of = "id")
@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Account {

    @Id @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String username;

    private String password;

    private boolean emailVerified;

    private String emailCheckToken;

    private LocalDateTime joinedAt;

    @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.ALL})
    @JoinTable(
            name = "account_roles",
            joinColumns = { @JoinColumn(name = "account_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
            )
    private Set<Role> userRoles = new HashSet<>();

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
    }

    public void checkEmail() {
        this.emailVerified =true;
        this.joinedAt = LocalDateTime.now();

    }
}

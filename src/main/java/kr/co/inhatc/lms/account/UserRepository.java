package kr.co.inhatc.lms.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<Account,Long> {

    boolean existsByEmail(String username);

    Account findByEmail(String email);

    Account findByUsername(String username);

    int countByUsername(String username);
}

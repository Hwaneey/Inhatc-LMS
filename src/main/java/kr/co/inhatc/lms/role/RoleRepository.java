package kr.co.inhatc.lms.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByRoleName(String name);

    @Override
    void deleteById(Long aLong);
}

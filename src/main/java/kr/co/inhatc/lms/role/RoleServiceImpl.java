package kr.co.inhatc.lms.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j @Transactional
@Service @RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public Role getRole(long id) {
        return roleRepository.findById(id).orElse(new Role());
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public void createRole(Role role){
        roleRepository.save(role);
    }

    public void deleteRole(long id) {
        roleRepository.deleteById(id);
    }
}
package kr.co.inhatc.lms.role;


import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.account.CurrentUser;
import kr.co.inhatc.lms.lecture.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller @RequiredArgsConstructor
public class RoleController {

	private final RoleService roleService;
	private final LectureRepository lectureRepository;

	private void list(Account account, Model model) {
		model.addAttribute("lectureManagerOf",lectureRepository.findFirst5ByLecturerContaining(account));
		model.addAttribute("studentManagerOf",lectureRepository.findFirst5ByStudentContaining(account));
	}

	@GetMapping(value="/admin/roles")
	public String getRoles(@CurrentUser Account account , Model model ) throws Exception {
		list(account, model);
		List<Role> roles = roleService.getRoles();
		model.addAttribute("roles", roles);
		return "admin/role/list";
	}

	@GetMapping(value="/admin/roles/register")
	public String viewRoles(@CurrentUser Account account,Model model) throws Exception {
		list(account, model);
		RoleDto role = new RoleDto();
		model.addAttribute("role", role);
		return "admin/role/detail";
	}

	@PostMapping(value="/admin/roles")
	public String createRole(RoleDto roleDto) throws Exception {
		ModelMapper modelMapper = new ModelMapper();
		Role role = modelMapper.map(roleDto, Role.class);
		roleService.createRole(role);
		return "redirect:/admin/roles";
	}

	@GetMapping(value="/admin/roles/{id}")
	public String getRole(@CurrentUser Account account,@PathVariable String id, Model model) throws Exception {
		list(account, model);
		Role role = roleService.getRole(Long.valueOf(id));
		ModelMapper modelMapper = new ModelMapper();
		RoleDto roleDto = modelMapper.map(role, RoleDto.class);
		model.addAttribute("role", roleDto);
		return "admin/role/detail";
	}

	@GetMapping(value="/admin/roles/delete/{id}")
	public String removeResources(@PathVariable String id, Model model) throws Exception {
		Role role = roleService.getRole(Long.valueOf(id));
		roleService.deleteRole(Long.valueOf(id));

		return "redirect:/admin/resources";
	}
}

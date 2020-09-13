package kr.co.inhatc.lms.admin;


import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.account.AccountService;
import kr.co.inhatc.lms.role.Role;
import kr.co.inhatc.lms.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller @RequiredArgsConstructor
public class UserManagerController {

	private final UserService userService;
	private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;
	private final AccountService accountService;

	@GetMapping("/admin/user/register")
	public String registerUser(Model model){
		return "admin/user/register";
	}

	@GetMapping(value="/admin/accounts")
	public String getUsers(Model model){

		List<Account> accounts = userService.getUsers();
		model.addAttribute("accounts", accounts);

		return "admin/user/list";
	}

	@PostMapping(value="/users")
	public String registerUser(AccountDto accountDto) throws Exception {
		ModelMapper modelMapper = new ModelMapper();
		Account account = modelMapper.map(accountDto, Account.class);
		account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
		accountService.sendEmail(account);
		userService.createUser(account);
		return "redirect:/admin/accounts";
	}

	@GetMapping(value = "/admin/accounts/{id}")
	public String getUser(@PathVariable(value = "id") Long id, Model model) {

		AccountDto accountDto = userService.getUser(id);
		List<Role> roleList = roleService.getRoles();

		model.addAttribute("account", accountDto);
		model.addAttribute("roleList", roleList);

		return "admin/user/detail";
	}

	@PostMapping(value="/admin/accounts")
	public String modifyUser(AccountDto accountDto) throws Exception {

		userService.modifyUser(accountDto);

		return "redirect:/admin/accounts";
	}

	@GetMapping(value = "/admin/accounts/delete/{id}")
	public String removeUser(@PathVariable(value = "id") Long id, Model model) {

		userService.deleteUser(id);

		return "redirect:/admin/accounts";
	}
}

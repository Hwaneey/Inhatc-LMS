package kr.co.inhatc.lms.admin;

import kr.co.inhatc.lms.account.Account;

import java.util.List;


public interface UserService {

    void createUser(Account account,AccountDto accountDto);

    void modifyUser(AccountDto accountDto);

    List<Account> getUsers();

    AccountDto getUser(Long id);

    void deleteUser(Long idx);

    void order();
}

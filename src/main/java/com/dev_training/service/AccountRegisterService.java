package com.dev_training.service;

import com.dev_training.entity.Account;
import com.dev_training.entity.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * アカウント登録サービス。
 */
@Service
public class AccountRegisterService {

    /** アカウントリポジトリ */
    private final AccountRepository accountRepository;
    /** パスワードエンコーダー */
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountRegisterService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 登録処理。
     *
     * @param account     登録対象のアカウント
     * @param rawPassword 暗号化前のパスワード
     */
    @Transactional
    public void register(Account account, String rawPassword) {
        // パスワードの暗号化
        String encodedPassword = passwordEncoder.encode(rawPassword);
        account.setPassword(encodedPassword);
        accountRepository.save(account);
    }

    /**
     * アカウントIDの重複精査。
     *
     * @param accountId 精査対象のアカウントID
     * @return true:未存在 false:存在
     */
    @Transactional(readOnly = true)
    public boolean isExistsAccountId(String accountId) {
        int result = accountRepository.countByAccountId(accountId);
        return result != 0;
    }
}

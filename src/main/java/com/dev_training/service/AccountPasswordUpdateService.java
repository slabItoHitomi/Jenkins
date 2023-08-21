package com.dev_training.service;

import com.dev_training.entity.Account;
import com.dev_training.entity.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * パスワード更新サービス。
 */
@Service
public class AccountPasswordUpdateService {

    /** アカウントリポジトリ */
    private final AccountRepository accountRepository;
    /** パスワードエンコーダー */
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountPasswordUpdateService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 現在のパスワード一致精査。
     *
     * @param id                 アカウントの主キー
     * @param rawCurrentPassword 入力された現在のパスワード
     * @return 精査結果
     */
    public boolean validCurrentPassword(int id, String rawCurrentPassword) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("account is not found"));
        return passwordEncoder.matches(rawCurrentPassword, account.getPassword());
    }

    /**
     * パスワード更新処理。
     *
     * @param id             アカウントの主キー
     * @param rawNewPassword 入力された新しいパスワード
     */
    public void updatePassword(int id, String rawNewPassword) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("account is not found"));
        account.setPassword(passwordEncoder.encode(rawNewPassword));
        accountRepository.save(account);
    }
}

package com.dev_training.service;

import com.dev_training.entity.Account;
import com.dev_training.entity.AccountRepository;
import com.dev_training.form.AccountUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * アカウント情報更新サービス。
 */
@Service
public class AccountUpdateService {

    /** アカウントリポジトリ */
    private final AccountRepository accountRepository;

    @Autowired
    public AccountUpdateService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * アカウント最新データ取得処理。
     *
     * @param id ID
     * @return アカウントエンティティ
     */
    @Transactional(readOnly = true)
    public Account getAccountById(int id) {
        Optional<Account> result = accountRepository.findById(id);
        return result.orElseThrow(() -> new RuntimeException("account is not found"));
    }

    /**
     * アカウント情報の更新有無精査。
     *
     * @param accountUpdateForm 精査対象のアカウント
     * @param targetAccount     更新対象のアカウント
     * @return true:更新なし false:更新あり
     */

    public boolean isNoChange(AccountUpdateForm accountUpdateForm, Account targetAccount) {
        return accountUpdateForm.getAccountId().equals(targetAccount.getAccountId())
                && accountUpdateForm.getName().equals(targetAccount.getName())
                && accountUpdateForm.getEmail().equals(targetAccount.getEmail())
                && accountUpdateForm.getSelfIntroduction().equals(targetAccount.getSelfIntroduction());
    }

    /**
     * アカウントIDの重複精査。
     *
     * @param accountId 精査対象のアカウントID
     * @return true:存在 false:未存在
     */
    @Transactional(readOnly = true)
    public boolean isExistsAccountId(String accountId) {
        // アカウントIDの重複精査
        int result = accountRepository.countByAccountId(accountId);
        return result != 0;
    }

    /**
     * 更新処理。
     *
     * @param account 更新対象のアカウント
     */
    @Transactional
    public void updateAccountById(Account account) {
        accountRepository.save(account);
    }

}
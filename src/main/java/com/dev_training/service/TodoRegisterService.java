package com.dev_training.service;

import com.dev_training.entity.Account;
import com.dev_training.entity.AccountRepository;
import com.dev_training.entity.Todo;
import com.dev_training.entity.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * TODO登録サービス。
 */
@Service
@Transactional
public class TodoRegisterService {

    /** TODOリポジトリ */
    private final TodoRepository todoRepository;
    /** アカウントリポジトリ */
    private final AccountRepository accountRepository;

    @Autowired
    public TodoRegisterService(TodoRepository todoRepository, AccountRepository accountRepository) {
        this.todoRepository = todoRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * TODO登録処理。
     */
    public void register(Todo todo) {
        todoRepository.save(todo);
    }

    /**
     * 日付の前後有効性チェック。
     *
     * @param startDate 開始日
     * @param endDate 終了日
     * @return true:無効 false:有効
     */
    public boolean isValidDate(String startDate, String endDate) {
        if (Objects.isNull(startDate) || Objects.isNull(endDate)) return false;
        return startDate.compareTo(endDate) > 0;
    }

    /**
     * 全アカウントを検索する。
     *
     * @return 全アカウントのリスト
     */
    public List<Account> findAllAccount() {
        return accountRepository.findAllAccount();
    }

    /**
     * アカウントの主キー検索。
     *
     * @param id ID
     * @return アカウントエンティティ
     */
    public Optional<Account> findAccountById(int id) {
        return accountRepository.findById(id);
    }

}

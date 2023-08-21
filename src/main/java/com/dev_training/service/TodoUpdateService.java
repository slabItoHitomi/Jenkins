package com.dev_training.service;

import com.dev_training.entity.Account;
import com.dev_training.entity.AccountRepository;
import com.dev_training.entity.Todo;
import com.dev_training.entity.TodoRepository;
import com.dev_training.form.TodoUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * TODO更新サービス。
 */
@Service
@Transactional
public class TodoUpdateService {

    /** TODOリポジトリ */
    private final TodoRepository todoRepository;
    /** アカウントリポジトリ */
    private final AccountRepository accountRepository;

    @Autowired
    public TodoUpdateService(TodoRepository todoRepository, AccountRepository accountRepository) {
        this.todoRepository = todoRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * TODO更新処理。
     */
    public void update(Todo todo) {
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
     * TODOの主キー検索。
     *
     * @param id ID
     * @return TODOエンティティ
     */
    public Todo findById(int id) {
        Optional<Todo> result = this.todoRepository.findById(id);
        return result.orElse(null);
    }

    /**
     * 更新内容が存在するかチェックする。
     *
     * @param form フォーム
     * @param entity 更新対象のEntity
     * @return true:更新なし false:更新あり
     */
    public boolean isNoChange(TodoUpdateForm form, Todo entity) {
        return form.getTitle().equals(entity.getTitle()) &&
                form.getDetail().equals(entity.getDetail()) &&
                form.getStartDate().equals(entity.getStartDate()) &&
                form.getEndDate().equals(entity.getEndDate()) &&
                form.getRemarks().equals(entity.getRemarks()) &&
                form.getIssuePersonId().equals(String.valueOf(entity.getIssuePersonId())) &&
                form.getPersonInChargeId().equals(String.valueOf(entity.getPersonInChargeId()))&&
                form.getPriority().equals(entity.getPriority()) &&
                form.getStatus().equals(entity.getStatus());
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

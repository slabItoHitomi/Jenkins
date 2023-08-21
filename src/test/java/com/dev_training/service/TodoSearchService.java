package com.dev_training.service;

import com.dev_training.entity.Account;
import com.dev_training.entity.AccountRepository;
import com.dev_training.entity.Todo;
import com.dev_training.entity.TodoRepository;
import com.dev_training.form.TodoSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.dev_training.service.TodoSpecifications.*;

/**
 * TODO検索サービス。
 */
@Service
@Transactional
public class TodoSearchService {

    /** TODOリポジトリ */
    private final TodoRepository todoRepository;
    /** アカウントリポジトリ */
    private final AccountRepository accountRepository;

    @Autowired
    public TodoSearchService(TodoRepository todoRepository, AccountRepository accountRepository) {
        this.todoRepository = todoRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * 検索条件からTODOを検索する。
     *
     * @param form 検索条件
     * @return 検索結果
     */
    @Transactional(readOnly = true)
    public List<Todo> findTodo(TodoSearchForm form) {
        return todoRepository.findAll(
                Specification
                        .where(titleContains(form.getTitle()))
                        .and(detailContains(form.getDetail()))
                        .and(remarksContains(form.getRemarks()))
                        .and(startDateContains(form.getStartDate()))
                        .and(endDateContains(form.getEndDate()))
                        .and(issuePersonIdContains(form.getIssuePersonId()))
                        .and(personInChargeIdContains(form.getPersonInChargeId()))
                        .and(statusContains(form.getSelectedStatus()))
                        .and(priorityContains(form.getSelectedPriority())));
    }

    /**
     * 検索条件からTODOを検索する（ページング）。
     *
     * @param form     検索条件
     * @param pageable ページャブル
     * @return 検索結果
     */
    @Transactional(readOnly = true)
    public Page<Todo> findTodo(TodoSearchForm form, Pageable pageable) {
        return todoRepository.findAll(
                Specification
                        .where(titleContains(form.getTitle()))
                        .and(detailContains(form.getDetail()))
                        .and(remarksContains(form.getRemarks()))
                        .and(startDateContains(form.getStartDate()))
                        .and(endDateContains(form.getEndDate()))
                        .and(issuePersonIdContains(form.getIssuePersonId()))
                        .and(personInChargeIdContains(form.getPersonInChargeId()))
                        .and(statusContains(form.getSelectedStatus()))
                        .and(priorityContains(form.getSelectedPriority()))
                , pageable);
    }

    /**
     * TODOの主キー検索。
     *
     * @param id ID
     * @return TODOエンティティ
     */
    public Todo findTodoById(int id) {
        Optional<Todo> result = this.todoRepository.findById(id);
        return result.orElse(null);
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

package com.dev_training.service;

import com.dev_training.entity.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO削除サービス。
 */
@Service
@Transactional
public class TodoDeleteService {

    /** TODOリポジトリ */
    private final TodoRepository todoRepository;

    @Autowired
    public TodoDeleteService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * 削除処理。
     *
     * @param id 削除対象のTODOのID
     */
    public void delete(int id) {
        todoRepository.deleteById(id);
    }

    /**
     * 主キーによる存在チェック。
     *
     * @param id ID
     * @return true:存在 false:非存在
     */
    public boolean isExistsById(int id) {
        return todoRepository.existsById(id);
    }
}

package com.dev_training.controller;

import com.dev_training.entity.Account;
import com.dev_training.service.CommentDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * コメント削除コントローラ。
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentDeleteController {

    /** コメント削除サービス */
    private final CommentDeleteService service;
    /** HTTPセッション */
    private final HttpSession session;
    /** セッションキー(ログインユーザのアカウント) */
    private static final String SESSION_FORM_ID = "account";

    @Autowired
    public CommentDeleteController(CommentDeleteService commentDeleteService, HttpSession session) {
        this.session = session;
        this.service = commentDeleteService;
    }

    /**
     * コメント削除処理。
     *
     * @param id コメントのID
     */
    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        Account account = (Account) session.getAttribute(SESSION_FORM_ID);
        service.deleteById(id, account.getId());
    }
}


package com.dev_training.controller;

import com.dev_training.entity.Account;
import com.dev_training.entity.Comment;
import com.dev_training.form.CommentRegisterForm;
import com.dev_training.service.CommentRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * コメント登録コントローラ。
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentRegisterController {

    /** HTTPセッション */
    private final HttpSession session;
    /** コメント登録サービス */
    private final CommentRegisterService service;
    /** セッションキー(ログインユーザのアカウント) */
    private static final String SESSION_FORM_ID = "account";

    @Autowired
    public CommentRegisterController(HttpSession session, CommentRegisterService commentRegisterService) {
        this.session = session;
        this.service = commentRegisterService;
    }

    /**
     * コメント登録処理。
     *
     * @param commentRegisterForm　コメント登録フォーム
     * @return コメント登録フォーム
     */
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CommentRegisterForm register(@Validated @RequestBody CommentRegisterForm commentRegisterForm) {
        Account account = (Account) session.getAttribute(SESSION_FORM_ID);
        //登録するコメントの作成
        Comment comment = new Comment();
        comment.setLoginId(account.getId());
        comment.setComment(commentRegisterForm.getComment());

        // 登録処理
        service.register(comment);
        return commentRegisterForm;
    }
}


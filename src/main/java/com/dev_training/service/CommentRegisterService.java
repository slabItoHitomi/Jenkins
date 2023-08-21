package com.dev_training.service;

import com.dev_training.entity.Comment;
import com.dev_training.entity.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * コメント登録サービス。
 */
@Service
public class CommentRegisterService {

    /** コメントリポジトリ */
    private final CommentRepository commentRepository;

    @Autowired
    public CommentRegisterService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * コメント登録処理。
     */
    public void register(Comment comment) {
        commentRepository.save(comment);
    }
}

package com.dev_training.service;

import com.dev_training.entity.Comment;
import com.dev_training.entity.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * コメント削除サービス。
 */
@Service
public class CommentDeleteService {

    /** コメントリポジトリ */
    private final CommentRepository commentRepository;

    @Autowired
    public CommentDeleteService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * コメント削除処理。
     *
     * @param commentId 削除対象コメントのID
     * @param loginId セッションアカウントのID
     */
    public void deleteById(int commentId, int loginId) {
        Optional<Comment> result = commentRepository.findById(commentId);
        Comment comment = result.orElseThrow(() -> new RuntimeException("comment is not found"));
        //セッションのアカウントによって作成されたコメントの場合、削除する
        if (comment.getLoginId() == loginId) {
            commentRepository.deleteById(commentId);
        }
    }
}

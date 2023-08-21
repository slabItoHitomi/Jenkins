package com.dev_training.service;

import com.dev_training.extended_entity.ExtendedComment;
import com.dev_training.extended_entity.ExtendedCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * コメント検索サービス。
 */
@Service
public class CommentSearchService {

    /** コメント機能用リポジトリ */
    private final ExtendedCommentRepository extendedCommentRepository;

    @Autowired
    public CommentSearchService(ExtendedCommentRepository extendedCommentRepository) {
        this.extendedCommentRepository = extendedCommentRepository;
    }

    /**
     * コメント検索処理。
     *
     * @return 検索結果のリスト
     */
    @Transactional(readOnly = true)
    public List<ExtendedComment> findComment() {
        return  extendedCommentRepository.findComment();
    }
}

package com.dev_training.extended_entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * コメント機能用リポジトリ。
 */
public interface ExtendedCommentRepository extends JpaRepository<ExtendedComment, Integer>, JpaSpecificationExecutor<ExtendedComment> {

    /**
     * コメント検索処理。
     * コメントテーブルとアカウントテーブルを結合し、コメントテーブルの作成日時降順で検索結果を取得。
     *
     * @return 検索結果
     */
    @Query(value = "SELECT c.id as id, c.login_id as login_id, a.name as name, c.comment as comment, c.created_tms as created_tms "
            + "FROM comment c  inner join accounts a on c.login_id = a.id "
            + "ORDER BY c.created_tms DESC", nativeQuery = true)
    List<ExtendedComment> findComment();
}
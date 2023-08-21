package com.dev_training.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * アカウントリポジトリ。
 */
public interface AccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {

    /**
     * アカウントIDに紐づくアカウントを検索する。
     *
     * @param accountId アカウントID
     * @return アカウント
     */
    @Query(value = "SELECT * FROM accounts WHERE account_id = :accountId AND delete_flag = 0", nativeQuery = true)
    Account findByAccountId(@Param("accountId") String accountId);

    /**
     * アカウントIDに紐づくアカウントの件数を取得する。
     *
     * @param accountId アカウントID
     * @return 件数
     */
    @Query(value = "SELECT COUNT(*) FROM accounts WHERE account_id = :accountId AND delete_flag = 0", nativeQuery = true)
    int countByAccountId(@Param("accountId") String accountId);

    /**
     * 全アカウントを検索する。
     *
     * @return 全アカウントのリスト
     */
    @Query(value = "SELECT * FROM accounts WHERE delete_flag = 0", nativeQuery = true)
    List<Account> findAllAccount();
}
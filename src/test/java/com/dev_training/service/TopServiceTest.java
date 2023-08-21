package com.dev_training.service;

import com.dev_training.entity.Account;
import com.dev_training.entity.AccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TopServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TopService topService;


    @Test
    void getAccountById() {
        // テストデータを作成
        int accountId = 1;
        Account account = new Account();
        account.setId(accountId);
        account.setName("John Doe");
        account.setEmail("john@example.com");

        // accountRepositoryの振る舞いを設定
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // テスト実行
        Account result = topService.getAccountById(accountId);

        // 結果の検証
        assertEquals(accountId, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
    }
}


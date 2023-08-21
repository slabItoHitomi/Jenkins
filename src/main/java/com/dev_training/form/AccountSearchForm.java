package com.dev_training.form;

import com.dev_training.validator.HalfAlphameric;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * アカウント検索フォーム。
 */
public class AccountSearchForm implements Serializable {

    @Size(max = 15, message = "{error.size.max}")
    @HalfAlphameric
    private String accountId;

    @Size(max = 45, message = "{error.size.max}")
    private String name;

    @Size(max = 255, message = "{error.size.max}")
    private String email;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

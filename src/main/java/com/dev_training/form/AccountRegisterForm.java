package com.dev_training.form;

import com.dev_training.validator.HalfAlphameric;
import com.dev_training.validator.Password;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * アカウント登録フォーム。
 */
public class AccountRegisterForm implements Serializable {

    @NotBlank
    @Size(min = 3, max = 15, message = "{error.size.min.max}")
    @HalfAlphameric
    private String accountId;

    @NotBlank
    @Password
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    @Size(max = 45, message = "{error.size.max}")
    private String name;

    @NotBlank
    @Size(max = 255, message = "{error.size.max}")
    @Email
    private String email;

    @NotBlank
    @Size(max = 255, message = "{error.size.max}")
    private String selfIntroduction;

    @AssertTrue(message = "パスワードが一致しません。")
    public boolean isPasswordValid() {
        return Objects.nonNull(password) && password.equals(confirmPassword);
    }


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }
}

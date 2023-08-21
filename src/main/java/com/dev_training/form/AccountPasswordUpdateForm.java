package com.dev_training.form;

import com.dev_training.validator.Password;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

/**
 * パスワード更新フォーム。
 */
public class AccountPasswordUpdateForm implements Serializable {

    @NotBlank
    private String currentPassword;

    @NotBlank
    @Password
    private String newPassword;

    @NotBlank
    private String confirmPassword;

    @AssertTrue(message = "パスワードが一致しません。")
    public boolean isPasswordValid() {
        return Objects.nonNull(newPassword) && newPassword.equals(confirmPassword);
    }

    @AssertTrue(message = "パスワードが変更されていません。")
    public boolean isNewPasswordValid() {
        return Objects.nonNull(currentPassword) && !currentPassword.equals(newPassword);
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}


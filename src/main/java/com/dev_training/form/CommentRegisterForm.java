package com.dev_training.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * コメント登録フォーム。
 */
public class CommentRegisterForm implements Serializable {

    @NotBlank
    @Size(max = 255, message = "{error.size.max}")
    private String comment;

    public void setComment(String comment) { this.comment = comment; }

    public String getComment() { return comment; }
}

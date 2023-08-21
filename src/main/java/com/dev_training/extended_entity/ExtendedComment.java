package com.dev_training.extended_entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * コメント機能用エンティティ。
 */
@Entity
public class ExtendedComment implements Serializable {

    @Id
    private int id;

    private int loginId;

    private String name;

    private String comment;

    private String createdTms;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoginId() { return loginId; }

    public void setLoginId(int loginId) { this.loginId = loginId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public String getCreatedTms() {
        // 作成日を「YYYY/MM/DD」の形式に編集
        return createdTms = createdTms.substring(0, 4) + '/' + createdTms.substring(5, 7) + '/' + createdTms.substring(8, 16);
    }

    public void setCreatedTms(String createdTms) { this.createdTms = createdTms; }
}
package com.dev_training.form;


import java.io.Serializable;

/**
 * TODO更新フォーム。
 */
public class TodoUpdateForm extends TodoRegisterForm implements Serializable {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

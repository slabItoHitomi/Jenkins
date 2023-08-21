package com.dev_training.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * コード管理クラス。
 */
@Component
public class CodeValue {

    /** ステータス */
    private Status status;
    /** 優先度 */
    private Priority priority;

    @Autowired
    public CodeValue(Status status, Priority priority) {
        this.status = status;
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}

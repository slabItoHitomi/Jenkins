package com.dev_training.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * ステータス。
 */
@Component
@ConfigurationProperties(prefix = "todo")
@PropertySource(value = "classpath:code.properties", encoding = "UTF-8")
public class Status {

    /** ステータス管理用Map */
    private Map<String, String> status;

    public Map<String, String> getStatus() {
        return status;
    }

    public void setStatus(Map<String, String> status) {
        this.status = status;
    }
}

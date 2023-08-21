package com.dev_training.form;

import com.dev_training.validator.Date;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * TODO検索フォーム。
 */
public class TodoSearchForm implements Serializable {

    @Size(max = 45, message = "{error.size.max}")
    private String title;

    @Size(max = 100, message = "{error.size.max}")
    private String detail;

    @Size(max = 100, message = "{error.size.max}")
    private String remarks;

    @Date
    @Size(max = 10, message = "{error.size.max}")
    private String startDate;

    @Date
    @Size(max = 10, message = "{error.size.max}")
    private String endDate;

    private String issuePersonId;

    private String personInChargeId;

    private String selectedStatus;

    private String selectedPriority;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getIssuePersonId() {
        return issuePersonId;
    }

    public void setIssuePersonId(String issuePersonId) {
        this.issuePersonId = issuePersonId;
    }

    public String getPersonInChargeId() {
        return personInChargeId;
    }

    public void setPersonInChargeId(String personInChargeId) {
        this.personInChargeId = personInChargeId;
    }

    public String getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(String selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    public String getSelectedPriority() {
        return selectedPriority;
    }

    public void setSelectedPriority(String selectedPriority) {
        this.selectedPriority = selectedPriority;
    }
}

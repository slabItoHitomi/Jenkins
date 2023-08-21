package com.dev_training.entity;

import javax.persistence.*;

@Entity
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "detail", nullable = false)
    private String detail;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "issue_person_id", nullable = false)
    private int issuePersonId;

    @Column(name = "person_in_charge_id")
    private int personInChargeId;

    @Column(name = "status")
    private String status;

    @Column(name = "priority")
    private String priority;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getIssuePersonId() {
        return issuePersonId;
    }

    public void setIssuePersonId(int issuePersonId) {
        this.issuePersonId = issuePersonId;
    }

    public int getPersonInChargeId() {
        return personInChargeId;
    }

    public void setPersonInChargeId(int personInChargeId) {
        this.personInChargeId = personInChargeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
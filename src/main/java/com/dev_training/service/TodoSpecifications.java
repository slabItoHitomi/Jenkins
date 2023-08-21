package com.dev_training.service;

import com.dev_training.entity.Todo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

/**
 * TODO検索条件。
 */
class TodoSpecifications {

    /** title:like */
    static Specification<Todo> titleContains(String title) {
        return StringUtils.isEmpty(title) ? null : new Specification<Todo>() {
            @Override
            public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("title"), "%" + title + "%");
            }
        };
    }

    /** detail:like */
    static Specification<Todo> detailContains(String detail) {
        return StringUtils.isEmpty(detail) ? null : new Specification<Todo>() {
            @Override
            public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("detail"), "%" + detail + "%");
            }
        };
    }

    /** remarks:like */
    static Specification<Todo> remarksContains(String remarks) {
        return StringUtils.isEmpty(remarks) ? null : new Specification<Todo>() {
            @Override
            public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("remarks"), "%" + remarks + "%");
            }
        };
    }

    /** startDate:greaterThanOrEqualTo */
    static Specification<Todo> startDateContains(String startDate) {
        return StringUtils.isEmpty(startDate) ? null : new Specification<Todo>() {
            @Override
            public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.greaterThanOrEqualTo(root.get("startDate"), startDate);
            }
        };
    }

    /** endDate:lessThanOrEqualTo */
    static Specification<Todo> endDateContains(String endDate) {
        return StringUtils.isEmpty(endDate) ? null : new Specification<Todo>() {
            @Override
            public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.lessThanOrEqualTo(root.get("endDate"), endDate);
            }
        };
    }

    /** issuePersonId:in */
    static Specification<Todo> issuePersonIdContains(String issuePersonId) {
        return StringUtils.isEmpty(issuePersonId) ? null : new Specification<Todo>() {
            @Override
            public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<String> condition = Arrays.asList(issuePersonId.split(","));
                return root.get("issuePersonId").in(condition);
            }
        };
    }

    /** personInChargeId:in */
    static Specification<Todo> personInChargeIdContains(String personInChargeId) {
        return StringUtils.isEmpty(personInChargeId) ? null : new Specification<Todo>() {
            @Override
            public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<String> condition = Arrays.asList(personInChargeId.split(","));
                return root.get("personInChargeId").in(condition);
            }
        };
    }

    /** status:in */
    static Specification<Todo> statusContains(String status) {
        return StringUtils.isEmpty(status) ? null : new Specification<Todo>() {
            @Override
            public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<String> condition = Arrays.asList(status.split(","));
                return root.get("status").in(condition);
            }
        };
    }

    /** priority:in */
    static Specification<Todo> priorityContains(String priority) {
        return StringUtils.isEmpty(priority) ? null : new Specification<Todo>() {
            @Override
            public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<String> condition = Arrays.asList(priority.split(","));
                return root.get("priority").in(condition);
            }
        };
    }

}

package com.dev_training.service;

import com.dev_training.entity.Account;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * アカウント検索条件。
 */
class AccountSpecifications {
    static Specification<Account> accountIdContains(String accountId) {
        return StringUtils.isEmpty(accountId) ? null : new Specification<Account>() {
            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("accountId"), "%" + accountId + "%");
            }
        };
    }

    static Specification<Account> nameContains(String name) {
        return StringUtils.isEmpty(name) ? null : new Specification<Account>() {
            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("name"), "%" + name + "%");
            }
        };
    }

    static Specification<Account> emailContains(String email) {
        return StringUtils.isEmpty(email) ? null : new Specification<Account>() {
            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("email"), "%" + email + "%");
            }
        };
    }

    static Specification<Account> deleteFlag() {
        return new Specification<Account>() {
            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("deleteFlag"), false);
            }
        };
    }
}

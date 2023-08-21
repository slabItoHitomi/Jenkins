package com.dev_training.controller;

import com.dev_training.extended_entity.ExtendedComment;
import com.dev_training.service.CommentSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * コメント検索コントローラ。
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentSearchController {

    /** コメント検索サービス */
    private final CommentSearchService service;

    @Autowired
    public CommentSearchController(CommentSearchService commentSearchService) {
        this.service = commentSearchService;
    }

    /**
     * コメント検索。
     *
     * @return コメント検索結果
     */
    @RequestMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<ExtendedComment> search() {
        return  service.findComment();
    }
}

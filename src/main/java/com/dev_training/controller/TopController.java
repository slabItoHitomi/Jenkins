package com.dev_training.controller;

import com.dev_training.common.CodeValue;
import com.dev_training.entity.Account;
import com.dev_training.entity.Todo;
import com.dev_training.form.TodoSearchForm;
import com.dev_training.service.TodoSearchService;
import com.dev_training.service.TopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;


/**
 * トップコントローラ。
 */
@Controller
@RequestMapping("/top")
public class TopController {

    /**トップサービス*/
    private final TopService service;
    /** HTTPセッション */
    private final HttpSession session;
    /** セッションキー(ログインユーザのアカウント) */
    private static final String SESSION_FORM_ID = "account";

    /** TODO検索サービス */
    private final TodoSearchService todoSearchService;
    /** コード値 */
    private final CodeValue codeValue;

    @Autowired
    public TopController(TopService topService, HttpSession session, TodoSearchService todoSearchService, CodeValue codeValue) {
        this.service = topService;
        this.session = session;
        this.todoSearchService = todoSearchService;
        this.codeValue = codeValue;
    }

    /**
     * ログイン成功時処理。
     *
     * @return Path
     */
    @RequestMapping(value = "loginSuccess")
    public String loginSuccess() {
        return "redirect:/top";
    }

    /**
     * トップ画面表示。
     *
     * @param account 認証されたアカウント
     * @param model モデル
     * @return Path
     */
    @RequestMapping(value = "")
    public String init(@AuthenticationPrincipal Account account, Model model) {
        // 初回のアクセスなら、アカウントを検索してセッションに格納する
        if (Objects.isNull(session.getAttribute(SESSION_FORM_ID))) {
            Account sessionAccount = service.getAccountById(account.getId());
            session.setAttribute(SESSION_FORM_ID, sessionAccount);
        }

        TodoSearchForm todoSearchForm = new TodoSearchForm();
        // 担当者にAccountIDをSetして検索
        todoSearchForm.setPersonInChargeId(Integer.toString(account.getId()));
        // ステータスが「新規」か「着手中」を格納
        todoSearchForm.setSelectedStatus("4,5");
        List<Todo> list = todoSearchService.findTodo(todoSearchForm);
        model.addAttribute("list", list);
        // ステータスプルダウンの初期化
        model.addAttribute("allStatus", codeValue.getStatus());
        // 優先度プルダウンの初期化
        model.addAttribute("allPriority", codeValue.getPriority());

        return "top/topForm";
    }
}

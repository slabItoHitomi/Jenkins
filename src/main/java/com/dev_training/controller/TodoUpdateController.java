package com.dev_training.controller;

import com.dev_training.common.CodeValue;
import com.dev_training.entity.Account;
import com.dev_training.entity.Todo;
import com.dev_training.form.TodoUpdateForm;
import com.dev_training.service.TodoUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * TODO更新コントローラ。
 */
@Controller
@RequestMapping(value = "/todo/update")
public class TodoUpdateController {

    /** TODO更新サービス */
    private final TodoUpdateService service;
    /** コード値 */
    private final CodeValue codeValue;
    /** フォーム名 */
    private static final String FORM_NAME = "todoUpdateForm";
    /** メッセージソース */
    private final MessageSource messageSource;

    @Autowired
    public TodoUpdateController(TodoUpdateService todoUpdateService, CodeValue codeValue, MessageSource messageSource) {
        this.service = todoUpdateService;
        this.codeValue = codeValue;
        this.messageSource = messageSource;
    }

    /**
     * TODO更新-初期表示。
     *
     * TODO詳細画面からの連携時にマッピングさせる。
     *
     * @param model モデル
     * @return Path
     */
    @RequestMapping(value = "/init")
    public String updateInit(@RequestParam String todoId, Model model) {
        // 遷移元から更新対象のTODOIDが正しく渡ってこなければ、エラー表示。
        if (Objects.isNull(todoId) || StringUtils.isEmpty(todoId)) {
            model.addAttribute("errorMsg", messageSource.getMessage("validation.invalid.screen.transition", null, Locale.JAPAN));
            return "common/commonError";
        }

        // TODOが存在しなければ、エラー表示。
        Todo todo = service.findById(Integer.parseInt(todoId));
        if (Objects.isNull(todo)) {
            model.addAttribute("errorMsg", messageSource.getMessage("validation.incorrect.specification.todo", null, Locale.JAPAN));
            return "common/commonError";
        }
        // 初期表示のため、Modelに詰める
        model.addAttribute("todoUpdateForm", todo);

        // 担当者選択用のプルダウンリスト
        List<Account> accounts = service.findAllAccount();
        model.addAttribute("accountList", accounts);
        // ステータスプルダウンの初期化
        model.addAttribute("allStatus", codeValue.getStatus());
        // 優先度プルダウンの初期化
        model.addAttribute("allPriority", codeValue.getPriority());

        return "todo/todoUpdateForm";
    }

    /**
     * TODO更新-初期表示。
     *
     * 戻るボタン、エラー時用。
     *
     * @param model モデル
     * @return Path
     */
    @RequestMapping(value = "/backInit")
    public String backInit(@ModelAttribute TodoUpdateForm todoUpdateForm, Model model) {
        // 担当者選択用のプルダウンリスト
        List<Account> accounts = service.findAllAccount();
        model.addAttribute("accountList", accounts);
        // ステータスプルダウンの初期化
        model.addAttribute("allStatus", codeValue.getStatus());
        // 優先度プルダウンの初期化
        model.addAttribute("allPriority", codeValue.getPriority());

        // リダイレクト元で設定されているエラーをモデルに格納して、画面に表示。
        String key = BindingResult.MODEL_KEY_PREFIX + FORM_NAME;
        if (model.asMap().containsKey("errors")) {
            model.addAttribute(key, model.asMap().get("errors"));
        }

        return "todo/todoUpdateForm";
    }

    /**
     * TODO更新-確認画面表示。
     *
     * @param todoUpdateForm 精査済みフォーム
     * @param bindingResult    精査結果
     * @param model            モデル
     * @param redirectAttributes redirectAttributes
     * @return Path
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String updateConfirm(@ModelAttribute @Validated TodoUpdateForm todoUpdateForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // BeanValidationのエラー確認
        if (bindingResult.hasErrors()) {
            return redirectToInit(todoUpdateForm, bindingResult, redirectAttributes);
        }

        // 日付の有効範囲精査
        if (service.isValidDate(todoUpdateForm.getStartDate(), todoUpdateForm.getEndDate())) {
            bindingResult.reject("validation.invalidDate", "default message");
            return redirectToInit(todoUpdateForm, bindingResult, redirectAttributes);
        }

        Todo target = service.findById(todoUpdateForm.getId());
        if (service.isNoChange(todoUpdateForm, target)){
            bindingResult.reject("validation.noChange","default message");
            return redirectToInit(todoUpdateForm, bindingResult, redirectAttributes);
        }

        // 選択されたIDに紐づくAccountを取得する。
        Optional<Account> issuePersonAccount = service.findAccountById(Integer.parseInt(todoUpdateForm.getIssuePersonId()));
        Optional<Account> inChargeAccount = service.findAccountById(Integer.parseInt(todoUpdateForm.getPersonInChargeId()));
        // バックグラウンドで削除されていたら、エラーとする。
        if (!issuePersonAccount.isPresent()){
            bindingResult.reject("validation.invalidAccount", new String[]{"起票者"}, "default message");
            return redirectToInit(todoUpdateForm, bindingResult, redirectAttributes);
        }
        if (!inChargeAccount.isPresent()){
            bindingResult.reject("validation.invalidAccount", new String[]{"担当者"}, "default message");
            return redirectToInit(todoUpdateForm, bindingResult, redirectAttributes);
        }

        // 確認画面に表示する氏名をセットする。
        model.addAttribute("issuePersonName",issuePersonAccount.get().getName());
        model.addAttribute("personInChargeName",inChargeAccount.get().getName());
        // 確認画面に表示するステータス、優先度をセットする。
        model.addAttribute("statusName", codeValue.getStatus().getStatus().get(todoUpdateForm.getStatus()));
        model.addAttribute("priorityName", codeValue.getPriority().getPriority().get(todoUpdateForm.getPriority()));

        return "todo/todoUpdateConfirmForm";
    }

    /**
     * TODO登録-完了画面表示。
     *
     * @param todoUpdateForm 精査済みフォーム
     * @param bindingResult    精査結果
     * @param redirectAttributes redirectAttributes
     * @return Path
     */
    @RequestMapping(value = "/do", params = "update", method = RequestMethod.POST)
    public String updateComplete(@Validated TodoUpdateForm todoUpdateForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // BeanValidationのエラー確認
        if (bindingResult.hasErrors()) {
            return redirectToInit(todoUpdateForm, bindingResult, redirectAttributes);
        }

        // 日付の有効範囲精査
        if (service.isValidDate(todoUpdateForm.getStartDate(), todoUpdateForm.getEndDate())) {
            bindingResult.reject("validation.invalidDate", "default message");
            return redirectToInit(todoUpdateForm, bindingResult, redirectAttributes);
        }

        // 選択されたIDに紐づくAccountを取得する。
        Optional<Account> issuePersonAccount = service.findAccountById(Integer.parseInt(todoUpdateForm.getIssuePersonId()));
        Optional<Account> inChargeAccount = service.findAccountById(Integer.parseInt(todoUpdateForm.getPersonInChargeId()));
        // バックグラウンドで削除されていたら、エラーとする。
        if (!issuePersonAccount.isPresent()){
            bindingResult.reject("validation.invalidAccount", new String[]{"起票者"}, "default message");
            return redirectToInit(todoUpdateForm, bindingResult, redirectAttributes);
        }
        if (!inChargeAccount.isPresent()){
            bindingResult.reject("validation.invalidAccount", new String[]{"担当者"}, "default message");
            return redirectToInit(todoUpdateForm, bindingResult, redirectAttributes);
        }

        // 更新するTODOの作成
        Todo todo = new Todo();
        todo.setId(todoUpdateForm.getId());
        todo.setTitle(todoUpdateForm.getTitle());
        todo.setDetail(todoUpdateForm.getDetail());
        todo.setRemarks(todoUpdateForm.getRemarks());
        todo.setStartDate(todoUpdateForm.getStartDate());
        todo.setEndDate(todoUpdateForm.getEndDate());
        todo.setIssuePersonId(Integer.parseInt(todoUpdateForm.getIssuePersonId()));
        todo.setPersonInChargeId(Integer.parseInt(todoUpdateForm.getPersonInChargeId()));
        todo.setStatus(todoUpdateForm.getStatus());
        todo.setPriority(todoUpdateForm.getPriority());
        // 更新処理
        service.update(todo);

        return "todo/todoUpdateCompleteForm";
    }

    /**
     * TODO更新-入力画面に戻る。
     *
     * @param todoUpdateForm フォーム
     * @return Path
     */
    @RequestMapping(value = "/do", params = "back", method = RequestMethod.POST)
    public String back(TodoUpdateForm todoUpdateForm, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("todoUpdateForm", todoUpdateForm);
        return "redirect:/todo/update/backInit";
    }

    /**
     * エラー時のリダイレクト処理。
     *
     * @param todoUpdateForm フォーム
     * @param bindingResult 精査結果
     * @param redirectAttributes redirectAttributes
     * @return リダイレクトURL
     */
    private String redirectToInit(@Validated TodoUpdateForm todoUpdateForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("todoUpdateForm", todoUpdateForm);
        redirectAttributes.addFlashAttribute("errors", bindingResult);
        return "redirect:/todo/update/backInit";
    }

}

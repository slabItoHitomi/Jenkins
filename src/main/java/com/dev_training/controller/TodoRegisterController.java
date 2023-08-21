package com.dev_training.controller;

import com.dev_training.common.CodeValue;
import com.dev_training.entity.Account;
import com.dev_training.entity.Todo;
import com.dev_training.form.TodoRegisterForm;
import com.dev_training.service.TodoRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * TODO登録コントローラ。
 */
@Controller
@RequestMapping(value = "/todo/register")
public class TodoRegisterController {

    /** TODO登録サービス */
    private final TodoRegisterService service;
    /** コード値 */
    private final CodeValue codeValue;
    /** フォーム名 */
    private static final String FORM_NAME = "todoRegisterForm";

    @Autowired
    public TodoRegisterController(TodoRegisterService todoRegisterService, CodeValue codeValue) {
        this.service = todoRegisterService;
        this.codeValue = codeValue;
    }

    /**
     * TODO登録-初期表示。
     *
     * @param model モデル
     * @return Path
     */
    @RequestMapping(value = "/init")
    public String registerInit(@ModelAttribute TodoRegisterForm todoRegisterForm, Model model) {
        // 全アカウントを取得し、担当者選択用のプルダウンリストを作成し、格納
        List<Account> accounts = service.findAllAccount();
        Map<Integer, String> accountMap = new HashMap<>();
        for (Account account :accounts) {
            accountMap.put(account.getId(), account.getName());
        }
        model.addAttribute("accountList", accountMap);
        // ステータスプルダウンを作成し、格納
        model.addAttribute("allStatus", codeValue.getStatus());
        // 優先度プルダウンの初期化を作成し、格納
        model.addAttribute("allPriority", codeValue.getPriority());

        // リダイレクト元で設定されているエラーをモデルに格納して、画面に表示
        String key = BindingResult.MODEL_KEY_PREFIX + FORM_NAME;
        if (model.asMap().containsKey("errors")) {
            model.addAttribute(key, model.asMap().get("errors"));
        }
        return "todo/todoRegisterForm";
    }

    /**
     * TODO登録-確認画面表示。
     *
     * @param todoRegisterForm 精査済みフォーム
     * @param bindingResult    精査結果
     * @param model            モデル
     * @param redirectAttributes redirectAttributes
     * @return Path
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String registerConfirm(@ModelAttribute @Validated TodoRegisterForm todoRegisterForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // BeanValidationのエラー確認
        if (bindingResult.hasErrors()) {
            return redirectToInit(todoRegisterForm, bindingResult, redirectAttributes);
        }

        // 日付の前後関係精査
        if (service.isValidDate(todoRegisterForm.getStartDate(), todoRegisterForm.getEndDate())) {
            bindingResult.reject("validation.invalidDate", "default message");
            return redirectToInit(todoRegisterForm, bindingResult, redirectAttributes);
        }

        // 選択されたIDに紐づくAccountを取得する
        Optional<Account> issuePersonAccount = service.findAccountById(Integer.parseInt(todoRegisterForm.getIssuePersonId()));
        Optional<Account> inChargeAccount = service.findAccountById(Integer.parseInt(todoRegisterForm.getPersonInChargeId()));
        // バックグラウンドで削除されていたら、エラーとする
        if (!issuePersonAccount.isPresent()){
            bindingResult.reject("validation.invalidAccount", new String[]{"起票者"}, "default message");
            return redirectToInit(todoRegisterForm, bindingResult, redirectAttributes);
        }
        if (!inChargeAccount.isPresent()){
            bindingResult.reject("validation.invalidAccount", new String[]{"担当者"}, "default message");
            return redirectToInit(todoRegisterForm, bindingResult, redirectAttributes);
        }

        // 確認画面に表示する氏名をセットする
        model.addAttribute("issuePersonName",issuePersonAccount.get().getName());
        model.addAttribute("personInChargeName",inChargeAccount.get().getName());
        // 確認画面に表示するステータス、優先度をセットする
        model.addAttribute("statusName", codeValue.getStatus().getStatus().get(todoRegisterForm.getStatus()));
        model.addAttribute("priorityName", codeValue.getPriority().getPriority().get(todoRegisterForm.getPriority()));

        return "todo/todoRegisterConfirmForm";
    }

    /**
     * TODO登録-完了画面表示。
     *
     * @param todoRegisterForm 精査済みフォーム
     * @param bindingResult    精査結果
     * @param redirectAttributes redirectAttributes
     * @return Path
     */
    @RequestMapping(value = "/do", params = "register", method = RequestMethod.POST)
    public String registerComplete(@ModelAttribute @Validated TodoRegisterForm todoRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // BeanValidationのエラー確認
        if (bindingResult.hasErrors()) {
            return redirectToInit(todoRegisterForm, bindingResult, redirectAttributes);
        }

        // 日付の有効範囲精査
        if (service.isValidDate(todoRegisterForm.getStartDate(), todoRegisterForm.getEndDate())) {
            bindingResult.reject("validation.invalidDate", "default message");
            return redirectToInit(todoRegisterForm, bindingResult, redirectAttributes);
        }

        // 選択されたIDに紐づくAccountを取得する
        Optional<Account> issuePersonAccount = service.findAccountById(Integer.parseInt(todoRegisterForm.getIssuePersonId()));
        Optional<Account> inChargeAccount = service.findAccountById(Integer.parseInt(todoRegisterForm.getPersonInChargeId()));
        // バックグラウンドで削除されていたら、エラーとする
        if (!issuePersonAccount.isPresent()){
            bindingResult.reject("validation.invalidAccount", new String[]{"起票者"}, "default message");
            return redirectToInit(todoRegisterForm, bindingResult, redirectAttributes);
        }
        if (!inChargeAccount.isPresent()){
            bindingResult.reject("validation.invalidAccount", new String[]{"担当者"}, "default message");
            return redirectToInit(todoRegisterForm, bindingResult, redirectAttributes);
        }

        // 登録するTODOの作成
        Todo todo = new Todo();
        todo.setTitle(todoRegisterForm.getTitle());
        todo.setDetail(todoRegisterForm.getDetail());
        todo.setRemarks(todoRegisterForm.getRemarks());
        todo.setStartDate(todoRegisterForm.getStartDate());
        todo.setEndDate(todoRegisterForm.getEndDate());
        todo.setIssuePersonId(Integer.parseInt(todoRegisterForm.getIssuePersonId()));
        todo.setPersonInChargeId(Integer.parseInt(todoRegisterForm.getPersonInChargeId()));
        todo.setStatus(todoRegisterForm.getStatus());
        todo.setPriority(todoRegisterForm.getPriority());
        // 登録処理
        service.register(todo);

        return "todo/todoRegisterCompleteForm";
    }

    /**
     * TODO登録-入力画面に戻る。
     *
     * @param todoRegisterForm TODO登録フォーム。
     * @return Path
     */
    @RequestMapping(value = "/do", params = "registerBack", method = RequestMethod.POST)
    public String registerBack(TodoRegisterForm todoRegisterForm, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("todoRegisterForm", todoRegisterForm);
        return "redirect:/todo/register/init";
    }

    /**
     * エラー時のリダイレクト処理。
     *
     * @param todoRegisterForm フォーム
     * @param bindingResult 精査結果
     * @param redirectAttributes redirectAttributes
     * @return リダイレクトURL
     */
    private String redirectToInit(@Validated TodoRegisterForm todoRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // TODOのフォームをリダイレクト先に引き継ぐ
        redirectAttributes.addFlashAttribute("todoRegisterForm", todoRegisterForm);
        // エラー情報をリダイレクト先に引き継ぐ
        redirectAttributes.addFlashAttribute("errors", bindingResult);
        return "redirect:/todo/register/init";
    }

}

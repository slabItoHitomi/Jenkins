package com.dev_training.controller;

import com.dev_training.entity.Account;
import com.dev_training.form.AccountUpdateForm;
import com.dev_training.service.AccountUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * アカウント情報更新コントローラ。
 */
@Controller
@RequestMapping(value = "/account/update")
public class AccountUpdateController {

    /** アカウント情報更新サービス */
    private final AccountUpdateService service;
    /** HTTPセッション */
    private final HttpSession session;
    /** セッションキー(ログインユーザのアカウント) */
    private static final String SESSION_FORM_ID = "account";

    @Autowired
    public AccountUpdateController(AccountUpdateService accountUpdateService, HttpSession session) {
        this.service = accountUpdateService;
        this.session = session;
    }

    /**
     * アカウント情報更新-初期表示。
     *
     * @param model モデル
     * @return Path
     */
    @RequestMapping(value = "/init")
    public String updateInit(Model model) {
        // セッションに格納されているアカウントをもとに、DBから最新のアカウントを取得してModelに格納する。
        Account account = (Account) session.getAttribute(SESSION_FORM_ID);
        Account targetAccount = service.getAccountById(account.getId());
        model.addAttribute("accountUpdateForm", targetAccount);
        return "account/accountUpdateForm";
    }

    /**
     * アカウント情報更新-確認画面表示。
     *
     * @param accountUpdateForm 精査済みフォーム
     * @param bindingResult     精査結果
     * @param model             モデル
     * @return Path
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String confirm(@ModelAttribute @Validated AccountUpdateForm accountUpdateForm, BindingResult bindingResult, Model model) {
        // BeanValidationのエラー確認
        if (bindingResult.hasErrors()) {
            return "account/accountUpdateForm";
        }
        Account account = (Account) session.getAttribute(SESSION_FORM_ID);
        Account targetAccount = service.getAccountById(account.getId());

        // 更新有無チェック。何も更新されていなければエラーとする。
        if (service.isNoChange(accountUpdateForm, targetAccount)) {
            bindingResult.reject("validation.noChange", "default message");
            return "account/accountUpdateForm";
        }
        // アカウントIDの重複精査
       String accountId = accountUpdateForm.getAccountId();
        if (!accountId.equals(targetAccount.getAccountId())) {
            if (service.isExistsAccountId(accountId)) {
                bindingResult.rejectValue("accountId", "validation.duplicate", new String[]{"アカウントID"}, "default message");
                return "account/accountUpdateForm";
            }
        }
        return "account/accountUpdateConfirmForm";
    }

    /**
     * アカウント情報更新-完了画面表示。
     *
     * @param accountUpdateForm 精査済みフォーム
     * @param bindingResult     精査結果
     * @return Path
     */
    @RequestMapping(value = "/do", params = "update", method = RequestMethod.POST)
    public String doUpdate(@ModelAttribute @Validated AccountUpdateForm accountUpdateForm, BindingResult bindingResult) {
        // BeanValidationのエラー確認
        if (bindingResult.hasErrors()) {
            return "account/accountUpdateForm";
        }
        Account account = (Account) session.getAttribute(SESSION_FORM_ID);
        Account targetAccount = service.getAccountById(account.getId());

        // 更新有無チェック。何も更新されていなければエラーとする。
        if (service.isNoChange(accountUpdateForm, targetAccount)) {
            bindingResult.reject("validation.noChange", "default message");
            return "account/accountUpdateForm";
        }
        // アカウントIDの重複精査
        String accountId = accountUpdateForm.getAccountId();
        if (!accountId.equals(targetAccount.getAccountId())) {
            if (service.isExistsAccountId(accountId)) {
                bindingResult.rejectValue("accountId", "validation.duplicate", new String[]{"アカウントID"}, "default message");
                return "account/accountUpdateForm";
            }
        }

        // 更新用アカウントの作成
        targetAccount.setAccountId(accountUpdateForm.getAccountId());
        targetAccount.setName(accountUpdateForm.getName());
        targetAccount.setEmail(accountUpdateForm.getEmail());
        targetAccount.setSelfIntroduction(accountUpdateForm.getSelfIntroduction());
        // 更新処理
        service.updateAccountById(targetAccount);
        // セッション情報の更新
        Account sessionAccount = service.getAccountById(targetAccount.getId());
        session.setAttribute(SESSION_FORM_ID, sessionAccount);
        return "account/accountUpdateCompleteForm";
    }

    /**
     * アカウント情報更新-入力画面に戻る。
     *
     * @param accountUpdateForm フォーム。
     * @return Path
     */
    @RequestMapping(value = "/do", params = "back", method = RequestMethod.POST)
    public String back(@ModelAttribute AccountUpdateForm accountUpdateForm) {
        return "account/accountUpdateForm";
    }

}

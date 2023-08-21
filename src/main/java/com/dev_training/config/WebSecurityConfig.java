
package com.dev_training.config;

import com.dev_training.service.JpaUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Webセキュリティコンフィグ。
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        // 認証状態によらず許可するパス
        web.ignoring().antMatchers("/favicon.ico", "/css/**", "/js/**", "/bootstrap/css/**", "/bootstrap/js/**", "/jquery/**", "/images/**", "/fonts/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // SessionFixation対策
        http.sessionManagement().sessionFixation().newSession();

        http.authorizeRequests()
                // 認証状態によらず許可するURL
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/account/register/**").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login") // ログインページのパス
                .loginProcessingUrl("/login") // 認証処理を起動させるパス
                .failureUrl("/login/?error") // ログイン処理失敗時の遷移先
                .successForwardUrl("/top/loginSuccess") // ログイン成功時の繊維先
                .usernameParameter("login_id")// ユーザid
                .passwordParameter("login_password").permitAll(); // パスワード

        http.logout()
                .logoutUrl("/logout") // ログアウト処理を起動させるパス
                .logoutSuccessUrl("/login") // ログアウト完了時のパス
                .deleteCookies("JSESSIONID", "SESSION")
                .invalidateHttpSession(true).permitAll();

    }

    @Configuration
    protected static class AuthenticationConfiguration
            extends GlobalAuthenticationConfigurerAdapter {

        final JpaUserDetailsServiceImpl userDetailsService;

        @Autowired
        public AuthenticationConfiguration(JpaUserDetailsServiceImpl userDetailsService) {
            this.userDetailsService = userDetailsService;
        }

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            // 認証するユーザーを設定する
            auth.userDetailsService(userDetailsService)
                    // 入力値をbcryptでハッシュ化した値でパスワード認証を行う
                    .passwordEncoder(new BCryptPasswordEncoder());
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
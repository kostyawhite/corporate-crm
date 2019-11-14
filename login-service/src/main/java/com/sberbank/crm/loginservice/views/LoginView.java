package com.sberbank.crm.loginservice.views;

import com.sberbank.crm.loginservice.security.SecurityUtils;
import com.sberbank.crm.loginservice.service.UserService;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Route(value = LoginView.ROUTE)
@PageTitle(value = LoginView.PAGE_TITLE)
public class LoginView extends VerticalLayout
        implements BeforeEnterObserver, HasUrlParameter<String> {

    public static final String ROUTE = "login";
    public static final String PAGE_TITLE = "Вход";

    private LoginForm login = new LoginForm();
    private Label label = new Label("");

    private UserService userService;

    @Autowired
    public LoginView(UserService userService) {
        this.userService = userService;

        H1 mainHeader = new H1("Менеджер документов");
        H2 formTitle = new H2("Вход в систему");

        LoginI18n i18n = LoginI18n.createDefault();

        i18n.setForm(new LoginI18n.Form());
        i18n.getForm().setSubmit("Войти");
        i18n.getForm().setUsername("Логин или почта");
        i18n.getForm().setPassword("Пароль");

        i18n.setErrorMessage(new LoginI18n.ErrorMessage());
        i18n.getErrorMessage().setTitle("Неверный логин/почта или пароль");
        i18n.getErrorMessage().setMessage("Убедитесь, что вы ввели правильный логин/почту или пароль, и повторите попытку.");

        login.setI18n(i18n);
        login.setForgotPasswordButtonVisible(false);
        login.setAction("login");

        Anchor anchor = new Anchor("/registration", "Регистрация");

        setAlignItems(Alignment.CENTER);
        add(mainHeader, formTitle, login, anchor, label);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        // Возвращает пользователя к главной странице
        // если он уже залогинился
        if (SecurityUtils.isUserLoggedIn()) {
            event.forwardTo(MainView.class);
        }

        // Кидает ошибку в форму
        // если неверные данные для входа
        if(!event.getLocation().getQueryParameters().getParameters().getOrDefault("error", Collections.emptyList()).isEmpty()) {
            login.setError(true);
        }
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String parameter) {
        Map<String, List<String>> parametersMap = beforeEvent.getLocation().getQueryParameters().getParameters();
        if (parametersMap.containsKey("confirmKey")) {
            String confirmKey = parametersMap.get("confirmKey").get(0);
            if (userService.activeUser(confirmKey)) {
                label.setText("Пользователь активирован");
            } else {
                label.setText("Пользователь уже был активирован");
            }
        }
    }
}

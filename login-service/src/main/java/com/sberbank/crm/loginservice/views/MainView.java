package com.sberbank.crm.loginservice.views;

import com.sberbank.crm.loginservice.security.SecurityUtils;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

@Route(value = MainView.ROUTE)
@Secured("ROLE_USER")
public class MainView extends VerticalLayout {
    public static final String ROUTE = "";

    public MainView() {
        Label label = new Label(String.format("Вы вошли как %s", SecurityUtils.getUsername()));
        Anchor logoutLink = new Anchor("logout", "Выйти");

        setAlignItems(Alignment.CENTER);

        add(label, logoutLink);
    }

}
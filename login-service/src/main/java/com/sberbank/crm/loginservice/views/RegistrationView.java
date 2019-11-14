package com.sberbank.crm.loginservice.views;

import com.sberbank.crm.loginservice.entities.Role;
import com.sberbank.crm.loginservice.entities.User;
import com.sberbank.crm.loginservice.service.RoleService;
import com.sberbank.crm.loginservice.service.UserService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Binder.Binding;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


@Route(value = RegistrationView.ROUTE)
@PageTitle(value = RegistrationView.PAGE_TITLE)
public class RegistrationView extends VerticalLayout {
    public static final String ROUTE = "registration";
    public static final String PAGE_TITLE = "Регистрация";

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public RegistrationView(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;

        FormLayout registrationForm = new FormLayout();

        Binder<User> userBinder = new Binder<>();
        Binder<Role> roleBinder = new Binder<>();
        Binding<User, String> passwordBinder;
        Binding<User, String> confirmPasswordBinder;

        User user = new User();
        Role role = new Role();

        H2 formTitle = new H2("Регистрация");
        TextField emailField = new TextField("Почта", "mail@mail.ru");
        TextField userNameField = new TextField("Логин", "username");
        Select<String> rolesDescription = new Select<>();
        PasswordField passwordField = new PasswordField("Пароль");
        PasswordField confirmPasswordField = new PasswordField("Повторите пароль");
        Button signUpButton = new Button("Зарегистрироваться");
        Label label = new Label("");

        emailField.setValueChangeMode(ValueChangeMode.EAGER);
        userNameField.setValueChangeMode(ValueChangeMode.EAGER);
        passwordField.setValueChangeMode(ValueChangeMode.EAGER);
        confirmPasswordField.setValueChangeMode(ValueChangeMode.EAGER);

        emailField.setRequiredIndicatorVisible(true);
        userNameField.setRequiredIndicatorVisible(true);
        rolesDescription.setRequiredIndicatorVisible(true);
        passwordField.setRequiredIndicatorVisible(true);
        confirmPasswordField.setRequiredIndicatorVisible(true);

        rolesDescription.setLabel("Кто вы?");
        rolesDescription.setItems(roleService.getAllRolesDescription());
        rolesDescription.setEmptySelectionAllowed(true);
        rolesDescription.setEmptySelectionCaption("Выберите роль");
        rolesDescription.addComponents(null, new Hr());

        passwordField.setMinLength(8);

        signUpButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        signUpButton.addClickShortcut(Key.ENTER);


        SerializablePredicate<String> confirmPasswordPredicate = value ->
                Objects.equals(passwordField.getValue(), confirmPasswordField.getValue());

        userBinder.forField(emailField)
                .withValidator(new RegexpValidator("Неверный формат почты",
                        "[a-zA-Z0-9]+[._a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]*[a-zA-Z]*@[a-zA-Z0-9]{2,8}.[a-zA-Z.]{2,6}"))
                .bind(User::getEmail, User::setEmail);
        userBinder.forField(userNameField)
                .asRequired("Заполните логин пользователя")
                .withValidator(new RegexpValidator("Неверный формат логина",
                        "[a-zA-Z0-9_-]+"))
                .bind(User::getName, User::setName);
        roleBinder.forField(rolesDescription)
                .asRequired("Выберите одну из ролей")
                .bind(Role::getDescription, Role::setDescription);
        passwordBinder = userBinder.forField(passwordField)
                .withValidator(confirmPasswordPredicate, "Пароль не совпадает")
                .withValidator(min -> min.length() >= 8, "Пароль меньше 8 знаков")
                .bind(User::getPassword, User::setPassword);
        confirmPasswordBinder = userBinder.forField(confirmPasswordField)
                .withValidator(confirmPasswordPredicate, "Пароль не совпадает")
                .bind(User::getPassword, (u, p) -> {});

        signUpButton.addClickListener(event -> {
            if (userBinder.writeBeanIfValid(user) && roleBinder.writeBeanIfValid(role)) {
                if(!userService.isUserExist(user)) {
                    userService.addUser(user, role.getDescription());
                    label.setText(String.format("Пользователь %s успешно зарегистрирован!\n" +
                            "Письмо с активацией направлено на почту", user.getName()));
                } else {
                    label.setText("Данный пользователь уже существует!");
                }
            } else {
                userBinder.validate();
                roleBinder.validate();
            }
        });

        passwordField.addValueChangeListener(value -> confirmPasswordBinder.validate());
        confirmPasswordField.addValueChangeListener(value -> passwordBinder.validate());


        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER,
                registrationForm, formTitle);
        registrationForm.setResponsiveSteps(new FormLayout.ResponsiveStep("200px", 1));
        registrationForm.setWidth("312px");
        registrationForm.add(
                emailField, userNameField,
                rolesDescription,
                passwordField, confirmPasswordField,
                signUpButton, label);

        add(formTitle, registrationForm);
    }
}

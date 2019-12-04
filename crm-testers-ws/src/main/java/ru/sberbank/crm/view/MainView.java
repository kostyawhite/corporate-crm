package ru.sberbank.crm.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.shared.communication.PushMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.crm.entity.Department;
import ru.sberbank.crm.entity.Task;
import ru.sberbank.crm.service.CommunicationService;
import ru.sberbank.crm.service.TaskService;
import ru.sberbank.crm.util.Broadcaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Push(PushMode.MANUAL)
@Route(value = "")
@PageTitle(value = "Менеджер задач")
public class MainView extends VerticalLayout {
    private final String webServiceTitle = "Отдел тестировщиков";
    private final Long selfDepartmentId = 4L;

    private TaskService taskService;
    private CommunicationService communicationService;
    private Registration broadcasterRegistration;

    private VerticalLayout tasksLayout = new VerticalLayout();
    private H2 documentTextHeading = new H2();
    private Span documentTextLabel = new Span("Откройте документ задачи с помощью конпки \"Открыть документ\"");
    private Task currentTask;
    private Button documentUpdateTextFieldButton = new Button("Обновить документ");

    public MainView(@Autowired TaskService taskService, @Autowired CommunicationService communicationService) {
        this.taskService = taskService;
        this.communicationService = communicationService;

        // Заголовок веб-сервиса
        H1 webServiceTitleHeading = new H1(webServiceTitle);

        // Блок с задачами
        H2 tasksHeading = new H2("Задачи");
        tasksLayout.setHorizontalComponentAlignment(Alignment.CENTER, tasksHeading);
        tasksLayout.add(tasksHeading);
        for (Task task : taskService.getAllTasks()) {
            tasksLayout.add(getTaskLayout(task));
        }

        // Форма с документом
        VerticalLayout documentLayout = new VerticalLayout();
        H2 documentHeading = new H2("Документ задачи");

        // Часть с тектсом документа
        VerticalLayout documentTextLayout = new VerticalLayout();
        documentTextLabel.getStyle().set("white-space", "pre-wrap");
        documentTextLayout.getStyle().set("background", "#FFFFFF");
        documentTextLayout.getStyle().set("border-radius", "20px");
        documentTextLayout.getStyle().set("padding", "30px 40px 40px 40px");
        documentTextLayout.getStyle().set("box-shadow", "0px 10px 10px rgba(229, 229, 229, 0.5)");
        documentTextLayout.setPadding(true);
        documentTextLayout.setHeightFull();
        documentTextLayout.add(documentTextHeading, documentTextLabel);

        // Часть с обновлением документа
        HorizontalLayout documentUpdateTextLayout = new HorizontalLayout();
        TextField documentUpdateTextField = new TextField();
        documentUpdateTextField.setValue("Введите текст для документа");
        documentUpdateTextField.setClearButtonVisible(true);
        documentUpdateTextField.setWidth("80%");
        // TODO binder updateTextField
        documentUpdateTextFieldButton.setEnabled(false);
        documentUpdateTextFieldButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        documentUpdateTextFieldButton.addClickListener(clickEvent ->
                updateDocumentTextLabel(documentUpdateTextField.getValue()));
        documentUpdateTextFieldButton.setWidth("20%");
        documentUpdateTextLayout.setWidthFull();
        documentUpdateTextLayout.add(documentUpdateTextField, documentUpdateTextFieldButton);
        documentLayout.setHorizontalComponentAlignment(Alignment.CENTER, documentHeading);
        documentLayout.add(documentHeading, documentTextLayout, documentUpdateTextLayout);

        // Блок с разделителем для блока задач и документа
        SplitLayout splitLayout = new SplitLayout(tasksLayout, documentLayout);
        splitLayout.setHeightFull();
        splitLayout.setWidthFull();
        splitLayout.setSplitterPosition(30);

        getStyle().set("background", "#F9F9F9");
        setAlignItems(Alignment.CENTER);
        setPadding(true);
        setHeightFull();
        add(webServiceTitleHeading, splitLayout);
    }

    private VerticalLayout getTaskLayout(Task task) {
        Binder<Department> taskDepartmentsBinder = new Binder<>();
        Map<String, String> departments = new HashMap<>();
        for (Department department : communicationService.getDepartmentsFromRouter(selfDepartmentId, task.getTemplateId())) {
            departments.put(department.getDescription(), department.getName());
        }

        VerticalLayout taskLayout = new VerticalLayout();

        H2 taskTitle = new H2(task.getTitle());
        Label taskDescription = new Label(task.getDescription());

        Select<String> taskDepartmentsSelect = new Select<>();
        taskDepartmentsSelect.setLabel("Выбрать отдел");
        taskDepartmentsSelect.setItems(departments.keySet());
        taskDepartmentsSelect.setRequiredIndicatorVisible(true);
        taskDepartmentsSelect.setEmptySelectionAllowed(true);
        taskDepartmentsSelect.setEmptySelectionCaption("Выберите отдел");
        taskDepartmentsSelect.addComponents(null, new Hr());
        taskDepartmentsBinder.forField(taskDepartmentsSelect)
                .asRequired("Выберите один из отделов")
                .bind(Department::getDescription, Department::setDescription);

        HorizontalLayout taskButtonsLayout = new HorizontalLayout();
        Button sendTaskButton = new Button("Отправить задачу");
        sendTaskButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendTaskButton.addClickListener(clickEvent -> {
            if (taskDepartmentsBinder.isValid()) {
                sendTask(task, departments.get(taskDepartmentsSelect.getValue()));
                VerticalLayout verticalLayout = (VerticalLayout) taskLayout.getParent().get();
                verticalLayout.remove(taskLayout);
                documentUpdateTextFieldButton.setEnabled(false);
                documentTextHeading.setText("");
                documentTextLabel.setText("Откройте документ задачи с помощью конпки \"Открыть документ\"");
                taskService.deleteTask(task.getId());
            } else {
                taskDepartmentsBinder.validate();
            }
        });

        Button openTaskDocumentButton = new Button("Открыть документ");
        openTaskDocumentButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        openTaskDocumentButton.addClickListener(clickEvent -> setDocumentFromTask(task));
        taskButtonsLayout.add(sendTaskButton, openTaskDocumentButton);

        taskLayout.getStyle().set("background", "#FFFFFF");
        taskLayout.getStyle().set("border-radius", "20px");
        taskLayout.getStyle().set("padding", "30px 40px 40px 40px");
        taskLayout.getStyle().set("box-shadow", "0px 10px 10px rgba(229, 229, 229, 0.5)");
        taskLayout.setWidthFull();
        taskLayout.add(taskTitle, taskDescription, taskDepartmentsSelect, taskButtonsLayout);

        return taskLayout;
    }

    private void sendTask(Task task, String departmentName) {
        communicationService.sendTaskToDepartmentViaRouter(task, departmentName);
    }

    private void setDocumentFromTask(Task task) {
        documentUpdateTextFieldButton.setEnabled(true);
        currentTask = task;
        documentTextHeading.setText(currentTask.getTitle());
        documentTextLabel.setText(currentTask.getText());
    }

    private void updateDocumentTextLabel(String appendText) {
        String resultText = String.format("%s%s -> %s\n", currentTask.getText(), webServiceTitle, appendText);
        documentTextLabel.setText(resultText);
        currentTask.setText(resultText);
        taskService.saveTask(currentTask);
    }


    // Для динамического обновления
    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI ui = attachEvent.getUI();
        broadcasterRegistration = Broadcaster.register(newTask ->
                ui.access(() -> {
                    tasksLayout.add(getTaskLayout(newTask));
                    ui.push();
                }));
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        broadcasterRegistration.remove();
        broadcasterRegistration = null;
    }
}

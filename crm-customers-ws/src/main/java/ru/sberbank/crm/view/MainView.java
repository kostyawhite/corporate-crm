package ru.sberbank.crm.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.shared.communication.PushMode;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sberbank.crm.entity.Department;
import ru.sberbank.crm.entity.Task;
import ru.sberbank.crm.entity.TaskState;
import ru.sberbank.crm.entity.Template;
import ru.sberbank.crm.service.CommunicationService;
import ru.sberbank.crm.service.TaskService;
import ru.sberbank.crm.service.TaskStateService;
import ru.sberbank.crm.service.TemplateService;
import ru.sberbank.crm.util.TaskBroadcaster;

import java.util.HashMap;
import java.util.Map;


@Push(PushMode.MANUAL)
@Route(value = "")
@PageTitle(value = "Менеджер задач")
public class MainView extends VerticalLayout {
    private final String webServiceTitle = "Отдел клиентов";
    private final Long selfDepartmentId = 2L;

    private TaskService taskService;
    private TaskStateService taskStateService;
    private CommunicationService communicationService;
    private TemplateService templateService;
    private Registration broadcasterRegistration;

    private VerticalLayout tasksLayout = new VerticalLayout();
    private H2 documentTextHeading = new H2();
    private Span documentTextLabel = new Span("Откройте документ задачи с помощью конпки \"Открыть документ\"");
    private Task currentTask;
    private Button documentUpdateTextFieldButton = new Button("Обновить документ");

    @Autowired
    public MainView(TaskService taskService,
                    TaskStateService taskStateService,
                    CommunicationService communicationService,
                    TemplateService templateService) {
        this.taskService = taskService;
        this.taskStateService = taskStateService;
        this.communicationService = communicationService;
        this.templateService = templateService;

        // Заголовок веб-сервиса
        H1 webServiceTitleHeading = new H1(webServiceTitle);

        // Добавление задач (только у клиентов)
        Dialog addTasksDialog = getTaskDialog();

        HorizontalLayout routerLayout = new HorizontalLayout();
        Button addNewTask = new Button("Создать задачу");
        addNewTask.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addNewTask.addClickListener(clickEvent -> addTasksDialog.open());
        Anchor checkTasksStatus = new Anchor("task-status","Отслеживать задачи");
        Anchor createTemplate = new Anchor("create-template","Созать шаблон");
        routerLayout.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.CENTER);
        routerLayout.add(addNewTask, checkTasksStatus, createTemplate);

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
        add(webServiceTitleHeading, routerLayout, splitLayout);
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

        Button deleteTaskButton = new Button("Удалить задачу");
        deleteTaskButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
        deleteTaskButton.addClickListener(clickEvent -> {
            taskService.deleteTask(task.getId());
            VerticalLayout verticalLayout = (VerticalLayout) taskLayout.getParent().get();
            verticalLayout.remove(taskLayout);

            taskStateService.deleteTask(task.getId());
        });

        taskLayout.getStyle().set("background", "#FFFFFF");
        taskLayout.getStyle().set("border-radius", "20px");
        taskLayout.getStyle().set("padding", "30px 40px 40px 40px");
        taskLayout.getStyle().set("box-shadow", "0px 10px 10px rgba(229, 229, 229, 0.5)");
        taskLayout.setWidthFull();
        taskLayout.add(taskTitle, taskDescription, taskDepartmentsSelect, taskButtonsLayout, deleteTaskButton);

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

    private Dialog getTaskDialog() {
        Map<String, Long> templates = new HashMap<>();
        for (Template template : templateService.getAllTemplates()) {
            templates.put(template.getDescription(), template.getId());
        }

        Dialog addTaskDialog = new Dialog();
        FormLayout taskForm = new FormLayout();

        Binder<Task> taskBinder = new Binder<>();
        Binder<Template> templateBinder = new Binder<>();

        H2 taskHeading = new H2("Новая задача");
        TextField taskTitle = new TextField("Название задачи", "Задача 1");
        TextArea taskDescription = new TextArea("Описание задачи", "Новая задача для выполения");
        Select<String> taskTemplatesDescription = new Select<>();
        Button addTaskButton = new Button("Добавить задачу");

        taskTitle.setValueChangeMode(ValueChangeMode.EAGER);
        taskDescription.setValueChangeMode(ValueChangeMode.EAGER);

        taskTitle.setRequiredIndicatorVisible(true);
        taskDescription.setRequiredIndicatorVisible(true);
        taskTemplatesDescription.setRequiredIndicatorVisible(true);

        taskTemplatesDescription.setLabel("Выберите шаблон");
        taskTemplatesDescription.setItems(templates.keySet());
        taskTemplatesDescription.setEmptySelectionAllowed(true);
        taskTemplatesDescription.setEmptySelectionCaption("Выберите шаблон");
        taskTemplatesDescription.addComponents(null, new Hr());

        addTaskButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        taskBinder.forField(taskTitle)
                .asRequired("Заполните название задачи")
                .bind(Task::getTitle, Task::setTitle);
        taskBinder.forField(taskDescription)
                .asRequired("Заполните название задачи")
                .bind(Task::getDescription, Task::setDescription);
        templateBinder.forField(taskTemplatesDescription)
                .asRequired("Выберите один из шаблонов")
                .bind(Template::getDescription, Template::setDescription);


        addTaskButton.addClickListener(event -> {
            if (taskBinder.isValid() && templateBinder.isValid()) {
                Task newTask = new Task();
                newTask.setTitle(taskTitle.getValue());
                newTask.setDescription(taskDescription.getValue());
                newTask.setText("");
                newTask.setTemplateId(templates.get(taskTemplatesDescription.getValue()));

                TaskState taskState = new TaskState();
                taskState.setTemplateId(newTask.getTemplateId());
                taskState.setPreviousDepartment("");
                taskState.setCurrentDepartment(webServiceTitle);
                taskState.setTitle(newTask.getTitle());
                taskStateService.saveTask(taskState);
                newTask.setId(taskState.getId());

                taskService.saveTask(newTask);
                TaskBroadcaster.broadcast(taskService.getTaskById(newTask.getId()));
            } else {
                taskBinder.validate();
                templateBinder.validate();
            }
        });


        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER,
                taskForm, taskHeading);
        taskForm.setResponsiveSteps(new FormLayout.ResponsiveStep("200px", 1));
        taskForm.add(taskHeading, taskTitle, taskDescription, taskTemplatesDescription, addTaskButton);

        addTaskDialog.add(taskForm);

        return addTaskDialog;
    }


    // Для динамического обновления
    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI ui = attachEvent.getUI();
        broadcasterRegistration = TaskBroadcaster.register(newTask ->
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

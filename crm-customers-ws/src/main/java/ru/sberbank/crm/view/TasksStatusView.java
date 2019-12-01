package ru.sberbank.crm.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sberbank.crm.component.GraphComponent;
import ru.sberbank.crm.entity.TaskState;
import ru.sberbank.crm.entity.Template;
import ru.sberbank.crm.service.TemplateService;
import ru.sberbank.crm.service.TaskStateService;

@Route(value = "task-status")
@PageTitle(value = "Состояние задач")
public class TasksStatusView extends VerticalLayout {

    private TaskStateService taskStateService;
    private TemplateService templateService;

    @Autowired
    public TasksStatusView(TaskStateService taskStateService, TemplateService templateService) {
        this.taskStateService = taskStateService;
        this.templateService = templateService;

        H1 taskStatusHeading = new H1("Состояние задач");

        ListBox<TaskState> tasksListBox = new ListBox<>();

        tasksListBox.setWidthFull();
        tasksListBox.setItems(taskStateService.getAllTasks());

        VerticalLayout tasksListLayout = new VerticalLayout();
        tasksListLayout.setWidth("15%");
        tasksListLayout.add(tasksListBox);

        VerticalLayout graphTemplateLayout = new VerticalLayout();

        H2 taskHeading = new H2("Выберите задачу из списка");
        VerticalLayout graphContainerLayout = new VerticalLayout();
        graphContainerLayout.setHeight("500px");
        graphContainerLayout.setWidth("1000px");
        graphTemplateLayout.getStyle().set("background", "#FFFFFF");
        graphTemplateLayout.getStyle().set("border-radius", "20px");
        graphTemplateLayout.getStyle().set("padding", "30px 40px 40px 40px");
        graphTemplateLayout.getStyle().set("box-shadow", "0px 10px 10px rgba(229, 229, 229, 0.5)");
        graphTemplateLayout.setPadding(true);
        graphTemplateLayout.setWidth("85%");
        graphTemplateLayout.setHeightFull();
        graphTemplateLayout.setHorizontalComponentAlignment(Alignment.CENTER, graphContainerLayout);
        Span previousTaskStateLabel = new Span();
        Span currentTaskStateLabel = new Span();
        graphTemplateLayout.add(taskHeading, graphContainerLayout, previousTaskStateLabel, currentTaskStateLabel);

        tasksListBox.addValueChangeListener(event ->
                setTemplatesGraph(taskHeading, graphContainerLayout, event.getValue(),
                        previousTaskStateLabel, currentTaskStateLabel));

        HorizontalLayout taskStateLayout = new HorizontalLayout();
        taskStateLayout.setHeightFull();
        taskStateLayout.setWidthFull();
        taskStateLayout.add(tasksListLayout, graphTemplateLayout);

        getStyle().set("background", "#F9F9F9");
        setAlignItems(Alignment.CENTER);
        setPadding(true);
        setHeightFull();
        add(taskStatusHeading, taskStateLayout);

    }

    private void setTemplatesGraph(H2 graphHeading, VerticalLayout graphContainer, TaskState taskState,
                                   Span previousSpan, Span currentSpan) {
        // TODO change graph id
        Template template = templateService.getTemplateById(taskState.getTemplateId());

        graphHeading.setText(taskState.getTitle());
        graphContainer.removeAll();
        GraphComponent graphComponent = new GraphComponent(template.getId());
        graphComponent.setData(template.getGraphNodes(), template.getGraphEdges());
        graphComponent.setCurrentElement(taskState.getCurrentDepartment());
        graphComponent.setPreviousElement(taskState.getPreviousDepartment());
        graphContainer.add(graphComponent);

        previousSpan.setText(String.format("Предыдущие местонахождение: %s", taskState.getPreviousDepartment()));
        currentSpan.setText(String.format("Текущее местонахождение: %s", taskState.getCurrentDepartment()));
    }
}

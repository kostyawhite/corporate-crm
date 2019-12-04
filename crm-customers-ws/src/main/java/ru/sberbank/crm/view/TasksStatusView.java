package ru.sberbank.crm.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
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

        HorizontalLayout graphContainerLegendLayout = new HorizontalLayout();

        H2 taskHeading = new H2("Выберите задачу из списка");
        VerticalLayout graphContainerLayout = new VerticalLayout();
        graphContainerLayout.setHeight("400px");
        graphContainerLayout.setWidth("900px");
        graphTemplateLayout.getStyle().set("background", "#FFFFFF");
        graphTemplateLayout.getStyle().set("border-radius", "20px");
        graphTemplateLayout.getStyle().set("padding", "30px 40px 40px 40px");
        graphTemplateLayout.getStyle().set("box-shadow", "0px 10px 10px rgba(229, 229, 229, 0.5)");
        graphTemplateLayout.setPadding(true);
        graphTemplateLayout.setWidth("85%");
        graphTemplateLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER,
                graphContainerLegendLayout);

        VerticalLayout graphLegend = new VerticalLayout();

        graphContainerLegendLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        graphContainerLegendLayout.add(graphContainerLayout, graphLegend);
        graphTemplateLayout.add(taskHeading, graphContainerLegendLayout);

        tasksListBox.addValueChangeListener(event ->
                setTemplatesGraph(taskHeading, graphContainerLayout, graphLegend, event.getValue()));

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

    private void setTemplatesGraph(H2 graphHeading,
                                   VerticalLayout graphContainer, VerticalLayout graphLegend,
                                   TaskState taskState) {
        // TODO change graph id
        Template template = templateService.getTemplateById(taskState.getTemplateId());

        graphHeading.setText(taskState.getTitle());
        graphContainer.removeAll();
        GraphComponent graphComponent = new GraphComponent(template.getId());
        graphComponent.setData(template.getGraphNodes(), template.getGraphEdges());
        graphComponent.setCurrentElement(taskState.getCurrentDepartment());
        graphComponent.setPreviousElement(taskState.getPreviousDepartment());
        graphContainer.add(graphComponent);

        Span previousState = new Span("Предыдущее местонахождение");
        previousState.getStyle().set("border-radius", "4px");
        previousState.getStyle().set("background", "#ff6f61");
        previousState.getStyle().set("padding", "4px 10px");
        previousState.getStyle().set("color", "#fff");

        Span currentState = new Span("Текущее местонахождение");
        currentState.getStyle().set("border-radius", "4px");
        currentState.getStyle().set("background", "#50c878");
        currentState.getStyle().set("padding", "4px 10px");
        currentState.getStyle().set("color", "#fff");

        graphLegend.add(previousState, currentState);
    }
}

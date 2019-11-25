package ru.sberbank.crm.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sberbank.crm.component.GraphComponent;
import ru.sberbank.crm.entity.Graph;
import ru.sberbank.crm.entity.TaskState;
import ru.sberbank.crm.service.GraphService;
import ru.sberbank.crm.service.TaskStateService;

@Route(value = "task-status")
@PageTitle(value = "Состояние задач")
public class TasksStatusView extends VerticalLayout {

    private TaskStateService taskStateService;
    private GraphService graphService;

    @Autowired
    public TasksStatusView(TaskStateService taskStateService, GraphService graphService) {
        this.taskStateService = taskStateService;
        this.graphService = graphService;

        H1 taskStatusHeading = new H1("Состояние задач");

        VerticalLayout tasksStateLayout = new VerticalLayout();
        for (TaskState taskState : taskStateService.getAllTasks()) {
            tasksStateLayout.add(getTaskStateLayout(taskState));
        }

        getStyle().set("background", "#F9F9F9");
        setAlignItems(Alignment.CENTER);
        setPadding(true);

        add(taskStatusHeading, tasksStateLayout);

    }

    public VerticalLayout getTaskStateLayout(TaskState taskState) {
        H2 taskHeading = new H2(taskState.getTitle());
        GraphComponent graphComponent = new GraphComponent(taskState.getId());
        Span previousTaskStateLabel = new Span(String.format("Предыдущие местонахождение: %s", taskState.getPreviousDepartment()));
        Span currentTaskStateLabel = new Span(String.format("Текущее местонахождение: %s", taskState.getCurrentDepartment()));

        Graph graph = graphService.getGraphById(taskState.getGraphId());
        graphComponent.setData(graph.getGraphNodes(), graph.getGraphEdges());
        graphComponent.setCurrentElement(String.format("[id = \"%s\"]", taskState.getCurrentDepartment()));

        VerticalLayout taskStateLayout = new VerticalLayout();
        taskStateLayout.getStyle().set("background", "#FFFFFF");
        taskStateLayout.getStyle().set("border-radius", "20px");
        taskStateLayout.getStyle().set("padding", "30px 40px 40px 40px");
        taskStateLayout.getStyle().set("box-shadow", "0px 10px 10px rgba(229, 229, 229, 0.5)");
        taskStateLayout.setPadding(true);

        taskStateLayout.add(taskHeading, graphComponent, previousTaskStateLabel, currentTaskStateLabel);

        return taskStateLayout;
    }
}

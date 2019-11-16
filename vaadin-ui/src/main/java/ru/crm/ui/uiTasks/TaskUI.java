package ru.crm.ui.uiTasks;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.crm.ui.service.impl.TaskServiceImpl;

@SpringUI
public class TaskUI  extends UI {

    @Autowired
    TaskServiceImpl service;

    private HorizontalLayout root;
    private VerticalLayout panelLayout;
    private VerticalLayout listLayout;
    private HorizontalLayout btnLayout;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        service = new TaskServiceImpl();
        setupLayout();
        showTaskList();
        showDocument();
        btnApprove();
        btnReject();
    }

    private void setupLayout() {
        root = new HorizontalLayout();
        root.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        root.setWidth("100%");
        root.setHeight("50%");
        setContent(root);
    }

    private void showTaskList() {
        listLayout = new VerticalLayout();
        listLayout.setMargin(true);

        TextField taskField = new TextField();
        taskField.setValue("Task name");

        ListSelect<String> taskList = new ListSelect<>("");
        //TaskServiceImpl service = new TaskServiceImpl();
        //List<Task> tasks = service.getAll();

        //Заполнение списка задач
        /*for(Task taskItem : tasks)
        {
            taskList.setItems(taskItem.getName());
        }
        taskList.setRows(tasks.size());*/

        taskList.setWidth("100%");
        taskList.setHeight("100%");
        taskField.setWidth("100%");
        taskField.setHeight("100%");

        listLayout.addComponent(taskField);
        listLayout.addComponent(taskList);

        root.addComponent(listLayout);
    }

    private void showDocument()
    {
        panelLayout = new VerticalLayout();
        btnLayout = new HorizontalLayout();

        Panel panel = new Panel("Исполнитель");
        String str = "A row\n"+
                "Another row\n"+
                "Yet another row\n"+
                "Another row\n"+
                "Yet another row\n"+
                "Another row\n"+
                "Yet another row\n"+
                "Another row\n"+
                "Yet another row\n"+
                "Another row\n"+
                "Yet another row\n"+
                "Another row\n"+
                "Yet another row\n";
        TextArea area = new TextArea();
        area.setValue(str);
        area.setWordWrap(false);
        area.setWidth("100%");
        area.setHeight("100%");
        panel.setContent(area);
        panel.setWidth("100%");
        panel.setHeight("100%");


        panelLayout.addComponent(panel);
        panelLayout.addComponent(btnLayout);
        panelLayout.setWidth("100%");
        panelLayout.setHeight("175%");
        root.addComponent(panelLayout);
        //root.getComponent(1).setWidth("300%");
    }

    private void btnApprove()
    {
        Button btn = new Button("Одобрить");
        btnLayout.addComponent(btn);
    }
    private void btnReject()
    {
        Button btn = new Button("Отклонить");
        btnLayout.addComponent(btn);
    }


}

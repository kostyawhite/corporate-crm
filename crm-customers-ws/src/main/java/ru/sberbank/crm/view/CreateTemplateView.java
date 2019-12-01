package ru.sberbank.crm.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.communication.PushMode;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sberbank.crm.component.EdgeHandlesGraphComponent;
import ru.sberbank.crm.component.GraphComponent;
import ru.sberbank.crm.entity.Department;
import ru.sberbank.crm.entity.Template;
import ru.sberbank.crm.service.CommunicationService;
import ru.sberbank.crm.service.TemplateService;
import ru.sberbank.crm.util.JSONParser;

import java.util.List;

@Push(PushMode.MANUAL)
@Route(value = "create-template")
@PageTitle(value = "Создание шаблона")
public class CreateTemplateView extends VerticalLayout {


    private TemplateService templateService;
    private CommunicationService communicationService;

    private List<Template> templates;
    private ListBox<Template> templateListBox;

    @Autowired
    public CreateTemplateView(TemplateService templateService,
                              CommunicationService communicationService) {
        this.templateService = templateService;
        this.communicationService = communicationService;

        H1 createTemplateHeading = new H1("Создание шаблонов");

        JSONParser parser = new JSONParser();
        List<Department> departments = communicationService.getDepartmentsFromRouter();
        EdgeHandlesGraphComponent edgeHandlesGraphComponent = new EdgeHandlesGraphComponent();
        String nodes = parser.getJSONNodes(departments);
        String edges = "{\"edges\":[]}";
        edgeHandlesGraphComponent.setData(nodes, edges);

        templates = templateService.getAllTemplates();

        templateListBox = new ListBox<>();
        templateListBox.setWidthFull();
        templateListBox.setItems(templates);

        Dialog addTemplateDialog = getTemplateDialog(edgeHandlesGraphComponent, departments);

        HorizontalLayout createTemplateButtonLayout = new HorizontalLayout();
        Button createTemplateButton = new Button("Создать шаблон");
        createTemplateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createTemplateButton.addClickListener(clickEvent -> {
            edgeHandlesGraphComponent.resetNodes();
            edgeHandlesGraphComponent.resetEdges();
            edgeHandlesGraphComponent.setData(nodes, edges);

            addTemplateDialog.open();
        });
        createTemplateButtonLayout.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.CENTER);
        createTemplateButtonLayout.add(createTemplateButton);


        VerticalLayout templatesListLayout = new VerticalLayout();
        templatesListLayout.setWidth("15%");
        templatesListLayout.add(templateListBox);

        VerticalLayout graphTemplateLayout = new VerticalLayout();

        H2 templateHeading = new H2("Выберите шаблон из списка для просмотра");
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
        graphTemplateLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, graphContainerLayout);
        graphTemplateLayout.add(templateHeading, graphContainerLayout);

        templateListBox.addValueChangeListener(event ->
                setTemplatesGraph(templateHeading, graphContainerLayout, event.getValue()));

        HorizontalLayout createTemplateLayout = new HorizontalLayout();
        createTemplateLayout.setHeightFull();
        createTemplateLayout.setWidthFull();
        createTemplateLayout.add(templatesListLayout, graphTemplateLayout);

        getStyle().set("background", "#F9F9F9");
        setAlignItems(Alignment.CENTER);
        setPadding(true);
        setHeightFull();
        add(createTemplateHeading, createTemplateButtonLayout, createTemplateLayout);
    }

    private void setTemplatesGraph(H2 graphHeading, VerticalLayout graphContainer, Template template) {
        graphHeading.setText(template.getDescription());
        graphContainer.removeAll();
        GraphComponent graphComponent = new GraphComponent(template.getId());
        graphComponent.setData(template.getGraphNodes(), template.getGraphEdges());
        graphContainer.add(graphComponent);
    }

    private Dialog getTemplateDialog(EdgeHandlesGraphComponent edgeHandlesGraphComponent,
                                     List<Department> departments) {

        Dialog addTemplateDialog = new Dialog();

        FormLayout templateForm = new FormLayout();

        Binder<Template> templateBinder = new Binder<>();

        H2 templateHeading = new H2("Новый шаблон");
        TextField templateDescription = new TextField("Название шаблона", "Шаблон 1");

        VerticalLayout graphContainerLayout = new VerticalLayout();
        graphContainerLayout.setHeight("440px");
        graphContainerLayout.setWidth("700px");
        graphContainerLayout.setAlignItems(Alignment.CENTER);
        graphContainerLayout.add(edgeHandlesGraphComponent);

        Button addTaskButton = new Button("Добавить шаблон");
        Button invisibleButton = new Button();
        invisibleButton.setVisible(false);
        addTaskButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        templateDescription.setValueChangeMode(ValueChangeMode.EAGER);
        templateDescription.setRequiredIndicatorVisible(true);


        templateBinder.forField(templateDescription)
                .asRequired("Заполните название шаблона")
                .bind(Template::getDescription, Template::setDescription);



        addTaskButton.addFocusListener(event -> edgeHandlesGraphComponent.updateData());
        addTaskButton.addClickListener(event -> {
            if (templateBinder.isValid()) {
                Template newTemplate = new Template();
                newTemplate.setDescription(templateDescription.getValue());

                String nodes = edgeHandlesGraphComponent.getNodes();
                String edges = edgeHandlesGraphComponent.getEdges();

                newTemplate.setGraphNodes(nodes);
                newTemplate.setGraphEdges(edges);

                templateService.saveTemplate(newTemplate);
                templates.add(newTemplate);
                templateListBox.setItems(templates);

                communicationService.sendTemplate(newTemplate.getId(), newTemplate.getGraphEdges(),
                        departments);
            } else {
                templateBinder.validate();
            }
        });

        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER,
                templateHeading, templateForm);
        templateForm.setResponsiveSteps(new FormLayout.ResponsiveStep("200px", 1));
        templateForm.add(templateDescription, graphContainerLayout, addTaskButton, invisibleButton);

        addTemplateDialog.add(templateHeading, templateForm);

        addTemplateDialog.setWidth("800px");

        return addTemplateDialog;
    }
}

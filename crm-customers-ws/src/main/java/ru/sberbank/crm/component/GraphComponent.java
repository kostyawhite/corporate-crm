package ru.sberbank.crm.component;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.dom.Element;

@Tag("cytoscape-graph")
@NpmPackage(value = "cytoscape", version = "3.12.0")
@JsModule("./src/cytoscape-graph.js") // frontend dir
public class GraphComponent extends PolymerTemplate<GraphModel> {

    public GraphComponent(Long taskId) {
        getModel().setTaskId(taskId.toString());
    }

    @Override
    public Element getElement() {
        return super.getElement();
    }

    public void setCurrentElement(String value){
        if (!value.isEmpty()) {
            this.getElement().executeJs("this.setCurrentElement($0)",
                    String.format("[id = '%s']", value));
        }
    }

    public void setPreviousElement(String value){
        if (!value.isEmpty()) {
            this.getElement().executeJs("this.setPreviousElement($0)",
                    String.format("[id = '%s']", value));
        }
    }

    public void setData(String nodes, String edges) {
        this.getElement().executeJs("this.setData($0, $1)", nodes, edges);
    }
}

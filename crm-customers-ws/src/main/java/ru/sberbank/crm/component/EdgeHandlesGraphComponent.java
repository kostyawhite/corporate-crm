package ru.sberbank.crm.component;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Synchronize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.PendingJavaScriptResult;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.dom.Element;
import ru.sberbank.crm.util.JSONParser;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;


@Tag("cytoscape-edgehandles-graph")
@NpmPackage(value = "cytoscape", version = "3.12.0")
@NpmPackage(value = "cytoscape-edgehandles", version = "3.6.0")
@JsModule("./src/cytoscape-edgehandles-graph.js")
public class EdgeHandlesGraphComponent extends PolymerTemplate<EdgeHandlesGraphModel> {

    private String nodes;
    private String edges;

    public EdgeHandlesGraphComponent() {
        getModel().setTemplateId("0");
    }

    @Override
    public Element getElement() {
        return super.getElement();
    }

    public void setData(String nodes, String edges) {
        this.getElement().executeJs("this.setData($0, $1)", nodes, edges);
    }

    public void resetEdges() {
        this.getElement().executeJs("this.resetEdges()");
    }
    public void resetNodes() {
        this.getElement().executeJs("this.resetNodes()");
    }

    public void updateData() {
        this.getElement().executeJs("return this.getEdges()")
                .then(String.class, this::setEdges);
        this.getElement().executeJs("return this.getNodes()")
                .then(String.class, this::setNodes);
    }

    public String getEdges(){
        return edges;
    }

    public String getNodes(){
        return nodes;
    }

    public void setEdges(String edges) {
        this.edges = edges;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }
}

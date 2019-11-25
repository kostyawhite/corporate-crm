package ru.sberbank.crm.component;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface GraphModel extends TemplateModel {
    void setTaskId(String taskId);
    String getTaskId();
}


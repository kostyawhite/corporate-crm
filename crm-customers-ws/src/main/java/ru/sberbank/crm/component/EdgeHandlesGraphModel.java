package ru.sberbank.crm.component;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface EdgeHandlesGraphModel extends TemplateModel {
    void setTemplateId(String templateId);
    String getTemplateId();
}

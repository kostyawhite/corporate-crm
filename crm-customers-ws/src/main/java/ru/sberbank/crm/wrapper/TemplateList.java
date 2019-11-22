package ru.sberbank.crm.wrapper;

import lombok.Getter;
import lombok.Setter;
import ru.sberbank.crm.entity.Template;

import java.util.ArrayList;
import java.util.List;

public class TemplateList {

    @Setter
    @Getter
    private List<Template> templatesList;

    public TemplateList() {
        templatesList = new ArrayList<>();
    }
}

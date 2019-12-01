package ru.sberbank.crm.util;

import ru.sberbank.crm.entity.Department;

import java.util.List;

public class JSONParser {

    public String getJSONNodes(List<Department> departmentList) {

        StringBuilder JSONNodesSb = new StringBuilder();
        JSONNodesSb.append("{\"nodes\":[");
        for (Department department : departmentList) {
            JSONNodesSb.append("{\"id\":\"");
            JSONNodesSb.append(department.getDescription());
            JSONNodesSb.append("\"}, ");
        }

        JSONNodesSb.delete(JSONNodesSb.length() - 2, JSONNodesSb.length());
        JSONNodesSb.append("]}");

        return JSONNodesSb.toString();
    }

    public String getJSONEdges(String[] edges) {
        StringBuilder JSONNodesSb = new StringBuilder();
        JSONNodesSb.append("{\"edges\":[");
        for (String edge : edges) {
            JSONNodesSb.append(edge);
            JSONNodesSb.append(", ");
        }

        JSONNodesSb.delete(JSONNodesSb.length() - 2, JSONNodesSb.length());
        JSONNodesSb.append("]}");

        return JSONNodesSb.toString();
    }

    public String getJSONNodes(String[] nodes) {

        StringBuilder JSONNodesSb = new StringBuilder();
        JSONNodesSb.append("{\"nodes\":[");
        for (String node : nodes) {
            JSONNodesSb.append(node);
            JSONNodesSb.append(", ");
        }

        JSONNodesSb.delete(JSONNodesSb.length() - 2, JSONNodesSb.length());
        JSONNodesSb.append("]}");

        return JSONNodesSb.toString();
    }
}

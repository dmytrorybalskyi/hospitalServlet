package org.example.model.entity;

import java.util.LinkedList;
import java.util.List;

public class Page {
    private List<Treatment> list = new LinkedList();
    private int objectsOnPage = 3;
    private int page;
    private int totalPages;

    public Page(){}

    public Page(int page){
        this.page = page;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public int getObjectsOnPage() {
        return objectsOnPage;
    }

    public void setObjectsOnPage(int objectsOnPage) {
        this.objectsOnPage = objectsOnPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages/objectsOnPage;
        if(totalPages%objectsOnPage!=0){
            this.totalPages = totalPages/objectsOnPage+1;
        }
    }
}

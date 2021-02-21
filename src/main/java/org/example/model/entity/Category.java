package org.example.model.entity;

public class Category{

    private Integer id;

    private String name;

    public Category() {
    }
    public Category(Integer id,String name) {
        this.id=id;
        this.name=name;
    }

    public Category(Integer id){
        this.id=id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o){
        if(o==this){
            return true;
        }
        if(o==null||o.getClass()!=this.getClass()){
            return false;
        }

        Category category = (Category) o;
        return id==category.getId()
                &&(name==category.getName()||name!=null&&name.equals(((Category) o).getName()));
    }

}

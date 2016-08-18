package com.example.lenovo.qqdemos.Beans;

/**
 * Created by lenovo on 2016/8/13.
 */
public class FunctionItem {

    int id;
    int pic;
    String funName;

    public FunctionItem(int id, int pic, String funName) {
        this.id = id;
        this.pic = pic;
        this.funName = funName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getFunName() {
        return funName;
    }

    public void setFunName(String funName) {
        this.funName = funName;
    }

}

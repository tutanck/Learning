package com.example.ajoan.todolist;

/**
 * Created by AJoan on 12/12/2016.
 */
public class Item {

    private boolean checked;
    public boolean isChecked() {return checked;}
    public void setChecked(boolean checked) {this.checked = checked;}

    private String text;
    public String getText() {return text; }
    public void setText(String text) {this.text = text;}

    public Item(boolean checked, String text){
        this.checked=checked;
        this.text=text;
    }

}

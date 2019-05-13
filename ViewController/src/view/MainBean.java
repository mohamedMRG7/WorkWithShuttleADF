package view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

public class MainBean {
    List sItems;
    List allItems;
    public MainBean() {
        sItems=new ArrayList();
       
     


    }

    public void setSItems(List sItems) {
        this.sItems = sItems;
    }

    public List getSItems() {
        return sItems;
    }

    public void setAllItems(List allItems) {
        this.allItems = allItems;
    }

    public List getAllItems() {
        allItems=new ArrayList();
        allItems.add(new SelectItem(2,"ahmed"));
        return allItems;
    }


    public void addToList(ActionEvent actionEvent) {
        // Add event code here...
        sItems.add(2);
        
    }

    public void onChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        sItems.add(2);
        System.out.println(valueChangeEvent.getNewValue()+" "+sItems.size());
        
    }
}

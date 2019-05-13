package view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.jbo.Row;

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
        if(allItems==null)
            allItems=getAllRolesFromItert();
        return allItems;
    }


    public void addToList(ActionEvent actionEvent) {
        // Add event code here...
       
        
    }

    public List<SelectItem> getAllRolesFromItert()
    {
        DCIteratorBinding roleIterator=getIterator("RolesVOIterator");
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (Row row : roleIterator.getAllRowsInRange()) {
            selectItems.add(new SelectItem(row.getAttribute("RoleId"),
                                           (String)row.getAttribute("Role")));
        }
        return selectItems;
    }
    
    
    public DCIteratorBinding getIterator(String iteratorID)
    {
            BindingContext ctx=BindingContext.getCurrent();
            DCBindingContainer bc = (DCBindingContainer) ctx.getCurrentBindingsEntry();
            DCIteratorBinding iter = bc.findIteratorBinding(iteratorID);
            return iter;
        }
    public void onChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println(valueChangeEvent.getOldValue()+" "+valueChangeEvent.getNewValue());
    }
}

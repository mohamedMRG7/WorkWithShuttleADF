package view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.AttributeBinding;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;

public class MainBean {
    List<Integer> sItems;
    List allItems;
    boolean enableAddRole = false;
    private RichPanelFormLayout addRoleForm;

    public MainBean() {

    }


    public void setEnableAddRole(boolean enableAddRole) {
        this.enableAddRole = enableAddRole;
    }

    public boolean isEnableAddRole() {
        return enableAddRole;
    }

    public void setSItems(List sItems) {
        this.sItems = sItems;
    }

    public List getSItems() {
        if (sItems == null)
            sItems = getRolesForEmp();
        return sItems;
    }

    public void setAllItems(List allItems) {
        this.allItems = allItems;
    }

    public List getAllItems() {
        if (allItems == null)
            allItems = getAllRolesFromItert();

        return allItems;
    }


    public List<SelectItem> getAllRolesFromItert() {
        DCIteratorBinding roleIterator = getIterator("RolesVOIterator");
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (Row row : roleIterator.getAllRowsInRange()) {
            selectItems.add(new SelectItem(row.getAttribute("RoleId"), (String) row.getAttribute("Role")));
        }
        return selectItems;
    }

    public List<Integer> getRolesForEmp() {
        DCIteratorBinding iter = getIterator("EmpRolesVOIterator");
        List<Integer> rolesForCurrentEmp = new ArrayList<Integer>();
        for (Row row : iter.getAllRowsInRange()) {
            rolesForCurrentEmp.add((Integer) row.getAttribute("RoleId"));
        }
        return rolesForCurrentEmp;
    }

    public DCIteratorBinding getIterator(String iteratorID) {
        BindingContext ctx = BindingContext.getCurrent();
        DCBindingContainer bc = (DCBindingContainer) ctx.getCurrentBindingsEntry();
        DCIteratorBinding iter = bc.findIteratorBinding(iteratorID);
        return iter;
    }

    public void onChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getOldValue() == valueChangeEvent.getNewValue())
            return;

        sItems = (ArrayList) valueChangeEvent.getNewValue();


    }


    public Integer getCurrentEmpId() {
        BindingContext ctx = BindingContext.getCurrent();
        DCBindingContainer bc = (DCBindingContainer) ctx.getCurrentBindingsEntry();
        AttributeBinding attr = (AttributeBinding) bc.findCtrlBinding("EmpId");
        Integer empId = (Integer) attr.getInputValue();
        return empId;
    }

    public void saveChanges(ActionEvent actionEvent) {
        // Add event code here...
        DCIteratorBinding iter = getIterator("EmpRolesVOIterator");
        for (Row row : iter.getAllRowsInRange())
            row.remove();

        for (Integer roleID : sItems) {
            Row row = iter.getRowSetIterator().createRow();
            row.setAttribute("EmpId", getCurrentEmpId());
            row.setAttribute("RoleId", roleID);
            iter.getRowSetIterator().insertRow(row);
        }

        excuteOperation("Commit");

    }

    public void excuteOperation(String operationID) {
        BindingContext ctx = BindingContext.getCurrent();
        DCBindingContainer bc = (DCBindingContainer) ctx.getCurrentBindingsEntry();
        OperationBinding operation = bc.getOperationBinding(operationID);
        operation.execute();
    }

    public String resetSelectedList() {
        // Add event code here...
        setSItems(null);
        return null;
    }

    public void addRole(ActionEvent actionEvent) {
        // Add event code here...
        setEnableAddRole(true);
       refreshPage();

    }

    public void setAddRoleForm(RichPanelFormLayout addRoleForm) {
        this.addRoleForm = addRoleForm;
    }

    public RichPanelFormLayout getAddRoleForm() {
        return addRoleForm;
    }

    protected void refreshPage() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String refreshpage = fc.getViewRoot().getViewId();
        ViewHandler ViewH = fc.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fc, refreshpage);
        UIV.setViewId(refreshpage);
        fc.setViewRoot(UIV);
    }

    public void addRole_action(ActionEvent actionEvent) {
        // Add event code here...
        excuteOperation("Commit");
        setEnableAddRole(false);
        allItems = getAllRolesFromItert();
        refreshPage();
    }

    public String toManySelect() {
        // Add event code here...
        setEnableAddRole(false);
        return "next";
    }
}

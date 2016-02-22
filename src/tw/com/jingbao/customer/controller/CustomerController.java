package tw.com.jingbao.customer.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import tw.com.jingbao.model.customer.Customer;
import tw.com.jingbao.model.customer.CustomerImp;
import tw.com.jingbao.model.customer.CustomerService;


public class CustomerController{

	 private static final long serialVersionUID = 1L;
	 private CustomerService customerImp = new CustomerImp();
	 private Customer customer;
	 private Customer selectedCustomer = new Customer();
	 private ListModelList<Customer> customerList;
	 
	 private Window win;
	 private Window parent;
	 private HashMap dataMap;
	 private String customerID;
	 private String customerName;
	 private String customerPhone;
	 private String customerFax;
	 private String customerAddress;
	 private String remark;
	 String SYSID;
	 String TSTAMP;
	 String actioName;
	 
	 @Init
	 public void Init(@ContextParam(ContextType.VIEW) Component view,
			 @ContextParam(ContextType.SPACE_OWNER) final Window win)
	 {
		final Execution execution = Executions.getCurrent();
		this.win = win;
		customerList = new ListModelList<Customer>(customerImp.getCustomerData());
		dataMap = new HashMap();
		
        if(execution.getArg().containsKey("CUSTOMERID")){
        	selectedCustomer.setCustomerID((execution.getArg().get("CUSTOMERID").toString()));
        	dataMap.put("CUSTOMERID", execution.getArg().get("CUSTOMERID"));
        }
        if(execution.getArg().containsKey("CUSTOMERNAME")){
        	selectedCustomer.setCustomerName(execution.getArg().get("CUSTOMERNAME").toString());
        	dataMap.put("CUSTOMERNAME", execution.getArg().get("CUSTOMERNAME"));
        }
        if(execution.getArg().containsKey("CUSTOMERPHONE")){
        	selectedCustomer.setCustomerPhone((execution.getArg().get("CUSTOMERPHONE").toString()));
        	dataMap.put("CUSTOMERPHONE", execution.getArg().get("CUSTOMERPHONE"));
        }
        if(execution.getArg().containsKey("CUSTOMERFAX")){
        	selectedCustomer.setCustomerFax((execution.getArg().get("CUSTOMERFAX").toString()));
        	dataMap.put("CUSTOMERFAX", execution.getArg().get("CUSTOMERFAX"));
        }
        if(execution.getArg().containsKey("CUSTOMERADDRESS")){
        	selectedCustomer.setCustomerAddress((execution.getArg().get("CUSTOMERADDRESS").toString()));
        	dataMap.put("CUSTOMERADDRESS", execution.getArg().get("CUSTOMERADDRESS"));
        }
        if(execution.getArg().containsKey("REMARK") ){ 
        	selectedCustomer.setRemark((execution.getArg().get("REMARK").toString()));
        	dataMap.put("REMARK", execution.getArg().get("REMARK"));
        }
        if(execution.getArg().containsKey("SYSID")){
        	
        	SYSID = execution.getArg().get("SYSID").toString();
        	dataMap.put("SYSID", execution.getArg().get("SYSID"));
        }
        if(execution.getArg().containsKey("ACTION")){
        	actioName = execution.getArg().get("ACTION").toString();
        	dataMap.put("ACTION", execution.getArg().get("ACTION"));
        }
        if(execution.getArg().containsKey("parent_page")){
        	parent = (Window) execution.getArg().get("parent_page");
        }
	  }
	 
	 
	 public ListModelList<Customer> getCustomerList() {
	        return customerList;
	 }

     public void setCustomerList(ListModelList<Customer> customerList) {
        this.customerList = customerList;
     }

	 public void setCustomer(Customer customer){
	 	this.customer = customer;
	 }

	 public Customer getCustomer() {
	        return customer;
	 }
	 @NotifyChange("selectedCustomer")
	 public void setSelectedCustomer(Customer selected) {
		
	     this.selectedCustomer = selected;
     }

	 public Customer getSelectedCustomer() {
		 System.out.println("selectedCustomer: " + selectedCustomer.getCustomerAddress());
    	 return selectedCustomer;
     }
	
	 // Get selected Map
	 public HashMap getSelectedMap(){
		 if(selectedCustomer != null){

			 HashMap selectedMap = new HashMap();
		     selectedMap.put("CUSTOMERID", selectedCustomer.getCustomerID());
		     selectedMap.put("CUSTOMERNAME", selectedCustomer.getCustomerName());
		     selectedMap.put("CUSTOMERPHONE", selectedCustomer.getCustomerPhone());
		     selectedMap.put("CUSTOMERFAX", selectedCustomer.getCustomerFax());
		     selectedMap.put("CUSTOMERADDRESS", selectedCustomer.getCustomerAddress());
		     selectedMap.put("REMARK", selectedCustomer.getRemark());
		     selectedMap.put("SYSID", selectedCustomer.getSYSID());
		     
		     return selectedMap;
		 }else{
			 Messagebox.show("請選取一筆資料");
			 return null;
		 }
	 }
	 
	 // MainFrame ToolBarButton-Add	
	 @Command
	 public void action_Add() throws Exception{
        HashMap passMap = new HashMap();
        
        passMap.put("ACTION", "Add");
        passMap.put("PARENT_WINDOW", win);
        Window w = (Window) Executions.createComponents("customer_Add.zul", win, passMap);
        
        w.doModal();
     }
	 
	 // MainFrame ToolBarButton-Add	
	 @Command
	 public void action_Update() throws Exception{
		 if(selectedCustomer.getCustomerID() == null ){
			 Messagebox.show("請選取一筆資料");
		 }else
		 {
			 HashMap updateMap = getSelectedMap();
			 updateMap.put("ACTION", "Update");
			 Window w = (Window) Executions.createComponents("customer_Add.zul", null, updateMap);
			 w.doModal();
		 }
	 }
	 
	 @Command
	 @NotifyChange("customerList")
	 public void action_Delete(@ContextParam(ContextType.VIEW) Component view){
		 final HashMap<String, Object> map = new HashMap<String, Object>();
	     map.put("customer", this.selectedCustomer);
	     map.put("recordMode", "Delete");
	        
		 if(selectedCustomer.getCustomerID() == null){
			 Messagebox.show("請選取一筆資料");
		 }else{
			 
			 Messagebox.show("確定刪除此筆資料？", "警告訊息",
				 Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				 new org.zkoss.zk.ui.event.EventListener() {

					@Override
					public void onEvent(Event evt) throws InterruptedException {
						// TODO Auto-generated method stub
						if(evt.getName().equals("onYes")){
							
								BindUtils.postGlobalCommand(null, null, "updateCustomerList", map);
								customerImp.action_Delete(selectedCustomer);
					        }
					}});
			 }
	 }

	
	 @Command
	 @NotifyChange(".")
	 public void btnSubmit()throws Exception{
		 
		 if(checkErrorInfo()){
			 customer = new Customer();
			 customer.setCustomerID(selectedCustomer.getCustomerID());
			 customer.setCustomerName(selectedCustomer.getCustomerName());
			 customer.setCustomerPhone(selectedCustomer.getCustomerPhone());
			 customer.setCustomerFax(selectedCustomer.getCustomerFax());
			 customer.setCustomerAddress(selectedCustomer.getCustomerAddress());
			 customer.setRemark(selectedCustomer.getRemark());
			
		     if(actioName == "Add"){
	        	 String SYSID = "CustomerSYSID" + String.valueOf(new Random().nextInt());
	        	 customer.setSYSID(SYSID.replace("-", ""));
	        	 customerImp.action_Add(customer);
	          }
	          else{
	        	
	        	String SYSID = this.SYSID;
	        	
	        	customer.setSYSID(SYSID);
	        	customerImp.action_Update(customer);
	        	
	        	customerList.remove(this.selectedCustomer);
	        	customerList.add(customer);
	         }
		     
		     Map args = new HashMap();
	         args.put("customer", this.customer);
	         args.put("recordMode", this.actioName);
	         BindUtils.postGlobalCommand(null, null, "updateCustomerList", args);
			 win.detach();
		 }
	 }
	 
	 @Command
	 public void btnClear()throws Exception{  
		selectedCustomer.setCustomerID("");
		selectedCustomer.setCustomerName("");
		selectedCustomer.setCustomerPhone("");
		selectedCustomer.setCustomerFax("");
		selectedCustomer.setCustomerAddress("");
		selectedCustomer.setRemark("");
	 }
	 
	 @Command
	 public void btnCancel(@ContextParam(ContextType.SPACE_OWNER) final Window customerAdd) {
	    // what you want to do before close
		 customerAdd.detach();
	 }
	 
	 @GlobalCommand
	 @NotifyChange("customerList")
	 public void updateCustomerList(@BindingParam("customer") Customer selectedCustomer,
		  @BindingParam("recordMode") String recordMode) {
		
	  if (recordMode.equals("Add")) {
		  customerList.add(selectedCustomer);
	  }
	  else if (recordMode.equals("Delete")) {
		 
		  customerList.remove(selectedCustomer);
	  }
	  else if(recordMode.equals("Update")){
		  customerList.remove(this.selectedCustomer);
		  customerList.add(selectedCustomer);
	  }
    }
	
	 @Command 
	public void onDoubleClicked(Event event) throws Exception
	{
		 if(parent != null)
		 {
		    // get the selected object
		    Textbox txtCutomerID = (Textbox) (parent.getFellow("shipToCustomerID"));
		    Textbox txtCutomerName = (Textbox) (parent.getFellow("shipToCustomerName"));
		    txtCutomerID.setText(selectedCustomer.getCustomerID());
		    txtCutomerName.setText(selectedCustomer.getCustomerName());
		    
		    win.detach();
		 }
		 else
		 {
			 HashMap passMap = new HashMap();
			 passMap.putAll(getSelectedMap());
			 passMap.put("ACTION", "Update");
		     Window editDialog = (Window) Executions.createComponents("customer_Add.zul", null, passMap);
		     editDialog.doModal();
		 }
    }
	 
	/* 
	 * 檢查必填欄位
	 */
	private boolean checkErrorInfo(){
		StringBuffer errorInfo = new StringBuffer();
		
		if(selectedCustomer.getCustomerID() == null){
			errorInfo.append("請輸入客戶代號 !\n");
		}
		if(selectedCustomer.getCustomerName() == null){
			errorInfo.append("請輸入客戶名稱 !\n");
		}
		if(errorInfo.length() > 0 ){	
			Messagebox.show(errorInfo.toString());
			return false;
		}
		
		return true;
	}
	    
}

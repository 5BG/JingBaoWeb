package tw.com.jingbao.shippingorder.controller;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import tw.com.jingbao.model.shippingorder.ShippingOrder;
import tw.com.jingbao.model.shippingorder.ShippingOrderImp;
import tw.com.jingbao.model.shippingorder.ShippingOrderService;
import tw.com.jingbao.shippingorder.viewmodel.ShippingOrderViewModel;

public class ShippingOrderController extends SelectorComposer<Component> {

	 private static final long serialVersionUID = 1L;
	 private ShippingOrderService order = new ShippingOrderImp();
	 private String recordMode;
	 private ShippingOrder shippingOrder;
	 private ShippingOrder selectedShippingOrder;
	 private ListModelList<ShippingOrder> shippingOrdersList;
	 
	 @Wire
	 private Window win;
	 @Wire
	 private Window addDialog;
	 @Wire
	 private Datebox shippingDate;
	 @Wire
	 private Textbox SDNO;
	 @Wire
	 private Textbox  shipToCustomerID;
	 @Wire
	 private Textbox shipToCustomerName;
	
	 private String mode;
	 
	 @AfterCompose
	 public void afterCompose(Component comp) {
		 
		 shippingOrdersList = new ListModelList<ShippingOrder>(order.getAllData());
	  }
	 
	 public ListModelList<ShippingOrder> getShippingOrdersList() {
	        return shippingOrdersList;
	 }

     public void setShippingOrdersList(ListModelList<ShippingOrder> shippingOrdersList) {
        this.shippingOrdersList = shippingOrdersList;
     }

	 public void setShippingOrder(ShippingOrder shippingOrder){
	 	this.shippingOrder = shippingOrder;
	 }

	 public ShippingOrder getShippingOrder() {
	        return shippingOrder;
	 }

	 @Listen("onClick = #btnTailClear")
	 public void action_clear() {
		 String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
		 
		 shippingDate.setText(timeStamp);
		 SDNO.setText("");
		 shipToCustomerID.setText("");
		 shipToCustomerName.setText("");
		 showNotify("Clear", win);
	 }
	 
	 @Listen("onClick = #btnTailCancel")
	 public void action_Cancel(){
		 addDialog.detach();
	 }
	 
	 @NotifyChange("selectedShippingOrder")
	 public void setSelectedShippingOrder(ShippingOrder selected) {
	     this.selectedShippingOrder = selected;
     }

	 public ShippingOrder getSelectedShippingOrder() {
    	 return selectedShippingOrder;
     }
	 
	 private void showNotify(String msg, Component ref) {
	     Clients.showNotification(msg, "info", ref, "end_center", 2000);
     }

	 public void convertJavaDateToSQLDate(){
		java.util.Date date =  new java.util.Date();
		date = shippingDate.getValue();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); 
		shippingOrder.setShippingDate(sqlDate);
	 }

	 // Get selected Map
	 public HashMap getSelectedMap(){
		 if(selectedShippingOrder != null){

			 HashMap selectedMap = new HashMap();
		     selectedMap.put("SDNO", selectedShippingOrder.getSDNO());
		     selectedMap.put("SHIPPINGDATE", selectedShippingOrder.getShippingDate());
		     selectedMap.put("SHIPTOCUSTOMERID", selectedShippingOrder.getShipToCustomerID());
		     selectedMap.put("SHIPTOCUSTOMERNAME", selectedShippingOrder.getShipToCustomerName());
		     selectedMap.put("SYSID", selectedShippingOrder.getSYSID());
		     selectedMap.put("TSTAMP", selectedShippingOrder.getTSTAMP());
		     
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
        
        java.util.Date date =  new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); 
		
        passMap.put("ACTION", "Add");
        passMap.put("SHIPPINGDATE", sqlDate);
        Window w = (Window) Executions.createComponents("shippingOrder_Edit.zul", win, passMap);
        
        w.doModal();
     }

	 // MainFrame ToolBarButton-Update
	 @Command
	 public void action_Update() throws Exception{
		
		 if(selectedShippingOrder == null ){
			 Messagebox.show("請選取一筆資料");
		 }else
		 {
			 HashMap updateMap = getSelectedMap();
			 updateMap.put("ACTION", "Update");
			 Window w = (Window) Executions.createComponents("shippingOrder_Edit.zul", win, updateMap);
			 w.doModal();
		 }
	 }
	 
	 @Command
	 public void onDoubleClicked() throws Exception {
		 if(selectedShippingOrder == null ){
			 Messagebox.show("請選取一筆資料");
		 }else{
			 HashMap passMap = new HashMap();
			 passMap.putAll(getSelectedMap());
			 passMap.put("ACTION", "Update");
			 Window editDialog = (Window) Executions.createComponents("shippingOrder_Edit.zul", win, passMap);
			 editDialog.doModal();
		 }
	 }
	 
	 // MainFrame ToolBarButton-Refresh
	 @Command
	 public void action_Refresh() throws Exception{
		 Executions.sendRedirect("shippingOrder.zul");
	 }
    
	 @Command
	 @NotifyChange("shippingOrdersList")
	 public void action_Delete(@ContextParam(ContextType.VIEW) Component view){
		 final HashMap<String, Object> map = new HashMap<String, Object>();
	     map.put("order", this.selectedShippingOrder);
	     map.put("recordMode", "Delete");
	        
		 if(selectedShippingOrder == null ){
			 Messagebox.show("請選取一筆資料");
		 }else{
			 
			 Messagebox.show("確定刪除此筆資料？", "警告訊息",
				 Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				 new org.zkoss.zk.ui.event.EventListener() {

					@Override
					public void onEvent(Event evt) throws InterruptedException {
						// TODO Auto-generated method stub
						if(evt.getName().equals("onYes")){
							
								//shippingOrdersList.remove(selectedShippingOrder);
								BindUtils.postGlobalCommand(null, null, "updateShippingOrdersList", map);
								order.action_Delete(selectedShippingOrder);
					        }
					}});
			 }
	 }

	 // The following method will be called from CustomerCRUDVM after the save
	 // When we say Notifychange("allcustomers), then ZUL list items will be
	 // updated
     @GlobalCommand
     @NotifyChange("shippingOrdersList")
     public void updateShippingOrdersList(@BindingParam("order") ShippingOrder selectedShippingOrder,
   		  @BindingParam("recordMode") String recordMode) {
	   	  
	   	  if (recordMode.equals("Add")) {
	       	  shippingOrdersList.add(selectedShippingOrder);
	   	  }
	   	  else if (recordMode.equals("Delete")) {
	   		  shippingOrdersList.remove(selectedShippingOrder);
	   	  }else if(recordMode.equals("Update")){
	   		  shippingOrdersList.clear();
	   		  shippingOrdersList.addAll(order.getAllData());
	   		  
	   	  }
     }
}

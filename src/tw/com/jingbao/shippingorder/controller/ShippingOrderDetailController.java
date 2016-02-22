package tw.com.jingbao.shippingorder.controller;

import java.awt.Desktop;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.zkoss.bind.BindUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import tw.com.jingbao.model.shippingorder.ShippingOrder;
import tw.com.jingbao.model.shippingorder.ShippingOrderDetail;
import tw.com.jingbao.model.shippingorder.ShippingOrderImp;
import tw.com.jingbao.model.shippingorder.ShippingOrderService;
import tw.com.jingbao.shippingorder.viewmodel.ShippingOrderViewModel;

public class ShippingOrderDetailController extends GenericForwardComposer {
	private static final long serialVersionUID = 1L;
	private ShippingOrderService order = new ShippingOrderImp();
	private ShippingOrderService orderDeail = new ShippingOrderImp();
	
	@Wire private Window editDialog;
	@Wire Datebox shippingDate;
	@Wire Textbox SDNO;
	@Wire Textbox shipToCustomerID;
	@Wire Textbox shipToCustomerName;
	@Wire Button btnCancel;
	
	private Listbox detailList = new Listbox();
	private ListModelList<ShippingOrderDetail> orderDetailList;
	private ShippingOrder shippingOrder;
	private ShippingOrderDetail shippingOrderDetail;
	private AnnotateDataBinder binder;
	private List<ShippingOrderDetail> model;
	private ShippingOrderViewModel viewModel = new ShippingOrderViewModel();
	String SYSID;
	String TSTAMP;
	String actioName;
	
	private ShippingOrderDetail selectedOrderDetail;
	private HashMap dataMap;
	
	public void doAfterCompose(Component win) throws Exception {
        super.doAfterCompose(win);
        
        final Execution execution = Executions.getCurrent();
        
        dataMap = new HashMap();
        
        if(execution.getArg().containsKey("SHIPPINGDATE")){
        	shippingDate.setValue((Date) execution.getArg().get("SHIPPINGDATE"));
        	dataMap.put("SHIPPINGDATE", execution.getArg().get("SHIPPINGDATE"));
        }
        if(execution.getArg().containsKey("SDNO")){
        	SDNO.setText(execution.getArg().get("SDNO").toString());
        	dataMap.put("SDNO", execution.getArg().get("SDNO"));
        }
        if(execution.getArg().containsKey("SHIPTOCUSTOMERID")){
        	shipToCustomerID.setText(execution.getArg().get("SHIPTOCUSTOMERID").toString());
        	dataMap.put("SHIPTOCUSTOMERID", execution.getArg().get("SHIPTOCUSTOMERID"));
        }
        if(execution.getArg().containsKey("SHIPTOCUSTOMERNAME")){
        	shipToCustomerName.setText(execution.getArg().get("SHIPTOCUSTOMERNAME").toString());
        	dataMap.put("SHIPTOCUSTOMERNAME", execution.getArg().get("SHIPTOCUSTOMERNAME"));
        }
        if(execution.getArg().containsKey("SYSID")){
        	
        	SYSID = execution.getArg().get("SYSID").toString();
        	dataMap.put("SYSID", execution.getArg().get("SYSID"));
        }
        if(execution.getArg().containsKey("TSTAMP")){
        	TSTAMP = execution.getArg().get("TSTAMP").toString();
        	dataMap.put("TSTAMP", execution.getArg().get("TSTAMP"));
        }
        if(execution.getArg().containsKey("BINDER")){
        	binder = (AnnotateDataBinder) execution.getArg().get("BINDER");
        	dataMap.put("BINDER", execution.getArg().get("BINDER"));
        }
        if(execution.getArg().containsKey("ACTION")){
        	actioName = execution.getArg().get("ACTION").toString();
        	dataMap.put("ACTION", execution.getArg().get("ACTION"));
        }

	}

	/**
	 * Automatically called method from zk.
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void onCreate$editDialog(Event event) throws Exception {
		
	   
	}
	
	public List getModel() {
		return model;
	}
	
	public void setModel(List model) {
		this.model = model;
	}

	public ListModelList<ShippingOrderDetail> getOrderDetailList(){
		return orderDetailList;
	}
	
	public void setOrderDetailList(ListModelList<ShippingOrderDetail> orderDetailList) {  
        this.orderDetailList = orderDetailList;  
    } 
	
	public void setShippingOrderDetail(ShippingOrderDetail shippingOrderDetail){
	 	this.shippingOrderDetail = shippingOrderDetail;
	}

	public ShippingOrderDetail getShippingOrderDetail() {
        return shippingOrderDetail;
	}
	
	public ShippingOrderDetail getSelectedOrderDetail() {
		return selectedOrderDetail;
	}

	public void setSelectedOrderDetail(ShippingOrderDetail selectedOrderDetail) {
		this.selectedOrderDetail = selectedOrderDetail;
	}
	
	public void onClick$btnTailSubmit(Event event)throws Exception{
		
		if(checkErrorInfo()){
			shippingOrder = new ShippingOrder();
			shippingOrder.setSDNO(SDNO.getText());
			shippingOrder.setShippingDate(shippingDate.getValue());
			shippingOrder.setShipToCustomerID(shipToCustomerID.getText());
			shippingOrder.setShipToCustomerName(shipToCustomerName.getText());
			
			//format shippingDate to yyyy/MM/DD
			java.util.Date date =  new java.util.Date();
			date = shippingDate.getValue();
			java.sql.Date sqlDate = new java.sql.Date(date.getTime()); 
			//re-setShippingDate
			shippingOrder.setShippingDate(sqlDate);
			
			shippingOrder.setTSTAMP(new Timestamp(date.getTime()).toString());
	
	        if(actioName == "Add"){
	        	String SYSID = "ShippingOrderSYSID" + String.valueOf(new Random().nextInt());
				shippingOrder.setSYSID(SYSID.replace("-", ""));
	        	order.action_Add(shippingOrder);
	        }
	        else{
	        	
	        	String SYSID = this.SYSID;
	        	
	        	shippingOrder.setSYSID(SYSID);
				order.action_Update(shippingOrder);
	        }
	
	        Map args = new HashMap();
	        args.put("order", this.shippingOrder);
	        args.put("recordMode", this.actioName);
	        BindUtils.postGlobalCommand(null, null, "updateShippingOrdersList", args);
	        if(viewModel.getActionList().size() != 0)
	        orderDeail.handleActionList(viewModel.getActionList(), this.SYSID);
	        
	        editDialog.detach();
		}
	}

	public void onClick$btnTailCancel(Event event)throws Exception{
		
		editDialog.detach();
	}
	
	public void onClick$btnPass(Event event)throws Exception{
		
		 HashMap args = new HashMap();
		 args.put("parent_page", editDialog);
		 Window w = (Window) Executions.createComponents("customer.zul", null, args);
		 w.doModal();
	}

	public HashMap getSelectedMap(){
		if(selectedOrderDetail != null)
		{
			if(execution.getArg().containsKey("SHIPPINGDATE")){
	        	shippingDate.setValue((Date) execution.getArg().get("SHIPPINGDATE"));
	        	dataMap.put("SHIPPINGDATE", execution.getArg().get("SHIPPINGDATE"));
	        }
	        if(execution.getArg().containsKey("SDNO")){
	        	SDNO.setText(execution.getArg().get("SDNO").toString());
	        	dataMap.put("SDNO", execution.getArg().get("SDNO"));
	        }
	        if(execution.getArg().containsKey("SHIPTOCUSTOMERID")){
	        	shipToCustomerID.setText(execution.getArg().get("SHIPTOCUSTOMERID").toString());
	        	dataMap.put("SHIPTOCUSTOMERID", execution.getArg().get("SHIPTOCUSTOMERID"));
	        }
	        if(execution.getArg().containsKey("SHIPTOCUSTOMERNAME")){
	        	shipToCustomerName.setText(execution.getArg().get("SHIPTOCUSTOMERNAME").toString());
	        	dataMap.put("SHIPTOCUSTOMERNAME", execution.getArg().get("SHIPTOCUSTOMERNAME"));
	        }
	        if(execution.getArg().containsKey("SYSID")){
	        	SYSID = execution.getArg().get("SYSID").toString();
	        	dataMap.put("SYSID", execution.getArg().get("SYSID"));
	
	        }
	        if(execution.getArg().containsKey("TSTAMP")){
	        	TSTAMP = execution.getArg().get("TSTAMP").toString();
	        	dataMap.put("TSTAMP", execution.getArg().get("TSTAMP"));
	        }
	        if(execution.getArg().containsKey("BINDER")){
	        	binder = (AnnotateDataBinder) execution.getArg().get("BINDER");
	        	dataMap.put("BINDER", execution.getArg().get("BINDER"));
	        }
	        return dataMap;
		}else
		{
			 Messagebox.show("請選取一筆資料");
			 return null;
		}
	}
    
    public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}
	
	/* 
	 * 檢查必填欄位
	 */
	private boolean checkErrorInfo(){
		StringBuffer errorInfo = new StringBuffer();
		
		if(SDNO.getText().equals("")){
			errorInfo.append("請輸入出貨單號 !\n");
		}
		if(shippingDate.getText().equals("")){
			errorInfo.append("請輸入出貨日期 !\n");
		}
		if(shipToCustomerID.getText().equals("")){
			errorInfo.append("請輸入出貨客戶 !\n");
		}
		if(errorInfo.length() > 0 ){	
			Messagebox.show(errorInfo.toString());
			return false;
		}
		
		return true;
	}
}

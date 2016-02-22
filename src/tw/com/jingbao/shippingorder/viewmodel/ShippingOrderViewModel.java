package tw.com.jingbao.shippingorder.viewmodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import tw.com.jingbao.model.shippingorder.ShippingOrderDetail;
import tw.com.jingbao.model.shippingorder.ShippingOrderImp;
import tw.com.jingbao.model.shippingorder.ShippingOrderService;
import tw.com.jingbao.shippingorder.controller.ShippingOrderDetailController;
import tw.com.jingbao.ui.ShippingOrderReference;

public class ShippingOrderViewModel{

	private static final long serialVersionUID = 1L;
	
	private String itemID= null;
	private String itemName= null;
	private BigDecimal quantity= new BigDecimal(0);
	private String unit= "PCS";
	private BigDecimal unitPrice= new BigDecimal(0);
	private BigDecimal total= new BigDecimal(0);
	private String SYSID;
	private String detailSYSID;
	private String detailTSTAMP;
	private String actionName;

	private ShippingOrderDetail orderDetail= new ShippingOrderDetail();
	private List<ShippingOrderDetail> orderDetailModel = new ArrayList<ShippingOrderDetail>();
	private ShippingOrderDetail selectedShippingOrderDetail = new ShippingOrderDetail();
	private ShippingOrderDetail shippingOrderDetail;
	
	private HashMap passMap;
	private ArrayList detailAllDataList;
	private static ArrayList actionList = new ArrayList();
	
	@Init
	public void Init(@ContextParam(ContextType.VIEW) Component view, 
			@ExecutionArgParam("SYSID") String orderSYSID,
			@ExecutionArgParam("ITEMID") String PitemID,
			@ExecutionArgParam("ITEMNAME") String PitemName,
			@ExecutionArgParam("QUANTITY") BigDecimal Pquantity,
			@ExecutionArgParam("UNIT") String Punit,
			@ExecutionArgParam("UNITPRICE") BigDecimal PunitPrice,
			@ExecutionArgParam("TOTAL") BigDecimal Ptotal,
			@ExecutionArgParam("DETAILSYSID") String PdetailSYSID,
			@ExecutionArgParam("DETAILTSTAMP") String PdetailTSTAMP,
			@ExecutionArgParam("ACTION") String action)
	{
		
		ShippingOrderService orderService = new ShippingOrderImp();
		SYSID = orderSYSID;
		
		if(SYSID != null){
			orderDetailModel = orderService.getAllDetailDataBySysID(SYSID);
		}
        passMap = new HashMap();
        if(PitemID != null){
        	passMap.put("ITEMID", PitemID);
        	itemID = PitemID;
        }
        if(PitemName != null){
        	passMap.put("ITEMNAME", PitemName);
        	itemName = PitemName;
        }
        if(Pquantity != null){
        	passMap.put("QUANTITY", Pquantity);
        	quantity = Pquantity;
        }
        if(Punit != null){
        	passMap.put("UNIT", Punit);
        	unit = Punit;
        }
        if(PunitPrice != null){
        	passMap.put("UNITPRICE", PunitPrice);
        	unitPrice = PunitPrice;
        }
        if(Ptotal != null){
        	passMap.put("TOTAL", quantity.multiply(PunitPrice));
        	total = Ptotal;
        }
        if(action != null){
        	passMap.put("ACTION", action);
        	actionName = action;
        }
        if(PdetailSYSID != null){
        	passMap.put("DETAILSYSID", PdetailSYSID);
        	detailSYSID = PdetailSYSID;
        	
        }
        if(PdetailTSTAMP != null){
        	passMap.put("DETAILTSTAMP", PdetailTSTAMP);
        	detailTSTAMP = PdetailTSTAMP;
        }
	}

	public List<ShippingOrderDetail> getOrderDetailModel(){
		
		return orderDetailModel;
	}
	
	public void setOrderDetailModel(List orderDetailModel){
		this.orderDetailModel = orderDetailModel;
	}
	 
	public void  setShippingOrderDetail(ShippingOrderDetail orderDetail) {
	   this.orderDetail = orderDetail;
	}
	public ShippingOrderDetail getShippingOrderDetail() {
		return shippingOrderDetail;
	}
	 
	public void setSelectedShippingOrderDetail(ShippingOrderDetail selected) {
		
	    this.selectedShippingOrderDetail = selected;
    }
	 
	public ShippingOrderDetail getSelectedShippingOrderDetail() {

		return selectedShippingOrderDetail;
	}
	
	@Command
	public void btnSubmit(){
		
		if(checkErrorInfo()){
			
			orderDetail.setItemID(itemID);
			orderDetail.setItemName(itemName);
			orderDetail.setQuantity(quantity);
			orderDetail.setUnit(unit);
			orderDetail.setUnitPrice(unitPrice);
			orderDetail.setTotal(total);
			orderDetail.setSYSID(SYSID);
			
			if(actionName == "Add"){
				String DetailSYSID = "ShippingOrderDetailSYSID" + String.valueOf(new Random().nextInt());
				orderDetail.setShippingOrderDetailSYSID(DetailSYSID.replace("-", ""));
			}else{
				
				String DetailSYSID = this.detailSYSID;
				orderDetail.setShippingOrderDetailSYSID(DetailSYSID);
			}
			java.util.Date date= new java.util.Date();
			orderDetail.setShippingOrderDetailTSTAMP(new Timestamp(date.getTime()).toString());
			
			Map args = new HashMap();
	        args.put("orderDetail", this.orderDetail);
	    	args.put("detailrecordMode", actionName);
	        BindUtils.postGlobalCommand(null, null, "updateShippingOrdersDetailList", args);
		}
	}
	
	@Command
	public void btnCancel(@ContextParam(ContextType.SPACE_OWNER) final Window orderDetailAdd) {
	    // what you want to do before close
		orderDetailAdd.detach();
	}
	
	@Command
	public void btnClear() {
		
		itemID= null;
		itemName= null;
		quantity= new BigDecimal(0);
		unit= "PCS";
		unitPrice= new BigDecimal(0);
		total= new BigDecimal(0);
	}
	
	 // The following method will be called from CustomerCRUDVM after the save
	 // When we say Notifychange("allcustomers), then ZUL list items will be
	 // updated
	@GlobalCommand
	@NotifyChange("orderDetailModel")
	public void updateShippingOrdersDetailList(@BindingParam("orderDetail") ShippingOrderDetail selectedShippingOrderDetail,
		  @BindingParam("detailrecordMode") String detailrecordMode) {
		
	  selectedShippingOrderDetail.setActionName(detailrecordMode);
		
	  if(!actionList.contains(selectedShippingOrderDetail)){
		  actionList.add(selectedShippingOrderDetail);
	  }
	  
	  if (detailrecordMode.equals("Add")) {
		  orderDetailModel.add(selectedShippingOrderDetail);
	  }
	  else if (detailrecordMode.equals("Delete")) {
		 
		  orderDetailModel.remove(selectedShippingOrderDetail);
	  }
	  else if(detailrecordMode.equals("Update")){
		  
		  orderDetailModel.remove(this.selectedShippingOrderDetail);
		  orderDetailModel.add(selectedShippingOrderDetail);
	  }
    }
	
	@Command
	@NotifyChange({"orderDetail","orderDetailModel"})
    public void detail_delete(){

    	if(selectedShippingOrderDetail.getItemID() == null ){
			 Messagebox.show("請選擇一筆資料");
		 }else{
			 
			 Messagebox.show("確定要刪除此筆資料？", "訊息",
				 Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				 new org.zkoss.zk.ui.event.EventListener() {

					@Override
					public void onEvent(Event evt) throws InterruptedException {
						// TODO Auto-generated method stub
						if(evt.getName().equals("onYes")){
							BindUtils.postNotifyChange(null, null, ShippingOrderViewModel.this, "orderDetailModel" );
							Map args = new HashMap();
							selectedShippingOrderDetail.setActionName("Delete");
					        args.put("orderDetail", selectedShippingOrderDetail);
					        args.put("detailrecordMode", "Delete");
					        
					       
					        BindUtils.postGlobalCommand(null, null, "updateShippingOrdersDetailList", args);
						
					        }
					}});
			 }
	 }
	
	@Command
	@NotifyChange({"orderDetail","orderDetailModel"})
	public void detail_add(){
		HashMap passMap = new HashMap();
		passMap.put("ACTION", "Add");
		Window w = (Window) Executions.createComponents("shippingOrderDetail_Add.zul", null, passMap);
		w.doModal();
	}
	
	@Command
	@NotifyChange({"orderDetail","orderDetailModel"})
	public void detail_update(){
		if(selectedShippingOrderDetail.getItemID() != null){
			HashMap updateMap = new HashMap();
			updateMap.put("ITEMID", selectedShippingOrderDetail.getItemID());
			updateMap.put("ITEMNAME", selectedShippingOrderDetail.getItemName());
			updateMap.put("QUANTITY", selectedShippingOrderDetail.getQuantity());
			updateMap.put("UNIT", selectedShippingOrderDetail.getUnit());
			updateMap.put("UNITPRICE", selectedShippingOrderDetail.getUnitPrice());
			updateMap.put("TOTAL", selectedShippingOrderDetail.getTotal());
			updateMap.put("SYSID", selectedShippingOrderDetail.getSYSID());
			updateMap.put("DETAILSYSID", selectedShippingOrderDetail.getShippingOrderDetailSYSID());
			updateMap.put("ACTION", "Update");
			Window w = (Window) Executions.createComponents("shippingOrderDetail_Add.zul", null, updateMap);
			w.doModal();
		}else
		{
			 Messagebox.show("請選擇一筆資料");
		}
	}
	
	public void setItemID(String itemID){
		this.itemID = itemID;
	}
	
	public String getItemID(){
		return itemID;
	}
	
	public void setItemName(String itemName){
		this.itemName = itemName;
	}
	
	public String getItemName(){
		return itemName;
	}
	
	public void setQuantity(BigDecimal quantity){
		this.quantity = quantity;
	}
	
	public BigDecimal getQuantity(){
		return quantity;
	}
	
	public void setUnit(String unit){
		this.unit = unit;
	}
	
	public String getUnit(){
		return unit;
	}
	
	public void setUnitPrice(BigDecimal unitPrice){
		this.unitPrice = unitPrice;
	}
	
	public BigDecimal getUnitPrice(){
		return unitPrice;
	}
	
	public void setTotal(BigDecimal total){
		this.total = total;
	}
	
	public BigDecimal getTotal(){
		return total;
	}
	
	public String getSYSID(){
		return SYSID;
	}
	
	public void setSYSID(String sysid){
		this.SYSID = sysid;
	}
	
	public String getDetailSYSID(){
		return detailSYSID;
	}
	
	public void setDetailSYSID(String detailSYSID){
		this.detailSYSID = detailSYSID;
	}
	
	public String getDetailTSTAMP(){
		return detailTSTAMP;
	}
	
	public void setDetailTSTAMP(String detailTSTAMP){
		this.detailTSTAMP = detailTSTAMP;
	}
	
	public ArrayList getActionList(){
		return actionList;
	}
	
	/* 
	 * 檢查必填欄位
	 */
	private boolean checkErrorInfo(){
		StringBuffer errorInfo = new StringBuffer();
		if(itemID == null){
			errorInfo.append("請輸入件號代號 !\n");
		}
		if(itemName == null){
			errorInfo.append("請輸入件號名稱 !\n");
		}
		if(errorInfo.length() > 0 ){	
			Messagebox.show(errorInfo.toString());
			return false;
		}
		
		return true;
	}
}

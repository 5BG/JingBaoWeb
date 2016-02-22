package tw.com.jingbao.model.shippingorder;

import java.math.BigDecimal;

public class ShippingOrderDetail {

	private String itemID;
	private String itemName;
	private BigDecimal quantity;
	private String unit;
	private BigDecimal unitPrice;
	private BigDecimal total;
	private String SYSID;
	private String shippingOrderDetailSYSID;
	private String shippingOrderDetailTSTAMP;
	private String actionName;
	
	public  ShippingOrderDetail(){
		
	}
	
	public ShippingOrderDetail(String itemID, String itemName, BigDecimal quantity,
			String unit, BigDecimal unitPrice, BigDecimal total){
		
		this.itemID = itemID;
		this.itemName = itemName;
		this.quantity = quantity;
		this.unit = unit;
		this.unitPrice = unitPrice;
		this.total = total;
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
		return quantity.multiply(unitPrice);
	}
	
	public void setSYSID(String sysID){
		this.SYSID = sysID;
	}
	
	public String getSYSID(){
		return SYSID;
	}
	
	public void setShippingOrderDetailSYSID(String shippingOrderDetailSYSID){
		this.shippingOrderDetailSYSID = shippingOrderDetailSYSID;
	}
	
	public String getShippingOrderDetailSYSID(){
		return shippingOrderDetailSYSID;
	}
	
	public void setShippingOrderDetailTSTAMP(String shippingOrderDetailTSTAMP){
		this.shippingOrderDetailTSTAMP = shippingOrderDetailTSTAMP;
	}
	
	public String getShippingOrderDetailTSTAMP(){
		return shippingOrderDetailTSTAMP;
	}
	
	public String getActionName(){
		return actionName;
	}
	
	public void setActionName(String action){
		this.actionName = action;
	}
}

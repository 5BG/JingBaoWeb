package tw.com.jingbao.model.shippingorder;

import java.util.Date;


public class ShippingOrder {
	
	private Date shippingDate;
	private String SDNO;
	private String shipToCustomerID;
	private String shipToCustomerName;
	public  String SYSID;
	private String TSTAMP = null;
	
	public ShippingOrder(){
		
	}
	
	public ShippingOrder(Date shippingDate, String SDNO, 
			String shipToCustomerID, String shipToCustomerName){
		
		this.shippingDate = shippingDate;
		this.SDNO = SDNO;
		this.shipToCustomerID = shipToCustomerID;
		this.shipToCustomerName = shipToCustomerName;
		
	}
	
	// �X�f���
	public void setShippingDate(Date shippingDate)
	{
		this.shippingDate = shippingDate;
	}
	
	public Date getShippingDate()
	{
		return shippingDate;
	}
	
	// �X�f�渹
	public void setSDNO(String SDNO)
	{
		this.SDNO = SDNO;
	}
	public String getSDNO()
	{
		return SDNO;
	}
	
	// �X�f�Ȥ�
	public void setShipToCustomerID(String shipToCustomerID){
		this.shipToCustomerID = shipToCustomerID;
	}
	public String getShipToCustomerID(){
		return shipToCustomerID;
	}
	
	// �X�f�Ȥ�W��
	public void setShipToCustomerName(String shipToCustomerName){
		this.shipToCustomerName = shipToCustomerName;
	}
	public String getShipToCustomerName(){
		return shipToCustomerName;
	}
	// SYSID
	public void setSYSID(String sysID){
		this.SYSID = sysID;
	}
	public String getSYSID(){
		return SYSID;
	}
	// TSTAMP
	public void setTSTAMP(String tStamp){
		this.TSTAMP = tStamp;
	}
	public String getTSTAMP(){
		return TSTAMP;
	}
}

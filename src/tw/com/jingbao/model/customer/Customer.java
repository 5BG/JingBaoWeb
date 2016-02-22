package tw.com.jingbao.model.customer;

public class Customer {
	
	private String customerID;
	private String customerName;
	private String customerPhone;
	private String customerFax;
	private String customerAddress;
	private String remark;
	public  String SYSID;
	private String TSTAMP = null;
	
	public Customer(){
			
	}
	
	public void setCustomerID(String customerID)
	{
		this.customerID = customerID;
	}
	
	public String getCustomerID()
	{
		return customerID;
	}
	
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}
	
	public String getCustomerName()
	{
		return customerName;
	}
	
	public void setCustomerPhone(String customerPhone)
	{
		this.customerPhone = customerPhone;
	}
	
	public String getCustomerPhone()
	{
		return customerPhone;
	}
	
	public void setCustomerFax(String customerFax)
	{
		this.customerFax = customerFax;
	}
	
	public String getCustomerFax()
	{
		return customerFax;
	}
	
	public void setCustomerAddress(String customerAddress)
	{
		this.customerAddress = customerAddress;
	}
	
	public String getCustomerAddress()
	{
		return customerAddress;
	}
	
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	
	public String getRemark()
	{
		return remark;
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

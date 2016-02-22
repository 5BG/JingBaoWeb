 package tw.com.jingbao.db;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import tw.com.jingbao.model.customer.Customer;
import tw.com.jingbao.model.shippingorder.ShippingOrder;
import tw.com.jingbao.model.shippingorder.ShippingOrderDetail;


public interface ShippingOrderDBService {
	public Connection connect() throws Exception;
	public List<ShippingOrder> qryAllShippingOrder();
	public void insert(ShippingOrder order);
	public void update(ShippingOrder order);
	public void delete(ShippingOrder order);
	public List<ShippingOrderDetail> qryAllShippingOrderDetail(String orderSYSID);
	public ShippingOrderDetail qrySingleShippingOrderDetial(String orderDetailSYSID);
	public void insertDetail(ShippingOrderDetail orderDetail);
	public void updateDetail(ShippingOrderDetail orderDetail);
	public void deleteDetail(String orderDetailSYSID);
	public void handleActionList(ArrayList actionList, String orderSYSID);
	
	
}

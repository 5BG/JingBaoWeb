package tw.com.jingbao.model.shippingorder;

import java.util.ArrayList;
import java.util.List;

public interface ShippingOrderService {

	public void action_Add(ShippingOrder order);
	public void action_Update(ShippingOrder order);
	public void action_Delete(ShippingOrder order);
	public List<ShippingOrder> getAllData();
	public List<ShippingOrderDetail> getAllDetailDataBySysID(String sysID);
	public void action_AddDetail(ShippingOrderDetail orderDetail);
	public void action_DeleteDetail(String orderDetailSysID);
	public void handleActionList(ArrayList actionList, String sysID);

	
}

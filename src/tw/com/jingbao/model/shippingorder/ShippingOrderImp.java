package tw.com.jingbao.model.shippingorder;


import java.util.ArrayList;
import java.util.List;

import tw.com.jingbao.db.ShippingOrderDBActionImp;


public class ShippingOrderImp implements ShippingOrderService {
	
	ShippingOrderDBActionImp db = new ShippingOrderDBActionImp();
	
	public ShippingOrderImp(){
		
	}

	@Override
	public List<ShippingOrder> getAllData() {
		// TODO Auto-generated method stub
		return db.qryAllShippingOrder();
	}

	@Override
	public void action_Add(ShippingOrder order) {
		// TODO Auto-generated method stub
		db.insert(order);
	}

	@Override
	public List<ShippingOrderDetail> getAllDetailDataBySysID(String sysID) {
		// TODO Auto-generated method stub
		return db.qryAllShippingOrderDetail(sysID);
	}

	@Override
	public void action_Delete(ShippingOrder order) {
		// TODO Auto-generated method stub
		db.delete(order);
	}

	@Override
	public void action_Update(ShippingOrder order) {
		// TODO Auto-generated method stub
		db.update(order);
	}

	@Override
	public void action_AddDetail(ShippingOrderDetail orderDetail) {
		// TODO Auto-generated method stub
		db.insertDetail(orderDetail);
	}

	@Override
	public void action_DeleteDetail(String detailSysID) {
		// TODO Auto-generated method stub
		db.deleteDetail(detailSysID);
	}

	@Override
	public void handleActionList(ArrayList actionList, String orderSYSID) {
		// TODO Auto-generated method stub
		db.handleActionList(actionList, orderSYSID);
	}

}

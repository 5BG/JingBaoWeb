package tw.com.jingbao.ui;

import tw.com.jingbao.shippingorder.controller.ShippingOrderDetailController;
import tw.com.jingbao.shippingorder.viewmodel.ShippingOrderViewModel;

public class ShippingOrderReference {

	public ShippingOrderViewModel viewModel = new ShippingOrderViewModel();
	public ShippingOrderDetailController detailController;
	
	public ShippingOrderReference(){
	}
	
	public void setShippingOrderViewModel(ShippingOrderViewModel newViewMode)
	{
		System.out.println("setShippingOrderViewModel: " + newViewMode);
		this.viewModel = newViewMode;
	}
	
	public ShippingOrderViewModel getShippingOrderViewModel()
	{
		System.out.println("getShippingOrderViewModel: " + viewModel);
		return viewModel;
	}
	
	public void setShippingOrderDetailController(ShippingOrderDetailController newDetailcontroller)
	{
		this.detailController = newDetailcontroller;
	}
	
	public ShippingOrderDetailController getShippingOrderDetailController()
	{
		return detailController;
	}
}

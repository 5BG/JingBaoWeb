package tw.com.jingbao.shippingorder.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

public class ShippingOrderTabProductComposer extends SelectorComposer<Component>{

	 @Wire("#orderDetailAdd")
     private Window win;
	 
	 public void doAfterCompose(Component comp) throws Exception {
			super.doAfterCompose(comp);
	 }
	 
	 @Listen("onClick = #btnSubmit, #btnCancel")
	 public void closeThis() {
        win.detach();
	 }
}

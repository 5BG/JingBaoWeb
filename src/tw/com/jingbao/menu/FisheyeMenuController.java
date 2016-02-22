package tw.com.jingbao.menu;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkex.zul.Fisheye;
import org.zkoss.zkex.zul.Fisheyebar;
import org.zkoss.zul.Window;

public class FisheyeMenuController extends SelectorComposer<Component>{

	@Wire
    Fisheyebar fisheyebar;
 
    @Listen("onClick = fisheye")
    public void menuItemClicked(Event event) {
//        Clients.showNotification(
//                "Menuitem '" + ((Fisheye) event.getTarget()).getLabel() + "' clicked.", 
//                "info", null, null, 1000);
    	
    	if( ((Fisheye) event.getTarget()).getLabel().equals("出貨單"))
    	{
    		Executions.createComponents("shippingOrder.zul", null, null);
    	}else if( ((Fisheye) event.getTarget()).getLabel().equals("客戶"))
    	{
    		Executions.createComponents("customer.zul", null, null);
    	}
    }
    
    // Detect if client is mobile device (such as Android or iOS devices)
    public boolean isMobile(){
        return Executions.getCurrent().getBrowser("mobile") !=null;
    }
}

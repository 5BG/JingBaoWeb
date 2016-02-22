package tw.com.jingbao.ui;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.Binder;
import org.zkoss.bind.DefaultBinder;
import org.zkoss.xel.VariableResolver;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zk.ui.util.Template;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

public class OrderRichlet extends GenericRichlet{

	private static HashMap<String, Object> formatedNumberArg = new HashMap<String, Object>();
    private static HashMap<String, Object> formatedDateArg = new HashMap<String, Object>();
    private static final String SYS_DATE_CONVERTER = "'formatedDate'"; 
    private static final String SYS_NUMBER_CONVERTER = "'formatedNumber'";
    
	static {
        formatedNumberArg.put("format", "###,##0.00");
        formatedDateArg.put("format","yyyy/MM/dd");
	}

	private Binder binder;
	
	@Override
	public void service(Page page) throws Exception {
		// TODO Auto-generated method stub
		 //1.Create root component which is to be associated with binder
        Window window = new Window("Order Management", "normal", false);
        window.setWidth("600px");
        window.setPage(page);
        //2. Instantiate a binder instance. Use DefaultBinder
        binder = new DefaultBinder(); 
        //3. Initialize it with View model and root component
        //binder.init(window, new OrderVM4());  
        //4. Set binder as an attribute on the root component
        window.setAttribute("vm", binder.getViewModel());


        Vbox vbox = new Vbox();
        vbox.setHflex("true");
        window.appendChild(vbox);

//        vbox.appendChild(buildOrderListbox(binder));
//        vbox.appendChild(buildToolbar(binder));
//        vbox.appendChild(buildFormArea(binder));
//        buildConfirmDialog(binder, window);
        
        // Must load value to components once, call it after all "add property binding" statements
        binder.loadComponent(window,true); 
	}
	
	class ListboxTemplate implements Template{

		  @SuppressWarnings("rawtypes")
          public Component[] create(Component parent, Component insertBefore,
                          VariableResolver resolver, Composer composer){
                  
                  //create template components & add binding expressions
                  Listitem listitem = new Listitem();
                  Listcell SDNOCell = new Listcell();
                  listitem.appendChild(SDNOCell);
                  binder.addPropertyLoadBindings(SDNOCell, "label", "item.SDNO", null, null, null, null, null);
                  Listcell shippingOrderDateCell = new Listcell();
                  listitem.appendChild(shippingOrderDateCell);
                  binder.addPropertyLoadBindings(shippingOrderDateCell, "label", "item.shippingOrderDate", null, null, null, SYS_DATE_CONVERTER, formatedDateArg);
                  Listcell customerIDCell = new Listcell();
                  listitem.appendChild(customerIDCell);
                  binder.addPropertyLoadBindings(customerIDCell, "label", "item.shipToCustomerID", null, null, null, null, null);
                  Listcell customerNameCell = new Listcell();
                  listitem.appendChild(customerNameCell);
                  binder.addPropertyLoadBindings(customerNameCell, "label", "item.shipToCustomerName", null, null, null, null, null);

                  //append to the parent
                  if (insertBefore ==null){
                          parent.appendChild(listitem);
                  }else{
                          parent.insertBefore(listitem, insertBefore);
                  }
                  
                  Component[] components = new Component[1];
                  components [0] = listitem;
                  
                  return components;
          }
          public Map<String, Object> getParameters(){
                  Map<String,Object> parameters = new HashMap<String, Object>();
                  //set binding variable
                  parameters.put("var","item");
                  
                  return parameters;
          }
		
	}
	
	private Component buildOrderListbox(Binder binder){
        Listbox listbox = new Listbox();
        listbox.setHeight("200px");
        listbox.setHflex("true");
        Listhead head = new Listhead();
        listbox.appendChild(head);
        head.appendChild(new Listheader("SDNO"));
        head.appendChild(new Listheader("ShippingOrderDate"));
        head.appendChild(new Listheader("CustomerID"));
        head.appendChild(new Listheader("CustomerName Date"));

        binder.addPropertyLoadBindings(listbox, "model", "vm.orders", null, null, null, null, null);
        binder.addPropertyLoadBindings(listbox, "selectedItem", "vm.selected", null, null, null, null, null);
        binder.addPropertySaveBindings(listbox, "selectedItem", "vm.selected", null, null, null, null, null,null,null);
        //can't create private class org.zkoss.zk.ui.impl.UiEngineImpl.TemplateImpl

        listbox.setTemplate("model", new ListboxTemplate());            
        return listbox;
	}
	
	private Toolbar buildToolbar(Binder binder){
        Button newButton = new Button("New");
        Button saveButton = new Button("Save");
        Button deleteButton = new Button("Delete");

        Toolbar toolbar = new Toolbar();
        toolbar.appendChild(newButton);
        toolbar.appendChild(saveButton);
        toolbar.appendChild(deleteButton);
        
        binder.addCommandBinding(newButton, Events.ON_CLICK, "'newOrder'", null);
        binder.addCommandBinding(saveButton, Events.ON_CLICK, "'saveOrder'", null);
        binder.addPropertyLoadBindings(saveButton, "disabled", "empty vm.selected", null, null, null, null, null);
        binder.addCommandBinding(deleteButton, Events.ON_CLICK, "empty vm.selected.id?'deleteOrder':'confirmDelete'", null);
        binder.addPropertyLoadBindings(deleteButton, "disabled", "empty vm.selected", null, null, null, null, null);
        
        return toolbar;
	}

}

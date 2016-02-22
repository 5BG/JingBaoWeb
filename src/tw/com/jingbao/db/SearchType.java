package tw.com.jingbao.db;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import com.sun.jdi.event.Event;

public class SearchType extends GenericForwardComposer{
	Radiogroup searchtype;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

	}

	public void onClick$btn(Event e) throws InterruptedException{
		
		String selected = searchtype.getSelectedItem().getLabel();
		System.out.println(selected);
		
		if(selected == "«È¤á¥N¸¹")
		{
			 Window window = (Window)Executions.createComponents(
		                "sono_search.zul", null, null);
		     //window.doModal();
		}
		
	}
}

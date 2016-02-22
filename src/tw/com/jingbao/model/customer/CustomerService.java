package tw.com.jingbao.model.customer;

import java.util.List;


public interface CustomerService {

	public void action_Add(Customer customer);
	public void action_Update(Customer customer);
	public void action_Delete(Customer customer);
	public List<Customer> getCustomerData();
}

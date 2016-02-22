package tw.com.jingbao.db;

import java.util.List;

import tw.com.jingbao.model.customer.Customer;

public interface CustomerDBService {
	public List<Customer> qryAllCutomer();
	public void insert(Customer customer);
	public void update(Customer customer);
	public void delete(Customer customer);
}

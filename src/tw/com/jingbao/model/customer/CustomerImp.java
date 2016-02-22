package tw.com.jingbao.model.customer;

import java.util.List;

import tw.com.jingbao.db.CustomerDBActionImp;

public class CustomerImp implements CustomerService{

	CustomerDBActionImp db = new CustomerDBActionImp();
	
	@Override
	public void action_Add(Customer customer) {
		// TODO Auto-generated method stub
		db.insert(customer);
	}

	@Override
	public void action_Update(Customer customer) {
		// TODO Auto-generated method stub
		db.update(customer);
	}

	@Override
	public void action_Delete(Customer customer) {
		// TODO Auto-generated method stub
		db.delete(customer);
	}

	@Override
	public List<Customer> getCustomerData() {
		// TODO Auto-generated method stub
		return db.qryAllCutomer();
	}

}

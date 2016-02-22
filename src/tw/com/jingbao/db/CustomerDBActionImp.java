package tw.com.jingbao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import tw.com.jingbao.model.customer.Customer;
import tw.com.jingbao.model.shippingorder.ShippingOrder;

public class CustomerDBActionImp implements CustomerDBService{

	Customer customer ;
	
	public Connection connect() throws Exception {
		// TODO Auto-generated method stub
		Connection conn = null;
		
		Class.forName("com.mysql.jdbc.Driver");
		// Let's create a connection.
		// setup the properties 
        java.util.Properties prop = new java.util.Properties();
        
        prop.put("user", "root");
        prop.put("password", "");
		conn = DriverManager.getConnection(
			"jdbc:mysql://localhost:3306/JingBao?useUnicode=true&characterEncoding=utf-8",prop);

		return (Connection) conn;
	}
	
	public List<Customer> qryAllCutomer() {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Customer> customerList = null ;
		
		try {
				customerList = new ArrayList();
				
				if(!connect().isClosed()){
					Statement s = connect().createStatement ( );
					s.executeQuery ("SELECT * FROM JingBao.JBCustomer");
					
					ResultSet rs = s.getResultSet ( );
					while (rs.next()) // loop through rows of result set
					{
						customer = new Customer();
						customer.setCustomerID(rs.getString("CUSTOMERID"));
						customer.setCustomerName(rs.getString("CUSTOMERNAME"));
						customer.setCustomerFax(rs.getString("CUSTOMERFAX"));
						customer.setCustomerPhone(rs.getString("CUSTOMERPHONE"));
						customer.setCustomerAddress(rs.getString("CUSTOMERADDRESS"));
						customer.setRemark(rs.getString("REMARK"));
						customer.setSYSID(rs.getString("SYSID"));
						customer.setTSTAMP(rs.getString("TSTAMP"));
						customerList.add(customer);
					}
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerList;
	}

	@Override
	public void insert(Customer customer) {
		// TODO Auto-generated method stub
		String customerID = customer.getCustomerID();
		String customerName = customer.getCustomerName();
		String customerPhone = customer.getCustomerPhone();
		String customerFax = customer.getCustomerFax();
		String customerAddress = customer.getCustomerAddress();
		String remark = customer.getRemark();
		String SYSID = customer.getSYSID();
		java.util.Date date= new java.util.Date();
		String TSTAMP = new Timestamp(date.getTime()).toString();
		
		System.err.println("remark: " + remark);
				
		try {
			if(!connect().isClosed()){
				Statement s = connect().createStatement ( );
				s.executeUpdate ("INSERT INTO JingBao.JBCustomer(CUSTOMERID,CUSTOMERNAME,CUSTOMERPHONE,"
						+ "CUSTOMERFAX,CUSTOMERADDRESS,REMARK,SYSID,TSTAMP) VALUES('"+ customerID + "','" + customerName + "','" + 
						customerPhone + "','"+ customerFax + "','" + customerAddress + "','" + remark + "','" +
						SYSID.replace("-", "") + "','" + TSTAMP + "')");
						
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Customer customer) {
		// TODO Auto-generated method stub
		String customerID = customer.getCustomerID();
		String customerName = customer.getCustomerName();
		String customerPhone = customer.getCustomerPhone();
		String customerFax = customer.getCustomerFax();
		String customerAddress = customer.getCustomerAddress();
		String remark = customer.getRemark();
		String sysID = customer.getSYSID();
		java.util.Date date= new java.util.Date();
		String TSTAMP = new Timestamp(date.getTime()).toString();
		System.err.println("customerID:" + customerID);
		System.err.println("customerName:" + customerName);
		System.err.println("customerPhone:" + customerPhone);
		System.err.println("customerFax:" + customerFax);
		System.err.println("customerAddress:" + customerAddress);
		System.err.println("sysID:" + remark);
		System.err.println("remark:" + remark);
		try {
			if(!connect().isClosed()){
				Statement s = connect().createStatement ( );
				s.executeUpdate ("UPDATE JingBao.JBCustomer SET "+
				"CUSTOMERID='"+customerID+"',"+
				"CUSTOMERNAME='"+customerName+"',"+
				"CUSTOMERPHONE='"+customerPhone+"',"+
				"CUSTOMERFAX='"+customerFax+"',"+
				"CUSTOMERADDRESS='"+customerAddress+"',"+
				"REMARK='"+remark+"',"+
				"TSTAMP='"+TSTAMP+"'"+
				"WHERE SYSID = '"+ sysID+"'");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(Customer customer) {
		// TODO Auto-generated method stub
		String SYSID = customer.getSYSID();
		
		try {
			if(!connect().isClosed()){
				Statement s = connect().createStatement ( );
				s.executeUpdate ("DELETE FROM JingBao.JBCustomer WHERE SYSID = '"+ SYSID+"'");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

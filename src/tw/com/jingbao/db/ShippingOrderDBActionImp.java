package tw.com.jingbao.db;
import java.math.BigDecimal;
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
import tw.com.jingbao.model.shippingorder.ShippingOrderDetail;
 
public class ShippingOrderDBActionImp implements ShippingOrderDBService{
	
	ShippingOrder order ;
	ShippingOrderDetail orderDetail ;
	
	public ShippingOrderDBActionImp(){
	}
	
	@Override
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

	@Override
	public List<ShippingOrder> qryAllShippingOrder() {
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
		
		List<ShippingOrder> allDataList = null ;
		
		try {
				allDataList = new ArrayList();
				
				if(!connect().isClosed()){
					Statement s = connect().createStatement ( );
					s.executeQuery ("SELECT * FROM JingBao.JBShippingOrder");
					
					ResultSet rs = s.getResultSet ( );
					while (rs.next()) // loop through rows of result set
					{
						order = new ShippingOrder();
						order.setShippingDate(rs.getDate("SHIPPINGDATE"));
						order.setSDNO(rs.getString("SDNO"));
						order.setShipToCustomerID(rs.getString("SHIPPINGTOCUSTOMERID"));
						order.setShipToCustomerName(rs.getString("SHIPPINGTOCUSTOMERNAME"));
						order.setSYSID(rs.getString("SYSID"));
						order.setTSTAMP(rs.getString("TSTAMP"));
						allDataList.add(order);
					}
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return allDataList;
	}

	@Override
	public void insert(ShippingOrder order) {
		// TODO Auto-generated method stub
		java.util.Date shippingDate =  new java.util.Date();
		shippingDate = order.getShippingDate();
		java.sql.Date sqlDate = new java.sql.Date(shippingDate.getTime()); 
		String SDNO = order.getSDNO();
		String shipToCustomerID = order.getShipToCustomerID();
		String shipToCustomerName = order.getShipToCustomerName();
		String SYSID = order.getSYSID();
		java.util.Date date= new java.util.Date();
		String TSTAMP = new Timestamp(date.getTime()).toString();
				
		try {
			if(!connect().isClosed()){
				Statement s = connect().createStatement ( );
				s.executeUpdate ("INSERT INTO JingBao.JBShippingOrder(SHIPPINGDATE,SDNO,SHIPPINGTOCUSTOMERID,"
						+ "SHIPPINGTOCUSTOMERNAME,SYSID,TSTAMP) VALUES('"+ sqlDate + "','" + SDNO + "','" + shipToCustomerID + "','"
						+ shipToCustomerName + "','" + SYSID.replace("-", "") + "','" + TSTAMP + "')");
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
	public List<ShippingOrderDetail> qryAllShippingOrderDetail(String shippingOrderSYSID) {
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
		
		List<ShippingOrderDetail> allDetailDataList = null ;
		
		try {
				allDetailDataList = new ArrayList();
			
				if(!connect().isClosed()){
					
					Statement s = connect().createStatement ( );
					s.executeQuery ("SELECT * FROM JingBao.JBShippingOrderDetail WHERE SYSID='" + shippingOrderSYSID +"'");
					
					ResultSet rs = s.getResultSet ( );
					while (rs.next()) // loop through rows of result set
					{
						orderDetail = new ShippingOrderDetail();
						orderDetail.setItemID(rs.getString("ITEMID"));
						orderDetail.setItemName(rs.getString("ITEMNAME"));
						orderDetail.setQuantity(rs.getBigDecimal("QUANTITY"));
						orderDetail.setUnit(rs.getString("UNIT"));
						orderDetail.setUnitPrice(rs.getBigDecimal("UNITPRICE"));
						orderDetail.setTotal(rs.getBigDecimal("TOTAL"));
						orderDetail.setSYSID(rs.getString("SYSID"));
						orderDetail.setShippingOrderDetailSYSID(rs.getString("SHIPPINGORDERDETAISYSID"));
						orderDetail.setShippingOrderDetailTSTAMP(rs.getString("SHIPPINGORDERDETAILTSTAMP"));
						allDetailDataList.add(orderDetail);
					}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return allDetailDataList;
	}

	@Override
	public void delete(ShippingOrder order) {
		// TODO Auto-generated method stub
		String SYSID = order.getSYSID();
		
		try {
			if(!connect().isClosed()){
				Statement s = connect().createStatement ( );
				s.executeUpdate ("DELETE FROM JingBao.JBShippingOrder WHERE SYSID = '"+ SYSID+"'");
				s.executeUpdate ("DELETE FROM JingBao.JBShippingOrderDetail WHERE SYSID = '"+ SYSID+"'");
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
	public void update(ShippingOrder order) {
		// TODO Auto-generated method stub
		String SYSID = order.getSYSID();
		java.util.Date shippingDate =  new java.util.Date();
		shippingDate = order.getShippingDate();
		java.sql.Date sqlDate = new java.sql.Date(shippingDate.getTime()); 
		String SDNO = order.getSDNO();
		String shipToCustomerID = order.getShipToCustomerID();
		String shipToCustomerName = order.getShipToCustomerName();
		java.util.Date date= new java.util.Date();
		String TSTAMP = new Timestamp(date.getTime()).toString();
				
		try {
			if(!connect().isClosed()){
				Statement s = connect().createStatement ( );
				s.executeUpdate ("UPDATE JingBao.JBShippingOrder SET "+
				"SHIPPINGDATE='"+shippingDate+"',"+
				"SDNO='"+SDNO+"',"+
				"SHIPPINGTOCUSTOMERID='"+shipToCustomerID+"',"+
				"SHIPPINGTOCUSTOMERNAME='"+shipToCustomerName+"',"+
				"TSTAMP='"+TSTAMP+"'"+
				"WHERE SYSID = '"+ SYSID+"'");
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
	public void insertDetail(ShippingOrderDetail orderDetail) {
		// TODO Auto-generated method stub
		String itemID = orderDetail.getItemID();
		String itemName = orderDetail.getItemName();
		String unit = orderDetail.getUnit();
		BigDecimal quantity = orderDetail.getQuantity();
		BigDecimal unitPrice = orderDetail.getUnitPrice();
		BigDecimal total = quantity.multiply(unitPrice);
		String SYSID = orderDetail.getSYSID();
		String DetailSYSID = orderDetail.getShippingOrderDetailSYSID();
		String TSTAMP = orderDetail.getShippingOrderDetailTSTAMP();
		try {
			if(!connect().isClosed()){
				Statement s = connect().createStatement ( );
				s.executeUpdate ("INSERT INTO JingBao.JBShippingOrderDetail(ITEMID,ITEMNAME,QUANTITY,"
					+ "UNIT,UNITPRICE,TOTAL,SYSID,SHIPPINGORDERDETAISYSID,SHIPPINGORDERDETAILTSTAMP) VALUES('"+ 
					itemID + "','" + itemName + "','" + quantity + "','"
					+ unit + "','" + unitPrice + "','" + total+"','" +SYSID +"','" +
					DetailSYSID.replace("-", "") + "','" + TSTAMP + "')");
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
	public void deleteDetail(String orderDetailSYSID) {
		// TODO Auto-generated method stub
		
		try {
			if(!connect().isClosed()){
				Statement s = connect().createStatement ( );
				s.executeUpdate ("DELETE FROM JingBao.JBShippingOrderDetail WHERE SHIPPINGORDERDETAISYSID = '"+ orderDetailSYSID+"'");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void handleActionList(ArrayList actionList, String orderSYSID){
		System.out.println(actionList.size());
		for(int i = 0 ; i < actionList.size() ; i++){
			String action = null;
			ShippingOrderDetail orderDetail = (ShippingOrderDetail) actionList.get(i);
			orderDetail.setSYSID(orderSYSID);
			
			if(orderDetail.getActionName()=="Add"){
				insertDetail(orderDetail);
			}else if(orderDetail.getActionName()=="Update"){
				ShippingOrderDetail returnResult = qrySingleShippingOrderDetial(orderDetail.getShippingOrderDetailSYSID());
				if(returnResult== null){
					insertDetail(orderDetail);
				}else{
					updateDetail(orderDetail);
				}
			}else{
				deleteDetail(orderDetail.getShippingOrderDetailSYSID());
			}
		}
	}

	@Override
	public void updateDetail(ShippingOrderDetail orderDetail) {
		// TODO Auto-generated method stub
		String SYSID = orderDetail.getSYSID();
		String itemID = orderDetail.getItemID();
		String itemName = orderDetail.getItemName();
		BigDecimal quantity = orderDetail.getQuantity();
		String unit = orderDetail.getUnit();
		BigDecimal unitPrice = orderDetail.getUnitPrice();
		BigDecimal total = quantity.multiply(unitPrice);
		java.util.Date date= new java.util.Date();
		String detailSYSID = orderDetail.getShippingOrderDetailSYSID();
		String detailTSTAMP = new Timestamp(date.getTime()).toString();
		System.out.println("unitPrice: " + unitPrice);
				
		try {
			if(!connect().isClosed()){
				Statement s = connect().createStatement ( );
				s.executeUpdate ("UPDATE JingBao.JBShippingOrderDetail SET "+
				"ITEMID='"+itemID+"',"+
				"ITEMNAME='"+itemName+"',"+
				"QUANTITY='"+quantity+"',"+
				"UNIT='"+unit+"',"+
				"UNITPRICE='"+unitPrice+"',"+
				"TOTAL='"+total+"',"+
				"SHIPPINGORDERDETAILTSTAMP='"+detailTSTAMP+"'"+
				"WHERE SHIPPINGORDERDETAISYSID = '"+ detailSYSID+"'");
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
	public ShippingOrderDetail qrySingleShippingOrderDetial(String orderDetailSYSID) {
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
		
		try {
				if(!connect().isClosed()){
					
					Statement s = connect().createStatement ( );
					s.executeQuery ("SELECT * FROM JingBao.JBShippingOrderDetail WHERE SHIPPINGORDERDETAISYSID='" + orderDetailSYSID +"'");
					
					ResultSet rs = s.getResultSet ( );
					while (rs.next()) // loop through rows of result set
					{
						orderDetail = new ShippingOrderDetail();
						orderDetail.setItemID(rs.getString("ITEMID"));
						orderDetail.setItemName(rs.getString("ITEMNAME"));
						orderDetail.setQuantity(rs.getBigDecimal("QUANTITY"));
						orderDetail.setUnit(rs.getString("UNIT"));
						orderDetail.setUnitPrice(rs.getBigDecimal("UNITPRICE"));
						orderDetail.setTotal(rs.getBigDecimal("TOTAL"));
						orderDetail.setSYSID(rs.getString("SYSID"));
						orderDetail.setShippingOrderDetailSYSID(rs.getString("SHIPPINGORDERDETAISYSID"));
						orderDetail.setShippingOrderDetailTSTAMP(rs.getString("SHIPPINGORDERDETAILTSTAMP"));
					}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return orderDetail;
	}

	

}
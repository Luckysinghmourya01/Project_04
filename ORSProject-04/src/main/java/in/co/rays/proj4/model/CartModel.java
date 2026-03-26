package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CartBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DublicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class CartModel {

	public Integer nextPk() throws DatabaseException {
	    Connection conn = null;
	    int pk = 0;

	    try {
	        conn = JDBCDataSource.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_cart");
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            pk = rs.getInt(1);
	        }
	    } catch (Exception e) {
	        throw new DatabaseException("Exception in getting PK");
	    } finally {
	        JDBCDataSource.closeconnection(conn);
	    }
	    return pk + 1;
	}
	
	public long add(CartBean bean) throws ApplicationException, DublicateRecordException {
		
	    Connection conn = null;
	    int pk = 0;
	    
	    CartBean existBean = 	findByUserName(bean.getUserName());
	    
	    if(existBean != null) {
	    	throw new DublicateRecordException("name already exist");
	    }

	    try {
	        pk = nextPk();
	        conn = JDBCDataSource.getConnection();
	        conn.setAutoCommit(false);

	        PreparedStatement pstmt = conn.prepareStatement(
	            "insert into st_cart values(?, ?, ?, ?, ?)"
	        );

	        pstmt.setInt(1, pk);
	        pstmt.setString(2, bean.getCartCode());
	        pstmt.setString(3, bean.getUserName());
	        pstmt.setInt(4, bean.getTotalItem());
	        pstmt.setString(5, bean.getStatus());

	        pstmt.executeUpdate();
	        conn.commit();
	        pstmt.close();

	    } catch (Exception e) {
	        try {
	            conn.rollback();
	        } catch (Exception ex) {
	            throw new ApplicationException("Rollback error " + ex.getMessage());
	        }
	        throw new ApplicationException("Exception in add Cart");
	    } finally {
	        JDBCDataSource.closeconnection(conn);
	    }
	    return pk;
	}
	
	public void delete(CartBean bean) throws ApplicationException {

	    Connection conn = null;

	    try {
	        conn = JDBCDataSource.getConnection();
	        conn.setAutoCommit(false);

	        PreparedStatement pstmt = conn.prepareStatement(
	            "delete from st_cart where id=?"
	        );

	        pstmt.setLong(1, bean.getId());
	        pstmt.executeUpdate();

	        conn.commit();
	        pstmt.close();

	    } catch (Exception e) {
	        try {
	            conn.rollback();
	        } catch (Exception ex) {
	            throw new ApplicationException("Rollback error " + ex.getMessage());
	        }
	        throw new ApplicationException("Exception in delete");
	    } finally {
	        JDBCDataSource.closeconnection(conn);
	    }
	}
	
	public void update(CartBean bean) throws ApplicationException {

	    Connection conn = null;

	    try {
	        conn = JDBCDataSource.getConnection();
	        conn.setAutoCommit(false);

	        PreparedStatement pstmt = conn.prepareStatement(
	            "update st_cart set cart_code=?, user_name=?, total_item=?, status=? where id=?"
	        );

	        pstmt.setString(1, bean.getCartCode());
	        pstmt.setString(2, bean.getUserName());
	        pstmt.setInt(3, bean.getTotalItem());
	        pstmt.setString(4, bean.getStatus());
	        pstmt.setLong(5, bean.getId());

	        pstmt.executeUpdate();
	        conn.commit();
	        pstmt.close();

	    } catch (Exception e) {
	        try {
	            conn.rollback();
	        } catch (Exception ex) {
	            throw new ApplicationException("Rollback error " + ex.getMessage());
	        }
	        throw new ApplicationException("Exception in update");
	    } finally {
	        JDBCDataSource.closeconnection(conn);
	    }
	}
	
	public CartBean findByPk(long pk) throws ApplicationException {

	    Connection conn = null;
	    CartBean bean = null;

	    try {
	        conn = JDBCDataSource.getConnection();

	        PreparedStatement pstmt = conn.prepareStatement(
	            "select * from st_cart where id=?"
	        );

	        pstmt.setLong(1, pk);
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            bean = new CartBean();
	            bean.setId(rs.getLong(1));
	            bean.setCartCode(rs.getString(2));
	            bean.setUserName(rs.getString(3));
	            bean.setTotalItem(rs.getInt(4));
	            bean.setStatus(rs.getString(5));
	        }

	        rs.close();
	        pstmt.close();

	    } catch (Exception e) {
	        throw new ApplicationException("Exception in findByPk");
	    } finally {
	        JDBCDataSource.closeconnection(conn);
	    }

	    return bean;
	}
	
	public List<CartBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	
	public List<CartBean> search(CartBean bean, int pageNo, int pageSize)
	        throws ApplicationException {

	    ArrayList<CartBean> list = new ArrayList();
	    Connection conn = null;

	    StringBuffer sql = new StringBuffer("select * from st_cart where 1=1");

	    if (bean != null) {

	        if (bean.getCartCode() != null && bean.getCartCode().length() > 0) {
	            sql.append(" and cart_code like '" + bean.getCartCode() + "%'");
	        }

	        if (bean.getUserName() != null && bean.getUserName().length() > 0) {
	            sql.append(" and user_name like '" + bean.getUserName() + "%'");
	        }

	        if (bean.getTotalItem() != null && bean.getTotalItem() > 0) {
	            sql.append(" and total_item = " + bean.getTotalItem());
	        }

	        if (bean.getStatus() != null && bean.getStatus().length() > 0) {
	            sql.append(" and status like '" + bean.getStatus() + "%'");
	        }
	    }

	    if (pageSize > 0) {
	        pageNo = (pageNo - 1) * pageSize;
	        sql.append(" limit " + pageNo + ", " + pageSize);
	    }

	    try {
	        conn = JDBCDataSource.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            CartBean cb = new CartBean();
	            cb.setId(rs.getLong(1));
	            cb.setCartCode(rs.getString(2));
	            cb.setUserName(rs.getString(3));
	            cb.setTotalItem(rs.getInt(4));
	            cb.setStatus(rs.getString(5));
	            list.add(cb);
	        }

	        rs.close();
	        pstmt.close();

	    } catch (Exception e) {
	        throw new ApplicationException("Exception in search");
	    } finally {
	        JDBCDataSource.closeconnection(conn);
	    }

	    return list;
	}
	
	public CartBean findByUserName(String userName) throws ApplicationException {

	    StringBuffer sql = new StringBuffer("select * from st_cart where user_name = ?");

	    CartBean bean = null;
	    Connection conn = null;

	    try {
	        conn = JDBCDataSource.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql.toString());

	        pstmt.setString(1, userName);

	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            bean = new CartBean();

	            bean.setId(rs.getLong(1));
	            bean.setCartCode(rs.getString(2));
	            bean.setUserName(rs.getString(3));
	            bean.setTotalItem(rs.getInt(4));
	            bean.setStatus(rs.getString(5));
	        }

	        rs.close();
	        pstmt.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new ApplicationException("Exception in getting Cart by userName");
	    } finally {
	        JDBCDataSource.closeconnection(conn);
	    }

	    return bean;
	}
}

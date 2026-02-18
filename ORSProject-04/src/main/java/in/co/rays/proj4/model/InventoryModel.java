package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.cj.protocol.Resultset;

import in.co.rays.proj4.bean.InventoryBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

public class InventoryModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstm = conn.prepareStatement("select max(id) from st_inventory");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				rs.getInt(1);
			}
		} catch (Exception e) {
			throw new DatabaseException("exception is getting next pk");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return pk + 1;
	}

	public long add(InventoryBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;
		try {
			 pk = nextPk();

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstm = conn.prepareStatement("insert into st_inventory values(?,?,?,?,?,?,?,?,?)");
			conn.setAutoCommit(false);
			pstm.setInt(1, pk);
			pstm.setString(2, bean.getSupplierName());
			pstm.setDate(3, new java.sql.Date(bean.getDob().getTime()));
			pstm.setLong(4, bean.getQuantity());
			pstm.setString(5, bean.getProduct());
			pstm.setString(6, bean.getCreatedby());
			pstm.setString(7, bean.getModifiedby());
			pstm.setTimestamp(8, bean.getCreateddatetime());
			pstm.setTimestamp(9, bean.getModifieddatetime());

			pstm.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());

			}
			throw new ApplicationException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return pk ;
	}
}

package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.TransportBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

   

public class TransportModel {
	
	
	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_transport");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}

		} catch (Exception e) {
			
			throw new DatabaseException("Exception : getting pk");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return pk + 1;
	}
	   
	public long add(TransportBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {

			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"insert into st_transport values(?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setLong(2, bean.getTransportId());
			pstmt.setString(3, bean.getVehicleNo());
			pstmt.setString(4, bean.getDriverName());
			pstmt.setString(5, bean.getVehicleType());
			pstmt.setString(6, bean.getTransportStatus());

			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Add rollback exception");
			}

			throw new ApplicationException("Exception in add Transport");

		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return pk;
	}
	
	public void delete(TransportBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"delete from st_transport where id=?");

			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Delete rollback exception");
			}

			throw new ApplicationException("Exception in delete Transport");

		} finally {
			JDBCDataSource.closeconnection(conn);
		}
	}
	
	public void update(TransportBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_transport set transport_id=?, vehicle_no=?, driver_name=?, vehicle_type=?, transport_status=? where id=?");

			pstmt.setLong(1, bean.getTransportId());
			pstmt.setString(2, bean.getVehicleNo());
			pstmt.setString(3, bean.getDriverName());
			pstmt.setString(4, bean.getVehicleType());
			pstmt.setString(5, bean.getTransportStatus());
			pstmt.setLong(6, bean.getId());

			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception");
			}

			throw new ApplicationException("Exception in update Transport");

		} finally {
			JDBCDataSource.closeconnection(conn);
		}
	}
	
	public TransportBean findByPk(long pk) throws ApplicationException {

		Connection conn = null;
		TransportBean bean = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(
					"select * from st_transport where id=?");

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new TransportBean();

				bean.setId(rs.getInt(1));
				bean.setTransportId(rs.getLong(2));
				bean.setVehicleNo(rs.getString(3));
				bean.setDriverName(rs.getString(4));
				bean.setVehicleType(rs.getString(5));
				bean.setTransportStatus(rs.getString(6));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting transport by pk");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return bean;
	}
	
	public List search(TransportBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_transport where 1=1");

		if (bean != null) {

			if (bean.getTransportId() > 0) {
				sql.append(" and transport_id = " + bean.getTransportId());
			}

			if (bean.getVehicleNo() != null && bean.getVehicleNo().length() > 0) {
				sql.append(" and vehicle_no like '" + bean.getVehicleNo() + "%'");
			}

			if (bean.getDriverName() != null && bean.getDriverName().length() > 0) {
				sql.append(" and driver_name like '" + bean.getDriverName() + "%'");
			}

			if (bean.getVehicleType() != null && bean.getVehicleType().length() > 0) {
				sql.append(" and vehicle_type like '" + bean.getVehicleType() + "%'");
			}

			if (bean.getTransportStatus() != null && bean.getTransportStatus().length() > 0) {
				sql.append(" and transport_status like '" + bean.getTransportStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new TransportBean();

				bean.setId(rs.getInt(1));
				bean.setTransportId(rs.getLong(2));
				bean.setVehicleNo(rs.getString(3));
				bean.setDriverName(rs.getString(4));
				bean.setVehicleType(rs.getString(5));
				bean.setTransportStatus(rs.getString(6));

				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search Transport");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return list;
	}
	
	
}

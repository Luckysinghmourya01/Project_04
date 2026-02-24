package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.AtmBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

public class AtmModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstm = conn.prepareStatement("select max(id) from st_atm");
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}

			rs.close();
			pstm.close();

		} catch (Exception e) {
			throw new DatabaseException("Exception in getting PK");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return pk + 1;
	}

	public long add(AtmBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_atm values(?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getLocation());
			pstmt.setInt(3, bean.getCashAailable());
			pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(5, bean.getRemark());

			pstmt.executeUpdate();
			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in adding ATM");

		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return pk;
	}

	public void delete(AtmBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_atm where id=?");

			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in deleting ATM");

		} finally {
			JDBCDataSource.closeconnection(conn);
		}
	}

	public void update(AtmBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("update st_atm set location=?, cash_available=?, dob=?, remark=? where id=?");

			pstmt.setString(1, bean.getLocation());
			pstmt.setInt(2, bean.getCashAailable());
			pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(4, bean.getRemark());
			pstmt.setLong(5, bean.getId());

			pstmt.executeUpdate();
			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in updating ATM");

		} finally {
			JDBCDataSource.closeconnection(conn);
		}
	}

	public AtmBean findByPk(long pk) throws ApplicationException {

		Connection conn = null;
		AtmBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_atm where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new AtmBean();
				bean.setId(rs.getLong(1));
				bean.setLocation(rs.getString(2));
				bean.setCashAailable(rs.getInt(3));
				bean.setDob(rs.getDate(4));
				bean.setRemark(rs.getString(5));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in finding ATM by PK");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return bean;
	}

	public List<AtmBean> search(AtmBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		ArrayList<AtmBean> list = new ArrayList<>();

		StringBuffer sql = new StringBuffer("select * from st_atm where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}

			if (bean.getLocation() != null && bean.getLocation().length() > 0) {
				sql.append(" and location like '" + bean.getLocation() + "%'");
			}

			if (bean.getCashAailable() > 0) {
				sql.append(" and cash_available = " + bean.getCashAailable());
			}

			if (bean.getDob() != null) {
				sql.append(" and dob = '" + new java.sql.Date(bean.getDob().getTime()) + "'");
			}

			if (bean.getRemark() != null && bean.getRemark().length() > 0) {
				sql.append(" and remark like '" + bean.getRemark() + "%'");
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

				bean = new AtmBean();

				bean.setId(rs.getLong(1));
				bean.setLocation(rs.getString(2));
				bean.setCashAailable(rs.getInt(3));
				bean.setDob(rs.getDate(4));
				bean.setRemark(rs.getString(5));

				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching ATM");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return list;
	}
}

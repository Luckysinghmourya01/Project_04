package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.TicketBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DublicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class TicketModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstm = conn.prepareStatement("select max(id) from st_ticket");
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}

		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting nextpk");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return pk + 1;
	}

	// ================== ADD ==================

	public long add(TicketBean bean) throws ApplicationException, DublicateRecordException {

		Connection conn = null;
		int pk = 0;

		/*
		 * TicketBean duplicate = findByTicketCode(bean.getTicketCode()); if (duplicate
		 * != null) { throw new DublicateRecordException("Ticket Code already exists");
		 * }
		 */

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_ticket values(?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getTicketCode());
			pstmt.setString(3, bean.getTittle());
			pstmt.setString(4, bean.getAssigendAgent());
			pstmt.setString(5, bean.getTicketStatus());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in add Ticket");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return pk;
	}

	// ================== UPDATE ==================

	public void update(TicketBean bean) throws ApplicationException, DublicateRecordException {

		Connection conn = null;

		TicketBean duplicate = findByTicketCode(bean.getTicketCode());
		if (duplicate != null && duplicate.getId() != bean.getId()) {
			throw new DublicateRecordException("Ticket Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_ticket set ticketCode=?, tittle=?, assigendAgent=?, ticketStatus=? where id=?");

			pstmt.setString(1, bean.getTicketCode());
			pstmt.setString(2, bean.getTittle());
			pstmt.setString(3, bean.getAssigendAgent());
			pstmt.setString(4, bean.getTicketStatus());
			pstmt.setLong(5, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in update Ticket");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
	}

	// ================== DELETE ==================

	public void delete(TicketBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_ticket where id=?");
			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Delete rollback exception " + ex.getMessage());
			}
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
	}

	// ================== FIND BY PK ==================

	public TicketBean findByPk(long pk) throws ApplicationException {

		TicketBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_ticket where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new TicketBean();
				bean.setId(rs.getLong(1));
				bean.setTicketCode(rs.getString(2));
				bean.setTittle(rs.getString(3));
				bean.setAssigendAgent(rs.getString(4));
				bean.setTicketStatus(rs.getString(5));
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

	// ================== FIND BY TICKET CODE ==================

	public TicketBean findByTicketCode(String ticketCode) throws ApplicationException {

		TicketBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_ticket where ticketCode=?");

			pstmt.setString(1, ticketCode);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new TicketBean();
				bean.setId(rs.getLong(1));
				bean.setTicketCode(rs.getString(2));
				bean.setTittle(rs.getString(3));
				bean.setAssigendAgent(rs.getString(4));
				bean.setTicketStatus(rs.getString(5));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByTicketCode");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return bean;
	}

	// ================== SEARCH ==================

	public List<TicketBean> search(TicketBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_ticket where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}

			if (bean.getTicketCode() != null && bean.getTicketCode().length() > 0) {
				sql.append(" and ticketCode like '" + bean.getTicketCode() + "%'");
			}

			if (bean.getTittle() != null && bean.getTittle().length() > 0) {
				sql.append(" and tittle like '" + bean.getTittle() + "%'");
			}

			if (bean.getTicketStatus() != null && bean.getTicketStatus().length() > 0) {
				sql.append(" and ticketStatus like '" + bean.getTicketStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		Connection conn = null;
		ArrayList<TicketBean> list = new ArrayList<>();

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new TicketBean();
				bean.setId(rs.getLong(1));
				bean.setTicketCode(rs.getString(2));
				bean.setTittle(rs.getString(3));
				bean.setAssigendAgent(rs.getString(4));
				bean.setTicketStatus(rs.getString(5));
				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search Ticket");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return list;
	}
}

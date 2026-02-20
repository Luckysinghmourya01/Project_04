package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.SubscriptionBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DublicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class SubscriptionModel {

	public Long nextPk() throws DatabaseException {

		Connection conn = null;
		Long pk = 0L;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_subscription");
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				pk = rs.getLong(1);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new DatabaseException("Exception in getting PK");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return pk + 1;
	}

	public Long add(SubscriptionBean bean) throws ApplicationException, DublicateRecordException {

		Connection conn = null;
		Long pk = 0L;

		SubscriptionBean existBean = findBySubscriptionCode(bean.getSubscriptionCode());
		if (existBean != null) {
			throw new DublicateRecordException("code already exist");
		}

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_subscription values(?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getSubscriptionCode());
			pstmt.setString(3, bean.getPlanName());
			pstmt.setDate(4, new java.sql.Date(bean.getStartDate().getTime()));
			pstmt.setDate(5, new java.sql.Date(bean.getEndDate().getTime()));
			pstmt.setString(6, bean.getSubscriptionStatus());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in add Subscription");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return pk;
	}

	public void update(SubscriptionBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_subscription set subscription_code=?, plan_name=?, start_date=?, end_date=?, subscription_status=? where id=?");

			pstmt.setString(1, bean.getSubscriptionCode());
			pstmt.setString(2, bean.getPlanName());
			pstmt.setDate(3, new java.sql.Date(bean.getStartDate().getTime()));
			pstmt.setDate(4, new java.sql.Date(bean.getEndDate().getTime()));
			pstmt.setString(5, bean.getSubscriptionStatus());
			pstmt.setLong(6, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Subscription");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
	}

	public void delete(SubscriptionBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_subscription where id=?");

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
			throw new ApplicationException("Exception in deleting Subscription");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
	}

	public SubscriptionBean findBySubscriptionCode(String code) throws ApplicationException {

		Connection conn = null;
		SubscriptionBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_subscription where subscription_code=?");

			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new SubscriptionBean();
				bean.setId(rs.getLong(1));
				bean.setSubscriptionCode(rs.getString(2));
				bean.setPlanName(rs.getString(3));
				bean.setStartDate(rs.getDate(4));
				bean.setEndDate(rs.getDate(5));
				bean.setSubscriptionStatus(rs.getString(6));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in findBySubscriptionCode");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return bean;
	}

	public List<SubscriptionBean> search(SubscriptionBean bean, int pageNo, int pageSize) throws ApplicationException {

		List<SubscriptionBean> list = new ArrayList<>();
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_subscription where 1=1");

		if (bean != null) {

			if (bean.getSubscriptionCode() != null && bean.getSubscriptionCode().length() > 0) {
				sql.append(" and subscription_code like '" + bean.getSubscriptionCode() + "%'");
			}

			if (bean.getPlanName() != null && bean.getPlanName().length() > 0) {
				sql.append(" and plan_name like '" + bean.getPlanName() + "%'");
			}

			if (bean.getSubscriptionStatus() != null && bean.getSubscriptionStatus().length() > 0) {
				sql.append(" and subscription_status like '" + bean.getSubscriptionStatus() + "%'");
			}
		}

		// Pagination Logic
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				SubscriptionBean sub = new SubscriptionBean();
				sub.setId(rs.getLong(1));
				sub.setSubscriptionCode(rs.getString(2));
				sub.setPlanName(rs.getString(3));
				sub.setStartDate(rs.getDate(4));
				sub.setEndDate(rs.getDate(5));
				sub.setSubscriptionStatus(rs.getString(6));
				list.add(sub);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search Subscription");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return list;
	}
}

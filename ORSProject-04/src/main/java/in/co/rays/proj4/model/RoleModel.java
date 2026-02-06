package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DublicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class RoleModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstm = conn.prepareStatement("select max(id) from st_role");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}

		} catch (Exception e) {
			throw new DatabaseException("Exception : exceptin in getting nextpk");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return pk + 1;
	}

	public long add(RoleBean bean) throws ApplicationException, DublicateRecordException {

		Connection conn = null;
		int pk = 0;

		RoleBean dublicate = findByName(bean.getName());
		if (dublicate != null) {
			throw new DublicateRecordException("record already exist");
		}

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_role values(?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getDescription());
			pstmt.setString(4, bean.getCreatedby());
			pstmt.setString(5, bean.getModifiedby());
			pstmt.setTimestamp(6, bean.getCreateddatetime());
			pstmt.setTimestamp(7, bean.getModifieddatetime());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Role");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return pk;
	}

	public void update(RoleBean bean) throws ApplicationException, DublicateRecordException {

		Connection conn = null;

		RoleBean dublicate = findByName(bean.getName());
		if (dublicate != null && dublicate.getId() != bean.getId()) {
			throw new DublicateRecordException("Record already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstm = conn.prepareStatement(
					"update st_role set name=?,description=? , created_by=? , modified_by=? , created_datetime=? , modified_datetime=? where id=?");
			pstm.setString(1, bean.getName());
			pstm.setString(2, bean.getDescription());
			pstm.setString(3, bean.getCreatedby());
			pstm.setString(4, bean.getModifiedby());
			pstm.setTimestamp(5, bean.getCreateddatetime());
			pstm.setTimestamp(6, bean.getModifieddatetime());
			pstm.setLong(7, bean.getId());

			pstm.executeUpdate();
			conn.commit();
			pstm.close();
		} catch (Exception e) {

			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("exception : update rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("exception : exception in updated roll");

		}

		finally {
			JDBCDataSource.closeconnection(conn);
		}
	}

	public void delete(RoleBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstm = conn.prepareStatement("delete  from st_role where id=?");
			pstm.setLong(1, bean.getId());
			pstm.executeUpdate();
			conn.commit();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("exception : exception delete rollback exception");
			}
			throw new ApplicationException("exception : exception in delete st_role");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
	}

	public RoleBean findByPk(long pk) throws ApplicationException {

		RoleBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_role where id = ?");

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedby(rs.getString(4));
				bean.setModifiedby(rs.getString(5));
				bean.setCreateddatetime(rs.getTimestamp(6));
				bean.setCreateddatetime(rs.getTimestamp(7));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting find by pk");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return bean;
	}

	public RoleBean findByName(String name) throws ApplicationException {

		Connection conn = null;
		RoleBean bean = null;
		StringBuffer sql = new StringBuffer("select * from st_role where name=?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, name);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedby(rs.getString(4));
				bean.setModifiedby(rs.getString(5));
				bean.setCreateddatetime(rs.getTimestamp(6));
				bean.setModifieddatetime(rs.getTimestamp(7));

			}
			conn.close();
		} catch (Exception e) {
			throw new ApplicationException("exception : exception is getting find by name");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return bean;
	}
	
	public List<RoleBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<RoleBean> search(RoleBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_role where 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append("and id=" + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append("and name like" + bean.getName() + "%");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append("and describtion like" + bean.getDescription() + "%");
			}
		}
		
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		Connection conn = null;
		ArrayList<RoleBean> list = new ArrayList<RoleBean>();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql.toString());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedby(rs.getString(4));
				bean.setModifiedby(rs.getString(5));
				bean.setCreateddatetime(rs.getTimestamp(6));
				bean.setModifieddatetime(rs.getTimestamp(7));
				list.add(bean);
			}
			conn.close();
		} catch (Exception e) {
			throw new ApplicationException("exception : exception in getting search role");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return list;
	}

}

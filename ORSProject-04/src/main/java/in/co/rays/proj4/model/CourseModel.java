package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DublicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class CourseModel {

	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_course");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return pk + 1;
	}

	public long add(CourseBean bean) throws ApplicationException, DublicateRecordException {
		Connection conn = null;
		int pk = 0;

		CourseBean duplicateCourse = findByName(bean.getName());

		if (duplicateCourse != null) {
			throw new DublicateRecordException("Course Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_course values(?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getDuration());
			pstmt.setString(4, bean.getDescription());
			pstmt.setString(5, bean.getCreatedby());
			pstmt.setString(6, bean.getModifiedby());
			pstmt.setTimestamp(7, bean.getCreateddatetime());
			pstmt.setTimestamp(8, bean.getModifieddatetime());
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
			throw new ApplicationException("Exception : Exception in add Course");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return pk;
	}

	public void delete(CourseBean bean) throws ApplicationException {
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstm = conn.prepareStatement("delete from st_course where id=?");
			pstm.setLong(1, bean.getId());
			pstm.executeUpdate();
			conn.commit();
			pstm.close();
		} catch (Exception e) {
			e.getMessage();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("exception : exception is getting delete rollback" + e.getMessage());
			}
			throw new ApplicationException("exception : exception is getting delete course");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
	}

	public void update(CourseBean bean) throws ApplicationException, DublicateRecordException {
		Connection conn = null;

		CourseBean duplicateCourse = findByName(bean.getName());
		if (duplicateCourse != null && duplicateCourse.getId() != bean.getId()) {
			throw new DublicateRecordException("Course already exists");
		}
		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_course set name = ?, duration = ?, description = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getDuration());
			pstmt.setString(3, bean.getDescription());
			pstmt.setString(4, bean.getCreatedby());
			pstmt.setString(5, bean.getModifiedby());
			pstmt.setTimestamp(6, bean.getCreateddatetime());
			pstmt.setTimestamp(7, bean.getModifieddatetime());
			pstmt.setLong(8, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Course ");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

	}

	public CourseBean findByPk(long pk) throws ApplicationException {

		Connection conn = null;
		CourseBean bean = null;
		StringBuffer sql = new StringBuffer("select * from st_course where id=?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, pk);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedby(rs.getString(5));
				bean.setModifiedby(rs.getString(6));
				bean.setCreateddatetime(rs.getTimestamp(7));
				bean.setModifieddatetime(rs.getTimestamp(8));

			}
			pstm.close();
		} catch (Exception e) {
			throw new ApplicationException("exception : exception is getting find by pk couse");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return bean;
	}

	public CourseBean findByName(String name) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_course where name = ?");
		CourseBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedby(rs.getString(5));
				bean.setModifiedby(rs.getString(6));
				bean.setCreateddatetime(rs.getTimestamp(7));
				bean.setModifieddatetime(rs.getTimestamp(8));

			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Course by Course Name");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return bean;
	}

	public List<CourseBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<CourseBean> search(CourseBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_course where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}
			if (bean.getDuration() != null && bean.getDuration().length() > 0) {
				sql.append(" and duration like '" + bean.getDuration() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" and description like '" + bean.getDescription() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		ArrayList<CourseBean> list = new ArrayList<CourseBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedby(rs.getString(5));
				bean.setModifiedby(rs.getString(6));
				bean.setCreateddatetime(rs.getTimestamp(7));
				bean.setModifieddatetime(rs.getTimestamp(8));
				list.add(bean);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search Course");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return list;
	}

}

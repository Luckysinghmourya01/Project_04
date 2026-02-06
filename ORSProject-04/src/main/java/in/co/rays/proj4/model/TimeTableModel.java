package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.rays.proj4.bean.TimetableBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

public class TimeTableModel {

	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstm = conn.prepareStatement("select max(id) from st_timetable");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
		} catch (Exception e) {
			throw new DatabaseException("exception : exception is getting find by pk timetable ");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return pk + 1;
	}

	public long add(TimetableBean bean) throws ApplicationException {
		Connection conn = null;
		int pk = 0;
		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstm = conn
					.prepareStatement("insert into st_timetable values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstm.setLong(1, bean.getId());
			pstm.setString(2, bean.getSemester());
			pstm.setString(3, bean.getDescription());
			pstm.setDate(4, new java.sql.Date(bean.getExamDate().getTime()));
			pstm.setString(5, bean.getExamTime());
			pstm.setLong(6, bean.getCourseId());
			pstm.setString(7, bean.getCourseName());
			pstm.setLong(8, bean.getSubjectId());
			pstm.setString(9, bean.getSubjectName());
			pstm.setString(10, bean.getCreatedby());
			pstm.setString(11, bean.getModifiedby());
			pstm.setTimestamp(12, bean.getCreateddatetime());
			pstm.setTimestamp(13, bean.getModifieddatetime());
			pstm.executeUpdate();
			conn.commit();
			pstm.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException(
						"exception : exception is getting add rollback timetable" + ex.getMessage());
			}
			throw new ApplicationException("exception : exception is getting a add timetable");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return pk;

	}

	public void update(TimetableBean bean) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstm = conn.prepareStatement(
					"update st_timetable set semester = ?, description = ?, exam_date = ?, exam_time = ?, course_id = ?, course_name = ?, subject_id = ?, subject_name = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
			pstm.setString(1, bean.getSemester());
			pstm.setString(2, bean.getDescription());
			pstm.setDate(3, new java.sql.Date(bean.getExamDate().getTime()));
			pstm.setString(4, bean.getExamTime());
			pstm.setLong(5, bean.getCourseId());
			pstm.setString(6, bean.getCourseName());
			pstm.setLong(7, bean.getSubjectId());
			pstm.setString(8, bean.getSubjectName());
			pstm.setString(9, bean.getCreatedby());
			pstm.setString(10, bean.getModifiedby());
			pstm.setTimestamp(11, bean.getCreateddatetime());
			pstm.setTimestamp(12, bean.getModifieddatetime());
			pstm.setLong(13, bean.getId());
			conn.commit();
			pstm.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("exception : exception  upadate rollback " + ex.getMessage());
			}
			throw new ApplicationException("exception : exception is getting update timetable");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
	}

	public void delete(TimetableBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_TIMETABLE WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Timetable");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
	}

	public TimetableBean checkByCourseName(long CourseId, Date examDate) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_timetable where corse_id=? and exam_date=?");
		Connection conn = null;
		TimetableBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, CourseId);
			pstm.setDate(2, new java.sql.Date(examDate.getTime()));
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setSemester(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setExamDate(rs.getDate(4));
				bean.setExamTime(rs.getString(5));
				bean.setCourseId(rs.getLong(6));
				bean.setCourseName(rs.getString(7));
				bean.setSubjectId(rs.getLong(8));
				bean.setSubjectName(rs.getString(9));
				bean.setCreatedby(rs.getString(10));
				bean.setModifiedby(rs.getString(11));
				bean.setCreateddatetime(rs.getTimestamp(12));
				bean.setModifieddatetime(rs.getTimestamp(13));

			}
			pstm.close();
		} catch (Exception e) {
			throw new ApplicationException("exception : exception is getting course name timetable");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return bean;

	}

	public TimetableBean checkBySubjectName(long courseId, long subjectId, Date examDate) throws ApplicationException {

		Connection conn = null;
		TimetableBean bean = null;
		StringBuffer sql = new StringBuffer(
				"select * from st_timetable where course_id=? and subject_name=? and exam_date=?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, courseId);
			pstm.setLong(2, subjectId);
			pstm.setDate(3, new java.sql.Date(examDate.getTime()));
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setSemester(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setExamDate(rs.getDate(4));
				bean.setExamTime(rs.getString(5));
				bean.setCourseId(rs.getLong(6));
				bean.setCourseName(rs.getString(7));
				bean.setSubjectId(rs.getLong(8));
				bean.setSubjectName(rs.getString(9));
				bean.setCreatedby(rs.getString(10));
				bean.setModifiedby(rs.getString(11));
				bean.setCreateddatetime(rs.getTimestamp(12));
				bean.setModifieddatetime(rs.getTimestamp(13));

			}
			pstm.close();
		} catch (Exception e) {
			throw new ApplicationException("exception : exception is getting Timetable check subjectName");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return bean;

	}

	public TimetableBean checkBySemester(long courseId, long subjectId, String semester, Date examDate)
			throws ApplicationException {
		Connection conn = null;
		StringBuffer sql = new StringBuffer(
				"select * from st_timetable where course_id=? and subject_id=? and semester=? and exam_date=?");
		TimetableBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, courseId);
			pstm.setLong(2, subjectId);
			pstm.setString(3, semester);
			pstm.setDate(4, new java.sql.Date(examDate.getTime()));
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setSemester(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setExamDate(rs.getDate(4));
				bean.setExamTime(rs.getString(5));
				bean.setCourseId(rs.getLong(6));
				bean.setCourseName(rs.getString(7));
				bean.setSubjectId(rs.getLong(8));
				bean.setSubjectName(rs.getString(9));
				bean.setCreatedby(rs.getString(10));
				bean.setModifiedby(rs.getString(11));
				bean.setCreateddatetime(rs.getTimestamp(12));
				bean.setModifieddatetime(rs.getTimestamp(13));
			}
			pstm.close();
		} catch (Exception e) {
			throw new ApplicationException("exception is getting check semester timetable");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return bean;
	}

	public TimetableBean checkByExamTime(long courseId, long subjectId, String semester, String examTime, Date examDate,
			String description) throws ApplicationException {

		Connection conn = null;
		TimetableBean bean = null;
		StringBuffer sql = new StringBuffer(
				"select * from st_timetable where course_id = ? and subject_id = ? and semester = ? and exam_date = ? and exam_time = ? and description = ?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, courseId);
			pstm.setLong(2, subjectId);
			pstm.setString(3, semester);
			pstm.setString(4, examTime);
			pstm.setDate(5, new java.sql.Date(examDate.getTime()));
			pstm.setString(6, description);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setSemester(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setExamDate(rs.getDate(4));
				bean.setExamTime(rs.getString(5));
				bean.setCourseId(rs.getLong(6));
				bean.setCourseName(rs.getString(7));
				bean.setSubjectId(rs.getLong(8));
				bean.setSubjectName(rs.getString(9));
				bean.setCreatedby(rs.getString(10));
				bean.setModifiedby(rs.getString(11));
				bean.setCreateddatetime(rs.getTimestamp(12));
				bean.setModifieddatetime(rs.getTimestamp(13));
			}
			pstm.close();
		} catch (Exception e) {
			throw new ApplicationException("exception : exception is getting semester timetable");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return bean;
	}

	public List<TimetableBean> search(TimetableBean bean, int pageNo, int pageSize)
			throws SQLException, ApplicationException {
		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from st_timetable where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append("and id = " + bean.getId());
			}
			if (bean.getCourseId() > 0) {
				sql.append(" and course_id = " + bean.getCourseId());
			}
			if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
				sql.append(" and course_name like '" + bean.getCourseName() + "%'");
			}
			if (bean.getSubjectId() > 0) {
				sql.append(" and subject_id = " + bean.getSubjectId());
			}
			if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
				sql.append(" and subject_name like '" + bean.getSubjectName() + "%'");
			}
			if (bean.getSemester() != null && bean.getSemester().length() > 0) {
				sql.append(" and semester like '" + bean.getSemester() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" and description like '" + bean.getDescription() + "%'");
			}
			if (bean.getExamDate() != null && bean.getExamDate().getDate() > 0) {
				sql.append(" and exam_date like '" + new java.sql.Date(bean.getExamDate().getTime()) + "%'");
			}
			if (bean.getExamTime() != null && bean.getExamTime().length() > 0) {
				sql.append(" and exam_time like '" + bean.getExamTime() + "%'");
			}
		}

		ArrayList<TimetableBean> list = new ArrayList<TimetableBean>();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql.toString());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setSemester(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setExamDate(rs.getDate(4));
				bean.setExamTime(rs.getString(5));
				bean.setCourseId(rs.getLong(6));
				bean.setCourseName(rs.getString(7));
				bean.setSubjectId(rs.getLong(8));
				bean.setSubjectName(rs.getString(9));
				bean.setCreatedby(rs.getString(10));
				bean.setModifiedby(rs.getString(11));
				bean.setCreateddatetime(rs.getTimestamp(12));
				bean.setModifieddatetime(rs.getTimestamp(13));
				list.add(bean);

			}
			pstm.close();
		} catch (Exception e) {
			throw new ApplicationException("exception is getting search tiemtable");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return list;
	}

	public TimetableBean findByPk(long pk) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_timetable where id = ?");
		TimetableBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setSemester(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setExamDate(rs.getDate(4));
				bean.setExamTime(rs.getString(5));
				bean.setCourseId(rs.getLong(6));
				bean.setCourseName(rs.getString(7));
				bean.setSubjectId(rs.getLong(8));
				bean.setSubjectName(rs.getString(9));
				bean.setCreatedby(rs.getString(10));
				bean.setModifiedby(rs.getString(11));
				bean.setCreateddatetime(rs.getTimestamp(12));
				bean.setModifieddatetime(rs.getTimestamp(13));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Timetable by pk");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return bean;
	}
}
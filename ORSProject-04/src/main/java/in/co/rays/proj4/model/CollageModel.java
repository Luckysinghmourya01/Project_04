package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CollageBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DublicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * @author lucky singh mourya
 *
 */
public class CollageModel {

	/**
	 * next pk method return pk + 1
	 * 
	 * @return
	 * @throws DatabaseException
	 */
	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_college");
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

	/**
	 * add method contaion college data
	 * 
	 * @param bean
	 * @return
	 * @throws ApplicationException
	 * @throws DublicateRecordException
	 */
	public long add(CollageBean bean) throws ApplicationException, DublicateRecordException {
		Connection conn = null;
		int pk = 0;

		CollageBean duplicateCollegeName = findByName(bean.getName());

		if (duplicateCollegeName != null) {
			throw new DublicateRecordException("College Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_college values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getAddress());
			pstmt.setString(4, bean.getState());
			pstmt.setString(5, bean.getCity());
			pstmt.setString(6, bean.getPhoneNo());
			pstmt.setString(7, bean.getCreatedby());
			pstmt.setString(8, bean.getModifiedby());
			pstmt.setTimestamp(9, bean.getCreateddatetime());
			pstmt.setTimestamp(10, bean.getModifieddatetime());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add College");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return pk;
	}

	/**
	 * Updates an existing college.
	 * 
	 * @param bean collage bean
	 * @throws ApplicationException     on DB errors
	 * @throws DublicateRecordException if name already exists for another ID
	 */
	public void update(CollageBean bean) throws ApplicationException, DublicateRecordException {

		Connection conn = null;

		CollageBean beanExist = findByName(bean.getName());

		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DublicateRecordException("College is already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_college set name = ?, address = ?, state = ?, city = ?, phone_no = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getAddress());
			pstmt.setString(3, bean.getState());
			pstmt.setString(4, bean.getCity());
			pstmt.setString(5, bean.getPhoneNo());
			pstmt.setString(6, bean.getCreatedby());
			pstmt.setString(7, bean.getModifiedby());
			pstmt.setTimestamp(8, bean.getCreateddatetime());
			pstmt.setTimestamp(9, bean.getModifieddatetime());
			pstmt.setLong(10, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating College ");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
	}

	/**
	 * delete a college data
	 * 
	 * @param bean CollegeBean containing ID
	 * @throws ApplicationException
	 */
	public void delete(CollageBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_college where id = ?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete college");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
	}

	public CollageBean findByPk(long pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_college where id = ?");

		CollageBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CollageBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedby(rs.getString(7));
				bean.setModifiedby(rs.getString(8));
				bean.setCreateddatetime(rs.getTimestamp(9));
				bean.setModifieddatetime(rs.getTimestamp(10));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting College by pk");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return bean;
	}

	public CollageBean findByName(String name) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_college where name = ?");

		CollageBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CollageBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedby(rs.getString(7));
				bean.setModifiedby(rs.getString(8));
				bean.setCreateddatetime(rs.getTimestamp(9));
				bean.setModifieddatetime(rs.getTimestamp(10));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting College by Name");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return bean;
	}

	public List<CollageBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<CollageBean> search(CollageBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_college where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}

			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}

			if (bean.getAddress() != null && bean.getAddress().length() > 0) {
				sql.append(" and address like '" + bean.getAddress() + "%'");
			}

			if (bean.getState() != null && bean.getState().length() > 0) {
				sql.append(" and state like '" + bean.getState() + "%'");
			}

			if (bean.getCity() != null && bean.getCity().length() > 0) {
				sql.append(" and city like '" + bean.getCity() + "%'");
			}

			if (bean.getPhoneNo() != null && bean.getPhoneNo().length() > 0) {
				sql.append(" and phone_no like '" + bean.getPhoneNo() + "%'");
			}

			System.out.println(sql.toString());
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		System.out.println(sql.toString());

		ArrayList<CollageBean> list = new ArrayList<CollageBean>();
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				CollageBean dto = new CollageBean();

				dto.setId(rs.getLong(1));
				dto.setName(rs.getString(2));
				dto.setAddress(rs.getString(3));
				dto.setState(rs.getString(4));
				dto.setCity(rs.getString(5));
				dto.setPhoneNo(rs.getString(6));
				dto.setCreatedby(rs.getString(7));
				dto.setModifiedby(rs.getString(8));
				dto.setCreateddatetime(rs.getTimestamp(9));
				dto.setModifieddatetime(rs.getTimestamp(10));

				list.add(dto);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search college");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return list;
	}
}

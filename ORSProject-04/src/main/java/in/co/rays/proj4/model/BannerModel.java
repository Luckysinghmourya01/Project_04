package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.bean.bannerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DublicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * @author lucky singh mourya
 *
 */
public class BannerModel {

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
			PreparedStatement pstm = conn.prepareStatement("select max(id) from st_banner");
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

	public long add(bannerBean bean) throws ApplicationException, DublicateRecordException {

		Connection conn = null;
		int pk = 0;

		bannerBean existCode = findByBannerCode(bean.getBannerCode());
		if (existCode != null) {
			throw new DublicateRecordException("Code already exist");
		}

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_banner values (?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getBannerCode());
			pstmt.setString(3, bean.getBannerTitle());
			pstmt.setString(4, bean.getImagePath());
			pstmt.setString(5, bean.getBannerStatus());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Add rollback exception");
			}
			throw new ApplicationException("Exception in adding Banner");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return pk;
	}

	public void update(bannerBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_banner set banner_code=?, banner_title=?, image_path=?, banner_status=? where id=?");

			pstmt.setString(1, bean.getBannerCode());
			pstmt.setString(2, bean.getBannerTitle());
			pstmt.setString(3, bean.getImagePath());
			pstmt.setString(4, bean.getBannerStatus());
			pstmt.setLong(5, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception");
			}
			throw new ApplicationException("Exception in updating Banner");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
	}

	public void delete(bannerBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_banner where id=?");

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
			throw new ApplicationException("Exception in deleting Banner");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
	}

	public bannerBean findByPk(long pk) throws ApplicationException {

		bannerBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_banner where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new bannerBean();
				bean.setId(rs.getLong("id"));
				bean.setBannerCode(rs.getString("banner_code"));
				bean.setBannerTitle(rs.getString("banner_title"));
				bean.setImagePath(rs.getString("image_path"));
				bean.setBannerStatus(rs.getString("banner_status"));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Banner by PK");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return bean;
	}

	public List<bannerBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<bannerBean> search(bannerBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		ArrayList<bannerBean> list = new ArrayList<>();

		StringBuffer sql = new StringBuffer("select * from st_banner where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}

			if (bean.getBannerCode() != null && bean.getBannerCode().length() > 0) {
				sql.append(" and banner_code like '" + bean.getBannerCode() + "%'");
			}

			if (bean.getBannerTitle() != null && bean.getBannerTitle().length() > 0) {
				sql.append(" and banner_title like '" + bean.getBannerTitle() + "%'");
			}

			if (bean.getImagePath() != null && bean.getImagePath().length() > 0) {
				sql.append(" and image_path like '" + bean.getImagePath() + "%'");
			}

			if (bean.getBannerStatus() != null && bean.getBannerStatus().length() > 0) {
				sql.append(" and banner_status like '" + bean.getBannerStatus() + "%'");
			}
		}

		// Pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bannerBean b = new bannerBean();
				b.setId(rs.getLong("id"));
				b.setBannerCode(rs.getString("banner_code"));
				b.setBannerTitle(rs.getString("banner_title"));
				b.setImagePath(rs.getString("image_path"));
				b.setBannerStatus(rs.getString("banner_status"));
				list.add(b);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching Banner");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return list;
	}

	public bannerBean findByBannerCode(String bannerCode) throws ApplicationException {

		bannerBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_banner where banner_code = ?");

			pstmt.setString(1, bannerCode);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new bannerBean();
				bean.setId(rs.getLong("id"));
				bean.setBannerCode(rs.getString("banner_code"));
				bean.setBannerTitle(rs.getString("banner_title"));
				bean.setImagePath(rs.getString("image_path"));
				bean.setBannerStatus(rs.getString("banner_status"));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Banner by Code");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return bean;
	}

}

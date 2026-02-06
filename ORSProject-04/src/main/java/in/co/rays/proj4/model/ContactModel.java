package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.protocol.Resultset;

import in.co.rays.proj4.bean.ContactBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

public class ContactModel {

	public Integer nextPk() throws SQLException, ApplicationException, DatabaseException {
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstm = conn.prepareStatement("select max(id) from contact");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
		} catch (Exception e) {

			throw new DatabaseException("exception is getting next pk");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}

		return pk + 1;
	}

	public long add(ContactBean bean) throws ApplicationException, SQLException, DatabaseException {

		Connection conn = null;
		int pk = nextPk();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstm = conn.prepareStatement("insert into contact values(?,?,?,?,?)");
			pstm.setLong(1, pk);
			pstm.setString(2, bean.getName());
			pstm.setString(3, bean.getEmail());
			pstm.setString(4, bean.getMobileNo());
			pstm.setString(5, bean.getMessage());

			pstm.executeUpdate();
		} catch (Exception e) {
			throw new ApplicationException("exception is getting add contact");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return pk;
	}
}

package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import in.co.rays.proj4.bean.PTicketbean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

public class PTicektModel {

	public static Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstm = conn.prepareStatement("select max(id) from st__ticket");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {

				pk = rs.getInt(1);

			}
			conn.close();
		} catch (Exception e) {
			throw new DatabaseException("exception is getting next pk ticket");
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return pk + 1;
	}

	public static void add(PTicketbean bean) throws DatabaseException, ApplicationException {

		Connection conn = null;

		int pk = nextPk();

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstm = conn.prepareStatement("insert into st__ticket values(?,?,?,?,?) ");
			pstm.setInt(1, pk);
			pstm.setLong(2, bean.getTicketId());
			pstm.setString(3, bean.getPassengerName());
			pstm.setInt(4, bean.getFare());
			pstm.setString(5, bean.getSeatNumber());
			pstm.executeUpdate();
			conn.commit();
			pstm.close();

		} catch (Exception e) {
			try {

				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("exception is getting ticket add rollback" + ex.getMessage());

			}
			throw new ApplicationException("exception is getting ticket add");

		} finally {
			JDBCDataSource.closeconnection(conn);
		}

	}
}

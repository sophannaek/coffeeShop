package db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.ConnectionManager;
import db.beans.Admin;

public class AdminManager{
	
	//open the connection 
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	
	public static void displayAllRows() throws SQLException {

		String sql = "SELECT adminId, username, password FROM admin";
		try (
			//	Connection conn = DBUtil.getConnection(DBType.MYSQL);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				){

			System.out.println("Admin Table:");
			System.out.println("----------------------------");
			System.out.println("AdminId   Username   Password");
			System.out.println("----------------------------");
			while (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getInt("adminId") + ":        ");
				bf.append(rs.getString("username") +",     ");
				bf.append(rs.getString("password"));
				System.out.println(bf.toString());
			}
		}
	}
	
	
	public static Admin getRow(int adminId) throws SQLException {

		String sql = "SELECT * FROM admin WHERE adminId = ?";
		ResultSet rs = null;

		try (
			//	Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			stmt.setInt(1, adminId);
			rs = stmt.executeQuery();

			if (rs.next()) {
				Admin bean = new Admin();
				bean.setAdminId(adminId);
				bean.setUserName(rs.getString("username"));
				bean.setPassword(rs.getString("password"));
				System.out.println("The Admin username is " +bean.getUserName());
				
				return bean;
			} else {
				return null;
			}

		} catch (SQLException e) {
			System.err.println(e);
			return null;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

	}
	
	
	public static boolean insert(Admin bean) throws Exception {
		//need to check if the username is already existed in the database 

		String sql = "INSERT into admin (username, password) " +
				"VALUES (?, ?)";
		ResultSet keys = null; 
		try (
			//	Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				) {
			stmt.setString(1,bean.getUserName());
			stmt.setString(2, bean.getPassword());
			int affected = stmt.executeUpdate();
			if(affected ==1) {//insert successfully 
				keys = stmt.getGeneratedKeys();
				keys.next(); 
				int newKey = keys.getInt(1);//get the adminId
				bean.setAdminId(newKey);
				
			}else {
				System.err.println("Now rows affected");
				return false;
			}
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		} finally{
			if(keys != null) keys.close();
		}
		return true;
	}

	public static boolean update(Admin bean) throws Exception {

		String sql =
				"UPDATE admin SET " +
				"username = ?, password = ? " +
				"WHERE adminId = ?";
		try (
				//Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			stmt.setString(1, bean.getUserName());
			stmt.setString(2, bean.getPassword());
			stmt.setInt(3, bean.getAdminId());
			int affected = stmt.executeUpdate();
			
			if(affected == 1) {
				return true; 
			}else 
				return false;
		}
		catch(SQLException e) {
			System.err.println(e);
			return false;
		}

	}
	
	public static boolean delete(int adminId) throws Exception {

		String sql = "DELETE FROM admin WHERE adminId = ?";
		try (
			//	Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			
			stmt.setInt(1, adminId);
			int affected = stmt.executeUpdate();
			if(affected == 1) {
				return true; 
			}else {
				return false;
			}
			
		}
		catch(SQLException e) {
			System.err.println(e);
			return false;
		}

	}
	
	
}
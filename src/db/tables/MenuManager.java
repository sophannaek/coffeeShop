package db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.ConnectionManager;
import db.beans.Menu;

public class MenuManager{
	
	//open the connection 
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	
	public static void displayAllRows() throws SQLException {

		String sql = "SELECT *  FROM drink";
		String sql1 = "SELECT *  FROM pastry";
		try (
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				){

			System.out.println("Drink Menu:");
			System.out.println("----------------------------");
			System.out.println("ItemId   ItemName   ItemPrice");
			System.out.println("----------------------------");
			while (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getInt("drinkId") + ":        ");
				bf.append(rs.getString("drinkName") +",     ");
				bf.append(rs.getString("drinkPrice"));
				System.out.println(bf.toString());
			}
			
			ResultSet rs1 = stmt.executeQuery(sql1);
			
			System.out.println("Pastry Menu:");
			System.out.println("----------------------------");
			System.out.println("ItemId   ItemName   ItemPrice");
			System.out.println("----------------------------");
			while (rs1.next()) {
				StringBuffer bf1 = new StringBuffer();
				bf1.append(rs1.getInt("pastryId") + ":        ");
				bf1.append(rs1.getString("pastryName") +",     ");
				bf1.append(rs1.getDouble("pastryPrice"));
				System.out.println(bf1.toString());
			}
		}
	}
	
	
	public static Menu getRow(int itemId, int menuType) throws SQLException {
 
		String sql = null; 
		String menuName = null; 
		String menuPrice = null; 
		if(menuType == 1 ) {//drink menu
			sql = "SELECT * FROM drink WHERE drinkId = ?";
			menuName = "drinkName";
			menuPrice ="drinkPrice";
			
		}else {
			sql = "SELECT * FROM pastry WHERE pastryId = ?";
			menuName = "pastryName";
			menuPrice = "pastryPrice";
		}

		ResultSet rs = null;

		try (
		
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			stmt.setInt(1, itemId);
			rs = stmt.executeQuery();

			if (rs.next()) { 
				Menu bean = new Menu();
				bean.setMenuId(itemId);
				bean.setMenuName(rs.getString(menuName));
				bean.setMenuPrice(rs.getDouble(menuPrice));
				System.out.println("The menu item is " +bean.getMenuName());
				
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
	
	
	public static boolean insert(Menu bean, int menuType) throws Exception {
		//need to check if the username is already existed in the database 
		String sql = null; 
		if(menuType == 1) {
			sql = "INSERT into drink (drinkName, drinkPrice) " +
					"VALUES (?, ?)";
		}else {
			sql = "INSERT into pastry (pastryName, pastryPrice) " +
					"VALUES (?, ?)";
		}
		
		ResultSet keys = null; 
		try (

				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				) {
			stmt.setString(1,bean.getMenuName());
			stmt.setDouble(2, bean.getMenuPrice());
			int affected = stmt.executeUpdate();
			if(affected ==1) {//insert successfully 
				keys = stmt.getGeneratedKeys();
				keys.next(); 
				int newKey = keys.getInt(1);//get the adminId
				bean.setMenuId(newKey);
				
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

	public static boolean update(Menu bean, int menuType) throws Exception {
		String sql = null; 
		if(menuType == 1) {
			sql =
					"UPDATE drink SET " +
					"drinkName = ?, drinkPrice = ? " +
					"WHERE drinkId = ?";
		}else {
			sql =
					"UPDATE pastry SET " +
					"pastryName = ?, pastryPrice = ? " +
					"WHERE pastryId = ?";
		}
		
		try (
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			stmt.setString(1, bean.getMenuName());
			stmt.setDouble(2, bean.getMenuPrice());
			stmt.setInt(3, bean.getMenuId());
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
	
	public static boolean delete(int itemId, int menuType) throws Exception {
		String sql = null; 
		if(menuType == 1) {
			sql = "DELETE FROM drink WHERE drinkId = ?";
		}else {
			sql = "DELETE FROM pastry WHERE pastryId = ?";
		}

		
		try (
			
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			
			stmt.setInt(1, itemId);
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
package db;
import db.beans.Admin;
import db.beans.Menu;
import db.tables.AdminManager;
import db.tables.MenuManager;
import util.InputHelper;

public class Main {

	public static void main(String[] args) throws Exception {
		System.out.println("Starting application");
		//create a DB connection instance
		ConnectionManager.getInstance().setDBType(DBType.MYSQL);
		
		printMainPanel();

//		
//		//insert to the database 
//		Admin bean = new Admin();
//		bean.setUserName(InputHelper.getInput("Enter Username: "));
//		bean.setPassword(InputHelper.getInput("Enter password: "));
//		
//		boolean result = AdminManager.insert(bean);
//		if(result) {
//			System.out.println("New row with primary key "+ bean.getAdminId() + " was inserted!");
//		}
//		
//		
//		//update the table in the database
//		int adminId = InputHelper.getIntegerInput("Selet a row to update: ");
//		Admin bean_update = AdminManager.getRow(adminId);
//		if(bean_update == null) {
//			System.err.println("Row not found");
//			return;
//		}
//		
//		String password = InputHelper.getInput("Enter new password: ");
//		bean_update.setPassword(password);
//		if(AdminManager.update(bean_update)) {
//			System.out.println("Successfully Updated");
//		}else {
//			System.err.println("Unccessfully Updated");
//		}
//		
//		//delete the row in the table 
//		int adminId1 = InputHelper.getIntegerInput("Selet a row to delete: ");
//		if(AdminManager.delete(adminId1)) {
//			System.out.println("Successfully deleted");
//		}else {
//			System.err.println("Unsuccessfully deleted");
//		}
//		
		//close the DB connection
		ConnectionManager.getInstance().close();
	}
	
	private static void printMainPanel() throws Exception {
		
		System.out.println("Control Panel: ");
		System.out.println("----------------------------");
		System.out.println("1. Admin Panel");
		System.out.println("2. Menu Item Panel");
		System.out.println("3. Exit ");
		int option = InputHelper.getIntegerInput("Select the option: ");
		
		switch(option) {
			case 1: 
				adminPanel();
				break;
			case 2: 
				
				menuPanel();
				break;
			case 3: 
				Exit();
		}
	}
	
	private static void adminPanel() throws Exception {
		System.out.println("Admin Panel: ");
		System.out.println("----------------------------");
		System.out.println("1. View the list of all users");
		System.out.println("2. Add Users");
		System.out.println("3. Edit Users");
		System.out.println("4. Delete Users");
		System.out.println("5. Back to the control panel");
		System.out.println("6. Exit the Application");
		System.out.println("----------------------------");
		int option1 = InputHelper.getIntegerInput("Choose the option: ");
		
		switch(option1) {
			case 1: 
				AdminManager.displayAllRows();
				System.out.println("----------------------------");
				printMainPanel();
				break;
			case 2:
				//insert to the database 
				Admin bean = new Admin();
				bean.setUserName(InputHelper.getInput("Enter Username: "));
				bean.setPassword(InputHelper.getInput("Enter password: "));
				
				boolean result = AdminManager.insert(bean);
				if(result) {
					System.out.println("New row with primary key "+ bean.getAdminId() + " was inserted!");
				}
				System.out.println("----------------------------");
				adminPanel();
				break;
			case 3:
				//update the table in the database
				AdminManager.displayAllRows();
				System.out.println("----------------------------");
				int adminId = InputHelper.getIntegerInput("Selet a row to update: ");
				Admin bean_update = AdminManager.getRow(adminId);
				if(bean_update == null) {
					System.err.println("Row not found");
					return;
				}
				String password = InputHelper.getInput("Enter new password: ");
				bean_update.setPassword(password);
				if(AdminManager.update(bean_update)) {
					System.out.println("Successfully Updated");
				}else {
					System.err.println("Unccessfully Updated");
				}
				System.out.println("----------------------------");
				adminPanel();
				
				break;
			case 4: 
				AdminManager.displayAllRows();
				//delete the row in the table 
				int adminId1 = InputHelper.getIntegerInput("Selet a row to delete: ");
				if(AdminManager.delete(adminId1)) {
					System.out.println("Successfully deleted");
				}else {
					System.err.println("Unsuccessfully deleted");
				}
				System.out.println("----------------------------");
				adminPanel();
				break;
			case 5: 
				printMainPanel();
				break;
			case 6: 
				Exit();
				
		}
	}
	
	
	

	
	private static void menuPanel() throws Exception {
		System.out.println("1. View the list of all menu");
		System.out.println("2. Add new menu item");
		System.out.println("3. Edit menu item");
		System.out.println("4. Delete menu item");
		System.out.println("5. Back to the control panel");
		System.out.println("6. Exit the Application");
		
		int option = InputHelper.getIntegerInput("Choose the option: ");
		switch(option) {
			case 1: 
				MenuManager.displayAllRows();
				System.out.println("----------------------------");
				printMainPanel();
				break;
				
			case 2: 
				//insert to the database 
				Menu bean = new Menu();
				bean.setMenuName(InputHelper.getInput("Enter Menu Name: "));
				bean.setMenuPrice(InputHelper.getDoubleInput("Enter the price: "));
				int menuType= InputHelper.getIntegerInput("Enter 1 for drink ; 2 for pastry item");
				
				boolean result = MenuManager.insert(bean, menuType);
				if(result) {
					System.out.println("New item "+ bean.getMenuName() + " was inserted!");
				}
				System.out.println("----------------------------");
				menuPanel();
				break;
				
			case 3: 
				//update the table in the database
				MenuManager.displayAllRows();
				int menuId = InputHelper.getIntegerInput("Selet a row to update: ");
				int menuType1= InputHelper.getIntegerInput("Enter 1 for drink ; 2 for pastry item");
				Menu bean_update = MenuManager.getRow(menuId, menuType1);
				if(bean_update == null) {
					System.err.println("Row not found");
					return;
				}
				Double price = InputHelper.getDoubleInput("Enter new price: ");
				bean_update.setMenuPrice(price);
				if(MenuManager.update(bean_update, menuType1)) {
					System.out.println("Successfully Updated");
				}else {
					System.err.println("Unccessfully Updated");
				}
				System.out.println("----------------------------");
				
				menuPanel();
				break;
				
			case 4: 
				MenuManager.displayAllRows();
				//delete the row in the table 
				int menuId1 = InputHelper.getIntegerInput("Selet a row to delete: ");
				if(AdminManager.delete(menuId1)) {
					System.out.println("Menu is successfully deleted");
				}else {
					System.err.println("Mneu is unsuccessfully deleted");
				}
				System.out.println("----------------------------");
				menuPanel();
				break;
			case 5: 
				printMainPanel();
				break;
			case 6: 
				Exit();
		}
	}
	
	private static void Exit() {
		System.out.println("Exiting the application...");
		System.exit(0);
	}
	
	
}

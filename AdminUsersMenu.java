
public class AdminUsersMenu {
            	
	private static AdminUsersMenu instance = new AdminUsersMenu();

	private AdminUsersMenu() {
	}

	public static AdminUsersMenu getInstance() {
		return instance;
	}

	public void printTheAdminUsersMenu() {
		System.out.println("***********************************");
		System.out.println("Users Menu:");
		System.out.println("1-User Information.");
		System.out.println("2-Edit User.");
		System.out.println("3-Make User.");
		System.out.println("4-Remove User.");
        System.out.println("5-Back.");
		System.out.println("***********************************");
		System.out.print("\r\nPlease select your choice: ");
	}

	public Admin.UsersOptions getAdminUsersMenuOption() {
		Admin.UsersOptions[] usersOptions = Admin.UsersOptions.values();
		int userInput = ScannerWrapper.getInstance().nextInt();
		userInput--;
		if (userInput >= 0 && userInput < usersOptions.length) {
			return usersOptions[userInput];
		}
		return Admin.UsersOptions.UNDEFINED;
	}
    
}

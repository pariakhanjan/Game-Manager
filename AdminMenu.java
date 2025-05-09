
public class AdminMenu {
        	
	private static AdminMenu instance = new AdminMenu();

	private AdminMenu() {
	}

	public static AdminMenu getInstance() {
		return instance;
	}

	public void printTheAdminMenu() {
		System.out.println("***********************************");
		System.out.println("Admin Menu:");
		System.out.println("1-Users.");
		System.out.println("2-Games.");
        System.out.println("3-Back.");
		System.out.println("***********************************");
		System.out.print("\r\nPlease select your choice: ");
	}

	public GameStoreProgram.AdminOption getAdminMenuOption() {
		GameStoreProgram.AdminOption[] adminMenuOptions = GameStoreProgram.AdminOption.values();
		int userInput = ScannerWrapper.getInstance().nextInt();
		userInput--;
		if (userInput >= 0 && userInput < adminMenuOptions.length) {
			return adminMenuOptions[userInput];
		}
		return GameStoreProgram.AdminOption.UNDEFINED;
	}
    
}

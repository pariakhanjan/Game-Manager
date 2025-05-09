public class UserMenu {
    	
	private static UserMenu instance = new UserMenu();

	private UserMenu() {
	}

	public static UserMenu getInstance() {
		return instance;
	}

	public void printTheUserMenu() {
		System.out.println("***********************************");
		System.out.println("User Menu:");
		System.out.println("1-Profile.");
		System.out.println("2-Store.");
		System.out.println("3-Library.");
        System.out.println("4-Friends.");
        System.out.println("5-Back.");
		System.out.println("***********************************");
		System.out.print("\r\nPlease select your choice: ");
	}

	public GameStoreProgram.UserMenuOption getUserMenuOption() {
		GameStoreProgram.UserMenuOption[] options = GameStoreProgram.UserMenuOption.values();
		int userInput = ScannerWrapper.getInstance().nextInt();
		userInput--;
		if (userInput >= 0 && userInput < options.length) {
			return options[userInput];
		}
		return GameStoreProgram.UserMenuOption.UNDEFINED;
	}
	
}

public class StoreMenu {
            	
	private static StoreMenu instance = new StoreMenu();

	private StoreMenu() {
	}

	public static StoreMenu getInstance() {
		return instance;
	}

	public void printTheStoreMenu() {
		System.out.println("***********************************");
		System.out.println("Store Menu:");
		System.out.println("1-Search.");
		System.out.println("2-Show All.");
        System.out.println("3-Back.");
		System.out.println("***********************************");
		System.out.print("\r\nPlease select your choice: ");
	}

	public User.StoreOption getStoreMenuOption() {
		User.StoreOption[] options = User.StoreOption.values();
		int userInput = ScannerWrapper.getInstance().nextInt();
		userInput--;
		if (userInput >= 0 && userInput < options.length) {
			return options[userInput];
		}
		return User.StoreOption.UNDEFINED;
	}
	
}

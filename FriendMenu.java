public class FriendMenu {
    
    private static FriendMenu instance = new FriendMenu();

	private FriendMenu() {
	}

	public static FriendMenu getInstance() {
		return instance;
	}

	public void printTheFriendMenu() {
		System.out.println("***********************************");
		System.out.println("Your Game Options:");
		System.out.println("1-Show.");
		System.out.println("2-Search.");
        System.out.println("3-New Friend.");
        System.out.println("4-Requests.");
        System.out.println("5-Back.");
		System.out.println("***********************************");
		System.out.print("\r\nPlease select your choice: ");
	}

	public GameStoreProgram.FriendOption getFriendMenuOption() {
		GameStoreProgram.FriendOption[] options = GameStoreProgram.FriendOption.values();
		int userInput = ScannerWrapper.getInstance().nextInt();
		userInput--;
		if (userInput >= 0 && userInput < options.length) {
			return options[userInput];
		}
		return GameStoreProgram.FriendOption.UNDEFINED;
	}
	    
}

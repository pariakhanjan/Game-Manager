public class Menu {
	
	private static Menu instance = new Menu();

	private Menu() {
	}

	public static Menu getInstance() {
		return instance;
	}

	public void printTheMenu() {
		System.out.println("***********************************");
		System.out.println("GameStore options:");
		System.out.println("1-Admin.");
		System.out.println("2-User.");
		System.out.println("3-Exit.");
		System.out.println("***********************************");
		System.out.print("\r\nPlease select your choice: ");
	}

	public GameStoreProgram.Option getOption() {
		GameStoreProgram.Option[] options = GameStoreProgram.Option.values();
		int userInput = ScannerWrapper.getInstance().nextInt();
		userInput--;
		if (userInput >= 0 && userInput < options.length) {
			return options[userInput];
		}
		return GameStoreProgram.Option.UNDEFINED;
	}
	
}
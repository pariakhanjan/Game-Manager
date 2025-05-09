public class GameMenu {
        	
	private static GameMenu instance = new GameMenu();

	private GameMenu() {
	}

	public static GameMenu getInstance() {
		return instance;
	}

	public void printTheGameMenu() {
		System.out.println("***********************************");
		System.out.println("Game Menu:");
		System.out.println("1-Make Game.");
		System.out.println("2-Edit Game Information.");
		System.out.println("3-Remove Game.");
        System.out.println("4-Back.");
		System.out.println("***********************************");
		System.out.print("\r\nPlease select your choice: ");
	}

	public Admin.GamesOptions getGameMenuOption() {
		Admin.GamesOptions[] options = Admin.GamesOptions.values();
		int userInput = ScannerWrapper.getInstance().nextInt();
		userInput--;
		if (userInput >= 0 && userInput < options.length) {
			return options[userInput];
		}
		return Admin.GamesOptions.UNDEFINED;
	}
	
}

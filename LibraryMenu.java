public class LibraryMenu {
    
    private static LibraryMenu instance = new LibraryMenu();

	private LibraryMenu() {
	}

	public static LibraryMenu getInstance() {
		return instance;
	}

	public void printTheLibraryMenu() {
		System.out.println("***********************************");
		System.out.println("Your Game Options:");
		System.out.println("1-Community.");
		System.out.println("2-Rate.");
        System.out.println("3-Back.");
		System.out.println("***********************************");
		System.out.print("\r\nPlease select your choice: ");
	}

	public Game.LibraryOption getLibraryMenuOption() {
		Game.LibraryOption[] options = Game.LibraryOption.values();
		int userInput = ScannerWrapper.getInstance().nextInt();
		userInput--;
		if (userInput >= 0 && userInput < options.length) {
			return options[userInput];
		}
		return Game.LibraryOption.UNDEFINED;
	}
	
}

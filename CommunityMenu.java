public class CommunityMenu {

    private static CommunityMenu instance = new CommunityMenu();

	private CommunityMenu() {
	}

	public static CommunityMenu getInstance() {
		return instance;
	}

	public void printTheCommunityMenu() {
		System.out.println("***********************************");
		System.out.println("Community Menu:");
		System.out.println("1-Show comments.");
		System.out.println("2-Comment.");
        System.out.println("3-Back.");
		System.out.println("***********************************");
		System.out.print("\r\nPlease select your choice: ");
	}

	public Game.CommunityOption getCommunityMenuOption() {
		Game.CommunityOption[] options = Game.CommunityOption.values();
		int userInput = ScannerWrapper.getInstance().nextInt();
		userInput--;
		if (userInput >= 0 && userInput < options.length) {
			return options[userInput];
		}
		return Game.CommunityOption.UNDEFINED;
	}
	
}

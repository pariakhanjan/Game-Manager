public class UserOptions {

    private static UserOptions instance = new UserOptions();

	private UserOptions() {
	}

	public static UserOptions getInstance() {
		return instance;
	}

	public void printTheUserOptions() {
		System.out.println("***********************************");
		System.out.println("users options:");
		System.out.println("1-Sign in.");
		System.out.println("2-Sign up.");
        System.out.println("3-Back.");
		System.out.println("***********************************");
		System.out.print("\r\nPlease select your choice: ");
	}

	public GameStoreProgram.UserOption getUserOptions() {
		GameStoreProgram.UserOption[] userOptions = GameStoreProgram.UserOption.values();
		int userInput = ScannerWrapper.getInstance().nextInt();
		userInput--;
		if (userInput >= 0 && userInput < userOptions.length) {
			return userOptions[userInput];
		}
		return GameStoreProgram.UserOption.UNDEFINED;
	}
	
}

public class ProfileMenu {

    private static ProfileMenu instance = new ProfileMenu();

    private ProfileMenu() {
    }

    public static ProfileMenu getInstance() {
        return instance;
    }

    public void printTheProfilerMenu() {
        System.out.println("***********************************");
        System.out.println("Profile Menu:");
        System.out.println("1-Show information.");
        System.out.println("2-Edit information.");
        System.out.println("3-Back.");
        System.out.println("***********************************");
        System.out.print("\r\nPlease select your choice: ");
    }

    public User.ProfileOption getProfileMenuOption() {
        User.ProfileOption[] options = User.ProfileOption.values();
        int userInput = ScannerWrapper.getInstance().nextInt();
        userInput--;
        if (userInput >= 0 && userInput < options.length) {
            return options[userInput];
        }
        return User.ProfileOption.UNDEFINED;
    }
   
}

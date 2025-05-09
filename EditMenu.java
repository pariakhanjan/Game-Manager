public class EditMenu {
    
    private static EditMenu instance = new EditMenu();

    private EditMenu() {
    }

    public static EditMenu getInstance() {
        return instance;
    }

    public void printTheEditMenu() {
        System.out.println("***********************************");
        System.out.println("Edit Menu:");
        System.out.println("Which one do you want to change?");
        System.out.println("1-UserName.");
        System.out.println("2-PhoneNumber.");
        System.out.println("3-Email.");
        System.out.println("4-Password.");
        System.out.println("5-Charge your wallet.");
        System.out.println("6-Back.");
        System.out.println("***********************************");
        System.out.print("\r\nPlease select your choice: ");
    }

    public User.EditOption getEditMenuOption() {
        User.EditOption[] options = User.EditOption.values();
        int userInput = ScannerWrapper.getInstance().nextInt();
        userInput--;
        if (userInput >= 0 && userInput < options.length) {
            return options[userInput];
        }
        return User.EditOption.UNDEFINED;
    }
    
}

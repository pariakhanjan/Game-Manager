public class GameEditMenu {
        
    private static GameEditMenu instance = new GameEditMenu();

    private GameEditMenu() {
    }

    public static GameEditMenu getInstance() {
        return instance;
    }

    public void printTheEditMenu() {
        System.out.println("***********************************");
        System.out.println("Edit Menu:");
        System.out.println("Which one do you want to change?");
        System.out.println("1-Game Name.");
        System.out.println("2-Description.");
        System.out.println("3-Genre.");
        System.out.println("4-Price.");
        System.out.println("5-Back.");
        System.out.println("***********************************");
        System.out.print("\r\nPlease select your choice: ");
    }

    public Admin.GameEditOption getEditMenuOption() {
        Admin.GameEditOption[] options = Admin.GameEditOption.values();
        int userInput = ScannerWrapper.getInstance().nextInt();
        userInput--;
        if (userInput >= 0 && userInput < options.length) {
            return options[userInput];
        }
        return Admin.GameEditOption.UNDEFINED;
    }
    
}

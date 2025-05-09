import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class User {

    enum ProfileOption {
        SHOW_INFO, EDIT_INFO, BACK, UNDEFINED
    }

    enum EditOption {
        USER_NAME, PHONE_NUMBER, EMAIL, PASSWORD, CHARGE_WALLET, BACK, UNDEFINED
    }

    enum StoreOption {
        SEARCH, SHOW_ALL, BACK, UNDEFINED
    }

    private String userName;

    private String phoneNumber;

    private String email;

    private String password;

    private double walletBalance;

    private ArrayList<User> friends;

    private ArrayList<Game> boughtGames;

    private ArrayList<User> requests;

    public User(String userName, String phoneNumber, String email, String password) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.walletBalance = 0;
        friends = new ArrayList<>();
        boughtGames = new ArrayList<>();
        requests = new ArrayList<>();
    }

    public void handleProfile(GameStoreProgram gameStoreProgram) {
        ProfileMenu.getInstance().printTheProfilerMenu();
        ProfileOption profileOption = ProfileMenu.getInstance().getProfileMenuOption();
        switch (profileOption) {
            case SHOW_INFO:
                showInfo(gameStoreProgram);
                break;
            case EDIT_INFO:
                handleNameAndPhoneNumberEdit(gameStoreProgram);
                break;
            case BACK:
                gameStoreProgram.handleUser(gameStoreProgram, this);
                return;
            default:
                System.out.println("Invalid choice!");
                handleProfile(gameStoreProgram);
                break;
        }
    }

    public void handleStore(GameStoreProgram gameStoreProgram, ArrayList<Game> games) {
        StoreMenu.getInstance().printTheStoreMenu();
        StoreOption storeOption = StoreMenu.getInstance().getStoreMenuOption();
        switch (storeOption) {
            case SEARCH:
                searchGame(gameStoreProgram, games);
                break;
            case SHOW_ALL:
                showGameList(gameStoreProgram, games, games, index(games));
                break;
            case BACK:
                gameStoreProgram.handleUser(gameStoreProgram, this);
                return;
            default:
                System.out.println("Invalid choice!");
                handleStore(gameStoreProgram, games);
                break;
        }
    }

    public void handleLibrary(GameStoreProgram gameStoreProgram) {
        StoreMenu.getInstance().printTheStoreMenu();
        StoreOption storeOption = StoreMenu.getInstance().getStoreMenuOption();
        switch (storeOption) {
            case SEARCH:
                librarySearchGame(gameStoreProgram);
                break;
            case SHOW_ALL:
                libShowGameList(gameStoreProgram, boughtGames, index(boughtGames));
                break;
            case BACK:
                gameStoreProgram.handleUser(gameStoreProgram, this);
                return;
            default:
                System.out.println("Invalid choice!");
                handleLibrary(gameStoreProgram);
                break;
        }
    }

    public ArrayList<Integer> index(ArrayList<Game> games) {
        ArrayList<Integer> index = new ArrayList<>();
        for (int i = 0; i < games.size(); i++) {
            index.add(i);
        }
        return index;
    }

    public void showInfo(GameStoreProgram gameStoreProgram) {
        System.out.println("Profile:");
        System.out.println(toString());
        System.out.println("***********************************");
        handleProfile(gameStoreProgram);
    }

    public void showGameList(GameStoreProgram gameStoreProgram, ArrayList<Game> games, ArrayList<Game> searchedGame,
            ArrayList<Integer> indexs) {
        System.out.println("Game List:");
        for (int i = 0; i < searchedGame.size(); i++) {
            System.out.println((i + 1) + "_ " + searchedGame.get(i).getName());
            System.out.println("***********************************");
        }
        System.out.println("Choose one to see the details: \n !To turn back enter 0!");
        int choosenOne = ScannerWrapper.getInstance().nextInt();
        if (choosenOne == 0) {
            handleStore(gameStoreProgram, games);
            return;
        }
        if (0 > choosenOne && choosenOne > indexs.size()) {
            System.out.println("Invalid choice!");
            showGameList(gameStoreProgram, games, searchedGame, indexs);
            return;
        }
        System.out.println(games.get(indexs.get(choosenOne - 1)).toString());
        if (!boughtGames.contains(games.get(indexs.get(choosenOne - 1)))) {
            buy(gameStoreProgram, games.get(indexs.get(choosenOne - 1)), games, searchedGame);
        }
        handleStore(gameStoreProgram, games);
    }

    public void libShowGameList(GameStoreProgram gameStoreProgram, ArrayList<Game> searchedGames,
            ArrayList<Integer> indexs) {
        System.out.println("Game List:");
        for (int i = 0; i < searchedGames.size(); i++) {
            System.out.println((i + 1) + "_ " + searchedGames.get(i).getName());
            System.out.println("***********************************");
        }
        System.out.println("Choose one to see the details: \n !To turn back enter 0!");
        int choosenOne = ScannerWrapper.getInstance().nextInt();
        if (choosenOne == 0) {
            handleLibrary(gameStoreProgram);
            return;
        }
        if (0 > choosenOne && choosenOne > indexs.size()) {
            System.out.println("Invalid choice!");
            libShowGameList(gameStoreProgram, searchedGames, indexs);
            return;
        }
        System.out.println(boughtGames.get(indexs.get(choosenOne - 1)).toString());
        boughtGames.get(indexs.get(choosenOne - 1)).libraryOptions(gameStoreProgram, this);
        handleLibrary(gameStoreProgram);
    }

    public void searchGame(GameStoreProgram gameStoreProgram, ArrayList<Game> games) {
        double minPrice = 0;
        double maxPrice = Integer.MAX_VALUE;
        ArrayList<Integer> indexs = new ArrayList<>();
        ArrayList<Game> searchedGame = new ArrayList<>();
        System.out.println("***********************************");
        System.out.println("Please Enter Game name: \n !To turn back enter 0!");
        String name = ScannerWrapper.getInstance().nextLine();
        if (name.equals("0")) {
            handleStore(gameStoreProgram, games);
            return;
        }
        System.out.println("To have price range: enter y otherwise enter n");
        String answer = ScannerWrapper.getInstance().nextLine();
        if (answer.equals("y")) {
            System.out.println("Please Enter Price Range: \n MinPrice: ");
            minPrice = ScannerWrapper.getInstance().nextDouble();
            System.out.println("MaxPrice: ");
            maxPrice = ScannerWrapper.getInstance().nextDouble();
        } else if (!answer.equals("n")) {
            System.out.println("Invalid choice!");
            searchGame(gameStoreProgram, games);
            return;
        }
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).getName().startsWith(name) && games.get(i).getPrice() >= minPrice
                    && games.get(i).getPrice() <= maxPrice) {
                searchedGame.add(games.get(i));
                indexs.add(i);
            }
        }
        Collections.sort(searchedGame, new SortByPrice());
        if (searchedGame.size() == 0) {
            System.out.println("There isn't a such game");
            searchGame(gameStoreProgram, games);
            return;
        }
        showGameList(gameStoreProgram, games, searchedGame, indexs);
    }

    public void librarySearchGame(GameStoreProgram gameStoreProgram) {
        ArrayList<Integer> indexs = new ArrayList<>();
        ArrayList<Game> searchedGame = new ArrayList<>();
        System.out.println("***********************************");
        System.out.println("Please Enter Game name: \n !To turn back enter 0!");
        String name = ScannerWrapper.getInstance().nextLine();
        if (name.equals("0")) {
            handleLibrary(gameStoreProgram);
            return;
        }
        for (int i = 0; i < boughtGames.size(); i++) {
            if (boughtGames.get(i).getName().startsWith(name)) {
                searchedGame.add(boughtGames.get(i));
                indexs.add(i);
            }
        }
        if (searchedGame.size() == 0) {
            System.out.println("There isn't a such game");
            librarySearchGame(gameStoreProgram);
            return;
        }
        libShowGameList(gameStoreProgram, searchedGame, indexs);
    }

    public void handleNameAndPhoneNumberEdit(GameStoreProgram gameStoreProgram) {
        boolean editPart = false;
        EditMenu.getInstance().printTheEditMenu();
        EditOption editOption = EditMenu.getInstance().getEditMenuOption();
        switch (editOption) {
            case USER_NAME:
                System.out.println("Please Enter new UserName: \n !To turn back enter 0!");
                String newUserName = ScannerWrapper.getInstance().nextLine();
                if (newUserName.equals("0")) {
                    handleNameAndPhoneNumberEdit(gameStoreProgram);
                    return;
                }
                while (!gameStoreProgram.userUniqueCheck(new User(newUserName, "-1", "-1", "-1"))) {
                    System.out.println("Please Enter new UserName again: \n !To turn back enter 0!");
                    newUserName = ScannerWrapper.getInstance().nextLine();
                    if (newUserName.equals("0")) {
                        handleNameAndPhoneNumberEdit(gameStoreProgram);
                        return;
                    }
                }
                this.userName = newUserName;
                break;
            case PHONE_NUMBER:
                System.out.println("Please Enter new PhoneNumber: \n !To turn back enter 0!");
                String newPhoneNumber = ScannerWrapper.getInstance().nextLine();
                if (newPhoneNumber.equals("0")) {
                    handleNameAndPhoneNumberEdit(gameStoreProgram);
                    return;
                }
                while (!gameStoreProgram.userUniqueCheck(new User("-1", newPhoneNumber, "-1", "-1"))) {
                    System.out.println("Please Enter new PhoneNumber again: \n !To turn back enter 0!");
                    newPhoneNumber = ScannerWrapper.getInstance().nextLine();
                    if (newPhoneNumber.equals("0")) {
                        handleNameAndPhoneNumberEdit(gameStoreProgram);
                        return;
                    }
                }
                this.phoneNumber = newPhoneNumber;
                break;
            default:
                editPart = true;
                handleEmailEdit(gameStoreProgram, editOption);
                break;
        }
        if (!editPart) {
            handleProfile(gameStoreProgram);
        }
    }

    public void handleEmailEdit(GameStoreProgram gameStoreProgram, EditOption editOption) {
        boolean editPart2 = false;
        switch (editOption) {
            case EMAIL:
                System.out.println("Please Enter new Email: \n !To turn back enter 0!");
                String newEmail = ScannerWrapper.getInstance().nextLine();
                if (newEmail.equals("0")) {
                    handleNameAndPhoneNumberEdit(gameStoreProgram);
                    return;
                }
                while (!gameStoreProgram.userUniqueCheck(new User("-1", "-1", newEmail, "-1"))) {
                    System.out.println("Please Enter new Email again: \n !To turn back enter 0!");
                    newEmail = ScannerWrapper.getInstance().nextLine();
                    if (newEmail.equals("0")) {
                        handleNameAndPhoneNumberEdit(gameStoreProgram);
                        return;
                    }
                }
                newEmail = gameStoreProgram.emailCheck(gameStoreProgram, newEmail);
                if (newEmail == null) {
                    handleNameAndPhoneNumberEdit(gameStoreProgram);
                    return;
                }
                this.email = newEmail;
                break;
            default:
                editPart2 = true;
                handlePasswordAndWalletEdit(gameStoreProgram, editOption);
                break;
        }
        if (!editPart2) {
            handleProfile(gameStoreProgram);
        }
    }

    public void handlePasswordAndWalletEdit(GameStoreProgram gameStoreProgram, EditOption editOption) {
        switch (editOption) {
            case PASSWORD:
                System.out.println("Please Enter new Password: \n !To turn back enter 0!");
                String newPassword = ScannerWrapper.getInstance().nextLine();
                if (newPassword.equals("0")) {
                    handleNameAndPhoneNumberEdit(gameStoreProgram);
                    return;
                }
                newPassword = gameStoreProgram.passwordChecking(gameStoreProgram, newPassword);
                if (newPassword == null) {
                    handleNameAndPhoneNumberEdit(gameStoreProgram);
                    return;
                }
                this.password = newPassword;
                break;
            case CHARGE_WALLET:
                System.out.println("Please Enter the amount of Charge: \n !To turn back enter 0!");
                double charge = ScannerWrapper.getInstance().nextDouble();
                if (charge == 0) {
                    handleNameAndPhoneNumberEdit(gameStoreProgram);
                    return;
                }
                this.walletBalance = charge(charge);
                break;
            case BACK:
                handleProfile(gameStoreProgram);
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        handleProfile(gameStoreProgram);
    }

    public void buy(GameStoreProgram gameStoreProgram, Game game, ArrayList<Game> games,
            ArrayList<Game> searchedGames) {
        System.out.println("For Buying send y otherwise send n: \n !To turn back enter 0!");
        String answer = ScannerWrapper.getInstance().nextLine();
        if (answer.equals("0") || answer.equals("n")) {
            showGameList(gameStoreProgram, games, searchedGames, index(games));
            return;
        }
        if (answer.equals("y")) {
            if (this.walletBalance < game.getPrice()) {
                System.out.println("You don't have enough money!");
                showGameList(gameStoreProgram, games, searchedGames, index(games));
                return;
            }
            this.walletBalance = this.walletBalance - game.getPrice();
            boughtGames.add(game);
            System.out.println("Successfully bought");
            showGameList(gameStoreProgram, games, searchedGames, index(games));
            return;
        }
        System.out.println("Invalid choice!");
        buy(gameStoreProgram, game, games, searchedGames);
    }

    public double charge(double charge) {
        return walletBalance + charge;
    }

    public void addRequest(User user) {
        requests.add(user);
    }

    public void addFriend(User user) {
        friends.add(user);
    }

    public void removeRequest(User user) {
        requests.remove(user);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public ArrayList<User> getFriends() {
        return new ArrayList<>(friends);
    }

    public ArrayList<Game> getBoughtGames() {
        return new ArrayList<>(boughtGames);
    }

    public ArrayList<User> getRequests() {
        return new ArrayList<>(requests);
    }

    public boolean userEquals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        boolean equals = false;
        if (userName.equals(user.userName)) {
            System.out.println("There is user with this userName");
            equals = true;
        }
        if (email.equals(user.email)) {
            System.out.println("There is user with this email");
            equals = true;
        }
        if (phoneNumber.equals(user.phoneNumber)) {
            System.out.println("There is user with this phoneNumber");
            equals = true;
        }
        return equals;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (!userName.equals(other.userName)) {
            return false;
        }
        if (!phoneNumber.equals(other.phoneNumber)) {
            return false;
        }
        if (!email.equals(other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, email, phoneNumber);
    }

    @Override
    public String toString() {
        System.out.println("***********************************");
        return "User: \n UserName: " + userName + ",\n PhoneNumber: " + phoneNumber + ",\n Email: " + email
                + ",\n Password: " + password + ",\n WalletBalance: " + walletBalance;
    }

}

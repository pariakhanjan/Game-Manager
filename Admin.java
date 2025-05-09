import java.util.ArrayList;

public class Admin {

    enum UsersOptions {
        USER_INFORMATION, EDIT_USER, MAKE_USER, REMOVE_USER, BACK, UNDEFINED
    }

    enum GamesOptions {
        MAKE_GAME, EDIT_GAME, REMOVE_GAME, BACK, UNDEFINED
    }

    enum GameEditOption {
        GAME_NAME, DESCRIPTION, GENRE, PRICE, BACK, UNDEFINED
    }

    private String adminName;

    private String password;

    private ArrayList<Game> games;

    public Admin(String adminName, String password) {
        this.adminName = adminName;
        this.password = password;
        games = new ArrayList<>();
    }

    public void handleUsers(GameStoreProgram gameStoreProgram, ArrayList<User> users) {
        AdminUsersMenu.getInstance().printTheAdminUsersMenu();
        UsersOptions usersOptions = AdminUsersMenu.getInstance().getAdminUsersMenuOption();
        switch (usersOptions) {
            case USER_INFORMATION:
                userInformatin(gameStoreProgram, users);
                break;
            case EDIT_USER:
                handleUserEdit(gameStoreProgram, users);
                break;
            case MAKE_USER:
                makeUser(gameStoreProgram, users);
                break;
            case REMOVE_USER:
                removeUser(gameStoreProgram, users);
                break;
            case BACK:
                gameStoreProgram.handleAdmin(gameStoreProgram, this);
                return;
            default:
                System.out.println("Invalid choice!");
                handleUsers(gameStoreProgram, users);
                break;
        }
    }

    public void handleGame(GameStoreProgram gameStoreProgram) {
        GameMenu.getInstance().printTheGameMenu();
        GamesOptions gamesOptions = GameMenu.getInstance().getGameMenuOption();
        switch (gamesOptions) {
            case MAKE_GAME:
                makeGame(gameStoreProgram);
                break;
            case EDIT_GAME:
                handleEdit(gameStoreProgram);
                break;
            case REMOVE_GAME:
                removeGame(gameStoreProgram);
                break;
            case BACK:
                gameStoreProgram.handleAdmin(gameStoreProgram, this);
                return;
            default:
                System.out.println("Invalid choice!");
                handleGame(gameStoreProgram);
                break;
        }
    }

    public void userInformatin(GameStoreProgram gameStoreProgram, ArrayList<User> users) {
        User searchedUser = searchUser(gameStoreProgram, users);
        if (searchedUser != null) {
            System.out.println(searchedUser.toString());
            System.out.println("***********************************");
        }
        handleUsers(gameStoreProgram, users);
    }

    public void handleUserEdit(GameStoreProgram gameStoreProgram, ArrayList<User> users) {
        User searchedUser = searchUser(gameStoreProgram, users);
        if (searchedUser != null) {
            for (User user : users) {
                if (user.equals(searchedUser)) {
                    handleNameAndPhoneNumberEdit(gameStoreProgram, user, users);
                    break;
                }
            }
        }
        handleUsers(gameStoreProgram, users);
    }

    public void handleEdit(GameStoreProgram gameStoreProgram) {
        Game searchedGame = searchGame(gameStoreProgram);
        if (searchedGame != null) {
            for (Game game : games) {
                if (game.equals(searchedGame)) {
                    handleGameEdit(gameStoreProgram, game);
                    break;
                }
            }
        }
        handleGame(gameStoreProgram);
    }

    public void makeUser(GameStoreProgram gameStoreProgram, ArrayList<User> users) {
        System.out.println("Please Enter UserName: \n !To turn back enter 0!");
        String userName = ScannerWrapper.getInstance().nextLine();
        if (userName.equals("0")) {
            handleUsers(gameStoreProgram, users);
            return;
        }
        System.out.println("Please Enter PhoneNumber: \n !To turn back enter 0!");
        String phoneNumber = ScannerWrapper.getInstance().nextLine();
        if (phoneNumber.equals("0")) {
            handleUsers(gameStoreProgram, users);
            return;
        }
        System.out.println("Please Enter Email: \n !To turn back enter 0!");
        String email = ScannerWrapper.getInstance().nextLine();
        if (email.equals("0")) {
            handleUsers(gameStoreProgram, users);
            return;
        }
        email = gameStoreProgram.emailCheck(gameStoreProgram, email);
        if (email == null) {
            handleUsers(gameStoreProgram, users);
            return;
        }
        System.out.println("Please Enter Password: \n !To turn back enter 0!");
        String password = ScannerWrapper.getInstance().nextLine();
        if (password.equals("0")) {
            handleUsers(gameStoreProgram, users);
            return;
        }
        password = gameStoreProgram.passwordChecking(gameStoreProgram, password);
        if (password == null) {
            handleUsers(gameStoreProgram, users);
            return;
        }
        User user = new User(userName, phoneNumber, email, password);
        boolean userUnique = gameStoreProgram.userUniqueCheck(user);
        if (userUnique) {
            users.add(user);
            System.out.println("Successfully user added!");
            System.out.println("***********************************");
            handleUsers(gameStoreProgram, users);
        } else {
            makeUser(gameStoreProgram, users);
        }
    }

    public void makeGame(GameStoreProgram gameStoreProgram) {
        System.out.println("Please Enter GameName: \n !To turn back enter 0!");
        String gameName = ScannerWrapper.getInstance().nextLine();
        if (gameName.equals("0")) {
            handleGame(gameStoreProgram);
            return;
        }
        System.out.println("Please Enter Description: \n !To turn back enter 0!");
        String description = ScannerWrapper.getInstance().nextLine();
        if (description.equals("0")) {
            handleGame(gameStoreProgram);
            return;
        }
        System.out.println("Please Enter Genre: \n !To turn back enter 0!");
        String genre = ScannerWrapper.getInstance().nextLine();
        if (genre.equals("0")) {
            handleGame(gameStoreProgram);
            return;
        }
        System.out.println("Please Enter Price: \n !To turn back enter 0!");
        double price = ScannerWrapper.getInstance().nextDouble();
        if (password.equals("0")) {
            handleGame(gameStoreProgram);
            return;
        }
        Game game = new Game(gameName, description, genre, price);
        boolean gameUnique = gameUniqueCheck(game);
        if (gameUnique) {
            games.add(game);
            System.out.println("Successfully game added!");
            System.out.println("***********************************");
            handleGame(gameStoreProgram);
        } else {
            makeGame(gameStoreProgram);
        }
    }

    public void removeUser(GameStoreProgram gameStoreProgram, ArrayList<User> users) {
        User searchedUser = searchUser(gameStoreProgram, users);
        if (searchedUser != null) {
            users.remove(searchedUser);
            System.out.println("Successfully removed");
            System.out.println("***********************************");
        }
        handleUsers(gameStoreProgram, users);
    }

    public void removeGame(GameStoreProgram gameStoreProgram) {
        Game searchedGame = searchGame(gameStoreProgram);
        if (searchedGame != null) {
            games.remove(searchedGame);
            System.out.println("Successfully removed");
            System.out.println("***********************************");
        }
        handleGame(gameStoreProgram);
    }

    public void handleGameEdit(GameStoreProgram gameStoreProgram, Game game) {
        GameEditMenu.getInstance().printTheEditMenu();
        GameEditOption gameEditOption = GameEditMenu.getInstance().getEditMenuOption();
        switch (gameEditOption) {
            case GAME_NAME:
                System.out.println("Please Enter new GameName: \n !To turn back enter 0!");
                String newGameName = ScannerWrapper.getInstance().nextLine();
                if (newGameName.equals("0")) {
                    handleGameEdit(gameStoreProgram, game);
                    return;
                }
                game.setName(newGameName);
                break;
            case DESCRIPTION:
                System.out.println("Please Enter new Description: \n !To turn back enter 0!");
                String newDescription = ScannerWrapper.getInstance().nextLine();
                if (newDescription.equals("0")) {
                    handleGameEdit(gameStoreProgram, game);
                    return;
                }
                game.setDescription(newDescription);
                break;
            case GENRE:
                System.out.println("Please Enter new Genre: \n !To turn back enter 0!");
                String newGenre = ScannerWrapper.getInstance().nextLine();
                if (newGenre.equals("0")) {
                    handleGameEdit(gameStoreProgram, game);
                    return;
                }
                game.setGenre(newGenre);
                break;
            case PRICE:
                System.out.println("Please Enter new Price: \n !To turn back enter 0!");
                double newPrice = ScannerWrapper.getInstance().nextDouble();
                if (newPrice == 0) {
                    handleGameEdit(gameStoreProgram, game);
                    return;
                }
                game.setPrice(newPrice);
                break;
            case BACK:
                return;
            default:
                System.out.println("Invalid choice!");
                handleGameEdit(gameStoreProgram, game);
                break;
        }
    }

    public void handleNameAndPhoneNumberEdit(GameStoreProgram gameStoreProgram, User user, ArrayList<User> users) {
        EditMenu.getInstance().printTheEditMenu();
        User.EditOption editOption = EditMenu.getInstance().getEditMenuOption();
        switch (editOption) {
            case USER_NAME:
                System.out.println("Please Enter new UserName: \n !To turn back enter 0!");
                String newUserName = ScannerWrapper.getInstance().nextLine();
                if (newUserName.equals("0")) {
                    handleNameAndPhoneNumberEdit(gameStoreProgram, user, users);
                    return;
                }
                while (!gameStoreProgram.userUniqueCheck(new User(newUserName, "-1", "-1", "-1"))) {
                    System.out.println("Please Enter new UserName again: \n !To turn back enter 0!");
                    newUserName = ScannerWrapper.getInstance().nextLine();
                    if (newUserName.equals("0")) {
                        handleNameAndPhoneNumberEdit(gameStoreProgram, user, users);
                        return;
                    }
                }
                user.setUserName(newUserName);
                break;
            case PHONE_NUMBER:
                System.out.println("Please Enter new PhoneNumber: \n !To turn back enter 0!");
                String newPhoneNumber = ScannerWrapper.getInstance().nextLine();
                if (newPhoneNumber.equals("0")) {
                    handleNameAndPhoneNumberEdit(gameStoreProgram, user, users);
                    return;
                }
                while (!gameStoreProgram.userUniqueCheck(new User("-1", newPhoneNumber, "-1", "-1"))) {
                    System.out.println("Please Enter new PhoneNumber again: \n !To turn back enter 0!");
                    newPhoneNumber = ScannerWrapper.getInstance().nextLine();
                    if (newPhoneNumber.equals("0")) {
                        handleNameAndPhoneNumberEdit(gameStoreProgram, user, users);
                        return;
                    }
                }
                user.setPhoneNumber(newPhoneNumber);
                break;
            default:
                handleEmailEdit(gameStoreProgram, editOption, user, users);
                break;
        }
    }

    public void handleEmailEdit(GameStoreProgram gameStoreProgram, User.EditOption editOption, User user,
            ArrayList<User> users) {
        switch (editOption) {
            case EMAIL:
                System.out.println("Please Enter new Email: \n !To turn back enter 0!");
                String newEmail = ScannerWrapper.getInstance().nextLine();
                if (newEmail.equals("0")) {
                    handleNameAndPhoneNumberEdit(gameStoreProgram, user, users);
                    return;
                }
                while (!gameStoreProgram.userUniqueCheck(new User("-1", "-1", newEmail, "-1"))) {
                    System.out.println("Please Enter new Email again: \n !To turn back enter 0!");
                    newEmail = ScannerWrapper.getInstance().nextLine();
                    if (newEmail.equals("0")) {
                        handleNameAndPhoneNumberEdit(gameStoreProgram, user, users);
                        return;
                    }
                }
                newEmail = gameStoreProgram.emailCheck(gameStoreProgram, newEmail);
                if (newEmail == null) {
                    handleNameAndPhoneNumberEdit(gameStoreProgram, user, users);
                    return;
                }
                user.setEmail(newEmail);
                break;
            default:
                handlePasswordAndWalletEdit(gameStoreProgram, editOption, user, users);
                break;
        }
    }

    public void handlePasswordAndWalletEdit(GameStoreProgram gameStoreProgram, User.EditOption editOption, User user,
            ArrayList<User> users) {
        switch (editOption) {
            case PASSWORD:
                System.out.println("Please Enter new Password: \n !To turn back enter 0!");
                String newPassword = ScannerWrapper.getInstance().nextLine();
                if (newPassword.equals("0")) {
                    handleNameAndPhoneNumberEdit(gameStoreProgram, user, users);
                    return;
                }
                newPassword = gameStoreProgram.passwordChecking(gameStoreProgram, newPassword);
                if (newPassword == null) {
                    handleNameAndPhoneNumberEdit(gameStoreProgram, user, users);
                    return;
                }
                user.setPassword(newPassword);
                break;
            case CHARGE_WALLET:
                System.out.println("Please Enter the amount of Charge: \n !To turn back enter 0!");
                double charge = ScannerWrapper.getInstance().nextDouble();
                if (charge == 0) {
                    handleNameAndPhoneNumberEdit(gameStoreProgram, user, users);
                    return;
                }
                user.setWalletBalance(user.charge(charge));
                break;
            case BACK:
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
    }

    public User searchUser(GameStoreProgram gameStoreProgram, ArrayList<User> users) {
        System.out.println("***********************************");
        System.out.println("Please Enter User information: \n !To turn back enter 0!");
        String userInfo = ScannerWrapper.getInstance().nextLine();
        if (userInfo.equals("0")) {
            return null;
        }
        ArrayList<Integer> indexs = new ArrayList<>();
        ArrayList<User> searchedUsers = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(userInfo)) {
                if (!searchedUsers.contains(users.get(i))) {
                    searchedUsers.add(users.get(i));
                    indexs.add(i);
                }
            }
            if (users.get(i).getPhoneNumber().equals(userInfo)) {
                if (!searchedUsers.contains(users.get(i))) {
                    searchedUsers.add(users.get(i));
                    indexs.add(i);
                }
            }
            if (users.get(i).getEmail().equals(userInfo)) {
                if (!searchedUsers.contains(users.get(i))) {
                    searchedUsers.add(users.get(i));
                    indexs.add(i);
                }
            }
        }
        if (searchedUsers.size() == 0) {
            System.out.println("There isn't a such user");
            return null;
        }
        System.out.println("Your search result:");
        for (int i = 0; i < searchedUsers.size(); i++) {
            System.out.println((i + 1) + "_ " + searchedUsers.get(i).getUserName());
        }
        System.out.println("Choose the one you want: \n !To turn back enter 0!");
        int choosenOne = ScannerWrapper.getInstance().nextInt();
        if (choosenOne == 0) {
            return null;
        }
        if (0 > choosenOne && choosenOne > searchedUsers.size()) {
            System.out.println("Invalid choice!");
            return null;
        }
        return users.get(indexs.get(choosenOne - 1));
    }

    public Game searchGame(GameStoreProgram gameStoreProgram) {
        System.out.println("***********************************");
        System.out.println("Please Enter Game name: \n !To turn back enter 0!");
        String name = ScannerWrapper.getInstance().nextLine();
        if (name.equals("0")) {
            return null;
        }
        ArrayList<Integer> indexs = new ArrayList<>();
        ArrayList<Game> searchedGame = new ArrayList<>();
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).getName().equals(name)) {
                searchedGame.add(games.get(i));
                indexs.add(i);
            }
        }
        if (searchedGame.size() == 0) {
            System.out.println("There isn't a such game");
            return null;
        }
        System.out.println("Your search result:");
        for (int i = 0; i < searchedGame.size(); i++) {
            System.out.println((i + 1) + "_ " + searchedGame.get(i).toString());
        }
        System.out.println("Choose the one you want: \n !To turn back enter 0!");
        int choosenOne = ScannerWrapper.getInstance().nextInt();
        if (choosenOne == 0) {
            return null;
        }
        if (0 > choosenOne && choosenOne > searchedGame.size()) {
            System.out.println("Invalid choice!");
            return null;
        }
        return games.get(indexs.get(choosenOne - 1));
    }

    public boolean gameUniqueCheck(Game game) {
        boolean gameUnique = true;
        for (Game gameCheck : games) {
            if (gameCheck.equals(game)) {
                gameUnique = false;
                System.out.println("This game exists");
                break;
            }
        }
        return gameUnique;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Game> getGames() {
        return new ArrayList<Game>(games);
    }

}

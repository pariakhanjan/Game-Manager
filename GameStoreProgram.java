import java.util.ArrayList;

public class GameStoreProgram {

    enum Option {
        ADMIN, USER, EXIT, UNDEFINED
    }

    enum UserOption {
        SIGNIN, SIGNUP, BACK, UNDEFINED
    }

    enum UserMenuOption {
        PROFILE, STORE, LIBRARY, FRIENDS, BACK, UNDEFINED
    }

    enum AdminOption {
        USERS, GAMES, BACK, UNDEFINED
    }

    enum FriendOption {
        SHOW, SEARCH, NEW_FRIEND, REQUESTS, BACK, UNDEFINED
    }

    private ArrayList<User> users = new ArrayList<>();

    private Admin admin = new Admin("Mahta", "mah82Ranjbar56");

    public static void main(String[] args) {
        Option option;
        Menu.getInstance().printTheMenu();
        option = Menu.getInstance().getOption();
        GameStoreProgram gameStoreProgram = new GameStoreProgram();
        while (option != Option.EXIT) {
            gameStoreProgram.handleTheOption(gameStoreProgram, option);
            Menu.getInstance().printTheMenu();
            option = Menu.getInstance().getOption();
        }
        System.out.println("The program is finished!");
        ScannerWrapper.getInstance().close();
    }

    public void handleTheOption(GameStoreProgram gameStoreProgram, Option option) {
        switch (option) {
            case ADMIN:
                amdinSignIn(gameStoreProgram);
                break;
            case USER:
                handleUserLogIn(gameStoreProgram);
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
    }

    public void handleUserLogIn(GameStoreProgram gameStoreProgram) {
        UserOptions.getInstance().printTheUserOptions();
        UserOption userOption = UserOptions.getInstance().getUserOptions();
        switch (userOption) {
            case SIGNIN:
                signIn(gameStoreProgram);
                break;
            case SIGNUP:
                signUp(gameStoreProgram);
                break;
            case BACK:
                return;
            default:
                System.out.println("Invalid choice!");
                handleUserLogIn(gameStoreProgram);
                break;
        }
    }

    public void handleUser(GameStoreProgram gameStoreProgram, User user) {
        UserMenuOption userMenuOption;
        UserMenu.getInstance().printTheUserMenu();
        userMenuOption = UserMenu.getInstance().getUserMenuOption();
        switch (userMenuOption) {
            case PROFILE:
                user.handleProfile(gameStoreProgram);
                break;
            case STORE:
                user.handleStore(gameStoreProgram, admin.getGames());
                break;
            case LIBRARY:
                user.handleLibrary(gameStoreProgram);
                break;
            case FRIENDS:
                handleFriend(gameStoreProgram, user);
                break;
            case BACK:
                handleUserLogIn(gameStoreProgram);
                return;
            default:
                System.out.println("Invalid choice!");
                handleUser(gameStoreProgram, user);
                break;
        }
    }

    public void handleAdmin(GameStoreProgram gameStoreProgram, Admin admin) {
        AdminOption adminMenuOption;
        AdminMenu.getInstance().printTheAdminMenu();
        adminMenuOption = AdminMenu.getInstance().getAdminMenuOption();
        switch (adminMenuOption) {
            case USERS:
                admin.handleUsers(gameStoreProgram, users);
                break;
            case GAMES:
                admin.handleGame(gameStoreProgram);
                break;
            case BACK:
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
    }

    public void handleFriend(GameStoreProgram gameStoreProgram, User user) {
        FriendMenu.getInstance().printTheFriendMenu();
        FriendOption friendOption = FriendMenu.getInstance().getFriendMenuOption();
        switch (friendOption) {
            case SHOW:
                showFriendList(gameStoreProgram, user, user.getFriends(), user.getFriends(), index(user.getFriends()));
                break;
            case SEARCH:
                searchFriend(gameStoreProgram, user, user.getFriends());
                break;
            case NEW_FRIEND:
                searchNewFriend(gameStoreProgram, user, user.getFriends());
                break;
            case REQUESTS:
                handleRequest(gameStoreProgram, user, user.getFriends(), user.getRequests());
                break;
            case BACK:
                handleUser(gameStoreProgram, user);
                return;
            default:
                System.out.println("Invalid choice!");
                handleFriend(gameStoreProgram, user);
                break;
        }
    }

    public void signIn(GameStoreProgram gameStoreProgram) {
        boolean userExistence = false;
        System.out.println("Please Enter userName: \n !To turn back enter 0!");
        String userName = ScannerWrapper.getInstance().nextLine();
        if (userName.equals("0")) {
            handleUserLogIn(gameStoreProgram);
            return;
        }
        System.out.println("Please Enter password: \n !To turn back enter 0!");
        String password = ScannerWrapper.getInstance().nextLine();
        if (password.equals("0")) {
            handleUserLogIn(gameStoreProgram);
            return;
        }
        int userIndex = 0;
        for (int i = 0; i < users.size(); i++) {
            if (userName.equals(users.get(i).getUserName())) {
                while (!password.equals(users.get(i).getPassword())) {
                    System.out.println("Error! wrong password!");
                    System.out.println("Please Enter Password again: \n !To turn back enter 0!");
                    password = ScannerWrapper.getInstance().nextLine();
                    if (password.equals("0")) {
                        handleUserLogIn(gameStoreProgram);
                        return;
                    }
                }
                userExistence = true;
                userIndex = i;
                break;
            }
        }
        if (userExistence) {
            System.out.println("Successfully Loged in!");
            handleUser(gameStoreProgram, users.get(userIndex));
        } else {
            System.out.println("This user doesn't exist!");
            signIn(gameStoreProgram);
        }
    }

    public void signUp(GameStoreProgram gameStoreProgram) {
        System.out.println("Please Enter UserName: \n !To turn back enter 0!");
        String userName = ScannerWrapper.getInstance().nextLine();
        if (userName.equals("0")) {
            handleUserLogIn(gameStoreProgram);
            return;
        }
        System.out.println("Please Enter PhoneNumber: \n !To turn back enter 0!");
        String phoneNumber = ScannerWrapper.getInstance().nextLine();
        if (phoneNumber.equals("0")) {
            handleUserLogIn(gameStoreProgram);
            return;
        }
        System.out.println("Please Enter Email: \n !To turn back enter 0!");
        String email = ScannerWrapper.getInstance().nextLine();
        if (email.equals("0")) {
            handleUserLogIn(gameStoreProgram);
            return;
        }
        email = emailCheck(gameStoreProgram, email);
        if (email == null) {
            handleUserLogIn(gameStoreProgram);
            return;
        }
        System.out.println("Please Enter Password: \n !To turn back enter 0!");
        String password = ScannerWrapper.getInstance().nextLine();
        if (password.equals("0")) {
            handleUserLogIn(gameStoreProgram);
            return;
        }
        password = passwordChecking(gameStoreProgram, password);
        if (password == null) {
            handleUserLogIn(gameStoreProgram);
            return;
        }
        User user = new User(userName, phoneNumber, email, password);
        boolean userUnique = userUniqueCheck(user);
        if (userUnique) {
            users.add(user);
            System.out.println("Successfully Signed up!");
            signIn(gameStoreProgram);
        } else {
            signUp(gameStoreProgram);
        }
    }

    public void amdinSignIn(GameStoreProgram gameStoreProgram) {
        boolean adminExistence = false;
        System.out.println("Please Enter Name: \n !To turn back enter 0!");
        String adminName = ScannerWrapper.getInstance().nextLine();
        if (adminName.equals("0")) {
            return;
        }
        System.out.println("Please Enter Password: \n !To turn back enter 0!");
        String password = ScannerWrapper.getInstance().nextLine();
        if (password.equals("0")) {
            return;
        }
        if (adminName.equals(admin.getAdminName())) {
            while (!password.equals(admin.getPassword())) {
                System.out.println("Error! wrong password!");
                System.out.println("Please Enter Password again: \n !To turn back enter 0!");
                password = ScannerWrapper.getInstance().nextLine();
                if (password.equals("0")) {
                    return;
                }
            }
            adminExistence = true;
        }
        if (adminExistence) {
            System.out.println("Successfully Loged in!");
            handleAdmin(gameStoreProgram, admin);
        } else {
            System.out.println("This admin doesn't exist!");
            amdinSignIn(gameStoreProgram);
        }
    }

    public String passwordChecking(GameStoreProgram gameStoreProgram, String password) {
        boolean goodPassword = true;
        if (password.length() < 8) {
            System.out.println("Error! The password should have more charecters");
            goodPassword = false;
        }
        if (!password.matches(".*[a-z].*")) {
            System.out.println("Error! The password should have small letters");
            goodPassword = false;
        }
        if (!password.matches(".*[A-Z].*")) {
            System.out.println("Error! The password should have capital letters");
            goodPassword = false;
        }
        if (!password.matches(".*[0-9].*")) {
            System.out.println("Error! The password should have number");
            goodPassword = false;
        }
        if (!goodPassword) {
            System.out.println("Password isn't good. \n Please Enter Password again: \n !To turn back enter 0!");
            password = ScannerWrapper.getInstance().nextLine();
            if (password.equals("0")) {
                return null;
            }
            password = passwordChecking(gameStoreProgram, password);
        }
        return password;
    }

    public String emailCheck(GameStoreProgram gameStoreProgram, String email) {
        if (!email.contains("@")) {
            System.out.println("Email isn't good! Enter Email again: \n !To turn back enter 0!");
            email = ScannerWrapper.getInstance().nextLine();
            if (email.equals("0")) {
                return null;
            }
            email = emailCheck(gameStoreProgram, email);
        }
        return email;
    }

    public boolean userUniqueCheck(User user) {
        boolean userUnique = true;
        for (User userList : users) {
            if (user.userEquals(userList)) {
                userUnique = false;
                System.out.println("This user exists");
                break;
            }
        }
        return userUnique;
    }

    public void showFriendList(GameStoreProgram gameStoreProgram, User user, ArrayList<User> friends,
            ArrayList<User> searchedfriends, ArrayList<Integer> indexs) {
        System.out.println("Friends List:");
        for (int i = 0; i < searchedfriends.size(); i++) {
            System.out.println((i + 1) + "_ " + searchedfriends.get(i).getUserName());
            System.out.println("***********************************");
        }
        System.out.println("Choose one to see the details: \n !To turn back enter 0!");
        int choosenOne = ScannerWrapper.getInstance().nextInt();
        if (choosenOne == 0) {
            handleFriend(gameStoreProgram, user);
            return;
        }
        if (0 > choosenOne && choosenOne > indexs.size()) {
            System.out.println("Invalid choice!");
            showFriendList(gameStoreProgram, user, friends, searchedfriends, indexs);
            return;
        }
        System.out.println(friends.get(indexs.get(choosenOne - 1)).getBoughtGames().toString());
        handleFriend(gameStoreProgram, user);
    }

    public void searchFriend(GameStoreProgram gameStoreProgram, User user, ArrayList<User> friends) {
        ArrayList<Integer> indexs = new ArrayList<>();
        ArrayList<User> searchedFriend = new ArrayList<>();
        System.out.println("***********************************");
        System.out.println("Please Enter Friend name: \n !To turn back enter 0!");
        String name = ScannerWrapper.getInstance().nextLine();
        if (name.equals("0")) {
            handleFriend(gameStoreProgram, user);
            return;
        }
        for (int i = 0; i < friends.size(); i++) {
            if (friends.get(i).getUserName().startsWith(name)) {
                searchedFriend.add(friends.get(i));
                indexs.add(i);
            }
        }
        if (searchedFriend.size() == 0) {
            System.out.println("There isn't a such user");
            searchFriend(gameStoreProgram, user, friends);
            return;
        }
        showFriendList(gameStoreProgram, user, friends, searchedFriend, indexs);
    }

    public void searchNewFriend(GameStoreProgram gameStoreProgram, User user, ArrayList<User> friends) {
        System.out.println("***********************************");
        System.out.println("Please Enter User name: \n !To turn back enter 0!");
        String userName = ScannerWrapper.getInstance().nextLine();
        if (userName.equals("0")) {
            handleFriend(gameStoreProgram, user);
            return;
        }
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(userName)) {
                if (!friends.contains(users.get(i))) {
                    users.get(i).addRequest(user);
                    System.out.println("Request successfully sent!");
                    showFriendList(gameStoreProgram, user, friends, friends, index(friends));
                    return;
                } else {
                    System.out.println("This user is your friend!");
                    showFriendList(gameStoreProgram, user, friends, friends, index(friends));
                    return;
                }
            }
        }
        System.out.println("There isn't a such user");
        searchNewFriend(gameStoreProgram, user, friends);
    }

    public void handleRequest(GameStoreProgram gameStoreProgram, User user, ArrayList<User> friends,
            ArrayList<User> requests) {
        System.out.println("Requests List:");
        for (int i = 0; i < requests.size(); i++) {
            System.out.println((i + 1) + "_ " + requests.get(i).getUserName());
            System.out.println("***********************************");
        }
        System.out.println("Choose one to accept or deny: \n !To turn back enter 0!");
        int choosenOne = ScannerWrapper.getInstance().nextInt();
        if (choosenOne == 0) {
            handleFriend(gameStoreProgram, user);
            return;
        }
        if (0 > choosenOne && choosenOne > friends.size()) {
            System.out.println("Invalid choice!");
            handleRequest(gameStoreProgram, user, friends, requests);
            return;
        }
        System.out.println("Accept or Deny this user: (enter y for accept otherwise enter n) "
                + requests.get(choosenOne - 1).getUserName() + "\n!To turn back enter 0!");
        String answer = ScannerWrapper.getInstance().nextLine();
        if (answer.equals("0")) {
            handleFriend(gameStoreProgram, user);
            return;
        }
        if (answer.equals("y")) {
            user.removeRequest(requests.get(choosenOne - 1));
            user.addFriend(requests.get(choosenOne - 1));
            requests.get(choosenOne - 1).addFriend(user);
            System.out.println("Request successfully accepted!");
        } else if (answer.equals("n")) {
            user.removeRequest(requests.get(choosenOne - 1));
            System.out.println("Request successfully denied!");
        } else {
            System.out.println("Invalid choice!");
            handleRequest(gameStoreProgram, user, friends, requests);
            return;
        }
        handleFriend(gameStoreProgram, user);
    }

    public ArrayList<Integer> index(ArrayList<User> users) {
        ArrayList<Integer> index = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            index.add(i);
        }
        return index;
    }

}
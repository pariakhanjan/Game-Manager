import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Game {

    enum CommunityOption {
        SHOW_COMMENTS, COMMENT, BACK, UNDEFINED
    }

    enum LibraryOption {
        COMMUNITY, RATE, BACK, UNDEFINED
    }

    private String name;

    private String description;

    private String genre;

    private double price;

    private double averageRate;

    private Map<User, Double> rates;

    private ArrayList<String> comments;

    public Game(String name, String description, String genre, double price) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.price = price;
        averageRate = 0;
        this.rates = new HashMap<>();
        comments = new ArrayList<>();
    }

    public void libraryOptions(GameStoreProgram gameStoreProgram, User user) {
        LibraryMenu.getInstance().printTheLibraryMenu();
        LibraryOption libraryOption = LibraryMenu.getInstance().getLibraryMenuOption();
        switch (libraryOption) {
            case COMMUNITY:
                community(gameStoreProgram, user);
                break;
            case RATE:
                addRate(gameStoreProgram, user);
                break;
            case BACK:
                user.handleLibrary(gameStoreProgram);
                return;
            default:
                System.out.println("Invalid choice!");
                libraryOptions(gameStoreProgram, user);
                break;
        }
    }

    public void community(GameStoreProgram gameStoreProgram, User user) {
        System.out.println("To see Community send y otherwise send n: \n !To turn back enter 0!");
        String answer = ScannerWrapper.getInstance().nextLine();
        if (answer.equals("0") || answer.equals("n")) {
            libraryOptions(gameStoreProgram, user);
            return;
        }
        if (answer.equals("y")) {
            CommunityMenu.getInstance().printTheCommunityMenu();
            CommunityOption communityOption = CommunityMenu.getInstance().getCommunityMenuOption();
            switch (communityOption) {
                case SHOW_COMMENTS:
                    showComments(gameStoreProgram, user);
                    libraryOptions(gameStoreProgram, user);
                    break;
                case COMMENT:
                    comment(gameStoreProgram, user);
                    libraryOptions(gameStoreProgram, user);
                    break;
                case BACK:
                    libraryOptions(gameStoreProgram, user);
                    return;
                default:
                    System.out.println("Invalid choice!");
                    community(gameStoreProgram, user);
                    return;
            }
        } else {
            System.out.println("Invalid choice!");
            libraryOptions(gameStoreProgram, user);
        }
    }

    public void addRate(GameStoreProgram gameStoreProgram, User user) {
        System.out.println("Enter Rate: \n !To turn back enter 0!");
        Double rate = ScannerWrapper.getInstance().nextDouble();
        if (rate == 0) {
            libraryOptions(gameStoreProgram, user);
            return;
        }
        putRate(user, rate);
        rate();
        System.out.println(averageRate);
        libraryOptions(gameStoreProgram, user);
    }

    public void showComments(GameStoreProgram gameStoreProgram, User user) {
        System.out.println("Comments:");
        System.out.println(getComments().toString());
        System.out.println("***********************************");
        community(gameStoreProgram, user);
    }

    public void comment(GameStoreProgram gameStoreProgram, User user) {
        System.out.println("Enter Comment: \n !To turn back enter 0!");
        String comment = ScannerWrapper.getInstance().nextLine();
        if (comment.equals("0")) {
            return;
        }
        addComments(comment);
    }

    public void addComments(String comment) {
        comments.add(comment);
    }

    public void putRate(User user, Double rate) {
        this.rates.put(user, rate);
    }

    public void rate() {
        double sum = 0;
        ArrayList<Double> rate = new ArrayList<>(rates.values());
        for (Double singleRate : rate) {
            sum += singleRate;
        }
        this.averageRate = sum / rates.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<String> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Game other = (Game) obj;
        if (!name.equals(other.name)) {
            return false;
        }
        if (!description.equals(other.description)) {
            return false;
        }
        if (!genre.equals(other.genre)) {
            return false;
        }
        if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, genre, price);
    }

    @Override
    public String toString() {
        System.out.println("***********************************");
        return "Game: \n Name: " + name + ",\n Description: " + description + ",\n Genre: " + genre + ",\n Price: "
                + price + ",\n Rate: " + averageRate;
    }

}

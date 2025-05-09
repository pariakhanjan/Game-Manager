import java.util.Comparator;

public class SortByPrice implements Comparator<Game>  {
    
    @Override
	public int compare(Game firstGame, Game secondGame) {
		return (int) (firstGame.getPrice() - secondGame.getPrice());
	}
	
}

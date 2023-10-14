package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.game.iservice;

import java.util.List;

import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.game.Game;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.player.Player;

public interface IgameService {
	
	public Game playGame(int playerId);
	
	public List<Game> getPlayerGames(int playerId);
	
	public void deletePlayerGames(int playerId);
	
	public double getAverageSuccessPercentage();
	
	public Player getLoser();
	 
	public Player getWinner(); 	 

}

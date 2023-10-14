package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.game.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.databaseProperties.DataBaseProperties.DatabaseProperties;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.game.Game;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.player.Player;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.gameMongo.service.GameMongoService;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.gameSql.service.GameSqlService;

@Profile({"sql", "mongo"}) 
@Service
public class GameService {
	 @Autowired
	 private GameSqlService sqlGameService;
	 @Autowired
	 private GameMongoService mongoGameService; 
	 @Autowired
	 private DatabaseProperties databaseProperties;	
	
	 public Game playGame(int playerId) {
		 if ("sql".equals(databaseProperties.activeDatabaseProfile())) {
	            return sqlGameService.playGame(playerId);
	        } else if ("mongo".equals(databaseProperties.activeDatabaseProfile())) {
	            return mongoGameService.playGame(playerId);
	        } else {
	            throw new UnsupportedOperationException("Base de datos no compatible");
	        }
	 }
	 
	 public List<Game> getPlayerGames(int playerId) {
		 if ("sql".equals(databaseProperties.activeDatabaseProfile())) {
	            return sqlGameService.getPlayerGames(playerId);
	        } else if ("mongo".equals(databaseProperties.activeDatabaseProfile())) {
	            return mongoGameService.getPlayerGames(playerId);
	        } else {
	            throw new UnsupportedOperationException("Base de datos no compatible");
	        } 
	 }
	 
	 public void deletePlayerGames(int playerId) {
		 if ("sql".equals(databaseProperties.activeDatabaseProfile())) {
	            sqlGameService.deletePlayerGames(playerId);
	        } else if ("mongo".equals(databaseProperties.activeDatabaseProfile())) {
	            mongoGameService.deletePlayerGames(playerId);
	        } else {
	            throw new UnsupportedOperationException("Base de datos no compatible");
	        } 
	 }

	 public double getAverageSuccessPercentage() {
		 if ("sql".equals(databaseProperties.activeDatabaseProfile())) {
	            return sqlGameService.getAverageSuccessPercentage();
	        } else if ("mongo".equals(databaseProperties.activeDatabaseProfile())) {
	            return mongoGameService.getAverageSuccessPercentage();
	        } else {
	            throw new UnsupportedOperationException("Base de datos no compatible");
	        } 
	 }

	 public Player getLoser() {
		 if ("sql".equals(databaseProperties.activeDatabaseProfile())) {
	            return sqlGameService.getLoser();
	        } else if ("mongo".equals(databaseProperties.activeDatabaseProfile())) {
	            return mongoGameService.getLoser();
	        } else {
	            throw new UnsupportedOperationException("Base de datos no compatible");
	        }  
	 }

	 public Player getWinner() {
		 if ("sql".equals(databaseProperties.activeDatabaseProfile())) {
	            return sqlGameService.getWinner();
	        } else if ("mongo".equals(databaseProperties.activeDatabaseProfile())) {
	            return mongoGameService.getWinner();
	        } else {
	            throw new UnsupportedOperationException("Base de datos no compatible");
	        } 
	 }
}

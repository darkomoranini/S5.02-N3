package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.gameSql.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.gameSql.repository.IgameSqlRepository;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.game.Game;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.player.Player;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.game.iservice.IgameService;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.playerSql.service.PlayerSqlService;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.playerSql.repository.IplayerSqlRepository;

@Service
public class GameSqlService implements IgameService{

    @Autowired
    private IgameSqlRepository gameRepository;
    @Autowired
    private IplayerSqlRepository playerRepository;
    @Autowired
    private PlayerSqlService playerService;    

    public Game playGame(int playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));

        int roll1 = (int) (Math.random() * 6) + 1;
        int roll2 = (int) (Math.random() * 6) + 1;
        boolean won = (roll1 + roll2) == 7;
        Game game = new Game();
        game.setRoll1(roll1);
        game.setRoll2(roll2);
        game.setWon(won);
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentDateTime);
        game.setTimestamp(timestamp);
        game.setPlayer(player);

        return gameRepository.save(game);
    }

    public List<Game> getPlayerGames(int playerId) {
        @SuppressWarnings("unused")
		Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
        List<Game> playerGames = gameRepository.findByPlayerId(playerId);

        return playerGames;
    }

    public void deletePlayerGames(int playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
        gameRepository.deleteByPlayer(player);
    }

    public double getAverageSuccessPercentage() {
        List<Player> players = playerRepository.findAll();
        if (players.isEmpty()) {
            
        	return 0.0;
        }

        double totalSuccessPercentage = players.stream()
                .mapToDouble(player -> playerService.getPlayerSuccessPercentage(player.getId()))
                .sum();

        return totalSuccessPercentage / players.size();
    }

    public Player getLoser() {
        List<Player> players = playerRepository.findAll();
        if (players.isEmpty()) {
            return null;
        }

        Player loser = players.get(0);
        double minSuccessPercentage = playerService.getPlayerSuccessPercentage(loser.getId());

        for (Player player : players) {
            double successPercentage = playerService.getPlayerSuccessPercentage(player.getId());
            if (successPercentage < minSuccessPercentage) {
                minSuccessPercentage = successPercentage;
                loser = player;
            }
        }

        return loser;
    }

    public Player getWinner() {
        List<Player> players = playerRepository.findAll();
        if (players.isEmpty()) {
            return null;
        }

        Player winner = players.get(0);
        double maxSuccessPercentage = playerService.getPlayerSuccessPercentage(winner.getId());

        for (Player player : players) {
            double successPercentage = playerService.getPlayerSuccessPercentage(player.getId());
            if (successPercentage > maxSuccessPercentage) {
                maxSuccessPercentage = successPercentage;
                winner = player;
            }
        }

        return winner;
    }
}

package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.playerSql.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.*;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.gameSql.repository.IgameSqlRepository;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.game.Game;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.player.Player;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.player.iservice.IplayerService;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.playerSql.repository.IplayerSqlRepository;

@Service
public class PlayerSqlService implements IplayerService {

    @Autowired
    private IplayerSqlRepository playerSqlRepository;
    @Autowired
    private IgameSqlRepository gameSqlRepository; 

    public Player createPlayer(Player player) {
    	LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentDateTime);
        Date currentDate = new Date(timestamp.getTime());

        player.setRegistrationDate(currentDate);
       
        return playerSqlRepository.save(player);
    }

    public Player updatePlayer(int id, Player updatedPlayer) {
        Player existingPlayer = null;
		try {
			existingPlayer = playerSqlRepository.findById(id)
			        .orElseThrow(() -> new NotFoundException());
		} catch (NotFoundException e) {
			
			e.printStackTrace();
		}
        existingPlayer.setName(updatedPlayer.getName());
        	
        return playerSqlRepository.save(existingPlayer);
    }

    public Optional<Player> getPlayerById(int id) {

        try {
        	playerSqlRepository.findById(id)
			        .orElseThrow(() -> new NotFoundException());
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
        
        return playerSqlRepository.findById(id);
    }

    public List<Player> getAllPlayers() {    
        
    	return playerSqlRepository.findAll();
    }

    public void deletePlayer(int id) {
    	playerSqlRepository.deleteById(id);
    }

    public double getPlayerSuccessPercentage(int id) {
        List<Game> playerGames = gameSqlRepository.findByPlayerId(id);
        if (playerGames.isEmpty()) {
            return 0.0;
        }

        double wonGames = playerGames.stream().filter(Game::won).count();
       
        return wonGames / playerGames.size() * 100.0;
    }
}

package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.playerMongo.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.gameMongo.repository.IgameMongoRepository;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.game.Game;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.player.Player;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.player.iservice.IplayerService;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.playerMongo.repository.IplayerMongoRepository;


public class PlayerMongoService implements IplayerService{
	@Autowired
    private IplayerMongoRepository playerMongoRepository;
    @Autowired
    private IgameMongoRepository gameMongoRepository; 

    public Player createPlayer(Player player) {
    	LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentDateTime);
        Date currentDate = new Date(timestamp.getTime());

        player.setRegistrationDate(currentDate);
       
        return playerMongoRepository.save(player);
    }

    public Player updatePlayer(int id, Player updatedPlayer) {
        Player existingPlayer = null;
		try {
			existingPlayer = playerMongoRepository.findById(id)
			        .orElseThrow(() -> new NotFoundException());
		} catch (NotFoundException e) {
			
			e.printStackTrace();
		}
        existingPlayer.setName(updatedPlayer.getName());
        	
        return playerMongoRepository.save(existingPlayer);
    }

    public Optional<Player> getPlayerById(int id) {

        try {
			playerMongoRepository.findById(id)
			        .orElseThrow(() -> new NotFoundException());
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
        
        return playerMongoRepository.findById(id);
    }

    public List<Player> getAllPlayers() {    
        
    	return playerMongoRepository.findAll();
    }

    public void deletePlayer(int id) {
    	playerMongoRepository.deleteById(id);
    }

    public double getPlayerSuccessPercentage(int id) {
        List<Game> playerGames = gameMongoRepository.findByPlayerId(id);
        if (playerGames.isEmpty()) {
            return 0.0;
        }

        double wonGames = playerGames.stream().filter(Game::won).count();
       
        return wonGames / playerGames.size() * 100.0;
    }
}


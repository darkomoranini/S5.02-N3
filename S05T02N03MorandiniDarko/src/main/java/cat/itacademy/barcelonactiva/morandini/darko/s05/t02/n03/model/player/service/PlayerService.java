package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.player.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.databaseProperties.DataBaseProperties.DatabaseProperties;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.player.Player;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.playerMongo.service.PlayerMongoService;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.playerSql.service.PlayerSqlService;

import java.util.List;
import java.util.Optional;

@Profile({"sql", "mongo"}) 
@Service
public class PlayerService {

    @Autowired
    private PlayerSqlService sqlPlayerService; 
    @Autowired
    private PlayerMongoService mongoPlayerService; 
    @Autowired
    private DatabaseProperties databaseProperties;
    
    public Player createPlayer(Player player) {
        if ("sql".equals(databaseProperties.activeDatabaseProfile())) {
            return sqlPlayerService.createPlayer(player);
        } else if ("mongo".equals(databaseProperties.activeDatabaseProfile())) {
            return mongoPlayerService.createPlayer(player);
        } else {
            throw new UnsupportedOperationException("Base de datos no compatible");
        }
    }

    public Player updatePlayer(int id, Player updatedPlayer) {
        if ("sql".equals(databaseProperties.activeDatabaseProfile())) {
            return sqlPlayerService.updatePlayer(id, updatedPlayer);
        } else if ("mongo".equals(databaseProperties.activeDatabaseProfile())) {
            return mongoPlayerService.updatePlayer(id, updatedPlayer);
        } else {
            throw new UnsupportedOperationException("Base de datos no compatible");
        }
    }

    public Optional<Player> getPlayerById(int id) {
        if ("sql".equals(databaseProperties.activeDatabaseProfile())) {
            return sqlPlayerService.getPlayerById(id);
        } else if ("mongo".equals(databaseProperties.activeDatabaseProfile())) {
            return mongoPlayerService.getPlayerById(id);
        } else {
            throw new UnsupportedOperationException("Base de datos no compatible");
        }
    }

    public List<Player> getAllPlayers() {
        if ("sql".equals(databaseProperties.activeDatabaseProfile())) {
            return sqlPlayerService.getAllPlayers();
        } else if ("mongo".equals(databaseProperties.activeDatabaseProfile())) {
            return mongoPlayerService.getAllPlayers();
        } else {
            throw new UnsupportedOperationException("Base de datos no compatible");
        }
    }

    public void deletePlayer(int id) {
        if ("sql".equals(databaseProperties.activeDatabaseProfile())) {
            sqlPlayerService.deletePlayer(id);
        } else if ("mongo".equals(databaseProperties.activeDatabaseProfile())) {
            mongoPlayerService.deletePlayer(id);
        } else {
            throw new UnsupportedOperationException("Base de datos no compatible");
        }
    }

    public double getPlayerSuccessPercentage(int id) {
        if ("sql".equals(databaseProperties.activeDatabaseProfile())) {
            return sqlPlayerService.getPlayerSuccessPercentage(id);
        } else if ("mongo".equals(databaseProperties.activeDatabaseProfile())) {
            return mongoPlayerService.getPlayerSuccessPercentage(id);
        } else {
            throw new UnsupportedOperationException("Base de datos no compatible");
        }
    }
}

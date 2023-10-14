package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.gameMongo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.game.Game;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.player.Player;

public interface IgameMongoRepository extends MongoRepository<Game, Integer> {
	List<Game> findByPlayerId(int playerId);
    void deleteByPlayer(Player player);
}

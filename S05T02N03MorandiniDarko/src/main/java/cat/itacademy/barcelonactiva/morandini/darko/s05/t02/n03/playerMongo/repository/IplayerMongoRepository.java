package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.playerMongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.player.Player;

public interface IplayerMongoRepository extends MongoRepository<Player, Integer> {
	 Player findByName(String name);
	 
}

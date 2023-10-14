package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.gameSql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.game.Game;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.player.Player;

public interface IgameSqlRepository extends JpaRepository<Game, Integer> {
    List<Game> findByPlayerId(int playerId);
    void deleteByPlayer(Player player);

}

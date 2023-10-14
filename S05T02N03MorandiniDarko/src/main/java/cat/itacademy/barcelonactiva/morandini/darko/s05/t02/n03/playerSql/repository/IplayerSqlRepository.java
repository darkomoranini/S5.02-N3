package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.playerSql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.player.Player;

public interface IplayerSqlRepository extends JpaRepository<Player, Integer> {
    Player findByName(String name);
}

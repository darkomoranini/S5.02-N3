package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.game.controller;


import java.sql.Timestamp;
import java.time.LocalDateTime;


import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.gameMongo.repository.IgameMongoRepository;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.gameSql.repository.IgameSqlRepository;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.game.Game;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.player.Player;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.playerMongo.repository.IplayerMongoRepository;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.playerSql.repository.IplayerSqlRepository;

@Controller
@RequestMapping("/games")
@Profile({"sql", "mongo"}) 
public class GameController {

    @Autowired
    private IplayerSqlRepository playerSqlRepository;

    @Autowired
    private IgameSqlRepository gameSqlRepository;

    @Autowired
    private IplayerMongoRepository playerMongoRepository;

    @Autowired
    private IgameMongoRepository gameMongoRepository;

    @Value("${spring.profiles.active}")
    @Autowired
    private String activeDatabaseProfile;

    @PostMapping("/{playerId}")
    public String playGame(@PathVariable int playerId, Model model) {
        Player player;
        Game game;
        boolean won;

        if ("sql".equals(activeDatabaseProfile)) {
            player = playerSqlRepository.findById(playerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
            int roll1 = (int) (Math.random() * 6) + 1;
            int roll2 = (int) (Math.random() * 6) + 1;
            won = (roll1 + roll2) == 7;

            game = new Game();
            game.setRoll1(roll1);
            game.setRoll2(roll2);
        } else if ("mongo".equals(activeDatabaseProfile)) {
            player = playerMongoRepository.findById(playerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
            won = Math.random() < 0.5;

            game = new Game();
        } else {
            throw new UnsupportedOperationException("Base de datos no compatible");
        }

        game.setWon(won);
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentDateTime);
        game.setTimestamp(timestamp);
        game.setPlayer(player);
        
        if ("sql".equals(activeDatabaseProfile)) {
            gameSqlRepository.save(game);
        } else if ("mongo".equals(activeDatabaseProfile)) {
            gameMongoRepository.save(game);
        }

        model.addAttribute("player", player);
        model.addAttribute("game", game);

        return "game-result";
    }

    @DeleteMapping("/{playerId}")
    public String deletePlayerGames(@PathVariable int playerId) {
        Player player;

        if ("sql".equals(activeDatabaseProfile)) {
            player = playerSqlRepository.findById(playerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
            gameSqlRepository.deleteByPlayer(player);
        } else if ("mongo".equals(activeDatabaseProfile)) {
            player = playerMongoRepository.findById(playerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
            gameMongoRepository.deleteByPlayer(player);
        } else {
            throw new UnsupportedOperationException("Base de datos no compatible");
        }

        return "redirect:/players/" + player.getId() + "/games";
    }
}


package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.gameMongo.controller;

import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.gameMongo.repository.IgameMongoRepository;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.playerMongo.repository.IplayerMongoRepository;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.player.Player;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.game.Game;

@Controller
@RequestMapping("/games")
public class GameMongoController {

    @Autowired
    private IgameMongoRepository gameMongoRepository;
    @Autowired
    private IplayerMongoRepository playerMongoRepository;

    @PostMapping("/{playerId}")
    public String playGame(@PathVariable int playerId, Model model) {
        Player player = playerMongoRepository.findById(playerId)
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
        gameMongoRepository.save(game);
        model.addAttribute("player", player);
        model.addAttribute("game", game);
        
        return "game-result";
    }

    @DeleteMapping("/{playerId}")
    public String deletePlayerGames(@PathVariable int playerId) {
        Player player = playerMongoRepository.findById(playerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
        gameMongoRepository.deleteByPlayer(player);
        
        return "redirect:/players/" + player.getId() + "/games";
    }
}


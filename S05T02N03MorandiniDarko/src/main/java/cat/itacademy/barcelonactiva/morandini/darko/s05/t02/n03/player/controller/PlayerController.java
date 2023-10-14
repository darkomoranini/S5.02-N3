package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.player.controller;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.databaseProperties.DataBaseProperties.DatabaseProperties;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.gameMongo.repository.IgameMongoRepository;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.gameSql.repository.IgameSqlRepository;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.game.Game;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.model.domain.player.Player;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.playerMongo.repository.IplayerMongoRepository;
import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.playerSql.repository.IplayerSqlRepository;

@Controller
@RequestMapping("/players")
@Profile({"sql", "mongo"}) 
public class PlayerController {

    @Autowired
    private IplayerSqlRepository playerSqlRepository;
    @Autowired
    private IplayerMongoRepository playerMongoRepository;
    @Autowired
    private IgameSqlRepository gameSqlRepository;
    @Autowired
    private IgameMongoRepository gameMongoRepository;
    
    @Value("${spring.profiles.active}")
    @Autowired
    private DatabaseProperties databaseProperties;
    

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("player", new Player());
        return "create-player";
    }

    @PostMapping("/create")
    public String createPlayer(
            @Validated @ModelAttribute("player") Player player,
            RedirectAttributes redirectAttributes
    ) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Date currentDate = Date.valueOf(currentDateTime.toLocalDate());
        player.setRegistrationDate(currentDate);

        if ("sql".equals(databaseProperties.activeDatabaseProfile())) {
            playerSqlRepository.save(player);
        } else if ("mongo".equals(databaseProperties.activeDatabaseProfile())) {
            playerMongoRepository.save(player);
        } else {
            throw new UnsupportedOperationException("Base de datos no compatible");
        }

        redirectAttributes.addFlashAttribute("Jugador creado exitosamente");
        return "redirect:/players/" + player.getId();
    }

    @GetMapping("/{id}")
    public String getPlayerDetails(@PathVariable int id, Model model) {
        Player player;
        if ("sql".equals(databaseProperties.activeDatabaseProfile())) {
            player = playerSqlRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
        } else if ("mongo".equals(databaseProperties.activeDatabaseProfile())) {
            player = playerMongoRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
        } else {
            throw new UnsupportedOperationException("Base de datos no compatible");
        }

        model.addAttribute("player", player);
        return "player-details";
    }

    @GetMapping("/{id}/games")
    public String getPlayerGames(@PathVariable int id, Model model) {
        Player player;
        if ("sql".equals(databaseProperties.activeDatabaseProfile())) {
            player = playerSqlRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
        } else if ("mongo".equals(databaseProperties.activeDatabaseProfile())) {
            player = playerMongoRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
        } else {
            throw new UnsupportedOperationException("Base de datos no compatible");
        }

        List<Game> games;
        if ("sql".equals(databaseProperties.activeDatabaseProfile())) {
            games = gameSqlRepository.findByPlayerId(id);
        } else if ("mongo".equals(databaseProperties.activeDatabaseProfile())) {
            games = gameMongoRepository.findByPlayerId(id);
        } else {
            throw new UnsupportedOperationException("Base de datos no compatible");
        }

        model.addAttribute("player", player);
        model.addAttribute("games", games);
        return "game-result";
    }

   
}
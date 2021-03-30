package com.example.cricket;

import com.example.cricket.game.Game;
import com.example.cricket.game.Runs;
import com.example.cricket.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.example.cricket.DBConnector.openConnection;
import static com.example.cricket.Stat.*;
import static com.example.cricket.game.GameHelper.gameString;
import com.example.cricket.GameService;
@RestController
@Validated
public class MainController {

    private GameService gameService = new GameService();

    // this will create game
    @RequestMapping(method = RequestMethod.POST,  path="/createGame")
    public @ResponseBody int createGame(@RequestBody RawGame rawGame){
        Game game = new Game(rawGame.getOvers(), rawGame.getLocation(), rawGame.getDate());//
        game.build(rawGame.getTeamOneId(), rawGame.getTeamTwoId(), rawGame.getTeamOnePlayers(),
                rawGame.getTeamTwoPlayers());
        game.buildStart();
        return game.getGameId();
    }

    @GetMapping(path = "/playerStat/{playerId}")
    public @ResponseBody
    PlayerStat playerStat(@PathVariable int playerId){
        return getPlayerStat(playerId);
    }

    @RequestMapping(path = "/teamStat/{teamId}")
    public @ResponseBody
    TeamStat teamStat(@PathVariable int teamId){
        return getTeamStat(teamId);
    }

    @RequestMapping(path = "/game/team/{gameId}/{teamId}")
    public @ResponseBody
    TeamGameStat teamGameStat(@PathVariable("gameId") int gameId, @PathVariable("teamId") int teamId){
        return getTeamGameStat( gameId, teamId);
    }

    @RequestMapping(path = "/game/playerStat/{gameId}/{playerId}")
    public @ResponseBody
    GamePlayerStat gamePlayerStat(@PathVariable("gameId") int gameId, @PathVariable("playerId") int playerId){
        return getGamePlayerStat(playerId, gameId);
    }

    @RequestMapping(path = "/game/teamStat/{gameId}/{teamId}")
    public @ResponseBody
    GameTeamStat gameTeamStat(@PathVariable("gameId") int gameId, @PathVariable("teamId") int teamId){
        return getGameTeamStat(teamId, gameId);
    }

    @RequestMapping(path = "game/stat/{gameId}")
    public @ResponseBody
    GameStat gameStat(@PathVariable int gameId){
        return getGameStat(gameId);
    }

    @RequestMapping(path="/test/{run}")
    public @ResponseBody String test(@PathVariable Runs run){
         //return "Tested";
       return  gameService.show() + run;
    }


    // TODO
    // this will create new game.
//    @RequestMapping(method = RequestMethod.POST, path="/game/create")
//    public @ResponseBody ScoreBoard create(@RequestBody RawGame game){
//        return gameService.create(game);
//    }

//     this will throw nextBall
//    @RequestMapping(path = "/game/nextBall")
//    public @ResponseBody ScoreBoard nextBall(@PathVariable int gameId, Runs run ){
//        return gameService.nextBall(int gameId, Runs run);
//    }

}



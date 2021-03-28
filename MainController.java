package com.example.cricket;

import com.example.cricket.game.Game;
import com.example.cricket.model.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.example.cricket.DBConnector.openConnection;
import static com.example.cricket.Stat.*;
import static com.example.cricket.game.GameHelper.gameString;

@RestController
@Validated
public class MainController {

    @RequestMapping(path = "/playerStat")
    public @ResponseBody
    PlayerStat playerStat(@RequestParam int playerId){
        return getPlayerStat(playerId);
    }

    @RequestMapping(path = "/teamStat")
    public @ResponseBody
    TeamStat teamStat(@RequestParam int teamId){
        return getTeamStat(teamId);
    }

    @RequestMapping(path = "/game/playerStat")
    public @ResponseBody
    GamePlayerStat gamePlayerStat(@RequestParam int playerId, int gameId){
        return getGamePlayerStat(playerId, gameId);
    }

    @RequestMapping(path = "/game/teamStat")
    public @ResponseBody
    GameTeamStat gameTeamStat(@RequestParam int teamId, int gameId){
        return getGameTeamStat(teamId, gameId);
    }

    @RequestMapping(path = "game/stat")
    public @ResponseBody
    GameStat gameStat(@RequestParam int gameId){
        return getGameStat(gameId);
    }

    @RequestMapping(path="/test")
    public @ResponseBody String test(){
        return "Tested";
    }
    // this will create game
    @RequestMapping(path="/createGame")
    public @ResponseBody String createGame(@RequestParam int overs, String location, String date, int teamOneId,
                                           int teamTwoId, int teamOnePlayers[], int teamTwoPlayers[]){
        Game game = new Game(overs, location, date);
       // game.build(teamOneId, teamTwoId, teamOnePlayers, teamTwoPlayers);
        //game.buildStart();
          return "Game created: " + gameString(overs, location, date, teamOneId, teamTwoId, teamOnePlayers, teamTwoPlayers);
    }
}



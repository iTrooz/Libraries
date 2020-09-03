package fr.entasia.apis.other;

import fr.entasia.errors.LibraryException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Arrays;

public abstract class ScoreBoard {

    public final Player p;
    public Scoreboard scoreboard;
    public Objective objective;

    public String[] cache = new String[15];

    public ScoreBoard(String name, Player p){
        this.p = p;
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective(name, "dummy");
        Arrays.fill(cache, "");
    }

    public void softSet(){
        if(p.getScoreboard()!=scoreboard)refresh();
    }

    public void clear(){
        scoreboard.getEntries().forEach(a -> scoreboard.resetScores(a));
    }

    public void refresh(){
        p.setScoreboard(scoreboard);
        clear();
        objective.getScore("§bplay.enta§7sia.fr").setScore(0);
    }

    public void refreshLine(int number, String text){
        if(number>=cache.length)throw new LibraryException("Too high line number. Maximum : "+cache.length);
        scoreboard.resetScores(cache[number]);
        cache[number] = text;
        objective.getScore(text).setScore(number);
    }

}

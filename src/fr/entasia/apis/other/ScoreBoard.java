package fr.entasia.apis.other;

import fr.entasia.errors.LibraryException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Arrays;

// en cours de test :)
public abstract class ScoreBoard {

	public final Player p;
	public Scoreboard scoreboard;
	public Objective objective;

	public String[] cache = new String[15];

	public ScoreBoard(Player p, String id, String name){
		this.p = p;
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		objective = scoreboard.registerNewObjective(id, "dummy");
		objective.setDisplayName(name);
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		Arrays.fill(cache, "");
	}

	public void set(){
		if(p.getScoreboard()!=scoreboard)forceSet();
	}

	public void clear(){
		scoreboard.getEntries().forEach(a -> scoreboard.resetScores(a));
	}

	public void forceSet(){
		p.setScoreboard(scoreboard);
		clear();
		objective.getScore("§bplay.enta§7sia.fr").setScore(0);
	}

	private int check(int number){
		number = number-10;
		if(number<0||number>=cache.length)throw new LibraryException("Invalid line number. Accepted 10-"+(cache.length+10));
		return number;
	}

	public void changeLine(int number, String text){
		int n2 = check(number);
		scoreboard.resetScores(cache[n2]);
		cache[n2] = text;
		objective.getScore(text).setScore(number);
	}

	public void staticLine(int number, String text){
		check(number);
		objective.getScore(text).setScore(number);
	}

}

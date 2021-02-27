package fr.entasia.apis.other;

import fr.entasia.errors.LibraryException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;

// en cours de test :)
public abstract class ScoreBoardHelper {

	public Player p;
	public Scoreboard scoreboard;
	public Objective objective;

	public HashMap<Integer, String> cache = new HashMap<>(); // can't do an Array ):

	public ScoreBoardHelper(Player p, String id, String name){
		this.p = p;
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		objective = scoreboard.registerNewObjective(id, "dummy", name);
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	public boolean isActive(){
		return p.getScoreboard()==scoreboard;
	}

	public void clear(){
		scoreboard.getEntries().forEach(a -> scoreboard.resetScores(a));
	}

	public void reload(){
		p.setScoreboard(scoreboard);
		clear();
		objective.getScore("§bplay.enta§7sia.fr").setScore(10);
		setSlots();
	}

	protected abstract void setSlots();

	private int check(int number){
		if(number<0||number>88)throw new LibraryException("Invalid line number. Accepted 0-88"); // 11-99
		return number+11;
	}

	public void dynamicLine(int number, String text){
		number = check(number);
		String s = cache.get(number);
		if(s!=null) scoreboard.resetScores(cache.get(number));

		cache.put(number, text);
		objective.getScore(text).setScore(number);
	}

	public void staticLine(int number, String text){
		number = check(number);
		objective.getScore(text).setScore(number);
	}

	public void delete(){
		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		p = null;
		scoreboard = null;
		objective = null;
	}
}

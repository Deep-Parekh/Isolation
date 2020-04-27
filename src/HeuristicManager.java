import java.util.HashMap;

/**
 * 
 */

/**
 * @author dparekh
 *
 * This class is instantiated in the IsolationGame class and helps the Game playing agent 
 * select a different heuristic based on the state of the game
 */
public class HeuristicManager {
	
	private static String OFFENSIVE = "Offensive";
	private static String DEFENSIVE = "Defensive";
	
	private String strategy;
	private HashMap<String, Heuristic> heuristics;
	
	public HeuristicManager(String strategy) {
		this.strategy = strategy.trim();
		this.heuristics = new HashMap<String,Heuristic>(2,1);
		this.heuristics.put(OFFENSIVE, new Offensive());
		this.heuristics.put(DEFENSIVE, new Defensive());
	}
	
	public Heuristic getHeuristic() {
		return this.heuristics.getOrDefault(strategy, new Offensive());
	}
	
	public Heuristic getHeuristic(int totalMoves) {
		if(strategy.equals(OFFENSIVE) || strategy.equals(DEFENSIVE))
			return this.heuristics.get(strategy);
		if(totalMoves > 32) {
			if(this.strategy.startsWith(OFFENSIVE)) 
				return this.heuristics.get(DEFENSIVE);
			else
				return this.heuristics.get(OFFENSIVE);
		}else {
			if(this.strategy.startsWith(OFFENSIVE)) 
				return this.heuristics.get(OFFENSIVE);
			else
				return this.heuristics.get(DEFENSIVE);	
		}
	}
}

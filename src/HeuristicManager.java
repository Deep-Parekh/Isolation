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
	
	static String OFFENSIVE = "Offensive";
	static String DEFENSIVE = "Defensive";
	static String BLOCKING = "BlockingTheOpponent";
	
	private String strategy;
	private HashMap<String, Heuristic> heuristics;
	
	public HeuristicManager(String strategy) {
		this.strategy = strategy.trim();
		this.heuristics = new HashMap<String,Heuristic>(2,1);
		this.heuristics.put(OFFENSIVE, new Offensive());
		this.heuristics.put(DEFENSIVE, new Defensive());
		this.heuristics.put(BLOCKING, new BlockingTheOpponent());
	}
	
	public Heuristic getHeuristic() {
		return this.heuristics.getOrDefault(strategy, new Offensive());
	}
	
	public Heuristic getHeuristic(int totalMoves) {
		if(strategy.equals(OFFENSIVE) || strategy.equals(DEFENSIVE) || strategy.equals(BLOCKING))
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

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
	
	private String strategy;
	private HashMap<String, Heuristic> heuristics;
	
	public HeuristicManager(String strategy) {
		this.strategy = strategy.trim();
		this.heuristics.put("Offensive", new Offensive());
		this.heuristics.put("Defensive", new Defensive());
	}
	
	public Heuristic getHeuristic() {
		return this.heuristics.getOrDefault(strategy, new Offensive());
	}
	
	public Heuristic getHeuristic(int totalMoves) {
		if(totalMoves > 32) {
			if(this.strategy.startsWith("Offensive")) 
				return this.heuristics.get("Defensive");
			else
				return this.heuristics.get("Offensive");
		}else {
			if(this.strategy.startsWith("Offensive")) 
				return this.heuristics.get("Offensive");
			else
				return this.heuristics.get("Defensive");	
		}
	}
}

package tatai.statistics;

public class Leader {
	
	private Integer _score;
	private String _name;
	private Integer _rank;

	public Leader(String name, Integer score) {
		
		String withoutSpaces = name.replace(" ", "");
		
		if (!withoutSpaces.isEmpty()) {
			_name = name;
		} else {
			_name = "Anonymous";
		}
		_score = score;
	}
	
	public String getName() {
		return _name;
	}
	
	public Integer getScore() {
		return _score;
	}
	
	public void setRank(Integer rank) {
		_rank = rank;
	}
	
	public Integer getRank() {
		return _rank;
	}
	
}

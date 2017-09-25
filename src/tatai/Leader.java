package tatai;

public class Leader {
	
	private Integer _score;
	private String _name;
	private Integer _rank;

	Leader(String name, Integer score) {
		_name = name;
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

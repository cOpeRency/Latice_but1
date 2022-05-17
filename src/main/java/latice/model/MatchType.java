package latice.model;

public enum MatchType {
	SIMPLE(0),DOUBLE(1),TREFOIL(2),LATICE(4);
	
	Integer value;
	
	private MatchType(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return value;
	}
}

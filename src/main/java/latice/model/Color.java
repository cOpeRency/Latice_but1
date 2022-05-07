package latice.model;

public enum Color {
	COLOR1("g"),COLOR2("m"),COLOR3("n"),COLOR4("r"),COLOR5("t"),COLOR6("y");
	
	private String code;

	private Color(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}
}

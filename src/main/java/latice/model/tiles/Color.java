package latice.model.tiles;

public enum Color {
	GREEN("g"),MAGENTA("m"),NIGHTBLUE("n"),RED("r"),TURQUOISE("t"),YELLOW("y");
	
	private String code;

	private Color(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}
}

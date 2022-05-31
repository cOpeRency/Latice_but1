package latice.vue;

public enum GameTheme {
	POKEMON("pokemon"),ONE_PIECE("one_piece"),ZELDA("zelda");
	
	private String code;

	private GameTheme(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}
}

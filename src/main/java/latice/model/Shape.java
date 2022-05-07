package latice.model;

public enum Shape {
	// Abréviation des noms des séries
	SHAPE1("bird"),SHAPE2("dolphin"),SHAPE3("feather"),SHAPE4("flower"),SHAPE5("gecko"),SHAPE6("turtle");
	

	
	private String code;

	private Shape(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}
}

package latice.model.tiles;

public enum Shape {
	SHAPE1("shp1"),SHAPE2("shp2"),SHAPE3("shp3"),SHAPE4("shp4"),SHAPE5("shp5"),SHAPE6("shp6");
	

	
	private String code;

	private Shape(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}
}

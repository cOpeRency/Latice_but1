package latice.model;

import java.io.Serializable;

public final class Position implements Serializable {
	private final Integer row;
	private final Integer column;

	
	
	public Position(Integer row, Integer column) {
		this.row = row;
		this.column = column;
	}

	public Integer row() {
		return row;
	}

	public Integer column() {
		return column;
	}

	@Override
	public String toString() {
		return row.toString() + "," + column.toString();
	}

}

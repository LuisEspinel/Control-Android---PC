package logico;

import java.io.Serializable;

public class Mensaje implements Serializable{
	private static final long serialVersionUID = 9898989342412023L;
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}

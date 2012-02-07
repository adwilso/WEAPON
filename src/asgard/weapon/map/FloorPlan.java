package asgard.weapon.map;


public class FloorPlan  {
	private String name;
	private int image;
	
	public FloorPlan(String name) {
		this.name = name;
	}
	public FloorPlan(String name, int image) {
		this.name = name;
		this.image = image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public int getImage() {
		return image;
	}
	public String getName() {
		return name;
	}

	
	

}

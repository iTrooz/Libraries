package fr.entasia.apis;

public class BasicLocation {
	public int x;
	public int y;
	public int z;

	public BasicLocation(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public int getZ(){
		return z;
	}
}

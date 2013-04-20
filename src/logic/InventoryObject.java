package logic;

public class InventoryObject extends WorldObject {

	private int count;

	public InventoryObject(String objectName) {
		super(objectName);
		count = 1;
	}

	public void increment(){
		count++;
	}

	public void decrease(){
		count--;
	}

	public int getCount() {
		return count;
	}
}

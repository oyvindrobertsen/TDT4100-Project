package logic;

import java.util.ArrayList;

public class Inventory {
	private ArrayList<InventoryObject> items = new ArrayList<InventoryObject>();
	
	public Inventory() {	
	}
	
	public void addItem(InventoryObject i) {
		items.add(i);
	}
	
	public String toString() {
		String out = "";
		for (InventoryObject i : items) {
			out += i;
		}
		return out;
	}
}

package logic;

import java.util.ArrayList;

import org.lwjgl.Sys;

public class Inventory {
	private ArrayList<InventoryObject> items = new ArrayList<InventoryObject>();
	
	public Inventory() {
	}
	
	public void addItem(InventoryObject arg) {
		for ( InventoryObject itm : items){
			if ( itm.toString().equals( arg.toString() ) ){
				int i = items.indexOf( itm );
				itm = items.get(i);
				itm.increment();
				items.set(i, itm);
				return;
			}
		}
		items.add(arg);
	}
	
	public String toString() {
		String out = "Inventory:\t";
		for (InventoryObject i : items) {
			out += i + " (" + i.getCount() + ")\t";
		}
		return out;
	}
}

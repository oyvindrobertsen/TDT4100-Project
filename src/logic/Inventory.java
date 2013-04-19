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
		sort();
	}
	
	public String toString() {
		String out = "Inventory:\t";
		for (InventoryObject i : items) {
			out += i + " (" + i.getCount() + ")\t";
		}
		return out;
	}
	
	private void sort(){
		for (int i = 0; i < items.size() - 1; i++) {
			boolean sorted = true;

			for (int j = 1; j < items.size() - i; j++) {
				if ( items.get(j).toString().compareTo( items.get(j-1).toString() ) < 0 ) {
					InventoryObject temp = items.get(j);
					items.set(j, items.get(j-1));
					items.set(j-1, temp);

					sorted = false;
				}
			}

			if (sorted) break;
		}
	}
}

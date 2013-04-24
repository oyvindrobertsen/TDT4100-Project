package logic;

import java.util.ArrayList;

public class Inventory {
	private ArrayList<InventoryObject> items = new ArrayList<InventoryObject>();

	public Inventory() {}

	public void addItem( InventoryObject item ) {
		for ( InventoryObject o : items){
			if ( o.toString().equals( item.toString() ) ){
				o.increment();
				return;
			}
		}
		items.add(item);
		sort();
	}

	private void sort(){
		for ( int i = 0; i < items.size() - 1; i++ ) {
			boolean sorted = true;

			for ( int j = 1; j < items.size() - i; j++ ) {
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

	public void clear() {
		items.clear();
	}

	public int contains(InventoryObject io) {
		for (InventoryObject i : items) {
			if (i.toString().equals(io.toString())) return i.getCount();
		}
		return 0;
	}
	public String toString() {
		String out = "Inventory:\t";
		for ( InventoryObject o : items ) {
			out += o + " (" + o.getCount() + ")\t";
		}
		return out;
	}

}

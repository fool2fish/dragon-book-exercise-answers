package model.support;


/**
 *
 * Item of LR(1)
 *
 * @author Hiki
 * @create 2017-11-14 0:30
 */
public class Item {

	/**
	 * Id of the production in CFG.
	 */
	private int id;
	/**
	 * Index of the current position [dot].
	 */
	private int ptr;

	public Item(int id, int ptr) {
		this.id = id;
		this.ptr = ptr;
	}

	public static Item createDefaultItem(int id){
		return new Item(id, 0);
	}

	@Override
	public boolean equals(Object object){
		if (object instanceof Item){
			Item other = (Item) object;
			if (this.id == other.id && this.ptr == other.ptr){
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString(){
		return "[" + id + ", " + ptr + "]";
	}

	public int getId() {
		return id;
	}

	public int getPtr() {
		return ptr;
	}


}


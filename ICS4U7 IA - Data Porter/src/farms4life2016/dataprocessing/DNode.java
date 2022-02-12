package farms4life2016.dataprocessing;

public class DNode { 

	private DNode next, prev;
	private Object data;
	
	public DNode(Object data, DNode next, DNode prev) {
		this.data = data;
		this.next = next;
		this.prev = prev;
	}
	
	public DNode(Object data) {
		this(data, null, null);
	}
	
	public Object getData() {
		return data;
	}
	
	public DNode getNext() {
		return next;
	}
	
	public DNode getPrev() {
		return prev;
	}
	
	public void setItem(Object newItem) {
		data = newItem;
	}
	
	public void setNext(DNode newNext) {
		next = newNext;
	}
	
	public void setPrev(DNode newPrev) {
		prev = newPrev;
	}

	@Override
	public String toString() {
		if (data != null) return data.toString();
		else return null;
	}
	
}

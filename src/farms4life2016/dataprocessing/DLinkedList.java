package farms4life2016.dataprocessing;

/**
 * Doubly linked list: a linked list that allows faster
 * backwards travel at the cost of extra memory
 */
public class DLinkedList {

	private DNode dummyHead;
	private DNode dummyTail;
	private int size;
	
	/**
	 * Creates a new Doubly linked list
	 */
	public DLinkedList() {
		dummyHead = new DNode(null, null, null);
		dummyTail = new DNode(null, null, null);
		dummyHead.setNext(dummyTail);
		dummyTail.setPrev(dummyHead);
		size = 0;
	}
	
	/**
	 * Returns the length of this list.
	 * @return How many elements are in this list.
	 */
	public int length() {
		return size;
	}
	
	/**
	 * Returns the element at a specified index.
	 * @param index The specified index
	 * @return The data in the node at that index
	 */
	public Object get(int index) {
		return getNode(index).getData();		
	}
	
	/**
	 * Returns the DNode object at the specified index. Used mostly for internal processing, and sometimes 
	 * used in external processing for efficiency purposes.
	 * @param index The specified index.
	 * @return The DNode at that index.
	 */
	public DNode getNode(int index) {
		
		//check for valid input
		checkIndex(index);
		
		//optimization
		int half = size/2;
		DNode n = null;
		
		//to be efficient, we search from tail if index is in the back half of the list
		if (index > half) {
			n = dummyTail.getPrev();
			for (int i = size-1; i > index; i--) {
				n = n.getPrev(); //loop through all nodes unti we get the one we want
			}
					
		//and from head if index is in the front half of the list
		} else {
			n = dummyHead.getNext();
			for (int i = 0; i < index; i++) {
				n = n.getNext();
			}
		}
		
		return n;
		
	}
	
	/**
	 * Adds an object to the end of this Doubly Linked List
	 * @param data The object that gets added to this list
	 */
	public void add(Object data) {
		
		//make a new node to represent data
		DNode n = new DNode(data);
		DNode secondLastElement = dummyTail.getPrev();
		
		//update new node's pointers
		n.setNext(dummyTail);
		n.setPrev(secondLastElement);
		
		//update old nodes to point to new node
		secondLastElement.setNext(n);
		dummyTail.setPrev(n);
		
		//update size
		size++;
		
	}
	
	
	/**
	 * Adds a new object to the list such that the new object occupies
	 * the position at the specified index. Any elements that were at or to the right of 
	 * this index will have their index shifted rightwards by 1. 
	 * @param data The object to be inserted into the list.
	 * @param index The index where the object should be inserted.
	 */
	public void insert(Object data, int index) {

		//for inserting at the end of the list
		if (index == size) {
			add(data);
			return;
		}
		
		DNode currentlyAtIndex = getNode(index);
		DNode n = new DNode(data);
		
		//update new node
		n.setNext(currentlyAtIndex);
		n.setPrev(currentlyAtIndex.getPrev());
		
		//update old nodes
		currentlyAtIndex.getPrev().setNext(n);
		currentlyAtIndex.setPrev(n);
		
		//update size
		size++;
				
	}
	
	/**
	 * Removes the node at the specified index. All elements to the right of this removed element
	 * have their index shifted left by 1.
	 * @param index The index of the node to be removed.
	 * @return The object inside of the removed node.
	 */
	public Object remove(int index) {
		DNode goodbye = getNode(index);
		
		//unlinked other nodes from current node
		goodbye.getPrev().setNext(goodbye.getNext());
		goodbye.getNext().setPrev(goodbye.getPrev());
		
		//unlink this node from other nodes
		goodbye.setNext(null);
		goodbye.setPrev(null);
		
		//update size
		size--;
		
		//return this node's data
		return goodbye.getData();
		
	}

	/**
	 * Replaces the element at the specified index with a new object. 
	 * @param data The new element that is replacing the old one
	 * @param index The index where this replacement should take place
	 * @return The data in the node that is being replaced.
	 */
	public Object replace(Object data, int index) {
		DNode n = getNode(index);
		Object o = n.getData();
		n.setItem(data);
		return o;
	}
	
	/**
	 * Checks whether an element exists at the specified index. Used internally
	 * to determine whether the program should throw an exception due to bad input.
	 * @param index The index to be checked.
	 */
	private void checkIndex(int index) {
		
		if (index < 0) {
			throw new ArrayIndexOutOfBoundsException("Index cannot be less than 0. You entered: " + index);
		} else if (index >= size) {
			throw new ArrayIndexOutOfBoundsException("Index cannot be greater than or equal to the list's length. Remember that lists are zero-indexed!"
					+ "\nList length: " + size + ". You entered: " + index);
		}
	}

	@Override
	public String toString() {
		String output = "[";
		DNode n = dummyHead.getNext();
		
		for (int i = 0; i < this.size; i++) {
			System.out.println(n.getData());
			output = output + n.getData().toString();
			n = n.getNext();
			if (i != this.size - 1) output += "\n";
		}
		return "";
	}
	
}

package textgen;

import java.util.AbstractList;

/**
 * A class that implements a doubly linked list
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	private int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		size = 0;
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * 
	 * @param element The element to add
	 */
	public boolean add(E element) {
		checkNullSituation(element);
		LLNode<E> newElement = new LLNode<E>(element, tail.prev);
		size += 1;
		return (newElement != null);
	}

	/**
	 * Get the element at position index
	 * 
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E get(int index) {
		checkElementIndex(index);
		LLNode<E> dummy = new LLNode<E>(null);
		dummy = this.head;
		for (int i = 0; i <= index; i++) {
			dummy = dummy.next;
		}
		return dummy.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * 
	 * @param The     index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element) {
		checkPositionIndex(index);
		checkNullSituation(element);
		LLNode<E> dummy = new LLNode<E>(null);
		dummy = this.head;
		for (int i = 0; i <= index; i++) {
			dummy = dummy.next;
		}
		new LLNode<E>(element, dummy.prev);
		size += 1;
	}

	/** Return the size of the list */
	public int size() {
		return size;
	}

	/**
	 * Remove a node at the specified index and return its data element.
	 * 
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) {
		checkElementIndex(index);
		LLNode<E> removedNode = new LLNode<E>(null);
		removedNode = this.head;
		for (int i = 0; i <= index; i++) {
			removedNode = removedNode.next;
		}
		removedNode.next.prev = removedNode.prev;
		removedNode.prev.next = removedNode.next;
		size -= 1;
		return removedNode.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * 
	 * @param index   The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) {
		checkElementIndex(index);
		checkNullSituation(element);
		add(index, element);
		E oldElement = remove(index + 1);
		return oldElement;
	}

	private boolean isPositionIndex(int index) {
		return index >= 0 && index <= size;
	}

	private boolean isElementIndex(int index) {
		return index >= 0 && index < size;
	}

	private void checkElementIndex(int index) {
		if (!isElementIndex(index)) {
			throw new IndexOutOfBoundsException("Wrong Index bro");
		}
	}

	private void checkPositionIndex(int index) {
		if (!isPositionIndex(index))
			throw new IndexOutOfBoundsException("Wrong Index bro");
	}

	private void checkNullSituation(E element) {
		if (element == null) {
			throw new NullPointerException();
		}
	}

	@Override
	public String toString() {
		String message = "ListNode ";
		if (size() == 0) {
			message += "has 0 nodes";
		}
		for (int i = 0; i < this.size(); i++) {
			message += "at index " + i + " has data " + this.get(i) + "\t";
		}
		return message;
	}

	public static void main(String[] args) {
		MyLinkedList<Integer> test = new MyLinkedList<Integer>();
		test.add(12);
		test.add(98);
		test.add(45);
		System.out.println(test);

	}

}

class LLNode<E> {
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	public LLNode(E e) {
		this.data = e;
		this.prev = null;
		this.next = null;
	}

	public LLNode(E e, LLNode<E> prevNode) {
		this.data = e;
		this.next = prevNode.next;
		this.next.prev = this;
		prevNode.next = this;
		this.prev = prevNode;
	}

	@Override
	public String toString() {
		return "[data=" + data + "]";
	}

}

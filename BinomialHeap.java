/**
 * BinomialHeap
 *
 * An implementation of binomial heap over positive integers.
 *
 */
public class BinomialHeap
{
	public int size;
	public HeapNode last;
	public HeapNode min;

	public BinomialHeap() {
		this.size = 0;
		this.last = null;
		this.min = null;
	}

	/**
	 * 
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapItem.
	 *
	 */
	public HeapItem insert(int key, String info) {
		HeapItem item = new HeapItem(null, key, info);
		HeapNode node = new HeapNode(item, null, null, null);
		item.node = node;

		BinomialHeap heap0 = new BinomialHeap();
		heap0.last = node;
		heap0.last.next = node;
		heap0.min = node;
		heap0.size++;

		if (this.empty() || this.last.next.rank == 0) {
			this.meld(heap0);
		} else {
			HeapNode temp = this.last.next;
			this.last.next = node;
			node.next = temp;

			if (node.item.key < this.min.item.key) {
				this.min = node;
			}
			this.size++;
		}

		return item; // should be replaced by student code
	}

	/**
	 * 
	 * Delete the minimal item
	 *
	 */
	public void deleteMin()
	{
		return; // should be replaced by student code

	}

	/**
	 * 
	 * Return the minimal HeapItem, null if empty.
	 *
	 */
	public HeapItem findMin()
	{
		return this.min.item; // should be replaced by student code
	} 

	/**
	 * 
	 * pre: 0<diff<item.key
	 * 
	 * Decrease the key of item by diff and fix the heap. 
	 * 
	 */
	public void decreaseKey(HeapItem item, int diff) 
	{    
		return; // should be replaced by student code
	}

	/**
	 * 
	 * Delete the item from the heap.
	 *
	 */
	public void delete(HeapItem item) 
	{    
		return; // should be replaced by student code
	}

	/**
	 * 
	 * Meld the heap with heap2
	 *
	 */
	public void meld(BinomialHeap heap2) {
		if (!this.empty() && !heap2.empty()) {

			HeapNode node1 = this.last.next;
			HeapNode node2 = heap2.last.next;

			do {
				if (node1.rank == node2.rank) {
					HeapNode newNode = this.link(node1, node2, false);
					if (newNode.item.key == node1.item.key) {
						node1 = newNode;
						node2 = node2.next;
					} else {
						node2 = newNode;
						node1 = node1.next;
					}
				} else {
					if (node1.rank < node2.rank) {
						node1 = node1.next;
					} else {
						node2 = node2.next;
					}
				}
			} while (node1 != null && node2 != null && node1 != this.last && node2 != heap2.last);

			System.out.println(node1.item.key);
			node1 = this.last.next;

			while (node1 != null && node1 != this.last) {
				if (node1.rank == node1.next.rank) {
					node1 = this.link(node1, node1.next, true);
				} else {
					node1 = node1.next;
				}
			}

			System.out.println(node1.item.key);

			this.size += heap2.size();
		}

		if (this.empty()) {
			this.last = heap2.last;
			this.min = heap2.min;
			this.size = heap2.size();
		}
	}

	public HeapNode link(HeapNode node1, HeapNode node2, boolean isSameHeap) {
		boolean isLast = node1 == this.last || node2 == this.last;
		if (this.min.item.key > node1.item.key) {
			this.min = node1;
		}
		if (this.min.item.key > node2.item.key) {
			this.min = node2;
		}

		if (node1.item.key > node2.item.key) {
			if (isSameHeap) {
				node2.next = node1.next;
			}

			node1.parent = node2;
			node1.next = node2.child;
			node2.child = node1;
			node2.rank++;

			if (isLast) {
				this.last = node2;
			}

			return node2;
		} else {
			if (isSameHeap) {
				node1.next = node2.next;
			}

			node2.parent = node1;
			node2.next = node1.child;
			node1.child = node2;
			node1.rank++;

			if (isLast) {
				this.last = node1;
			}

			return node1;
		}
	}

	/**
	 * 
	 * Return the number of elements in the heap
	 *   
	 */
	public int size()
	{
		return this.size; // should be replaced by student code
	}

	/**
	 * 
	 * The method returns true if and only if the heap
	 * is empty.
	 *   
	 */
	public boolean empty()
	{
		return this.size == 0; // should be replaced by student code
	}

	/**
	 * 
	 * Return the number of trees in the heap.
	 * 
	 */
	public int numTrees() {
		if (this.empty()) {
			return 0;
		}

		int numTrees = 1;
		HeapNode node = this.last;
		while (node.next != this.last) {
			numTrees++;
			node = node.next;
		}
		return numTrees; // should be replaced by student code
	}

	/**
	 * Class implementing a node in a Binomial Heap.
	 *  
	 */
	public static class HeapNode{
		public HeapItem item;
		public HeapNode child;
		public HeapNode next;
		public HeapNode parent;
		public int rank;

		public HeapNode(HeapItem item, HeapNode child, HeapNode next, HeapNode parent) {
			this.item = item;
			this.child = child;
			this.next = next;
			this.parent = parent;
			this.rank = 0;
		}
	}

	/**
	 * Class implementing an item in a Binomial Heap.
	 *  
	 */
	public static class HeapItem{
		public HeapNode node;
		public int key;
		public String info;

		public HeapItem(HeapNode node, int key, String info) {
			this.node = node;
			this.key = key;
			this.info = info;
		}
	}

	//DELETE LATER
	@Override
	public String toString() {
		if (this.empty()) {
			return "Heap is empty";
		}

		StringBuilder sb = new StringBuilder();
		sb.append("BinomialHeap:\n");

		HeapNode current = this.last.next;
		do {
			printTree(current, sb, 0);
			current = current.next;
		} while (current != this.last.next);

		return sb.toString();
	}

	private void printTree(HeapNode node, StringBuilder sb, int indent) {
		for (int i = 0; i < indent; i++) {
			sb.append("  ");
		}
		sb.append(node.item.key).append(": ").append(node.item.info).append("\n");

		HeapNode child = node.child;
		while (child != null) {
			printTree(child, sb, indent + 1);
			child = child.next;
		}
	}
}

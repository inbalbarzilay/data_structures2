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
	public void deleteMin() {
		return; // should be replaced by student code

	}

	/**
	 * 
	 * Return the minimal HeapItem, null if empty.
	 *
	 */
	public HeapItem findMin() {
		return this.min.item; // should be replaced by student code
	} 

	/**
	 * 
	 * pre: 0<diff<item.key
	 * 
	 * Decrease the key of item by diff and fix the heap. 
	 * 
	 */
	public void decreaseKey(HeapItem item, int diff) {
		item.key -= diff;
		int key = item.key;
		HeapNode node = item.node;

		while (node.parent != null && node.parent.item.key > key) {
			HeapItem tempItem = node.parent.item;
			node.parent.item = node.item;
			node.item = tempItem;

			node = node.parent;
		}

		if (key < this.min.item.key) {
			this.min = node;
		}
	}

	/**
	 * 
	 * Delete the item from the heap.
	 *
	 */
	public void delete(HeapItem item) {
		return; // should be replaced by student code
	}

	/**
	 * 
	 * Meld the heap with heap2
	 *
	 */
	public void meld(BinomialHeap heap2) {
		if (!this.empty() && !heap2.empty()) {
			int lenHeap1 = this.last.rank;
			int lenHeap2 = heap2.last.rank;

			HeapNode [] arrHeap1 = new HeapNode[Math.max(lenHeap1, lenHeap2) + 2];
			HeapNode [] arrHeap2 = new HeapNode[Math.max(lenHeap1, lenHeap2) + 2];

			HeapNode node1 = this.last.next;
			HeapNode node2 = heap2.last.next;

			for (int i = 0; i < Math.max(lenHeap1, lenHeap2) + 1; i++) {
				if (node1.rank == i) {
					arrHeap1[i] = node1;
					node1 = node1.next;
				}
				if (node2.rank == i) {
					arrHeap2[i] = node2;
					node2 = node2.next;
				}
			}

			HeapNode [] result = new HeapNode[Math.max(lenHeap1, lenHeap2) + 2];
			HeapNode carry = null;

			for (int i = 0; i < result.length; i++) {
				if (carry != null) {
					if (arrHeap1[i] != null && arrHeap2[i] == null) {
						carry = this.link(arrHeap1[i], carry);
					} else if (arrHeap1[i] == null && arrHeap2[i] != null) {
						carry = this.link(arrHeap2[i], carry);
					} else if (arrHeap1[i] != null && arrHeap2[i] != null){
						if (carry.item.key <= arrHeap1[i].item.key && carry.item.key <= arrHeap2[i].item.key) {
							result[i] = carry;
							carry = this.link(arrHeap1[i], arrHeap2[i]);
						} else if (arrHeap1[i].item.key <= carry.item.key && arrHeap1[i].item.key <= arrHeap2[i].item.key) {
							result[i] = arrHeap1[i];
							carry = this.link(arrHeap2[i], carry);
						} else if (arrHeap2[i].item.key <= arrHeap1[i].item.key && arrHeap2[i].item.key <= arrHeap1[i].item.key) {
							result[i] = arrHeap2[i];
							carry = this.link(arrHeap1[i], carry);
						}
					} else {
						result[i] = carry;
						carry = null;
					}
				} else {
					if (arrHeap1[i] != null && arrHeap2[i] == null) {
						result[i] = arrHeap1[i];
					} else if (arrHeap1[i] == null && arrHeap2[i] != null) {
						result[i] = arrHeap2[i];
					} else if (arrHeap1[i] != null && arrHeap2[i] != null){
						carry = this.link(arrHeap1[i], arrHeap2[i]);
					}
				}
			}

			if (carry != null) {
				result[result.length - 1] = carry;
			}

			HeapNode first = result[0];
			HeapNode prev = result[0];
			int i = 1;
			while (i < result.length) {
				if (result[i] != null) {
					if (first == null) {
						first = result[i];
					}
					if (prev != null) {
						prev.next = result[i];
					}
					prev = result[i];
				}
				i++;
			}
			this.last = prev;
			this.last.next = first;
			this.size += heap2.size();
		}


		if (this.empty()) {
			this.last = heap2.last;
			this.min = heap2.min;
			this.size = heap2.size();
		}
	}

	private HeapNode link(HeapNode node1, HeapNode node2) {
		if (node1.item.key > node2.item.key) {
			HeapNode temp = node1;
			node1 = node2;
			node2 = temp;
		}

		node2.parent = node1;

		if (node1.child == null) {
			node1.child = node2;
			node1.firstChild = node2;
			node1.lastChild = node2;
			node2.next = node2;
		} else {
			node1.lastChild.next = node2;
			node1.lastChild = node2;
			node2.next = node1.firstChild;
		}

		node1.rank++;

		if (this.min.item.key > node1.item.key) {
			this.min = node1;
		}

		return node1;
	}

	/**
	 * 
	 * Return the number of elements in the heap
	 *   
	 */
	public int size() {
		return this.size; // should be replaced by student code
	}

	/**
	 * 
	 * The method returns true if and only if the heap
	 * is empty.
	 *   
	 */
	public boolean empty() {
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

		int size = this.size;
		int[] binary = new int[(int) (Math.log(size) / Math.log(2)) + 1];
		int numTrees = 0;
		int i;

		for (i = 0; size > 0; i++) {
			size /= 2;
			binary[i] = size % 2;

			if (binary[i] == 1)
				numTrees++;
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
		public HeapNode firstChild;
		public HeapNode lastChild;
		public HeapNode next;
		public HeapNode parent;
		public int rank;

		public HeapNode(HeapItem item, HeapNode child, HeapNode next, HeapNode parent) {
			this.item = item;
			this.child = child;
			this.firstChild = child;
			this.lastChild = child;
			this.next = next;
			this.parent = parent;
			this.rank = 0;
		}

		@Override
		public String toString() {
			return "(" + this.item.key + ": " + this.item.info + ")";
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
		sb.append("BinomialHeap\n");

		HeapNode current = this.last;
		do {
			if (current != null) {
				sb.append("Tree with root: ").append(current.toString()).append("\n");
				appendTree(sb, current, "", true);
				current = current.next;
			}
		} while (current != this.last);

		return sb.toString();
	}

	private void appendTree(StringBuilder sb, HeapNode node, String indent, boolean last) {
		if (node == null) return;

		sb.append(indent);
		if (last) {
			sb.append("└── ");
			indent += "    ";
		} else {
			sb.append("├── ");
			indent += "│   ";
		}
		sb.append(node.toString()).append("\n");

		if (node.child != null) {
			HeapNode child = node.child;
			do {
				appendTree(sb, child, indent, child.next == node.child);
				child = child.next;
			} while (child != node.child);
		}
	}

}

/**
 * BinomialHeap
 *
 * An implementation of binomial heap over positive integers.
 *
 */
public class BinomialHeap
{
	public int size;
	public int numTrees;
	public HeapNode last;
	public HeapNode min;

	public BinomialHeap() {
		this.size = 0;
		this.numTrees = 0;
		this.last = null;
		this.min = null;
	}

	/**
	 * 
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapItem.
	 * Complexity: O(logn)
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
		heap0.numTrees++;

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
			this.numTrees++;
		}

		return item; // should be replaced by student code
	}

	/**
	 * 
	 * Delete the minimal item
	 * Complexity: O(logn)
	 *
	 */
	public void deleteMin() {
		if (this.empty()) {
			return;
		}

		HeapNode min = this.min;

		if (this.numTrees() == 1) {
			this.last = min.child;

			if (min.child == null) {
				this.size = 0;
				this.numTrees = 0;
				this.min = null;
			} else {
				this.min = this.last;
				this.size = 0;
				this.numTrees = 0;
				HeapNode node = this.last.next;

				while (node != this.last) {
					if (this.min.item.key > node.item.key) {
						this.min = node;
					}
					this.size += (int) Math.pow(2, node.rank);
					this.numTrees++;
					node = node.next;
				}

				this.size += (int) Math.pow(2, node.rank);
				this.numTrees++;
			}
		} else {
			if (min.child == null) this.numTrees--;

			BinomialHeap childHeap = new BinomialHeap();
			HeapNode child = min.firstChild;

			int childHeapSize = 0;
			while (child != min.lastChild) {
				if (child != null) {
					child.parent = null;
					childHeapSize += (int) Math.pow(2, child.rank);

					if (childHeap.min == null || childHeap.min.item.key > child.item.key) {
						childHeap.min = child;
					}

					child = child.next;
				}
			}

			if (child != null) {
				child.parent = null;
				childHeapSize += (int) Math.pow(2, child.rank);

				if (childHeap.min == null || childHeap.min.item.key > child.item.key) {
					childHeap.min = child;
				}
			}

			childHeap.last = child;
			childHeap.size = childHeapSize;

			boolean isLast = this.min == this.last;
			HeapNode minNext = min.next;
			HeapNode minPrev = min;
			HeapNode node = min.next;

			while (node != min) {
				minPrev = node;
				node = node.next;
			}

			if (isLast) {
				this.last = minPrev;
			}
			minPrev.next = minNext;

			this.min = this.last;
			node = this.last.next;
			while (node != this.last) {
				if (this.min.item.key > node.item.key) {
					this.min = node;
				}
				node = node.next;
			}

			this.meld(childHeap);
			this.size -= childHeapSize + 1;
		}
	}

	/**
	 * 
	 * Return the minimal HeapItem, null if empty.
	 * Complexity: O(1)
	 *
	 */
	public HeapItem findMin() {
		if (this.min != null) {
			return this.min.item;
		}
		return null;
	}

	/**
	 * 
	 * pre: 0<diff<item.key
	 * 
	 * Decrease the key of item by diff and fix the heap.
	 * Complexity: O(logn)
	 * 
	 */
	public void decreaseKey(HeapItem item, int diff) {
		if (!this.empty()) {
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
	}

	/**
	 * 
	 * Delete the item from the heap.
	 * Complexity: O(logn)
	 *
	 */
	public void delete(HeapItem item) {
		if (!this.empty()) {
			this.decreaseKey(item, item.key);
			this.deleteMin();
		}
	}

	/**
	 * 
	 * Meld the heap with heap2
	 * Complexity: O(logn)
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
			this.numTrees = result[0] != null ? 1 : 0;

			int i = 1;
			while (i < result.length) {
				if (result[i] != null) {
					if (first == null) {
						first = result[i];
						this.min = result[i];
					}
					if (prev != null) {
						prev.next = result[i];
					}
					if (this.min.item.key > result[i].item.key) {
						this.min = result[i];
					}
					prev = result[i];
					this.numTrees++;
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
			this.numTrees = heap2.numTrees();
		}
	}

	/**
	 *
	 * Link trees with roots node1 and node2
	 * Complexity: O(1)
	 *
	 */
	private HeapNode link(HeapNode node1, HeapNode node2) {
		if (node1 == null) {
			return node2;
		}
		if (node2 == null) {
			return node1;
		}

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
			node1.child = node1.lastChild;
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
	 * Complexity: O(1)
	 *   
	 */
	public int size() {
		return this.size; // should be replaced by student code
	}

	/**
	 * 
	 * The method returns true if and only if the heap
	 * is empty.
	 * Complexity: O(1)
	 *   
	 */
	public boolean empty() {
		return this.size == 0; // should be replaced by student code
	}

	/**
	 * 
	 * Return the number of trees in the heap.
	 * Complexity: O(1)
	 * 
	 */
	public int numTrees() {
		return this.numTrees;
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

		//DELETE LATER
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

package implementation;

import java.util.Comparator;
import java.util.TreeSet;

public class BinarySearchTree<K, V> {
	private class Node {
		private K key;
		private V value;
		private Node left, right;

		private Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	private Node root;
	private int treeSize, maxEntries;
	private Comparator<K> comparator;

	public BinarySearchTree(Comparator<K> comparator, int maxEntries) {
		if (maxEntries < 1 || comparator == null) {
			throw new IllegalArgumentException("Invalid input to constructor");
		}
		this.root = null;
		this.treeSize = 0;
		this.maxEntries = maxEntries;
		this.comparator = comparator;
	}

	private void addNode(Node n, K k, V v) {
		if (n == null) {
			return;
		}
		int compVal = comparator.compare(n.key, k);
		if (compVal == 0) {
			n.value = v;
		} else if (compVal < 0) {
			if (n.right == null) {
				n.right = new Node(k, v);
				treeSize++;
			} else {
				addNode(n.right, k, v);
			}
		} else {
			if (n.left == null) {
				n.left = new Node(k, v);
				treeSize++;
			} else {
				addNode(n.left, k, v);
			}
		}
	}

	public BinarySearchTree<K, V> add(K key, V value) throws TreeIsFullException {
		if (key == null || value == null) {
			throw new IllegalArgumentException("Either key or value is null");
		}
		if (treeSize == maxEntries) {
			throw new TreeIsFullException("Tree is full");
		}
		if (root == null) {
			root = new Node(key, value);
			treeSize++;
			return this;
		}
		addNode(root, key, value);
		return this;
	}

	private String toStringHelp(Node n) {
		if (n == null) {
			return "";
		}
		return toStringHelp(n.left) + "{" + n.key + ":" + n.value + "}" + toStringHelp(n.right);
	}

	@Override
	public String toString() {
		if (treeSize == 0) {
			return "EMPTY TREE";
		}
		return toStringHelp(root);
	}

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return treeSize;
	}

	public boolean isFull() {
		return treeSize == maxEntries;
	}

	private KeyValuePair<K, V> getMinKVHelper(Node n) {
		if (n.left == null) {
			return new KeyValuePair<K, V>(n.key, n.value);
		}
		return getMinKVHelper(n.left);
	}

	public KeyValuePair<K, V> getMinimumKeyValue() throws TreeIsEmptyException {
		if (isEmpty()) {
			throw new TreeIsEmptyException("Tree is empty");
		}
		return getMinKVHelper(root);
	}

	private KeyValuePair<K, V> getMaxKVHelper(Node n) {
		if (n.right == null) {
			return new KeyValuePair<K, V>(n.key, n.value);
		}
		return getMaxKVHelper(n.right);
	}

	public KeyValuePair<K, V> getMaximumKeyValue() throws TreeIsEmptyException {
		if (isEmpty()) {
			throw new TreeIsEmptyException("Tree is empty");
		}
		return getMaxKVHelper(root);
	}

	private KeyValuePair<K, V> findHelper(Node n, K k) {
		if (n == null) {
			return null;
		}
		int compVal = comparator.compare(n.key, k);
		if (compVal == 0) {
			return new KeyValuePair<K, V>(n.key, n.value);
		} else if (compVal < 0) {
			return findHelper(n.right, k);
		} else {
			return findHelper(n.left, k);
		}
	}

	public KeyValuePair<K, V> find(K key) {
		if (key == null) {
			return null;
		}
		return findHelper(root, key);
	}

	private Node findNode(Node n, K k) {
		if (n == null) {
			return null;
		}
		if (comparator.compare(n.key, k) == 0) {
			return n;
		} else if (comparator.compare(n.key, k) < 0) {
			return findNode(n.right, k);
		} else {
			return findNode(n.left, k);
		}
	}

	private Node deleteHelperLeft(Node next, Node toFill) {
		if (next == null) {
			return null;
		}
		if (next.left == null && next.right == null) {
			toFill.key = next.key;
			toFill.value = next.value;
			treeSize--;
			return null;
		}
		if (next.right == null) {
			// update toFill's key and value
			toFill.key = next.key;
			toFill.value = next.value;

			//update next's left and right pointer to be that of the one on the left
			next.right = next.left.right;
			if (next.left != null) {
				next.key = next.left.key;
				next.value = next.left.value;
			}
			next.left = next.left.left;
			treeSize--;
			return next;
		} else {
			next.right = deleteHelperLeft(next.right, toFill);
			return next;
		}

	}

	private Node deleteHelperRight(Node next, Node toFill) {
		if (next == null) {
			return null;
		}
		if (next.left == null && next.right == null) {
			toFill.key = next.key;
			toFill.value = next.value;
			treeSize--;
			return null;
		}
		if (next.left == null) {
			toFill.key = next.key;
			toFill.value = next.value;
			next.left = next.right.left;
			if (next.right != null) {
				next.key = next.right.key;
				next.value = next.right.value;
			}
			next.right = next.right.right;
			treeSize--;
			return next;
		} else {
			next.right = deleteHelperLeft(next.left, toFill);
			return next;
		}

	}

	private void eraseNode(Node cur, Node erase) {
		if (cur.left == erase) {
			cur.left = null;
			treeSize--;
		} else if (cur.right == erase) {
			cur.right = null;
			treeSize--;
		} else {
			if (comparator.compare(cur.key, erase.key) > 0) {
				eraseNode(cur.left, erase);
			} else {
				eraseNode(cur.right, erase);
			}
		}
	}

	public BinarySearchTree<K, V> delete(K key) throws TreeIsEmptyException {
		if (key == null) {
			return this;
		}
		if (isEmpty()) {
			throw new TreeIsEmptyException("Tree is empty");
		}
		if (find(key) == null) {
			return this;
		}
		if (treeSize == 1) {
			this.root = null;
			treeSize--;
			return this;
		}
		Node replaceNode = findNode(root, key);
		if (replaceNode == root) {
			if (root.left == null) {
				root.right = deleteHelperRight(root.right, root);
			} else {
				root.left = deleteHelperLeft(root.left, root);
			}
		} else if (replaceNode.left == null && replaceNode.right == null) {
			eraseNode(root, replaceNode);
		} else if (replaceNode.left == null) {
			replaceNode.right = deleteHelperRight(replaceNode.right, replaceNode);
		} else {
			replaceNode.left = deleteHelperLeft(replaceNode.left, replaceNode);
		}

		return this;
	}

	private void processInorderHelper(Node n, Callback<K, V> callback) {
		if (n == null) {
			return;
		}
		processInorderHelper(n.left, callback);
		callback.process(n.key, n.value);
		processInorderHelper(n.right, callback);
	}

	public void processInorder(Callback<K, V> callback) {
		if (callback == null) {
			throw new IllegalArgumentException("Callback is null");
		}
		processInorderHelper(root, callback);
	}

	private void subtreeHelper(BinarySearchTree<K, V> tree, Node n, K lowLim, K upLim) {
		if (n == null) {
			return;
		}
		if (comparator.compare(n.key, lowLim) < 0) {
			subtreeHelper(tree, n.right, lowLim, upLim);
		} else if (comparator.compare(n.key, upLim) > 0) {
			subtreeHelper(tree, n.left, lowLim, upLim);
		} else {
			try {
				tree.add(n.key, n.value);
			} catch (TreeIsFullException e) {
				// will never happen, ignore
			}
			subtreeHelper(tree, n.right, lowLim, upLim);
			subtreeHelper(tree, n.left, lowLim, upLim);
		}
	}

	public BinarySearchTree<K, V> subTree(K lowerLimit, K upperLimit) {
		if (lowerLimit == null || upperLimit == null || comparator.compare(lowerLimit, upperLimit) > 0) {
			throw new IllegalArgumentException("Invalid lower or upper limit");
		}
		BinarySearchTree<K, V> newTree = new BinarySearchTree<K, V>(this.comparator, this.maxEntries);
		subtreeHelper(newTree, root, lowerLimit, upperLimit);
		return newTree;
	}

	private TreeSet<V> getLeavesValuesHelper(Node n) {
		if (n == null) {
			return new TreeSet<V>();
		}
		if (n.left == null && n.right == null) {
			TreeSet<V> singletonTreeSet = new TreeSet<V>();
			singletonTreeSet.add(n.value);
			return singletonTreeSet;
		}
		TreeSet<V> combinedTreeSet = new TreeSet<V>();
		combinedTreeSet.addAll(getLeavesValuesHelper(n.left));
		combinedTreeSet.addAll(getLeavesValuesHelper(n.right));
		return combinedTreeSet;
	}

	public TreeSet<V> getLeavesValues() {
		return getLeavesValuesHelper(root);
	}
}

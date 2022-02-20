package tests;

import static org.junit.Assert.*;

import java.util.TreeSet;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import implementation.BinarySearchTree;
import implementation.KeyValuePair;
import implementation.TreeIsEmptyException;
import implementation.TreeIsFullException;

/* The following directive executes tests in sorted order */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class Tests {

	@Test
	public void testConstructor() {
		@SuppressWarnings("unused")
		BinarySearchTree<String, Integer> tree = new BinarySearchTree<String, Integer>(String.CASE_INSENSITIVE_ORDER, 100);
		try {
			tree = new BinarySearchTree<String, Integer>(null, 1000);
		} catch (IllegalArgumentException e) {
			assert(true);
		} catch (Exception e) {
			assert(false);
		}
		try {
			tree = new BinarySearchTree<String, Integer>(String.CASE_INSENSITIVE_ORDER, 0);
		} catch (IllegalArgumentException e) {
			assert(true);
		} catch (Exception e) {
			assert(false);
		}
	}

	@Test
	public void testAddNode() {
		BinarySearchTree<String, Integer> tree = new BinarySearchTree<String, Integer>(String.CASE_INSENSITIVE_ORDER, 3);

		try {
			tree.add("Whomever", null);
		} catch (IllegalArgumentException e) {
			assert(true);
		} catch (Exception e) {
			assert(false);
		}
		try {
			tree.add(null, 10);
		} catch (IllegalArgumentException e) {
			assert(true);
		} catch (Exception e) {
			assert(false);
		}
		try {
			tree.add("Sally", 2);
			tree.add("Carl", 1);
			tree.add("Zanzibar", 3);
		} catch (Exception e) {
			assert(false);
		}
		assert(true);
		try {
			tree.add("Whomever", -1);
		} catch (TreeIsFullException e) {
			assert(true);
		} catch (Exception e) {
			assert(false);
		}
		String output = tree.toString();
		assertEquals(output, "{Carl:1}{Sally:2}{Zanzibar:3}");
	}

	@Test
	public void testDeleteKey() {
		BinarySearchTree<String, Integer> tree = new BinarySearchTree<String, Integer>(String.CASE_INSENSITIVE_ORDER, 1000);

		try {
			tree.delete("whatever");
		} catch (TreeIsEmptyException e) {
			assert(true);
		} catch (Exception e) {
			assert(false);
		}

		try {
			tree.delete(null);
		} catch (IllegalArgumentException e) {
			assert(true);
		} catch (Exception e) {
			assert(false);
		}

		assertEquals(tree.size(), 0);

		try {
			tree.add("mmm", 4);
			tree.add("fff", 2);
			tree.add("rrr", 6);
			tree.add("aaa", 1);
			tree.add("iii", 3);
			tree.add("ppp", 5);
			tree.add("zzz", 7);
		} catch (Exception e) {
			assert(false);
		}
		assertEquals(7, tree.size());
		assertEquals("{aaa:1}{fff:2}{iii:3}{mmm:4}{ppp:5}{rrr:6}{zzz:7}", tree.toString());

		try {
			tree.delete("fff");
		} catch (Exception e) {
			e.printStackTrace();
			assert(false);
		}
		assertEquals(6, tree.size());
		assertEquals("{aaa:1}{iii:3}{mmm:4}{ppp:5}{rrr:6}{zzz:7}", tree.toString());

		try {
			tree.delete("fff");
		} catch (Exception e) {
			assert(false);
		}
		assertEquals(6, tree.size());
		assertEquals("{aaa:1}{iii:3}{mmm:4}{ppp:5}{rrr:6}{zzz:7}", tree.toString());

		try {
			tree.delete("zzz");
		} catch (Exception e) {
			assert(false);
		}
		assertEquals(5, tree.size());
		assertEquals("{aaa:1}{iii:3}{mmm:4}{ppp:5}{rrr:6}", tree.toString());

		try {
			tree.delete("rrr");
		} catch (Exception e) {
			assert(false);
		}
		assertEquals(4, tree.size());
		assertEquals("{aaa:1}{iii:3}{mmm:4}{ppp:5}", tree.toString());

		try {
			tree.delete("ppp");
		} catch (Exception e) {
			assert(false);
		}
		assertEquals(3, tree.size());
		assertEquals("{aaa:1}{iii:3}{mmm:4}", tree.toString());

		try {
			tree.delete("mmm");
		} catch (Exception e) {
			assert(false);
		}
		assertEquals(2, tree.size());
		assertEquals("{aaa:1}{iii:3}", tree.toString());

		try {
			tree.delete("iii");
		} catch (Exception e) {
			assert(false);
		}
		assertEquals(1, tree.size());
		assertEquals("{aaa:1}", tree.toString());

		try {
			tree.delete("aaa");
		} catch (Exception e) {
			assert(false);
		}
		assertEquals(0, tree.size());
		assertEquals("EMPTY TREE", tree.toString());

		try {
			tree.delete("aaa");
		} catch (TreeIsEmptyException e) {
			assert(true);
		} catch (Exception e) {
			assert(false);
		}
		assertEquals(0, tree.size());
		assertEquals("EMPTY TREE", tree.toString());

		try {
			tree.add("aaa", 1);
			tree.add("bbb", 2);
			tree.add("ccc", 3);
			tree.delete("aaa");
			assertEquals(2, tree.size());
			assertEquals("{bbb:2}{ccc:3}", tree.toString());
			tree.delete("bbb");
			assertEquals(1, tree.size());
			assertEquals("{ccc:3}", tree.toString());
			tree.delete("ccc");
			assertEquals(0, tree.size());
			assertEquals("EMPTY TREE", tree.toString());
		} catch (TreeIsEmptyException e) {
			assert(false);
		} catch (IllegalArgumentException e) {
			assert(false);
		} catch (TreeIsFullException e) {
			assert(false);
		}

		try {
			tree.add("ccc", 3);
			tree.add("bbb", 2);
			tree.add("aaa", 1);
			tree.delete("ccc");
			assertEquals(2, tree.size());
			assertEquals("{aaa:1}{bbb:2}", tree.toString());
			tree.delete("bbb");
			assertEquals(1, tree.size());
			assertEquals("{aaa:1}", tree.toString());
			tree.delete("aaa");
			assertEquals(0, tree.size());
			assertEquals("EMPTY TREE", tree.toString());
		} catch (TreeIsEmptyException e) {
			assert(false);
		} catch (IllegalArgumentException e) {
			assert(false);
		} catch (TreeIsFullException e) {
			assert(false);
		}
		assert(true);
	}

	@Test
	public void testGetMinMaxKV() {
		BinarySearchTree<String, Integer> tree = new BinarySearchTree<String, Integer>(String.CASE_INSENSITIVE_ORDER, 1000);
		try {
			tree.add("mmm", 4);
			tree.add("fff", 2);
			tree.add("rrr", 6);
			tree.add("aaa", 1);
			tree.add("iii", 3);
			tree.add("ppp", 5);
			tree.add("zzz", 7);
		} catch (Exception e) {
			assert(false);
		}
		try {
			KeyValuePair<String, Integer> minKV = tree.getMinimumKeyValue();
			KeyValuePair<String, Integer> maxKV = tree.getMaximumKeyValue();
			assertEquals("aaa", minKV.getKey());
			assertEquals((Integer)1, (Integer)minKV.getValue());
			assertEquals("zzz", maxKV.getKey());
			assertEquals((Integer)7, (Integer)maxKV.getValue());
		} catch (TreeIsEmptyException e) {
			assert(false);
		}
	}

	@Test
	public void testFind() {
		BinarySearchTree<String, Integer> tree = new BinarySearchTree<String, Integer>(String.CASE_INSENSITIVE_ORDER, 1000);
		assertEquals(null, tree.find("whatever"));
		try {
			tree.add("mmm", 4);
			tree.add("fff", 2);
			tree.add("rrr", 6);
			tree.add("aaa", 1);
			tree.add("iii", 3);
			tree.add("ppp", 5);
			tree.add("zzz", 7);
		} catch (Exception e) {
			assert(false);
		}
		assertEquals("aaa", tree.find("aaa").getKey());
		assertEquals("mmm", tree.find("mmm").getKey());
		assertEquals("zzz", tree.find("zzz").getKey());
		assertEquals(null, tree.find("doesnotexist"));
	}

	@Test
	public void testSubtree() {
		BinarySearchTree<String, Integer> tree = new BinarySearchTree<String, Integer>(String.CASE_INSENSITIVE_ORDER, 1000);
		try {
			tree.add("mmm", 4);
			tree.add("fff", 2);
			tree.add("rrr", 6);
			tree.add("aaa", 1);
			tree.add("iii", 3);
			tree.add("ppp", 5);
			tree.add("zzz", 7);
		} catch (Exception e) {
			assert(false);
		}
		BinarySearchTree<String, Integer> subtree = tree.subTree("bbb", "yyy");
		assertEquals("{fff:2}{iii:3}{mmm:4}{ppp:5}{rrr:6}", subtree.toString());

		subtree = tree.subTree("ccc", "ccc");
		assert(subtree.isEmpty());
		subtree = tree.subTree("", "");
		assert(subtree.isEmpty());
		subtree = tree.subTree("a", "a");
		assert(subtree.isEmpty());
		subtree = tree.subTree("zzzz", "zzzz");
		assert(subtree.isEmpty());

		try {
			subtree = tree.subTree("zzz", "aaa");
			assert(false);
		} catch (IllegalArgumentException e) {
			assert(true);
		}
	}

	@Test
	public void testGetLeavesValues() {
		BinarySearchTree<String, Integer> tree = new BinarySearchTree<String, Integer>(String.CASE_INSENSITIVE_ORDER, 1000);
		assert(tree.getLeavesValues().isEmpty());
		try {
			tree.add("mmm", 4);
			tree.add("fff", 2);
			tree.add("rrr", 6);
			tree.add("aaa", 1);
			tree.add("iii", 3);
			tree.add("ppp", 5);
			tree.add("zzz", 7);
		} catch (Exception e) {
			assert(false);
		}
		TreeSet<Integer> targetSet = new TreeSet<Integer>();
		targetSet.add(1);
		targetSet.add(3);
		targetSet.add(5);
		targetSet.add(7);
		assert(targetSet.equals(tree.getLeavesValues()));
	}
}

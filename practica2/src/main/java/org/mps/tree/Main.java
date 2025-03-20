package org.mps.tree;

/**
 * Main
 */
public class Main {
	public static void main(String[] args) {
		BinarySearchTree<Integer> tree = new BinarySearchTree<>(Integer::compareTo);
		tree.insert(5);
		tree.insert(3);
		tree.insert(7);
		tree.insert(2);
		tree.insert(4);
		tree.insert(6);
		tree.insert(8);
		tree.insert(9);

		System.out.println(tree.render());

		tree.removeBranch(8);
		tree.removeBranch(2);
		System.out.println(tree.render());
		System.out.println(tree.minimum());
		System.out.println(tree.maximum());
	}
}

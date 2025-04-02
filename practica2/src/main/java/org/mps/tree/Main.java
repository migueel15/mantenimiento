package org.mps.tree;

/**
 * Main
 */
public class Main {
  public static void main(String[] args) {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>(Integer::compareTo);
    tree.insert(10);
    tree.insert(15); // Nodo derecho
    tree.insert(25);
    tree.removeValue(15); // Eliminar la raíz, que tiene un hijo derecho
    System.out.println(tree.render());
  }
}

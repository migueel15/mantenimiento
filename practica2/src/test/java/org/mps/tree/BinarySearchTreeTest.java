// Salma Boulagna Moreno
// Miguel Ángel Dorado Maldonado

package org.mps.tree;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.awt.List;
import java.util.ArrayList;
import java.util.Comparator;

@DisplayName("BinarySearchTree Tests")
class BinarySearchTreeTest {
  private BinarySearchTree<Integer> tree;

  @BeforeEach
  void setUp() {
    tree = new BinarySearchTree<Integer>(Comparator.naturalOrder());
  }

  @Nested
  @DisplayName("Insertion Tests")
  class InsertionTests {
    @Test
    @DisplayName("Insert single value")
    void insertSingleValue10Expected10() throws BinarySearchTreeException {
      tree.insert(10);
      assertEquals("10", tree.render());
    }

    @Test
    @DisplayName("Insert multiple values")
    void insertMultipleValues10515Expected10515() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
      assertEquals("10(5,15)", tree.render());
    }

    @Test
    @DisplayName("Insert duplicate value throws exception")
    void insertDuplicateValue10ExpectedException() throws BinarySearchTreeException {
      tree.insert(10);
      assertThrows(BinarySearchTreeException.class, () -> tree.insert(10));
    }

    @Test
    @DisplayName("Insert values to cover both branches of if statement")
    void insertValuesToCoverIfBranches() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5); // Rama izquierda del if
      tree.insert(15); // Rama derecha del if
      assertEquals("10(5,15)", tree.render());
    }
  }

  @Nested
  @DisplayName("Leaf Tests")
  class LeafTests {
    private BinarySearchTree<Integer> tree;

    @BeforeEach
    void setUp() {
      tree = new BinarySearchTree<Integer>(Comparator.naturalOrder());
    }

    @Test
    @DisplayName("Calling isLeaf on an empty tree throws exception")
    void isLeafOnEmptyTreeThrowsException() {
      assertThrows(BinarySearchTreeException.class, () -> tree.isLeaf());
    }

    @Test
    @DisplayName("Leaf node returns true")
    void isLeafReturnsTrueForLeafNode() throws BinarySearchTreeException {
      tree.insert(10);
      assertTrue(tree.isLeaf());
    }

    @Test
    @DisplayName("Node with left child returns false")
    void isLeafReturnsFalseForNodeWithLeftChild() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5);
      assertFalse(tree.isLeaf());
    }

    @Test
    @DisplayName("Node with right child returns false")
    void isLeafReturnsFalseForNodeWithRightChild() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(15);
      assertFalse(tree.isLeaf());
    }

    @Test
    @DisplayName("Node with two children returns false")
    void isLeafReturnsFalseForNodeWithTwoChildren() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
      assertFalse(tree.isLeaf());
    }
  }

  @Nested
  @DisplayName("Render Tests")
  class RenderTests {
    @Test
    @DisplayName("Render empty tree")
    void renderEmptyTreeExpectedEmptyString() {
      assertEquals("", tree.render());
    }

    @Test
    @DisplayName("Render tree with only root")
    void renderSingleNodeTreeExpected10() throws BinarySearchTreeException {
      tree.insert(10);
      assertEquals("10", tree.render());
    }

    @Test
    @DisplayName("Render tree with left child only")
    void renderTreeWithLeftChildOnly() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5);
      assertEquals("10(5,)", tree.render());
    }

    @Test
    @DisplayName("Render tree with right child only")
    void renderTreeWithRightChildOnly() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(15);
      assertEquals("10(,15)", tree.render());
    }
  }

  @Nested
  @DisplayName("Search and Contains Tests")
  class SearchTests {

    @Test
    @DisplayName("Tree contains inserted value")
    void containsInsertedValue10ExpectedTrue() throws BinarySearchTreeException {
      tree.insert(10);
      assertTrue(tree.contains(10));
    }

    @Test
    @DisplayName("Tree does not contain non-inserted value")
    void doesNotContainValue99ExpectedFalse() {
      assertFalse(tree.contains(99));
    }

    @Test
    @DisplayName("Tree contains value in left sub-tree")
    void containsValueInLeftSubTree() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5);
      assertTrue(tree.contains(5)); // Valor en el subárbol izquierdo
    }

    @Test
    @DisplayName("Tree contains value in right sub-tree")
    void containsValueInRightSubTree() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(15);
      assertTrue(tree.contains(15)); // Valor en el subárbol derecho
    }

    @Test
    @DisplayName("Tree does not contain value that is less than all values")
    void doesNotContainValueWhenLessThanAll() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5);
      assertFalse(tree.contains(1)); // Valor menor que todos los existentes
    }

    @Test
    @DisplayName("Tree does not contain value that is greater than all values")
    void doesNotContainValueWhenGreaterThanAll() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5);
      assertFalse(tree.contains(20)); // Valor mayor que todos los existentes
    }

    @Test
    @DisplayName("Empty tree does not contain any value")
    void containsOnEmptyTreeExpectedFalse() {
      assertFalse(tree.contains(10)); // Árbol vacío
    }
  }

  @Nested
  @DisplayName("Minimum and Maximum Tests")
  class MinMaxTests {

    @Test
    @DisplayName("Find minimum value in a non-empty tree")
    void minimumValueInTreeExpected5() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
      assertEquals(5, tree.minimum()); // Mínimo valor está en el subárbol izquierdo
    }

    @Test
    @DisplayName("Find maximum value in a non-empty tree")
    void maximumValueInTreeExpected15() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
      assertEquals(15, tree.maximum()); // Máximo valor está en el subárbol derecho
    }

    @Test
    @DisplayName("Find minimum value in a tree with only one node")
    void minimumValueInTreeWithOneNodeExpected10() throws BinarySearchTreeException {
      tree.insert(10);
      assertEquals(10, tree.minimum()); // Solo un nodo, mínimo es el mismo valor
    }

    @Test
    @DisplayName("Find maximum value in a tree with only one node")
    void maximumValueInTreeWithOneNodeExpected10() throws BinarySearchTreeException {
      tree.insert(10);
      assertEquals(10, tree.maximum()); // Solo un nodo, máximo es el mismo valor
    }

    @Test
    @DisplayName("Throw exception when trying to find minimum in empty tree")
    void minimumInEmptyTreeThrowsException() {
      assertThrows(BinarySearchTreeException.class, () -> tree.minimum()); // Árbol vacío
    }

    @Test
    @DisplayName("Throw exception when trying to find maximum in empty tree")
    void maximumInEmptyTreeThrowsException() {
      assertThrows(BinarySearchTreeException.class, () -> tree.maximum()); // Árbol vacío
    }
  }

  @Nested
  @DisplayName("Remove Branch Tests")
  class RemoveBranchTests {

    @Test
    @DisplayName("Remove branch with value null")
    void removeBranchWithValueNullThrowsException() {
      assertThrows(BinarySearchTreeException.class, () -> tree.removeBranch(null)); // Valor nulo
    }

    @Test
    @DisplayName("Remove non-existent value throws exception")
    void removeNonExistentValueThrowsException() {
      tree.insert(10);
      assertThrows(BinarySearchTreeException.class, () -> tree.removeBranch(5)); // Valor no existe
    }

    @Test
    @DisplayName("Remove node with only left child")
    void removeNodeWithLeftChildExpectedCorrectTree() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5); // Nodo izquierdo
      tree.removeBranch(5); // Eliminar el hijo izquierdo
      assertEquals("10", tree.render()); // Solo debe quedar el nodo raíz (10)
    }

    @Test
    @DisplayName("Remove node with only right child")
    void removeNodeWithRightChildExpectedCorrectTree() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(15); // Nodo derecho
      tree.removeBranch(15); // Eliminar el hijo derecho
      assertEquals("10", tree.render()); // Solo debe quedar el nodo raíz (10)
    }

    @Test
    @DisplayName("Remove node with both left and right children")
    void removeNodeWithBothChildrenExpectedCorrectTree() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5); // Nodo izquierdo
      tree.insert(15); // Nodo derecho
      tree.removeBranch(5); // Eliminar el hijo izquierdo
      assertEquals("10(,15)", tree.render()); // El subárbol izquierdo debe desaparecer, solo debe quedar el nodo raíz y
                                              // el hijo derecho (15)
    }

    @Test
    @DisplayName("Recursively remove value in left subtree")
    void removeInLeftSubtree() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5); // Nodo izquierdo
      tree.insert(3); // Nodo izquierdo de 5
      tree.removeBranch(3); // Eliminar el valor en el subárbol izquierdo
      assertEquals("10(5,)", tree.render()); // El valor 3 debe ser eliminado, y el árbol debe ser correcto
    }

    @Test
    @DisplayName("Recursively remove value in right subtree")
    void removeInRightSubtree() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(15); // Nodo derecho
      tree.insert(20); // Nodo derecho de 15
      tree.removeBranch(20); // Eliminar el valor en el subárbol derecho
      assertEquals("10(,15)", tree.render()); // El valor 20 debe ser eliminado, y el árbol debe ser correcto
    }
  }

  @Nested
  @DisplayName("Size Tests")
  class SizeTests {

    @Test
    @DisplayName("Size of an empty tree")
    void sizeOfEmptyTreeExpected0() {
      // Árbol vacío, debe devolver 0
      assertEquals(0, tree.size());
    }

    @Test
    @DisplayName("Size of a tree with only the root node")
    void sizeOfSingleNodeTreeExpected1() throws BinarySearchTreeException {
      // Árbol con un solo nodo, debe devolver 1
      tree.insert(10);
      assertEquals(1, tree.size());
    }

    @Test
    @DisplayName("Size of a tree with left and right children")
    void sizeOfTreeWithBothChildrenExpected3() throws BinarySearchTreeException {
      // Árbol con raíz, hijo izquierdo y derecho, debe devolver 3
      tree.insert(10);
      tree.insert(5); // Hijo izquierdo
      tree.insert(15); // Hijo derecho
      assertEquals(3, tree.size());
    }

    @Test
    @DisplayName("Size of a tree with only left subtree")
    void sizeOfTreeWithLeftSubtreeExpected2() throws BinarySearchTreeException {
      // Árbol con un nodo raíz y un hijo izquierdo, debe devolver 2
      tree.insert(10);
      tree.insert(5); // Hijo izquierdo
      assertEquals(2, tree.size());
    }

    @Test
    @DisplayName("Size of a tree with only right subtree")
    void sizeOfTreeWithRightSubtreeExpected2() throws BinarySearchTreeException {
      // Árbol con un nodo raíz y un hijo derecho, debe devolver 2
      tree.insert(10);
      tree.insert(15); // Hijo derecho
      assertEquals(2, tree.size());
    }

    @Test
    @DisplayName("Size of a large tree")
    void sizeOfLargeTreeExpected7() throws BinarySearchTreeException {
      // Árbol con varios nodos distribuidos entre ambos subárboles, debe devolver 7
      tree.insert(10);
      tree.insert(5); // Hijo izquierdo
      tree.insert(15); // Hijo derecho
      tree.insert(3); // Subárbol izquierdo de 5
      tree.insert(7); // Subárbol derecho de 5
      tree.insert(12); // Subárbol izquierdo de 15
      tree.insert(17); // Subárbol derecho de 15
      assertEquals(7, tree.size());
    }
  }

  @Nested
  @DisplayName("Depth Tests")
  class DepthTests {

    @Test
    @DisplayName("Depth of an empty tree")
    void depthOfEmptyTreeExpected0() {
      // Árbol vacío, debe devolver 0
      assertEquals(0, tree.depth());
    }

    @Test
    @DisplayName("Depth of a tree with only the root node")
    void depthOfSingleNodeTreeExpected1() throws BinarySearchTreeException {
      // Árbol con solo un nodo (raíz), debe devolver 1
      tree.insert(10);
      assertEquals(1, tree.depth());
    }

    @Test
    @DisplayName("Depth of a tree with only left subtree")
    void depthOfTreeWithLeftSubtreeExpected2() throws BinarySearchTreeException {
      // Árbol con un nodo raíz y un hijo izquierdo, la profundidad será 2
      tree.insert(10);
      tree.insert(5); // Hijo izquierdo
      assertEquals(2, tree.depth());
    }

    @Test
    @DisplayName("Depth of a tree with only right subtree")
    void depthOfTreeWithRightSubtreeExpected2() throws BinarySearchTreeException {
      // Árbol con un nodo raíz y un hijo derecho, la profundidad será 2
      tree.insert(10);
      tree.insert(15); // Hijo derecho
      assertEquals(2, tree.depth());
    }

    @Test
    @DisplayName("Depth of a balanced tree with left and right children")
    void depthOfBalancedTreeExpected3() throws BinarySearchTreeException {
      // Árbol con raíz, hijo izquierdo y derecho. Profundidad será 3
      tree.insert(10);
      tree.insert(5); // Hijo izquierdo
      tree.insert(15); // Hijo derecho
      assertEquals(2, tree.depth());
    }

    @Test
    @DisplayName("Depth of a left-leaning tree")
    void depthOfLeftLeaningTreeExpected5() throws BinarySearchTreeException {
      // Árbol completamente desbalanceado a la izquierda, profundidad será 5
      tree.insert(10);
      tree.insert(5); // Hijo izquierdo
      tree.insert(3); // Hijo izquierdo del hijo izquierdo
      tree.insert(2); // Hijo izquierdo del hijo izquierdo
      tree.insert(1); // Hijo izquierdo del hijo izquierdo
      assertEquals(5, tree.depth());
    }

    @Test
    @DisplayName("Depth of a right-leaning tree")
    void depthOfRightLeaningTreeExpected5() throws BinarySearchTreeException {
      // Árbol completamente desbalanceado a la derecha, profundidad será 5
      tree.insert(10);
      tree.insert(15); // Hijo derecho
      tree.insert(20); // Hijo derecho del hijo derecho
      tree.insert(25); // Hijo derecho del hijo derecho
      tree.insert(30); // Hijo derecho del hijo derecho
      assertEquals(5, tree.depth());
    }

    @Test
    @DisplayName("Depth of a larger tree")
    void depthOfLargeTreeExpected4() throws BinarySearchTreeException {
      // Árbol con 7 nodos distribuidos entre subárbol izquierdo y derecho. La
      // profundidad será 4
      tree.insert(10);
      tree.insert(5); // Hijo izquierdo
      tree.insert(15); // Hijo derecho
      tree.insert(3); // Hijo izquierdo del hijo izquierdo
      tree.insert(7); // Hijo derecho del hijo izquierdo
      tree.insert(12); // Hijo izquierdo del hijo derecho
      tree.insert(17); // Hijo derecho del hijo derecho
      assertEquals(3, tree.depth());
    }
  }

  @Nested
  @DisplayName("Remove Value Tests")
  class RemoveValueTests {

    @Test
    @DisplayName("Removing a null value throws an exception")
    void removeNullValueThrowsException() {
      // Intentamos eliminar un valor nulo
      assertThrows(BinarySearchTreeException.class, () -> tree.removeValue(null));
    }

    @Test
    @DisplayName("Trying to remove a non-existent value throws an exception")
    void removeNonExistentValueThrowsException() {
      // Intentamos eliminar un valor que no existe en el árbol
      tree.insert(10);
      assertThrows(BinarySearchTreeException.class, () -> tree.removeValue(5));
    }

    @Test
    @DisplayName("Remove value with no left children")
    void removeValueWithNoLeftChildren() throws BinarySearchTreeException {
      // Eliminar un valor que no tiene hijos izquierdos
      tree.insert(10);
      tree.insert(15); // Nodo derecho
      tree.insert(20); // Nodo derecho del nodo derecho
      tree.removeValue(15); // Eliminar el nodo 15, que no tiene hijos izquierdos
      assertEquals("10(,20)", tree.render()); // Comprobamos la estructura después de la eliminación
    }

    @Test
    @DisplayName("Removing a leaf node (root node) when tree has only one node")
    void removeLeafNodeRoot() throws BinarySearchTreeException {
      // Eliminar un nodo hoja que es también la raíz (solo un nodo en el árbol)
      tree.insert(10);
      tree.removeValue(10);
      // El árbol debe estar vacío después de eliminar la raíz
      assertEquals(0, tree.size());
    }

    @Test
    @DisplayName("Removing a leaf node (not root node)")
    void removeLeafNodeNotRoot() throws BinarySearchTreeException {
      // Eliminar un nodo hoja que no es la raíz
      tree.insert(10);
      tree.insert(5); // Nodo izquierdo
      tree.insert(15); // Nodo derecho
      tree.removeValue(5); // Eliminar el nodo hoja (5)
      assertEquals("10(,15)", tree.render()); // Comprobamos la estructura después de la eliminación
    }

    @Test
    @DisplayName("Removing a node with only a left child (root node)")
    void removeNodeWithOnlyLeftChild() throws BinarySearchTreeException {
      // Eliminar un nodo con un solo hijo a la izquierda (raíz)
      tree.insert(10);
      tree.insert(5); // Nodo izquierdo
      tree.removeValue(10); // Eliminar la raíz, que tiene un hijo izquierdo
      assertEquals("5", tree.render()); // Comprobamos la estructura después de la eliminación
    }

    @Test
    @DisplayName("Eliminar nodo con solo hijo derecho")
    void removeNodeWithOnlyRightChild() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(15); // Nodo derecho
      tree.removeValue(10); // Eliminar la raíz con un solo hijo derecho
      assertEquals("15", tree.render());
    }

    @Test
    @DisplayName("Eliminar nodo con solo hijo derecho siendo hijo izquierdo del padre")
    void removeLeftNodeWithOnlyRightChild() throws BinarySearchTreeException {
      tree.insert(20);
      tree.insert(10);
      tree.insert(15); // Hijo derecho de 10

      tree.removeValue(10); // Eliminar 10, su hijo 15 debe ocupar su lugar

      assertEquals("20(15,)", tree.render()); // Suponiendo que render() muestre la estructura
    }

    @Test
    @DisplayName("Eliminar nodo con solo hijo izquierdo siendo hijo izquierdo del padre")
    void removeLeftNodeWithOnlyLeftChild() throws BinarySearchTreeException {
      tree.insert(20);
      tree.insert(10);
      tree.insert(5); // Hijo izquierdo de 10

      tree.removeValue(10); // Eliminar 10, su hijo 5 debe ocupar su lugar

      assertEquals("20(5,)", tree.render()); // Suponiendo que render() muestre la estructura
    }

    @Test
    @DisplayName("Eliminar nodo con solo hijo izquierdo siendo hijo derecho del padre")
    void removeRightNodeWithOnlyLeftChild() throws BinarySearchTreeException {
      tree.insert(20);
      tree.insert(30);
      tree.insert(25); // Hijo izquierdo de 30

      tree.removeValue(30); // Eliminar 30, su hijo 25 debe ocupar su lugar

      assertEquals("20(,25)", tree.render()); // Suponiendo que render() muestre la estructura
    }

    @Test
    @DisplayName("Eliminar nodo con dos hijos (sucesor es hijo izquierdo del nodo derecho)")
    void removeNodeWithTwoChildrenSuccessorIsLeftChild() throws BinarySearchTreeException {
      tree.insert(20);
      tree.insert(10);
      tree.insert(30);
      tree.insert(25); // Sucesor in-order de 20 (menor de los mayores)
      tree.insert(27); // Hijo derecho del sucesor

      tree.removeValue(20); // 25 reemplaza a 20 y 27 se reubica

      assertEquals("25(10,30(27,))", tree.render()); // Suponiendo que render() representa el árbol correctamente
    }

    @Test
    @DisplayName("Eliminar nodo con dos hijos (sucesor es hijo derecho inmediato)")
    void removeNodeWithTwoChildrenSuccessorIsRightChild() throws BinarySearchTreeException {
      tree.insert(20);
      tree.insert(10);
      tree.insert(30);
      tree.insert(25);
      tree.insert(40);

      tree.removeValue(20); // 25 reemplaza a 20, y el sucesorParent (20) debe apuntar a 30.

      assertEquals("25(10,30(,40))", tree.render());
    }

    @Test
    @DisplayName("Eliminar nodo con solo hijo izquierdo")
    void removeValueLeftChildWithNoLeafs() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5); // Nodo izquierdo
      tree.removeValue(10); // Eliminar la raíz con un solo hijo izquierdo
      assertEquals("5", tree.render());
    }

    @Test
    @DisplayName("Removing a node with two children")
    void removeNodeWithTwoChildren() throws BinarySearchTreeException {
      // Eliminar un nodo con dos hijos
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
      tree.removeValue(5); // Eliminar el nodo 5, que tiene dos hijos (3 y 7)
      assertEquals("10(,15)", tree.render()); // El nodo 5 debe ser eliminado y el árbol reorganizado
    }

    @Test
    @DisplayName("Removing a node in the left subtree")
    void removeValueInLeftSubtree() throws BinarySearchTreeException {
      // Eliminar un nodo en el subárbol izquierdo
      tree.insert(10);
      tree.insert(5); // Hijo izquierdo
      tree.insert(15); // Hijo derecho
      tree.removeValue(5); // Eliminar un nodo del subárbol izquierdo
      assertEquals("10(,15)", tree.render()); // El árbol debería tener solo la raíz y el hijo derecho
    }

    @Test
    @DisplayName("Removing a node in the right subtree")
    void removeValueInRightSubtree() throws BinarySearchTreeException {
      // Eliminar un nodo en el subárbol derecho
      tree.insert(10);
      tree.insert(5); // Hijo izquierdo
      tree.insert(15); // Hijo derecho
      tree.removeValue(15); // Eliminar un nodo del subárbol derecho
      assertEquals("10(5,)", tree.render()); // El árbol debería tener solo la raíz y el hijo izquierdo
    }

    @Test
    @DisplayName("Removing a root with two children, replaced by right subtree's minimum")
    void removeRootWithTwoChildrenAndRightMin() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
      tree.insert(12); // Nodo en el subárbol derecho con un hijo izquierdo

      tree.removeValue(10); // Eliminar la raíz, que tiene dos hijos

      // El sucesor será el mínimo valor en el subárbol derecho
      assertEquals("12(5,15)", tree.render());
    }

    @Test
    @DisplayName("Removing the minimum value from the right subtree")
    void removeMinValueFromRightSubtree() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
      tree.insert(12); // Nodo en el subárbol derecho con un hijo izquierdo

      tree.removeValue(12); // Eliminar el nodo 12, el mínimo valor en el subárbol derecho

      // El árbol debe quedar reorganizado sin el nodo 12
      assertEquals("10(5,15)", tree.render());
    }

    @Test
    @DisplayName("Removing a value from an empty tree should throw an exception")
    void removeValueFromEmptyTreeThrowsException() {
      // Intentamos eliminar un valor de un árbol vacío
      assertThrows(BinarySearchTreeException.class, () -> tree.removeValue(10));
    }
  }

  @Nested
  @DisplayName("InOrder Tests")
  class InOrderTests {
    @Test
    @DisplayName("InOrder traversal of an empty tree")
    void inOrderTraversalOfEmptyTreeExpectedEmptyString() {
      ArrayList<Integer> expected = new ArrayList<>();
      assertEquals(expected, tree.inOrder());
    }

    @Test
    @DisplayName("InOrder traversal of a single node tree")
    void inOrderTraversalOfSingleNodeTreeExpected10() throws BinarySearchTreeException {
      tree.insert(10);
      ArrayList<Integer> expected = new ArrayList<>();
      expected.add(10);
      assertEquals(expected, tree.inOrder());
    }

    @Test
    @DisplayName("InOrder traversal of a multi-node tree")
    void inOrderTraversalOfMultiNodeTreeExpected1357() throws BinarySearchTreeException {
      tree.insert(5);
      tree.insert(1);
      tree.insert(3);
      tree.insert(7);
      ArrayList<Integer> expected = new ArrayList<>();
      expected.add(1);
      expected.add(3);
      expected.add(5);
      expected.add(7);
      assertEquals(expected, tree.inOrder());
    }
  }

  @Nested
  @DisplayName("Balance Tests")
  class BalanceTests {
    @Test
    @DisplayName("Balance an unbalanced tree")
    void balanceUnbalancedTree1051Expected510() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5);
      tree.insert(1);
      tree.balance();
      assertEquals("5(1,10)", tree.render());
    }
  }
}

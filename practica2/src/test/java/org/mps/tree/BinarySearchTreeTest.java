// Salma Boulagna Moreno
// Miguel Ángel Dorado Maldonado

package org.mps.tree;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

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
  }

  @Nested
  @DisplayName("Minimum and Maximum Tests")
  class MinMaxTests {
    @Test
    @DisplayName("Find minimum value")
    void minimumValueInTreeExpected5() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
      assertEquals(5, tree.minimum());
    }

    @Test
    @DisplayName("Find maximum value")
    void maximumValueInTreeExpected15() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
      assertEquals(15, tree.maximum());
    }
  }

  @Nested
  @DisplayName("Size and Depth Tests")
  class SizeAndDepthTests {
    @Test
    @DisplayName("Empty tree size is zero")
    void emptyTreeSizeExpected0() {
      assertEquals(0, tree.size());
    }

    @Test
    @DisplayName("Tree with zero return 0 depth")
    void treeDepthWithZeroNodesExpected0() {
      assertEquals(0, tree.depth());
    }

    @Test
    @DisplayName("Tree depth with multiple nodes")
    void treeDepthWithNodes105152Expected3() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
      tree.insert(2);
      assertEquals(3, tree.depth());
    }
  }

  @Nested
  @DisplayName("Removal Tests")
  class RemovalTests {
    @Test
    @DisplayName("Remove leaf node")
    void removeLeafNode5ExpectedNotContains5() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5);
      tree.removeValue(5);
      assertFalse(tree.contains(5));
    }

    @Test
    @DisplayName("Remove node with single child")
    void removeNodeWithSingleChild5ExpectedNotContains5() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5);
      tree.insert(2);
      tree.removeValue(5);
      assertFalse(tree.contains(5));
    }

    @Test
    @DisplayName("Remove node with two children")
    void removeNodeWithTwoChildren15ExpectedNotContains15() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
      tree.insert(12);
      tree.insert(20);
      tree.removeValue(15);
      assertFalse(tree.contains(15));
    }
  }

  @Nested
  @DisplayName("Traversal Tests")
  class TraversalTests {
    @Test
    @DisplayName("Inorder traversal")
    void inOrderTraversal10515ExpectedListOf51015() throws BinarySearchTreeException {
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
      List<Integer> expected = List.of(5, 10, 15);
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

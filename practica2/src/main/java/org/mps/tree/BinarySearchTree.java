package org.mps.tree;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class BinarySearchTree<T> implements BinarySearchTreeStructure<T> {
  private Comparator<T> comparator;
  private T value;
  private BinarySearchTree<T> left;
  private BinarySearchTree<T> right;

  public String render() {
    String render = "";

    if (value != null) {
      render += value.toString();
    }

    if (left != null || right != null) {
      render += "(";
      if (left != null) {
        render += left.render();
      }
      render += ",";
      if (right != null) {
        render += right.render();
      }
      render += ")";
    }

    return render;
  }

  public BinarySearchTree(Comparator<T> comparator) {
    this.comparator = comparator;
  }

  @Override
  public void insert(T value) throws BinarySearchTreeException {
    if (this.value == null) {
      this.value = value;
    } else {
      if (comparator.compare(value, this.value) < 0) {
        if (left == null) {
          left = new BinarySearchTree<T>(comparator);
        }
        left.insert(value);
      } else if (comparator.compare(value, this.value) > 0) {
        if (right == null) {
          right = new BinarySearchTree<T>(comparator);
        }
        right.insert(value);
      } else {
        throw new BinarySearchTreeException("Valor a insertar repetido");
      }
    }
  }

  @Override
  public boolean isLeaf() throws BinarySearchTreeException {
    if (this.value == null) {
      throw new BinarySearchTreeException("Arbol nulo");
    }
    if (left == null && right == null) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean contains(T value) {
    if (this.value == null) {
      return false;
    } else {
      if (comparator.compare(value, this.value) == 0) {
        return true;
      } else {
        if (comparator.compare(value, this.value) < 0) {
          if (left == null) {
            return false;
          } else {
            return left.contains(value);
          }
        } else {
          if (right == null) {
            return false;
          } else {
            return right.contains(value);
          }
        }
      }
    }
  }

  @Override
  public T minimum() throws BinarySearchTreeException {
    if (this.value == null) {
      throw new BinarySearchTreeException("No se encuentra valor minimo ya que el arbol es nulo");
    }
    if (left == null) {
      return value;
    } else {
      return left.minimum();
    }
  }

  @Override
  public T maximum() throws BinarySearchTreeException {
    if (this.value == null) {
      throw new BinarySearchTreeException("No se encuentra valor maximo ya que el arbol es nulo");
    }
    if (right == null) {
      return value;
    } else {
      return right.maximum();
    }
  }

  @Override
  public void removeBranch(T value) throws BinarySearchTreeException {
    if (!this.contains(value)) {
      throw new BinarySearchTreeException("No se encuentra el valor a eliminar");
    }

    if (this.value != null) {
      if (left != null && comparator.compare(value, this.left.value) == 0) {
        this.left = null;
      } else if (right != null && comparator.compare(value, this.right.value) == 0) {
        this.right = null;
      } else if (left != null && comparator.compare(value, this.left.value) < 0) {
        this.left.removeBranch(value);
      } else if (right != null && comparator.compare(value, this.right.value) > 0) {
        this.right.removeBranch(value);
      }
    }
  }

  @Override
  public int size() {
    if (this.value == null) {
      return 0;
    } else {
      int size = 1;
      if (left != null) {
        size += left.size();
      }
      if (right != null) {
        size += right.size();
      }
      return size;
    }
  }

  @Override
  public int depth() {
    if (this.value == null) {
      return 0;
    } else {
      int depth = 1;
      int leftDepth = 0;
      int rightDepth = 0;
      if (left != null) {
        leftDepth = left.depth();
      }
      if (right != null) {
        rightDepth = right.depth();
      }
      if (leftDepth > rightDepth) {
        depth += leftDepth;
      } else {
        depth += rightDepth;
      }
      return depth;
    }
  }

  @Override
  public void removeValue(T value) throws BinarySearchTreeException {
    if (this.value == null) {
      throw new BinarySearchTreeException("Árbol vacío");
    }
    if(!this.contains(value)) {
      throw new BinarySearchTreeException("Valor nulo");
    }

    int comparator = this.comparator.compare(value, this.value);
    if (comparator == 0) {

      if (this.left == null && this.right == null) {
        this.value = null;
      }
      else if (this.left == null) {
        this.value = this.right.value;
        this.left = this.right.left;
        this.right = this.right.right;
      } else if (this.right == null) {
        this.value = this.left.value;
        this.right = this.left.right;
        this.left = this.left.left;
      }
      else {
        T minRightSubtree = this.right.minimum();
        this.value = minRightSubtree;
        this.right.removeValue(minRightSubtree);
      }
    }
    if (comparator < 0 && this.left != null) {

      BinarySearchTree<T> i = this.left;
      if (this.left.comparator.compare(this.left.value, value) == 0) {
        this.left = null;
      }
      i.removeValue(value);
    } else if (comparator > 0 && this.right != null) {
      BinarySearchTree<T> i = this.right;

      if (this.right.comparator.compare(this.right.value, value) == 0) {
        this.right = null;
      }
      i.removeValue(value);
    }
  }


  @Override
  public List<T> inOrder() {
    List<T> list = new ArrayList<T>();
    if (this.value != null) {
      if (left != null) {
        list.addAll(left.inOrder());
      }
      list.add(this.value);
      if (right != null) {
        list.addAll(right.inOrder());
      }
    }
    return list;
  }

  private BinarySearchTree<T> buildBalancedTree(List<T> values, int start, int end) {
    if (start > end) {
      return null;
    }

    int mid = (start + end) / 2;
    BinarySearchTree<T> node = new BinarySearchTree<T>(comparator);
    node.value = values.get(mid);
    node.left = buildBalancedTree(values, start, mid - 1);
    node.right = buildBalancedTree(values, mid + 1, end);

    return node;
  }

  @Override
  public void balance() {
    List<T> values = inOrder();
    BinarySearchTree<T> balancedTree = buildBalancedTree(values, 0, values.size() - 1);
    this.value = balancedTree.value;
    this.left = balancedTree.left;
    this.right = balancedTree.right;
  }

}

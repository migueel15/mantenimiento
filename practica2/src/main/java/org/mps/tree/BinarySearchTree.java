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

    if (this.value == null) {
      throw new BinarySearchTreeException("Arbol nulo");
    }

    if (!this.contains(value)) {
      throw new BinarySearchTreeException("No se encuentra el valor a eliminar");
    }

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
      throw new BinarySearchTreeException("El árbol está vacío.");
    }

    BinarySearchTree<T> parent = null;
    BinarySearchTree<T> current = this;

    while (current != null) {
      int cmp = comparator.compare(value, current.value);
      if (cmp < 0) {
        parent = current;
        current = current.left;
      } else if (cmp > 0) {
        parent = current;
        current = current.right;
      } else {
        // Caso 1: Nodo hoja
        if (current.left == null && current.right == null) {
          if (parent == null) {
            this.value = null;
            this.left = null;
            this.right = null;
          } else if (parent.left == current) {
            parent.left = null;
          } else {
            parent.right = null;
          }
        }
        // Caso 2: Nodo con un solo hijo
        else if (current.left == null) {
          if (parent == null) {
            this.value = current.right.value;
            this.left = current.right.left;
            this.right = current.right.right;
          } else if (parent.left == current) {
            parent.left = current.right;
          } else {
            parent.right = current.right;
          }
        } else if (current.right == null) {
          if (parent == null) {
            this.value = current.left.value;
            this.right = current.left.right;
            this.left = current.left.left;
          } else if (parent.left == current) {
            parent.left = current.left;
          } else {
            parent.right = current.left;
          }
        }
        // Caso 3: Nodo con dos hijos
        else {
          BinarySearchTree<T> successorParent = current;
          BinarySearchTree<T> successor = current.right;
          while (successor.left != null) {
            successorParent = successor;
            successor = successor.left;
          }
          current.value = successor.value;
          if (successorParent.left == successor) {
            successorParent.left = successor.right;
          }
        }
        return;
      }
    }
    throw new BinarySearchTreeException("Valor no encontrado en el árbol.");
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

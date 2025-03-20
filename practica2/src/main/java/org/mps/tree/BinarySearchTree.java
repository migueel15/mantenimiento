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
	public void removeBranch(T value) {
		if (this.value != null) {
			if (comparator.compare(value, this.value) == 0) {
				this.value = null;
				this.left = null;
				this.right = null;
			} else {
				if (comparator.compare(value, this.value) < 0) {
					if (left != null) {
						left.removeBranch(value);
					}
				} else {
					if (right != null) {
						right.removeBranch(value);
					}
				}
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

	// Complex operations
	// (Estas operaciones se incluirán más adelante para ser realizadas en la
	// segunda
	// sesión de laboratorio de esta práctica)
}

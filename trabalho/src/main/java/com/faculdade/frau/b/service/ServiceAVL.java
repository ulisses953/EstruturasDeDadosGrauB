package com.faculdade.frau.b.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.faculdade.frau.b.Model.Avl.Node;

public class ServiceAVL<T extends Comparable<T>> {
    private static final Logger logger = LoggerFactory.getLogger(ServiceAVL.class);

    private Node<T> root;

    public Node<T> getRoot() {
        return root;
    }

    public ServiceAVL(Node<T> root) {
        this.root = root;
    }

    public ServiceAVL() {
    }

    /**
     * Insert a new node in the AVL tree
     * 
     * @param value
     * @param pointer
     * @return
     * @author Ulisses
     */
    public Node<T> insert(T value, int... pointer) {
        List<Integer> pointers = new ArrayList<>();
        for (int p : pointer) {
            pointers.add(p);
        }
        root = insertRecursive(value, root, pointers); // Atualiza o root da árvore
        return root;
    }

    /**
     * Recursive method to insert a new node in the AVL tree
     * 
     * @param value
     * @param root
     * @param pointer
     * @return
     * @Author Ulisses
     */
    protected Node<T> insertRecursive(T value, Node<T> root, List<Integer> pointer) {
        int BalanceFactorRoot, BalanceFactorLeft = 0, BalanceFactorRight = 0;

        if (value instanceof String && ((String) value).isEmpty()) {
            return root;
        }

        // Verifica se o valor é uma instância de Calendar e, se sim, impede a inserção
        // de datas iguais
        if (value instanceof Calendar) {
            Calendar newDate = (Calendar) value;
            if (root != null && root.getKey() instanceof Calendar) {
                Calendar existingDate = (Calendar) root.getKey();
                if (isSameDay(newDate, existingDate)) {
                    for (int p : pointer) {
                        root.addPointer(p);
                    }
                    return root;
                }
            }
        }

        if (value == null) {
            return root; // No value to insert
        }

        if (root == null) {
            root = new Node<>(value);
            for (int p : pointer) {
                root.addPointer(p);
            }
            return root;
        }
        if (value.compareTo(root.getKey()) < 0) {
            root.setLeft(insertRecursive(value, root.getLeft(), pointer));
        } else if (value.compareTo(root.getKey()) > 0) {
            root.setRight(insertRecursive(value, root.getRight(), pointer));
        }

        updateHeight(root);

        BalanceFactorRoot = calculateBalanceFactor(root);

        if (root.getLeft() != null) {
            BalanceFactorLeft = calculateBalanceFactor(root.getLeft());
        }
        if (root.getRight() != null) {
            BalanceFactorRight = calculateBalanceFactor(root.getRight());
        }

        // Left-Left (LL) Case
        if (BalanceFactorRoot > 1 && BalanceFactorLeft >= 0) {
            return rotateRight(root);
        }

        // Left-Right (LR) Case
        if (BalanceFactorRoot > 1 && BalanceFactorLeft < 0) {
            root.setLeft(rotateLeft(root.getLeft()));
            return rotateRight(root);
        }

        // Right-Right (RR) Case
        if (BalanceFactorRoot < -1 && BalanceFactorRight <= 0) {
            return rotateLeft(root);
        }

        // Right-Left (RL) Case
        if (BalanceFactorRoot < -1 && BalanceFactorRight > 0) {
            root.setRight(rotateRight(root.getRight()));
            return rotateLeft(root);
        }

        updateAllHeights();

        return root;
    }

    /**
     * Performs a left rotation on the given node.
     * 
     * @param root The root node to rotate.
     * @return The new root after rotation.
     */
    private Node<T> rotateLeft(Node<T> root) {
        if (root == null || root.getRight() == null) {
            return root; // nothing to rotate
        }

        Node<T> newRoot = root.getRight();
        Node<T> temp = newRoot.getLeft();

        // Perform rotation
        newRoot.setLeft(root);
        root.setRight(temp);

        // Update heights
        updateHeight(root);
        updateHeight(newRoot);

        // Return new root
        return newRoot;
    }

    /**
     * Performs a right rotation on the given node.
     * 
     * @param root The root node to rotate.
     * @return The new root after rotation.
     */
    private Node<T> rotateRight(Node<T> root) {
        if (root == null || root.getLeft() == null) {
            return root; // nothing to rotate
        }

        Node<T> newRoot = root.getLeft();
        Node<T> temp = newRoot.getRight();

        // Perform rotation
        newRoot.setRight(root);
        root.setLeft(temp);

        // Update heights
        updateHeight(root);
        updateHeight(newRoot);

        // Return new root
        return newRoot;
    }

    protected void updateHeight(Node<T> node) {
        if (node == null)
            return;
        int leftHeight = (node.getLeft() != null) ? node.getLeft().getHeight() : 0;
        int rightHeight = (node.getRight() != null) ? node.getRight().getHeight() : 0;
        node.setHeight(Math.max(leftHeight, rightHeight)+1);

    }

    public Node<T> findNode(T value) {
        return findNodeRecursive(value, root);
    }

    protected Node<T> findNodeRecursive(T value, Node<T> node) {
        if (node == null || value == null) {
            return null;
        }
        if (value.compareTo(node.getKey()) < 0) {
            return findNodeRecursive(value, node.getLeft());
        } else if (value.compareTo(node.getKey()) > 0) {
            return findNodeRecursive(value, node.getRight());
        } else {
            return node; // Found the node
        }
    }

    public int calculateBalanceFactor(Node<T> node) {
        if (node == null)
            return 0;
        return (node.getLeft() != null ? node.getLeft().getHeight() : 0)
                - (node.getRight() != null ? node.getRight().getHeight() : 0);
    }

    public static boolean isSameDay(Calendar c1, Calendar c2) {
        if (c1 == null || c2 == null)
            return false;
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
    }

    public Node<T> deleteNode(T value) {
        root = deleteNodeRecursive(value, root);
        return root;
    }

    public Node<T> deleteNodeRecursive(T value, Node<T> node) {
        if (node == null) {
            return null;
        }

        if (value.compareTo(node.getKey()) < 0) {
            node.setLeft(deleteNodeRecursive(value, node.getLeft()));
        } else if (value.compareTo(node.getKey()) > 0) {
            node.setRight(deleteNodeRecursive(value, node.getRight()));
        } else {
            // Node with only one child or no child
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }

            Node<T> temp = findMax(node.getLeft());
            node.setKey(temp.getKey());
            node.setLeft(deleteNodeRecursive(temp.getKey(), node.getLeft()));
        }
        // Update height of the current node

        updateHeight(node);

        // Check balance factor

        int balance = calculateBalanceFactor(node);
        int balanceLeft = (node.getLeft() != null) ? calculateBalanceFactor(node.getLeft()) : 0;
        int balanceRight = (node.getRight() != null) ? calculateBalanceFactor(node.getRight()) : 0;

        // Left-Left (LL) Case
        if (balance > 1 && balanceLeft >= 0) {
            return rotateRight(node);
        }
        // Left-Right (LR) Case
        if (balance > 1 && balanceLeft < 0) {
            node.setLeft(rotateLeft(node.getLeft()));
            return rotateRight(node);
        }
        // Right-Right (RR) Case
        if (balance < -1 && balanceRight <= 0) {
            return rotateLeft(node);
        }
        // Right-Left (RL) Case
        if (balance < -1 && balanceRight > 0) {
            node.setRight(rotateRight(node.getRight()));
            return rotateLeft(node);
        }
        return node;

    }

    private Node<T> findMax(Node<T> left) {
        Node<T> current = left;
        while (current.getRight() != null) {
            current = current.getRight();
        }
        return current;
    }

    public Node<T> findMin(Node<T> right) {
        Node<T> current = right;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    public void updateAllHeights() {
        updateAllHeightsRecursive(root);
    }

    private void updateAllHeightsRecursive(Node<T> node) {
        if (node == null)
            return;
        updateAllHeightsRecursive(node.getLeft());
        updateAllHeightsRecursive(node.getRight());
        updateHeight(node);
    }

}

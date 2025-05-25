package com.faculdade.frau.b.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.faculdade.frau.b.Model.Avl.Node;

public class ServiceAVL<T extends Comparable<T>> {
    private Node<T> root;

    public Node<T> getRoot() {
        return root;
    }
    /**
     * Insert a new node in the AVL tree
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

        // Verifica se o valor é uma instância de Calendar e, se sim, impede a inserção de datas iguais
        if (value instanceof Calendar) {
            Calendar newCal = (Calendar) value;
            if (root != null && root.getKey() instanceof Calendar) {
            Calendar rootCal = (Calendar) root.getKey();
            if (newCal.compareTo(rootCal) == 0) {
                return root; // Não insere datas iguais
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

        return root;
    }
    
    /**
     * Performs a left rotation on the given node.
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

    protected void updateHeight(Node<T> node){
        if (node == null) return;
        int leftHeight = (node.getLeft() != null) ? node.getLeft().getHeight() : 0;
        int rightHeight = (node.getRight() != null) ? node.getRight().getHeight() : 0;
        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
    }

    public int calculateBalanceFactor(Node<T> node) {
        if (node == null) return 0;
        return (node.getLeft() != null ? node.getLeft().getHeight() : 0) - (node.getRight() != null ? node.getRight().getHeight() : 0);
    }

    public ServiceAVL(Node<T> root) {
        this.root = root;
    }

    public ServiceAVL() {
    }
    

    
}

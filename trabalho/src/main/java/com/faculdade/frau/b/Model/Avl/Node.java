package com.faculdade.frau.b.Model.Avl;

import java.util.ArrayList;

public class Node<T extends Comparable<T>> {
    private T key;
    private Node<T> left;
    private Node<T> right;
    private int height;
    private ArrayList<T> pointer;  

    public Node(T key) {
        this.key = key;
        this.left = null;
        this.right = null;
        this.height = 1; // Inicializa a altura do nó como 1
        this.pointer = new ArrayList<>();
    }

    public T getKey() {
        return key;
    }
    public void setKey(T key) {
        this.key = key;
    }
    public Node<T> getLeft() {
        return left;
    }
    public void setLeft(Node<T> left) {
        this.left = left;
    }
    public Node<T> getRight() {
        return right;
    }
    public void setRight(Node<T> right) {
        this.right = right;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public ArrayList<T> getPointer() {
        return pointer;
    }
    public void setPointer(ArrayList<T> pointer) {
        this.pointer = pointer;
    }
    public void addPointer(T pointer) {
        this.pointer.add(pointer);
    }
    public void removePointer(T pointer) {
        this.pointer.remove(pointer);
    }
    public boolean containsPointer(T pointer) {
        return this.pointer.contains(pointer);
    }
    public void clearPointer() {
        this.pointer.clear();
    }


}

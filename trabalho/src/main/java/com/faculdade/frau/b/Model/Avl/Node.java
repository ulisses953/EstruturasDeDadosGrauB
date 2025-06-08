package com.faculdade.frau.b.Model.Avl;

import java.util.ArrayList;


public class Node<T extends Comparable<T>> {
    private T key;
    private Node<T> left;
    private Node<T> right;
    private int height;
    private ArrayList<Integer> pointer;

    public Node(T key) {
        this.pointer = new ArrayList<>();
        this.key = key;
        this.left = null;
        this.right = null;
        this.height = 1; // Inicializa a altura do nó como 1
    }

    public T getKey() {
        return key;
    }
    public void setKey(T key) {
        if (key instanceof String && ((String) key).isEmpty()) {
            this.key = null; // Se a chave for 
            
        }

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
    public ArrayList<Integer> getPointer() {
        return pointer;
    }
    public void setPointer(ArrayList<Integer> pointer) {
        this.pointer = pointer;
    }
    public void addPointer(int pointer) {
        this.pointer.add(pointer);
    }
    public void removePointer(int pointer) {
        this.pointer.remove(Integer.valueOf(pointer));
    }

    @Override
    public String toString() {
        return "Node{" +
                "key=" + key +
                ", left=" + left +
                ", right=" + right +
                ", height=" + height +
                ", pointer=" + pointer +
                '}';
    }
}

package org.binarytree;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree<T> {

    private Node<T> root;

    public void add(int key, T data) {
        if(root == null)
            root = new Node<>(key, data);
        else
            insertNode(key, new Node<>(key, data));
    }

    public Node<T> findNode(int key) {
        Node<T> focusNode = root;

        while(focusNode.key != key) {
            if(key < focusNode.key) focusNode = focusNode.left;
            else focusNode = focusNode.right;
            if (focusNode == null) return null;
        }
        return focusNode;
    }

    public List<Node<T>> inOrderTraverse(Node<T> focusNode) {
        return inOrderTraverse(focusNode, new ArrayList<>());
    }

    public List<Node<T>> postOrderTraverse(Node<T> focusNode) {
        return postOrderTraverse(focusNode, new ArrayList<>());
    }

    public List<Node<T>> preOrderTraverse(Node<T> focusNode) {
        return preOrderTraverse(focusNode, new ArrayList<>());
    }


    public boolean remove(int key) {
        Node<T> current = root;
        Node<T> parent = root;

        boolean leftNode = true;

        while (current.key != key) {
            parent = current;
            if(key < current.key) {
                leftNode = true;
                current = current.left;
            } else {
                leftNode = false;
                current = current.right;
            }
            if(current == null) return false;
        }
        if (current.left == null && current.right == null) resetHead(current, leftNode, parent);
        else if(current.right == null) shift(current, current.left, leftNode, parent);
        else if(current.left == null) shift(current, current.right, leftNode, parent);
        else extract(current, leftNode, parent);
        return true;
    }

    private void extract(Node<T> focusNode, boolean isALeftChild, Node<T> parent) {
        Node<T> replacement = getReplacement(focusNode);
        shift(focusNode, replacement, isALeftChild, parent);
        replacement.left = focusNode.left;
    }

    private Node<T> getReplacement(Node<T> replacedNode) {
        Node<T> replacementParent = replacedNode;
        Node<T> replacement = replacedNode;
        Node<T> focusNode = replacedNode.right;
        while (focusNode != null) {
            replacementParent = replacement;
            replacement = focusNode;
            focusNode = focusNode.left;
        }
        if(replacement != replacedNode.right) {
            replacementParent.left = replacement.right;
            replacement.right = replacedNode.right;
        }
        return replacement;
    }

    private static <T>Node<T> insertLeft(Node<T> newNode, Node<T> focusNode, Node<T> parent) {
        if (focusNode == null) parent.left = newNode;
        return focusNode;
    }

    private static <T>Node<T> insertRight(Node<T> newNode, Node<T> focusNode, Node<T> parent) {
        if(focusNode == null) parent.right = newNode;
        return focusNode;
    }

    private void insertNode(int key, Node<T> newNode) {
        Node<T> focusNode = root;
        while (true) {
            if(key < focusNode.key) focusNode = insertLeft(newNode, focusNode.left, focusNode);
            else focusNode = insertRight(newNode, focusNode.right, focusNode);
            if (focusNode == null) return;
        }
    }

    private List<Node<T>> inOrderTraverse(Node<T> focusNode, List<Node<T>> items) {
        if (focusNode != null) {
            inOrderTraverse(focusNode.left, items);
            items.add(focusNode);
            inOrderTraverse(focusNode.right, items);
        }
        return items;
    }

    private List<Node<T>> postOrderTraverse(Node<T> focusNode, List<Node<T>> items) {
        if (focusNode != null) {
            postOrderTraverse(focusNode.left, items);
            postOrderTraverse(focusNode.right, items);
            items.add(focusNode);
        }
        return items;
    }

    private List<Node<T>> preOrderTraverse(Node<T> focusNode, List<Node<T>> items) {
        if (focusNode != null) {
            items.add(focusNode);
            preOrderTraverse(focusNode.left, items);
            preOrderTraverse(focusNode.right, items);
        }
        return items;
    }

    private void resetHead(Node<T> focusNode, boolean isALeftChild, Node<T> parent) {
        shift(focusNode, null, isALeftChild, parent);
    }

    private void shift(Node<T> focusNode, Node<T> nextNode, boolean isALeftChild, Node<T> parent) {
        if (focusNode == root) root = nextNode;
        else if (isALeftChild) parent.left = nextNode;
        else parent.right = nextNode;
    }

    public static class Node<T> {

        Node<T> left;
        Node<T> right;

        int key;
        T data;

        Node(int key, T data) {
            this.key = key;
            this.data = data;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value='" + data + '\'' +
                    '}';
        }
    }
}

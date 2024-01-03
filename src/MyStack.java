
import java.util.EmptyStackException;

public class MyStack<T> {
    private Node<T> head;
    private int length;

    public MyStack() {
        head = null;
        length = 0;
    }
    public boolean isEmpty() {
        return length == 0;
    }

    public void push(T data) {
        Node<T> temp = new Node<>(data);
        temp.next = head;
        head = temp;
        length++;
    }

    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        } else if (head.next == null) {
            length--;
            T finalVal = head.data;
            head = null;
            return finalVal;
        } else {
            length--;
            T finalVal = head.data;
            head = head.next;
            return finalVal;
        }
    }

    private class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
}
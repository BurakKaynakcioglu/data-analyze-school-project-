public class MyQueue <T>{

    Node4<T> bas;
    Node4<T> son;

    public MyQueue(){
        bas = null;
        son = null;

    }
    public boolean bosMu() {
        return bas == null;
    }

    public T dequeue() { // eleman çıkarmak için elemanı return eder...
        if (bosMu()) {
            return null;
        }
        T data = bas.getData();
        bas = bas.next;
        if (bas == null) {
            son = null;
        }
        return data;
    }

    public void enqueue(T element) { // eleman eklemek için...

        Node4<T> newNode = new Node4<>(element);
        if (bosMu()) {
            bas = newNode;
            son = newNode;
        } else {
            son.next = newNode;
            son = newNode;
        }
    }

    public class Node4<T> {
        private T data;
        private Node4<T> next;

        public Node4(T data) {
            this.data = data;
            this.next = null;
        }

        public T getData() {
            return data;
        }
    }
}
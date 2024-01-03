
public class MyHashMap<K, V> {
    private int SIZE = 0;

    private Node<K, V> table[];

    public MyHashMap() {
        table = new Node[4];
    }

    public void put(K key, V value) {
        if (SIZE >= table.length)  {
            resize();
        }

        int index = key.hashCode() % table.length;
        index = Math.abs(index);
        Node<K ,V> node = table[index];

        if (node == null) {
            table[index] = new Node<>(key, value);
        } else {
            while (node.next != null) {
                if (node.getKey().equals(key)) {
                    node.setValue(value);
                    return;
                }

                node = node.next;
            }

            if (node.getKey().equals(key)) {
                node.setValue(value);
                return;
            }

            node.next = new Node<>(key, value);
        }
        SIZE++;
    }

    public V get(K key) {
        int index = key.hashCode() % table.length;
        index = Math.abs(index);
        Node<K ,V> node = table[index];

        if (node == null) {
            return null;
        }
        while (node != null) {
            if (node.getKey().equals(key)) {
                return node.getValue();
            }

            node = node.next;
        }

        return null;
    }

    private void resize() {
        Node<K, V>[] oldTable = table;
        table = new Node[SIZE * 2];
        SIZE = 0;

        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] == null){
                continue;
            }

            Node<K, V> Node = oldTable[i];
            while (Node != null){
                put(Node.key, Node.value);
                Node = Node.next;
            }
        }
    }

    public MyArrayList<String> findMaxValueForInt() {
        int max = 0;
        Node<K, V> node = null;
        MyArrayList<String> myArrayList = new MyArrayList<>();

        for (int i = 0; i < table.length; i++) {
            Node<K, V> currentNode = table[i];
            while (currentNode != null) {
                if ((int) currentNode.value > max) {
                    max = (int) currentNode.value;
                    node = currentNode;
                }
                currentNode = currentNode.next;
            }
        }

        myArrayList.add((String) node.key);
        myArrayList.add(node.value.toString());
        return myArrayList;
    }

    public String findMostUsedHashtag() {
        int max = 0;
        String mostUsedHashtag = "";

        for (int i = 0; i < table.length; i++) {
            Node<K, V> currentNode = table[i];
            while (currentNode != null) {
                if ((int) currentNode.value > max) {
                    max = (int) currentNode.value;
                    mostUsedHashtag = (String) currentNode.key;
                }
                currentNode = currentNode.next;
            }
        }

        return mostUsedHashtag;
    }

    public MyArrayList<K> keySet() {
        MyArrayList<K> keys = new MyArrayList<>();

        for (int i = 0; i < table.length; i++) {
            Node<K, V> currentNode = table[i];
            while (currentNode != null) {
                keys.add(currentNode.key);
                currentNode = currentNode.next;
            }
        }

        return keys;
    }

    private class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}
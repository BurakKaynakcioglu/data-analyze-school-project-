
public class MyArrayList<T>{
    private Object[] array;
    private int SIZE;
    private int CAPACITY = 4;

    public MyArrayList(){
        this.SIZE = 0;
        this.array = new Object[CAPACITY];
    }

    public void add(T object) {
        if (SIZE == array.length) {
            resize();
        }

        this.array[SIZE++] = object;
    }

    public T get(int index) {
        if (index < 0 || index >= this.SIZE) {
            throw new IndexOutOfBoundsException();
        }
        return (T) array[index];
    }

    public int size() {
        return this.SIZE;
    }

    private void resize() {
        CAPACITY *= 2;
        Object[] newArray = new Object[CAPACITY];
        System.arraycopy(array, 0, newArray, 0, SIZE);
        array = newArray;
    }

    public boolean isContains(T object) {
        for (int i = 0; i < SIZE; i++) {
            if (array[i].equals(object)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        for (int i = 0; i < SIZE; i++) {
            if (i == SIZE - 1) {
                sb.append(array[i]).append("\"");
            } else {
                sb.append(array[i]).append(", ");
            }
        }

        return sb.toString();
    }
}
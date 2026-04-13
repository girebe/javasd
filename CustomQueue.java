public class CustomQueue {
    private String[] data;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    public CustomQueue(int capacity) {
        this.capacity = capacity;
        this.data = new String[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    public void enqueue(String value) {
        if (size == capacity) {
            System.out.println("Queue is full.");
            return;
        }
        rear = (rear + 1) % capacity;
        data[rear] = value;
        size++;
    }

    public String dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return null;
        }
        String value = data[front];
        front = (front + 1) % capacity;
        size--;
        return value;
    }

    public String peek() {
        if (isEmpty()) return null;
        return data[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void display() {
        if (isEmpty()) { System.out.println("Queue is empty."); return; }
        System.out.print("Queue: ");
        for (int i = 0; i < size; i++)
            System.out.print(data[(front + i) % capacity] + (i < size - 1 ? " -> " : ""));
        System.out.println();
    }
}
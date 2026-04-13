public class CustomStack {
    private String[] data;
    private int top;
    private int capacity;

    public CustomStack(int capacity) {
        this.capacity = capacity;
        this.data = new String[capacity];
        this.top = -1;
    }

    public void push(String value) {
        if (top == capacity - 1) {
            System.out.println("Stack is full.");
            return;
        }
        data[++top] = value;
    }

    public String pop() {
        if (isEmpty()) {
            System.out.println("Stack is empty.");
            return null;
        }
        return data[top--];
    }

    public String peek() {
        if (isEmpty()) return null;
        return data[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }

    public void display() {
        if (isEmpty()) { System.out.println("Stack is empty."); return; }
        System.out.println("Transaction History (most recent first):");
        for (int i = top; i >= 0; i--)
            System.out.println((top - i + 1) + ". " + data[i]);
    }
}
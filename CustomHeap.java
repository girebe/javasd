public class CustomHeap {
    private BankAccount[] data;
    private int size;
    private int capacity;

    public CustomHeap(int capacity) {
        this.capacity = capacity;
        this.data = new BankAccount[capacity];
        this.size = 0;
    }

    // Insert a new account into the heap
    public void insert(BankAccount acc) {
        if (size == capacity) {
            System.out.println("Heap is full.");
            return;
        }
        data[size] = acc;
        size++;
        heapifyUp(size - 1);
    }

    // Remove and return the account with minimum balance
    public BankAccount extractMin() {
        if (isEmpty()) {
            System.out.println("Heap is empty.");
            return null;
        }
        BankAccount min = data[0];
        data[0] = data[size - 1];
        size--;
        heapifyDown(0);
        return min;
    }

    // Peek at minimum without removing
    public BankAccount peekMin() {
        if (isEmpty()) return null;
        return data[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // Bubble up to maintain heap property
    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (data[parent].getBalance() > data[index].getBalance()) {
                swap(parent, index);
                index = parent;
            } else {
                break;
            }
        }
    }

    // Bubble down to maintain heap property
    private void heapifyDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && data[left].getBalance() < data[smallest].getBalance())
                smallest = left;
            if (right < size && data[right].getBalance() < data[smallest].getBalance())
                smallest = right;

            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        BankAccount temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public void display() {
        if (isEmpty()) { System.out.println("Heap is empty."); return; }
        System.out.println("Heap (sorted by balance, lowest first):");
        for (int i = 0; i < size; i++)
            System.out.println((i + 1) + ". " + data[i].getUsername() + " -- Balance: " + data[i].getBalance());
    }
}
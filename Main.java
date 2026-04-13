import java.util.LinkedList;  // only used for accounts LinkedList (allowed)

public class Main {

    static LinkedList<BankAccount> accounts = new LinkedList<>();

    // Custom implementations — no java.util.Stack / Queue / PriorityQueue
    static CustomStack transactionHistory = new CustomStack(100);
    static CustomQueue billQueue = new CustomQueue(50);
    static CustomQueue accountRequests = new CustomQueue(50);
    static CustomHeap balanceHeap = new CustomHeap(100);

    static String[] pendingNames = new String[50];
    static double[] pendingBalances = new double[50];
    static int pendingCount = 0;

    static int accountCounter = 1001;
    static java.util.Scanner scanner = new java.util.Scanner(System.in);

    // ---------- Task 1: LinkedList ----------
    static void addAccount() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter initial balance: ");
        double balance = Double.parseDouble(scanner.nextLine().trim());
        BankAccount acc = new BankAccount("ACC" + accountCounter++, username, balance);
        accounts.add(acc);
        balanceHeap.insert(acc);
        System.out.println("Account added successfully");
    }

    static void displayAllAccounts() {
        if (accounts.isEmpty()) { System.out.println("No accounts found."); return; }
        System.out.println("\nAccounts List:");
        int i = 1;
        for (BankAccount a : accounts)
            System.out.println(i++ + ". " + a.getUsername() + " -- Balance: " + a.getBalance());
    }

    static BankAccount searchByUsername(String username) {
        for (BankAccount a : accounts)
            if (a.getUsername().equalsIgnoreCase(username)) return a;
        return null;
    }

    // ---------- Task 2: Deposit & Withdraw ----------
    static void deposit() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        BankAccount acc = searchByUsername(username);
        if (acc == null) { System.out.println("Account not found."); return; }
        System.out.print("Enter deposit amount: ");
        double amount = Double.parseDouble(scanner.nextLine().trim());
        acc.setBalance(acc.getBalance() + amount);
        transactionHistory.push("Deposit " + amount + " to " + username);
        System.out.println("New balance: " + acc.getBalance());
    }

    static void withdraw() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        BankAccount acc = searchByUsername(username);
        if (acc == null) { System.out.println("Account not found."); return; }
        System.out.print("Enter withdrawal amount: ");
        double amount = Double.parseDouble(scanner.nextLine().trim());
        if (amount > acc.getBalance()) { System.out.println("Insufficient funds."); return; }
        acc.setBalance(acc.getBalance() - amount);
        transactionHistory.push("Withdraw " + amount + " from " + username);
        System.out.println("New balance: " + acc.getBalance());
    }

    // ---------- Task 3: Bill Payment via Stack ----------
    static void addBillPayment() {
        System.out.print("Enter bill description: ");
        String desc = scanner.nextLine().trim();
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        BankAccount acc = searchByUsername(username);
        if (acc == null) { System.out.println("Account not found."); return; }
        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine().trim());
        if (amount > acc.getBalance()) { System.out.println("Insufficient funds."); return; }
        acc.setBalance(acc.getBalance() - amount);
        transactionHistory.push("Bill payment " + amount + " [" + desc + "] from " + username);
        System.out.println("Bill payment recorded.");
    }

    static void undoLastTransaction() {
        String removed = transactionHistory.pop();
        if (removed != null) System.out.println("Undo -> " + removed + " removed");
    }

    // ---------- Task 4: Bill Queue (Custom Queue — FIFO) ----------
    static void addBillToQueue() {
        System.out.print("Enter bill name: ");
        String bill = scanner.nextLine().trim();
        billQueue.enqueue(bill);
        System.out.println("Added: " + bill);
    }

    static void processNextBill() {
        String bill = billQueue.dequeue();
        if (bill != null) {
            System.out.println("Processing: " + bill);
            String next = billQueue.peek();
            System.out.println(next != null ? "Remaining next: " + next : "Queue is now empty.");
        }
    }

    // ---------- Task 5: Account Opening Queue ----------
    static void submitAccountRequest() {
        System.out.print("Enter requested username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter initial balance: ");
        double balance = Double.parseDouble(scanner.nextLine().trim());
        pendingNames[pendingCount] = username;
        pendingBalances[pendingCount] = balance;
        accountRequests.enqueue(String.valueOf(pendingCount));
        pendingCount++;
        System.out.println("Request submitted for: " + username);
    }

    static void processNextAccountRequest() {
        String indexStr = accountRequests.dequeue();
        if (indexStr == null) { System.out.println("No pending requests."); return; }
        int idx = Integer.parseInt(indexStr);
        BankAccount approved = new BankAccount("ACC" + accountCounter++, pendingNames[idx], pendingBalances[idx]);
        accounts.add(approved);
        balanceHeap.insert(approved);
        System.out.println("Approved and created: " + approved);
    }

    static void displayPendingRequests() {
        if (accountRequests.isEmpty()) { System.out.println("No pending requests."); return; }
        System.out.println("Pending count: " + accountRequests.size());
        accountRequests.display();
    }

    // ---------- Heap ----------
    static void showMinBalance() {
        BankAccount min = balanceHeap.peekMin();
        if (min != null) System.out.println("Lowest balance: " + min.getUsername() + " -- " + min.getBalance());
        else System.out.println("No accounts in heap.");
    }

    // =========================================================
    // PART 2 — PHYSICAL
    // =========================================================
    static void task6PhysicalArray() {
        System.out.println("\n--- Task 6: Physical Array Demo ---");
        BankAccount[] arr = new BankAccount[3];
        arr[0] = new BankAccount("PA001", "Alice", 300000);
        arr[1] = new BankAccount("PA002", "Bob", 150000);
        arr[2] = new BankAccount("PA003", "Charlie", 500000);
        for (int i = 0; i < arr.length; i++)
            System.out.println("[" + i + "] " + arr[i]);
    }

    // =========================================================
    // PART 3 — MENUS
    // =========================================================
    static void bankMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== BANK MENU ===");
            System.out.println("1. Submit account request");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Pay bill (adds to Stack history)");
            System.out.println("5. View transaction history");
            System.out.println("6. Undo last transaction");
            System.out.println("7. Search account");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String c = scanner.nextLine().trim();
            switch (c) {
                case "1": submitAccountRequest(); break;
                case "2": deposit(); break;
                case "3": withdraw(); break;
                case "4": addBillPayment(); break;
                case "5": transactionHistory.display(); break;
                case "6": undoLastTransaction(); break;
                case "7": {
                    System.out.print("Username: ");
                    BankAccount a = searchByUsername(scanner.nextLine().trim());
                    System.out.println(a != null ? "Found: " + a : "Not found.");
                    break;
                }
                case "0": running = false; break;
                default: System.out.println("Invalid input.");
            }
        }
    }

    static void atmMenu() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        BankAccount acc = searchByUsername(username);
        if (acc == null) { System.out.println("Account not found."); return; }
        boolean running = true;
        while (running) {
            System.out.println("\n=== ATM MENU ===");
            System.out.println("1. Balance enquiry");
            System.out.println("2. Withdraw");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String c = scanner.nextLine().trim();
            switch (c) {
                case "1": System.out.println("Balance: " + acc.getBalance()); break;
                case "2": {
                    System.out.print("Amount: ");
                    double amount = Double.parseDouble(scanner.nextLine().trim());
                    if (amount > acc.getBalance()) { System.out.println("Insufficient funds."); break; }
                    acc.setBalance(acc.getBalance() - amount);
                    transactionHistory.push("ATM Withdraw " + amount + " from " + username);
                    System.out.println("Dispensing " + amount + ". New balance: " + acc.getBalance());
                    break;
                }
                case "0": running = false; break;
                default: System.out.println("Invalid input.");
            }
        }
    }

    static void adminMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. View pending account requests");
            System.out.println("2. Process next account request");
            System.out.println("3. View bill queue");
            System.out.println("4. Process next bill");
            System.out.println("5. Add bill to queue");
            System.out.println("6. View all accounts");
            System.out.println("7. Add account manually");
            System.out.println("8. Show account with lowest balance (Heap)");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String c = scanner.nextLine().trim();
            switch (c) {
                case "1": displayPendingRequests(); break;
                case "2": processNextAccountRequest(); break;
                case "3": billQueue.display(); break;
                case "4": processNextBill(); break;
                case "5": addBillToQueue(); break;
                case "6": displayAllAccounts(); break;
                case "7": addAccount(); break;
                case "8": showMinBalance(); break;
                case "0": running = false; break;
                default: System.out.println("Invalid input.");
            }
        }
    }

    public static void main(String[] args) {
        BankAccount a1 = new BankAccount("ACC1001", "Ali", 150000);
        BankAccount a2 = new BankAccount("ACC1002", "Sara", 220000);
        accounts.add(a1); balanceHeap.insert(a1);
        accounts.add(a2); balanceHeap.insert(a2);
        accountCounter = 1003;

        task6PhysicalArray();

        boolean running = true;
        while (running) {
            System.out.println("\n=============================");
            System.out.println("    MINI BANKING SYSTEM      ");
            System.out.println("=============================");
            System.out.println("1. Enter Bank");
            System.out.println("2. Enter ATM");
            System.out.println("3. Admin Area");
            System.out.println("4. Exit");
            System.out.print("Choose: ");
            String c = scanner.nextLine().trim();
            switch (c) {
                case "1": bankMenu(); break;
                case "2": atmMenu(); break;
                case "3": adminMenu(); break;
                case "4": System.out.println("Goodbye!"); running = false; break;
                default: System.out.println("Invalid input.");
            }
        }
        scanner.close();
    }
}
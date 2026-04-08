import java.util.*;

public class Main {

    static LinkedList<BankAccount> accounts = new LinkedList<>();
    static Stack<String> transactionHistory = new Stack<>();
    static Queue<String> billQueue = new LinkedList<>();
    static Queue<BankAccount> accountRequests = new LinkedList<>();

    static int accountCounter = 1001;

    static Scanner scanner = new Scanner(System.in);
    static void addAccount() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter initial balance: ");
        double balance = Double.parseDouble(scanner.nextLine().trim());

        BankAccount acc = new BankAccount("ACC" + accountCounter++, username, balance);
        accounts.add(acc);
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
    static void deposit() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        BankAccount acc = searchByUsername(username);
        if (acc == null) { System.out.println("Account not found."); return; }

        System.out.print("Enter deposit amount: ");
        double amount = Double.parseDouble(scanner.nextLine().trim());
        acc.setBalance(acc.getBalance() + amount);

        String tx = "Deposit " + amount + " to " + username;
        transactionHistory.push(tx);
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
        String tx = "Withdraw " + amount + " from " + username;
        transactionHistory.push(tx);
        System.out.println("New balance: " + acc.getBalance());
    }

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
        String tx = "Bill payment " + amount + " [" + desc + "] from " + username;
        transactionHistory.push(tx);
        System.out.println("Bill payment added.");
    }

    static void undoLastTransaction() {
        if (transactionHistory.isEmpty()) { System.out.println("No transactions to undo."); return; }
        String removed = transactionHistory.pop();
        System.out.println("Undo → " + removed + " removed");
    }

    static void peekLastTransaction() {
        if (transactionHistory.isEmpty()) { System.out.println("No transactions yet."); return; }
        System.out.println("Last transaction: " + transactionHistory.peek());
    }

    static void displayTransactionHistory() {
        if (transactionHistory.isEmpty()) { System.out.println("No transaction history."); return; }
        System.out.println("\nTransaction History (most recent first):");
        List<String> list = new ArrayList<>(transactionHistory);
        Collections.reverse(list);
        int i = 1;
        for (String t : list) System.out.println(i++ + ". " + t);
    }

    static void addBillToQueue() {
        System.out.print("Enter bill name: ");
        String bill = scanner.nextLine().trim();
        billQueue.offer(bill);
        System.out.println("Added: " + bill);
    }

    static void processNextBill() {
        if (billQueue.isEmpty()) { System.out.println("No bills in queue."); return; }
        System.out.println("Processing: " + billQueue.poll());
        if (!billQueue.isEmpty()) System.out.println("Remaining: " + billQueue.peek());
        else System.out.println("Queue is now empty.");
    }

    static void displayBillQueue() {
        if (billQueue.isEmpty()) { System.out.println("Bill queue is empty."); return; }
        System.out.println("Bill Queue: " + billQueue);
    }

    static void submitAccountRequest() {
        System.out.print("Enter requested username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter initial balance: ");
        double balance = Double.parseDouble(scanner.nextLine().trim());

        BankAccount req = new BankAccount("PENDING", username, balance);
        accountRequests.offer(req);
        System.out.println("Request submitted for: " + username);
    }

    static void processNextAccountRequest() {
        if (accountRequests.isEmpty()) { System.out.println("No pending requests."); return; }
        BankAccount req = accountRequests.poll();
        BankAccount approved = new BankAccount("ACC" + accountCounter++, req.getUsername(), req.getBalance());
        accounts.add(approved);
        System.out.println("Approved and created: " + approved);
    }

    static void displayPendingRequests() {
        if (accountRequests.isEmpty()) { System.out.println("No pending requests."); return; }
        System.out.println("Pending Requests:");
        int i = 1;
        for (BankAccount r : accountRequests)
            System.out.println(i++ + ". " + r.getUsername() + " -- Requested Balance: " + r.getBalance());
    }

    static void task6PhysicalArray() {
        System.out.println("\n--- Task 6: Physical Array Demo ---");
        BankAccount[] physicalAccounts = new BankAccount[3];
        physicalAccounts[0] = new BankAccount("PA001", "Alice", 300000);
        physicalAccounts[1] = new BankAccount("PA002", "Bob", 150000);
        physicalAccounts[2] = new BankAccount("PA003", "Charlie", 500000);

        System.out.println("Array BankAccount[3]:");
        for (int i = 0; i < physicalAccounts.length; i++)
            System.out.println("[" + i + "] " + physicalAccounts[i]);
    }


    static void bankMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== BANK MENU ===");
            System.out.println("1. Submit account opening request");
            System.out.println("2. Deposit money");
            System.out.println("3. Withdraw money");
            System.out.println("4. Pay bill (with transaction history)");
            System.out.println("5. View transaction history");
            System.out.println("6. Undo last transaction");
            System.out.println("7. Search account by username");
            System.out.println("0. Back to main menu");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": submitAccountRequest(); break;
                case "2": deposit(); break;
                case "3": withdraw(); break;
                case "4": addBillPayment(); break;
                case "5": displayTransactionHistory(); break;
                case "6": undoLastTransaction(); break;
                case "7": {
                    System.out.print("Enter username: ");
                    String u = scanner.nextLine().trim();
                    BankAccount a = searchByUsername(u);
                    System.out.println(a != null ? "Found: " + a : "Not found.");
                    break;
                }
                case "0": running = false; break;
                default: System.out.println("Invalid input.");
            }
        }
    }

    static void atmMenu() {
        System.out.print("\nEnter your username: ");
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

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.println("Balance: " + acc.getBalance());
                    break;
                case "2": {
                    System.out.print("Enter amount: ");
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
            System.out.println("3. View bill payment queue");
            System.out.println("4. Process next bill payment");
            System.out.println("5. Add bill to queue");
            System.out.println("6. View all accounts");
            System.out.println("7. Add account manually");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": displayPendingRequests(); break;
                case "2": processNextAccountRequest(); break;
                case "3": displayBillQueue(); break;
                case "4": processNextBill(); break;
                case "5": addBillToQueue(); break;
                case "6": displayAllAccounts(); break;
                case "7": addAccount(); break;
                case "0": running = false; break;
                default: System.out.println("Invalid input.");
            }
        }
    }

    public static void main(String[] args) {
        accounts.add(new BankAccount("ACC1001", "Ali", 150000));
        accounts.add(new BankAccount("ACC1002", "Sara", 220000));
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

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": bankMenu(); break;
                case "2": atmMenu(); break;
                case "3": adminMenu(); break;
                case "4":
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid input. Please enter 1-4.");
            }
        }
        scanner.close();
    }
}
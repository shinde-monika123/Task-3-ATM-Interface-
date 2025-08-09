import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class BankAccount {
    private String name;
    private double balance;

    public BankAccount(String name, double initialBalance) {
        this.name = name;
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amt) {
        if (amt > 0) {
            balance += amt;
        }
    }

    public boolean withdraw(double amt) {
        if (amt > 0 && amt <= balance) {
            balance -= amt;
            return true;
        } else {
            return false;
        }
    }

    public String getName() {
        return name;
    }
}

public class ATMGUI extends JFrame {
    private BankAccount account;
    private JLabel balanceLabel;
    private JTextField amountField;

    public ATMGUI(BankAccount account) {
        this.account = account;
        setTitle("ATM Interface - Welcome " + account.getName());
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        balanceLabel = new JLabel("💰 Current Balance: ₹" + account.getBalance());
        amountField = new JTextField(10);

        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton checkBalanceBtn = new JButton("Check Balance");
        JButton exitBtn = new JButton("Exit");

        depositBtn.addActionListener(e -> depositAmount());
        withdrawBtn.addActionListener(e -> withdrawAmount());
        checkBalanceBtn.addActionListener(e -> updateBalance());
        exitBtn.addActionListener(e -> System.exit(0));

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Enter amount (₹):"));
        panel.add(amountField);
        panel.add(depositBtn);
        panel.add(withdrawBtn);
        panel.add(checkBalanceBtn);
        panel.add(exitBtn);

        add(balanceLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }

    private void depositAmount() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) {
                showMessage("❌ Please enter a valid positive amount.");
                return;
            }
            account.deposit(amount);
            updateBalance();
            showMessage("✅ ₹" + amount + " deposited successfully.");
        } catch (NumberFormatException e) {
            showMessage("❌ Invalid input. Please enter a number.");
        }
    }

    private void withdrawAmount() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) {
                showMessage("❌ Please enter a valid amount.");
                return;
            }

            if (account.withdraw(amount)) {
                updateBalance();
                showMessage("✅ ₹" + amount + " withdrawn successfully.");
            } else {
                showMessage("❌ Insufficient balance.");
            }
        } catch (NumberFormatException e) {
            showMessage("❌ Invalid input. Please enter a number.");
        }
    }

    private void updateBalance() {
        balanceLabel.setText("💰 Current Balance: ₹" + account.getBalance());
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        String name = JOptionPane.showInputDialog("Enter your name:");
        if (name == null || name.isEmpty()) name = "User";

        String initialStr = JOptionPane.showInputDialog("Enter initial deposit (₹):");
        double initialBalance = 0;

        try {
            initialBalance = Double.parseDouble(initialStr);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid amount! Setting balance to ₹0.");
        }

        BankAccount account = new BankAccount(name, initialBalance);
        SwingUtilities.invokeLater(() -> {
            ATMGUI atm = new ATMGUI(account);
            atm.setVisible(true);
        });
    }
}
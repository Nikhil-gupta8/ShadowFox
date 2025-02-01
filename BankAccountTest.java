import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BankAccountTest {
    private BankAccount account;

    @Before
    public void setUp() {
        account = new BankAccount("123456789");
    }

    @Test
    public void testDeposit() {
        account.deposit(100.0);
        assertEquals(100.0, account.getBalance(), 0.001); // Use delta for double comparison
        assertEquals(1, account.getTransactionHistory().size());
        assertEquals("Deposited: 100.0", account.getTransactionHistory().get(0));
    }

    @Test
    public void testWithdraw() {
        account.deposit(200.0);
        account.withdraw(100.0);
        assertEquals(100.0, account.getBalance(), 0.001); // Use delta for double comparison
        assertEquals(2, account.getTransactionHistory().size());
        assertEquals("Withdrew: 100.0", account.getTransactionHistory().get(1));
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        account.deposit(50.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(100.0);
        });
        assertEquals("Insufficient funds", exception.getMessage());
    }

    @Test
    public void testDepositNegativeAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-50.0);
        });
        assertEquals("Deposit amount must be positive", exception.getMessage());
    }

    @Test
    public void testWithdrawNegativeAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(-50.0);
        });
        assertEquals("Withdrawal amount must be positive", exception.getMessage());
    }

    @Test
    public void testTransactionHistory() {
        account.deposit(100.0);
        account.withdraw(50.0);
        List<String> history = account.getTransactionHistory();
        assertEquals(2, history.size());
        assertEquals("Deposited: 100.0", history.get(0));
        assertEquals("Withdrew: 50.0", history.get(1));
    }
}
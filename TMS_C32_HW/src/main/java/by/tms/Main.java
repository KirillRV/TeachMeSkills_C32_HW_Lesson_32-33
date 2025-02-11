package by.tms;

import by.tms.dataAccesObject.AccountDataAcces;
import by.tms.database.Database;
import by.tms.exception.AccountNotFoundException;
import by.tms.exception.InsufficientFundsException;
import by.tms.model.Account;
import by.tms.service.AccountService;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = Database.getConnection()) {
            AccountDataAcces accountDao = new AccountDataAcces(conn);
            AccountService accountService = new AccountService(accountDao);

            accountService.createAccount(new Account("123456789", new BigDecimal("500.00")));
            accountService.createAccount(new Account("987654321", new BigDecimal("300.00")));

            System.out.println("Initial Balance:");
            System.out.println("Account 123456789: " + accountService.getBalance("123456789"));
            System.out.println("Account 987654321: " + accountService.getBalance("987654321"));

            accountService.transferFunds("123456789", "987654321", new BigDecimal("200.00"));

            System.out.println("\nBalance After Transfer:");
            System.out.println("Account 123456789: " + accountService.getBalance("123456789"));
            System.out.println("Account 987654321: " + accountService.getBalance("987654321"));

        } catch (SQLException | AccountNotFoundException | InsufficientFundsException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
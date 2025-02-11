package by.tms.dataAccesObject;

import by.tms.exception.AccountNotFoundException;
import by.tms.model.Account;
import by.tms.model.Transaction;

import java.sql.*;
import java.util.Optional;

public class AccountDataAcces {

    public Connection connection;

    public AccountDataAcces(Connection connection) {
        this.connection = connection;
    }

    public void createAccount(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (account_number, balance) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, account.getAccountNumber());
            pstmt.setBigDecimal(2, account.getBalance());
            pstmt.executeUpdate();
        }
    }

    public Optional<Account> getAccount(String accountNumber) throws SQLException {
        String sql = "SELECT account_number, balance FROM accounts WHERE account_number = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, accountNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Account(rs.getString("account_number"), rs.getBigDecimal("balance")));
                }
            }
        }
        return Optional.empty();
    }

    public void updateAccount(Account account) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBigDecimal(1, account.getBalance());
            pstmt.setString(2, account.getAccountNumber());
            int updatedRows = pstmt.executeUpdate();
            if(updatedRows == 0){
                throw new AccountNotFoundException("Account not found");
            }
        }
    }

    public void logTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (from_account, to_account, amount, timestamp) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, transaction.getFromAccount());
            pstmt.setString(2, transaction.getToAccount());
            pstmt.setBigDecimal(3, transaction.getAmount());
            pstmt.executeUpdate();
        }
    }
}
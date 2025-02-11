package by.tms.service;

import by.tms.dataAccesObject.AccountDataAcces;
import by.tms.exception.AccountNotFoundException;
import by.tms.exception.InsufficientFundsException;
import by.tms.model.Account;
import by.tms.model.Transaction;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class AccountService {

    private AccountDataAcces accountDao;

    public AccountService(AccountDataAcces accountDao) {
        this.accountDao = accountDao;
    }

    public void createAccount(Account account) throws SQLException {
        accountDao.createAccount(account);
    }

    public BigDecimal getBalance(String accountNumber) throws SQLException, AccountNotFoundException {
        return accountDao.getAccount(accountNumber).orElseThrow(() -> new AccountNotFoundException("Account not found")).getBalance();
    }

    public void transferFunds(String fromAccount, String toAccount, BigDecimal amount) throws SQLException, AccountNotFoundException, InsufficientFundsException {
        Connection connection = accountDao.connection; //Получаем соединение из DAO
        connection.setAutoCommit(false);
        try {
            Account from = accountDao.getAccount(fromAccount).orElseThrow(() -> new AccountNotFoundException("From account not found"));
            Account to = accountDao.getAccount(toAccount).orElseThrow(() -> new AccountNotFoundException("To account not found"));

            from.withdraw(amount);
            to.deposit(amount);

            accountDao.updateAccount(from);
            accountDao.updateAccount(to);

            accountDao.logTransaction(new Transaction(fromAccount, toAccount, amount));
            connection.commit();
        } catch (SQLException | InsufficientFundsException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
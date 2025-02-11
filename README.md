Overview The Bank Account Management System is a Java application designed to manage bank accounts and transactions using JDBC for database interaction. This system allows users to create accounts, view balances, and transfer funds between accounts while ensuring data integrity through transaction management.

Features Create an Account: Add a new account with an initial balance. View Balance: Retrieve the current balance of an account by its account number. Transfer Funds: Transfer funds between two accounts with transaction support to ensure data integrity. Logging: Log all transactions in a separate table for auditing purposes.

Implementation Details

Create an Account Functionality: Users can create a new account by providing a unique account number and an initial balance. Validation: The account number must be unique; otherwise, an error will be raised.
View Balance Functionality: Users can view the current balance of an account by entering the account number. Output: The application retrieves and displays the balance associated with the provided account number.
Transfer Funds Functionality: Users can transfer funds from one account to another. Transaction Management: The transfer is executed within a transaction to ensure that: Both accounts exist. The sender has sufficient funds for the transfer. Rollback Mechanism: If any validation fails, the transaction is rolled back to maintain data integrity.
Exception Handling Insufficient Funds: If the sender does not have enough funds, an exception is thrown, and the transaction is rolled back. Logging Errors: Any errors encountered during the transfer process are logged for auditing purposes.
Logging Operations All transfer operations are logged in the transactions table, which includes:
transaction_id: Unique identifier for the transaction. from_account: The account number from which funds are transferred. to_account: The account number to which funds are transferred. amount: The amount transferred. timestamp: The time when the transaction occurred. Usage Instructions Set Up the Database:

Create the accounts and transactions tables in your PostgreSQL database using the provided SQL scripts. Configure Database Connection:

Update the database connection settings in the application configuration file to match your database credentials. Run the Application:

Start the application and interact with the menu to create accounts, view balances, and transfer funds. Conclusion The Bank Account Management System provides a comprehensive solution for managing bank accounts and transactions. With robust transaction management and detailed logging, this application ensures data integrity and offers a user-friendly experience for managing financial operations.

package be.pxl.student.entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements DAO<Account, AccountException> {

    public static final String SELECT_BY_ID = "SELECT * FROM Account WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM Account;";

    private DAOManager manager;

    public AccountDAO(DAOManager manager) {
        this.manager = manager;
    }

    @Override
    public Account create(Account account) throws AccountException {
        try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement("insert into account values(?, ?, ?)")){
            preparedStatement.setInt(1, account.getId());
            preparedStatement.setString(2, account.getIBAN());
            preparedStatement.setString(3, account.getName());

            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return account;
            }

            manager.commit();

        } catch (SQLException e) {
            manager.rollback(e);
            throw new AccountException(String.format("Error creating account [%s]", account.toString()), e);
        }
        throw new AccountException("not yet implemented");
    }

    @Override
    public List<Account> getAll() throws AccountException {
        List<Account> accountList = new ArrayList<>();
        try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                accountList.add(new Account(
                                resultSet.getInt("id"),
                                resultSet.getString("IBAN"),
                                resultSet.getString("name")
                        )
                );
            }
        } catch (SQLException e) {
            throw new AccountException("Error retrieving accounts", e);
        }

        return accountList;
    }

    @Override
    public Account getById(int id) throws AccountException {

        try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.first()) {
                return new Account(
                        resultSet.getInt("id"),
                        resultSet.getString("IBAN"),
                        resultSet.getString("name")
                );
            } else {
                throw new AccountNotFoundException(String.format("Account with id [%d] not found", id));
            }
        } catch (SQLException sqlException) {
            throw new AccountException(String.format("Exception while retrieving account with id [%d]", id), sqlException);
        }
    }

    @Override
    public Account update(Account account) throws AccountException {
        throw new AccountException("not yet implemented");
    }

    @Override
    public Account delete(Account account) throws AccountException {
        throw new AccountException("not yet implemented");
    }
}

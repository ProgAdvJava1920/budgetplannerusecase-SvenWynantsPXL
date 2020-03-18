package be.pxl.student.entity;

import java.util.List;

public interface AccountDAO {
    // CRUD

    // CREATE
    Account create(Account account) throws AccountException;

    // READ
    List<Account> getAll() throws AccountException;
    Account getById(int id) throws AccountException;

    // UPDATE
    Account update(Account account) throws AccountException;

    // DELETE
    Account delete(int id) throws AccountException;

}

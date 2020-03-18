package be.pxl.student.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountDAOTest {

    private static final String DB_URL = "jdbc:h2:mem:test;MODE=MySQL;INIT=RUNSCRIPT FROM 'classpath:BudgetPlannerTest.sql'";
    AccountDAO dao;
    DAOManager manager;

    @BeforeEach
    void SetUp(){
        manager = new DAOManager(DB_URL);
        dao = new AccountDAO(manager);
    }

    @AfterEach
    void tearDown(){
        manager.close();
    }

    @Test
    void create() {
        fail("not yet implemented");
    }

    @Test
    void it_should_return_2_items() throws AccountException {
        List<Account> accounts = dao.getAll();
        assertEquals(2, accounts.size());
    }

    @Test
    void it_should_return_account_with_id() throws AccountException {
        Account account = dao.getById(1);
        Account expected = new Account(1, "dummyIBAN", "dummyName");
        assertEquals(expected, account);
    }

    @Test
    void update() {
        fail("not yet implemented");
    }

    @Test
    void delete() {
        fail("not yet implemented");
    }
}
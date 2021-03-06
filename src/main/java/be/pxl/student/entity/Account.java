package be.pxl.student.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Account {
    private int id;
    private String name;
    private String IBAN;
    private List<Payment> payments = new ArrayList<>();

    public Account(int id, String name, String IBAN) {
        this.id = id;
        this.name = name;
        this.IBAN = IBAN;
    }

    public Account(String name, String IBAN){
        this.name = name;
        this.IBAN = IBAN;
    }

    public Account(){
    }

    public int getId() {
        return id;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "Account{" +
                "IBAN='" + IBAN + '\'' +
                ", name='" + name + '\'' +
                ", payments=[" + payments.stream().map(Payment::toString).collect(Collectors.joining(",")) + "]}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return name.equals(account.name) &&
                IBAN.equals(account.IBAN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, IBAN);
    }
}

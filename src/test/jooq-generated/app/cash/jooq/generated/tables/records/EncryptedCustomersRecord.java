/*
 * This file is generated by jOOQ.
 */
package app.cash.jooq.generated.tables.records;


import app.cash.jooq.generated.tables.EncryptedCustomers;

import java.time.LocalDateTime;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * The table <code>PUBLIC.ENCRYPTED_CUSTOMERS</code>.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class EncryptedCustomersRecord extends UpdatableRecordImpl<EncryptedCustomersRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>ENCRYPTED_CUSTOMERS.CUSTOMER_TOKEN</code>.
     */
    public void setCustomerToken(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>ENCRYPTED_CUSTOMERS.CUSTOMER_TOKEN</code>.
     */
    public String getCustomerToken() {
        return (String) get(0);
    }

    /**
     * Setter for <code>ENCRYPTED_CUSTOMERS.CREATED_AT</code>.
     */
    public void setCreatedAt(LocalDateTime value) {
        set(1, value);
    }

    /**
     * Getter for <code>ENCRYPTED_CUSTOMERS.CREATED_AT</code>.
     */
    public LocalDateTime getCreatedAt() {
        return (LocalDateTime) get(1);
    }

    /**
     * Setter for <code>ENCRYPTED_CUSTOMERS.UPDATED_AT</code>.
     */
    public void setUpdatedAt(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>ENCRYPTED_CUSTOMERS.UPDATED_AT</code>.
     */
    public LocalDateTime getUpdatedAt() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>ENCRYPTED_CUSTOMERS.FIRST_NAME</code>.
     */
    public void setFirstName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>ENCRYPTED_CUSTOMERS.FIRST_NAME</code>.
     */
    public String getFirstName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>ENCRYPTED_CUSTOMERS.LAST_NAME</code>.
     */
    public void setLastName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>ENCRYPTED_CUSTOMERS.LAST_NAME</code>.
     */
    public String getLastName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>ENCRYPTED_CUSTOMERS.EMAIL_ADDRESS</code>.
     */
    public void setEmailAddress(byte[] value) {
        set(5, value);
    }

    /**
     * Getter for <code>ENCRYPTED_CUSTOMERS.EMAIL_ADDRESS</code>.
     */
    public byte[] getEmailAddress() {
        return (byte[]) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached EncryptedCustomersRecord
     */
    public EncryptedCustomersRecord() {
        super(EncryptedCustomers.ENCRYPTED_CUSTOMERS);
    }

    /**
     * Create a detached, initialised EncryptedCustomersRecord
     */
    public EncryptedCustomersRecord(String customerToken, LocalDateTime createdAt, LocalDateTime updatedAt, String firstName, String lastName, byte[] emailAddress) {
        super(EncryptedCustomers.ENCRYPTED_CUSTOMERS);

        setCustomerToken(customerToken);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
        setFirstName(firstName);
        setLastName(lastName);
        setEmailAddress(emailAddress);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised EncryptedCustomersRecord
     */
    public EncryptedCustomersRecord(app.cash.jooq.generated.tables.pojos.EncryptedCustomers value) {
        super(EncryptedCustomers.ENCRYPTED_CUSTOMERS);

        if (value != null) {
            setCustomerToken(value.getCustomerToken());
            setCreatedAt(value.getCreatedAt());
            setUpdatedAt(value.getUpdatedAt());
            setFirstName(value.getFirstName());
            setLastName(value.getLastName());
            setEmailAddress(value.getEmailAddress());
            resetChangedOnNotNull();
        }
    }
}

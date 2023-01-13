/*
 * This file is generated by jOOQ.
 */
package app.cash.jooq.generated.tables.daos;


import app.cash.jooq.generated.tables.EncryptedCustomers;
import app.cash.jooq.generated.tables.records.EncryptedCustomersRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * The table <code>PUBLIC.ENCRYPTED_CUSTOMERS</code>.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class EncryptedCustomersDao extends DAOImpl<EncryptedCustomersRecord, app.cash.jooq.generated.tables.pojos.EncryptedCustomers, String> {

    /**
     * Create a new EncryptedCustomersDao without any configuration
     */
    public EncryptedCustomersDao() {
        super(EncryptedCustomers.ENCRYPTED_CUSTOMERS, app.cash.jooq.generated.tables.pojos.EncryptedCustomers.class);
    }

    /**
     * Create a new EncryptedCustomersDao with an attached configuration
     */
    public EncryptedCustomersDao(Configuration configuration) {
        super(EncryptedCustomers.ENCRYPTED_CUSTOMERS, app.cash.jooq.generated.tables.pojos.EncryptedCustomers.class, configuration);
    }

    @Override
    public String getId(app.cash.jooq.generated.tables.pojos.EncryptedCustomers object) {
        return object.getCustomerToken();
    }

    /**
     * Fetch records that have <code>CUSTOMER_TOKEN BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<app.cash.jooq.generated.tables.pojos.EncryptedCustomers> fetchRangeOfCustomerToken(String lowerInclusive, String upperInclusive) {
        return fetchRange(EncryptedCustomers.ENCRYPTED_CUSTOMERS.CUSTOMER_TOKEN, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>CUSTOMER_TOKEN IN (values)</code>
     */
    public List<app.cash.jooq.generated.tables.pojos.EncryptedCustomers> fetchByCustomerToken(String... values) {
        return fetch(EncryptedCustomers.ENCRYPTED_CUSTOMERS.CUSTOMER_TOKEN, values);
    }

    /**
     * Fetch a unique record that has <code>CUSTOMER_TOKEN = value</code>
     */
    public app.cash.jooq.generated.tables.pojos.EncryptedCustomers fetchOneByCustomerToken(String value) {
        return fetchOne(EncryptedCustomers.ENCRYPTED_CUSTOMERS.CUSTOMER_TOKEN, value);
    }

    /**
     * Fetch a unique record that has <code>CUSTOMER_TOKEN = value</code>
     */
    public Optional<app.cash.jooq.generated.tables.pojos.EncryptedCustomers> fetchOptionalByCustomerToken(String value) {
        return fetchOptional(EncryptedCustomers.ENCRYPTED_CUSTOMERS.CUSTOMER_TOKEN, value);
    }

    /**
     * Fetch records that have <code>CREATED_AT BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<app.cash.jooq.generated.tables.pojos.EncryptedCustomers> fetchRangeOfCreatedAt(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(EncryptedCustomers.ENCRYPTED_CUSTOMERS.CREATED_AT, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>CREATED_AT IN (values)</code>
     */
    public List<app.cash.jooq.generated.tables.pojos.EncryptedCustomers> fetchByCreatedAt(LocalDateTime... values) {
        return fetch(EncryptedCustomers.ENCRYPTED_CUSTOMERS.CREATED_AT, values);
    }

    /**
     * Fetch records that have <code>UPDATED_AT BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<app.cash.jooq.generated.tables.pojos.EncryptedCustomers> fetchRangeOfUpdatedAt(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(EncryptedCustomers.ENCRYPTED_CUSTOMERS.UPDATED_AT, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>UPDATED_AT IN (values)</code>
     */
    public List<app.cash.jooq.generated.tables.pojos.EncryptedCustomers> fetchByUpdatedAt(LocalDateTime... values) {
        return fetch(EncryptedCustomers.ENCRYPTED_CUSTOMERS.UPDATED_AT, values);
    }

    /**
     * Fetch records that have <code>FIRST_NAME BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<app.cash.jooq.generated.tables.pojos.EncryptedCustomers> fetchRangeOfFirstName(String lowerInclusive, String upperInclusive) {
        return fetchRange(EncryptedCustomers.ENCRYPTED_CUSTOMERS.FIRST_NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>FIRST_NAME IN (values)</code>
     */
    public List<app.cash.jooq.generated.tables.pojos.EncryptedCustomers> fetchByFirstName(String... values) {
        return fetch(EncryptedCustomers.ENCRYPTED_CUSTOMERS.FIRST_NAME, values);
    }

    /**
     * Fetch records that have <code>LAST_NAME BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<app.cash.jooq.generated.tables.pojos.EncryptedCustomers> fetchRangeOfLastName(String lowerInclusive, String upperInclusive) {
        return fetchRange(EncryptedCustomers.ENCRYPTED_CUSTOMERS.LAST_NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>LAST_NAME IN (values)</code>
     */
    public List<app.cash.jooq.generated.tables.pojos.EncryptedCustomers> fetchByLastName(String... values) {
        return fetch(EncryptedCustomers.ENCRYPTED_CUSTOMERS.LAST_NAME, values);
    }

    /**
     * Fetch records that have <code>EMAIL_ADDRESS BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<app.cash.jooq.generated.tables.pojos.EncryptedCustomers> fetchRangeOfEmailAddress(byte[] lowerInclusive, byte[] upperInclusive) {
        return fetchRange(EncryptedCustomers.ENCRYPTED_CUSTOMERS.EMAIL_ADDRESS, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>EMAIL_ADDRESS IN (values)</code>
     */
    public List<app.cash.jooq.generated.tables.pojos.EncryptedCustomers> fetchByEmailAddress(byte[]... values) {
        return fetch(EncryptedCustomers.ENCRYPTED_CUSTOMERS.EMAIL_ADDRESS, values);
    }
}

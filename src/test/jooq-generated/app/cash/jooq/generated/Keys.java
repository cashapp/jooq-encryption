/*
 * This file is generated by jOOQ.
 */
package app.cash.jooq.generated;


import app.cash.jooq.generated.tables.EncryptedCustomers;
import app.cash.jooq.generated.tables.ForcedTypeCustomers;
import app.cash.jooq.generated.tables.UnprotectedCustomers;
import app.cash.jooq.generated.tables.records.EncryptedCustomersRecord;
import app.cash.jooq.generated.tables.records.ForcedTypeCustomersRecord;
import app.cash.jooq.generated.tables.records.UnprotectedCustomersRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in the
 * default schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<EncryptedCustomersRecord> CONSTRAINT_1 = Internal.createUniqueKey(EncryptedCustomers.ENCRYPTED_CUSTOMERS, DSL.name("CONSTRAINT_1"), new TableField[] { EncryptedCustomers.ENCRYPTED_CUSTOMERS.CUSTOMER_TOKEN }, true);
    public static final UniqueKey<ForcedTypeCustomersRecord> CONSTRAINT_7 = Internal.createUniqueKey(ForcedTypeCustomers.FORCED_TYPE_CUSTOMERS, DSL.name("CONSTRAINT_7"), new TableField[] { ForcedTypeCustomers.FORCED_TYPE_CUSTOMERS.CUSTOMER_TOKEN }, true);
    public static final UniqueKey<UnprotectedCustomersRecord> CONSTRAINT_C = Internal.createUniqueKey(UnprotectedCustomers.UNPROTECTED_CUSTOMERS, DSL.name("CONSTRAINT_C"), new TableField[] { UnprotectedCustomers.UNPROTECTED_CUSTOMERS.CUSTOMER_TOKEN }, true);
}

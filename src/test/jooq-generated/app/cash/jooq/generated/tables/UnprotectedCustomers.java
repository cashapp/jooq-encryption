/*
 * This file is generated by jOOQ.
 */
package app.cash.jooq.generated.tables;


import app.cash.jooq.generated.DefaultSchema;
import app.cash.jooq.generated.Keys;
import app.cash.jooq.generated.tables.records.UnprotectedCustomersRecord;

import java.time.LocalDateTime;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function6;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row6;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * The table <code>PUBLIC.UNPROTECTED_CUSTOMERS</code>.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UnprotectedCustomers extends TableImpl<UnprotectedCustomersRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>UNPROTECTED_CUSTOMERS</code>
     */
    public static final UnprotectedCustomers UNPROTECTED_CUSTOMERS = new UnprotectedCustomers();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UnprotectedCustomersRecord> getRecordType() {
        return UnprotectedCustomersRecord.class;
    }

    /**
     * The column <code>UNPROTECTED_CUSTOMERS.CUSTOMER_TOKEN</code>.
     */
    public final TableField<UnprotectedCustomersRecord, String> CUSTOMER_TOKEN = createField(DSL.name("CUSTOMER_TOKEN"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>UNPROTECTED_CUSTOMERS.CREATED_AT</code>.
     */
    public final TableField<UnprotectedCustomersRecord, LocalDateTime> CREATED_AT = createField(DSL.name("CREATED_AT"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>UNPROTECTED_CUSTOMERS.UPDATED_AT</code>.
     */
    public final TableField<UnprotectedCustomersRecord, LocalDateTime> UPDATED_AT = createField(DSL.name("UPDATED_AT"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>UNPROTECTED_CUSTOMERS.FIRST_NAME</code>.
     */
    public final TableField<UnprotectedCustomersRecord, String> FIRST_NAME = createField(DSL.name("FIRST_NAME"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>UNPROTECTED_CUSTOMERS.LAST_NAME</code>.
     */
    public final TableField<UnprotectedCustomersRecord, String> LAST_NAME = createField(DSL.name("LAST_NAME"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>UNPROTECTED_CUSTOMERS.EMAIL_ADDRESS</code>.
     */
    public final TableField<UnprotectedCustomersRecord, String> EMAIL_ADDRESS = createField(DSL.name("EMAIL_ADDRESS"), SQLDataType.VARCHAR(255), this, "");

    private UnprotectedCustomers(Name alias, Table<UnprotectedCustomersRecord> aliased) {
        this(alias, aliased, null);
    }

    private UnprotectedCustomers(Name alias, Table<UnprotectedCustomersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>UNPROTECTED_CUSTOMERS</code> table reference
     */
    public UnprotectedCustomers(String alias) {
        this(DSL.name(alias), UNPROTECTED_CUSTOMERS);
    }

    /**
     * Create an aliased <code>UNPROTECTED_CUSTOMERS</code> table reference
     */
    public UnprotectedCustomers(Name alias) {
        this(alias, UNPROTECTED_CUSTOMERS);
    }

    /**
     * Create a <code>UNPROTECTED_CUSTOMERS</code> table reference
     */
    public UnprotectedCustomers() {
        this(DSL.name("UNPROTECTED_CUSTOMERS"), null);
    }

    public <O extends Record> UnprotectedCustomers(Table<O> child, ForeignKey<O, UnprotectedCustomersRecord> key) {
        super(child, key, UNPROTECTED_CUSTOMERS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<UnprotectedCustomersRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_C;
    }

    @Override
    public UnprotectedCustomers as(String alias) {
        return new UnprotectedCustomers(DSL.name(alias), this);
    }

    @Override
    public UnprotectedCustomers as(Name alias) {
        return new UnprotectedCustomers(alias, this);
    }

    @Override
    public UnprotectedCustomers as(Table<?> alias) {
        return new UnprotectedCustomers(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public UnprotectedCustomers rename(String name) {
        return new UnprotectedCustomers(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UnprotectedCustomers rename(Name name) {
        return new UnprotectedCustomers(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public UnprotectedCustomers rename(Table<?> name) {
        return new UnprotectedCustomers(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<String, LocalDateTime, LocalDateTime, String, String, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function6<? super String, ? super LocalDateTime, ? super LocalDateTime, ? super String, ? super String, ? super String, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function6<? super String, ? super LocalDateTime, ? super LocalDateTime, ? super String, ? super String, ? super String, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}

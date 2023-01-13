# jooq-encryption

The `jooq-encryption` library provides tools and interfaces to support Field Level Encryption 
(FLE) for databases using the jOOQ ORM **at the application layer**.

More specifically, encrypting specific database columns just before data gets written to the database,
and decrypting data as it's being retrieved back to the application.

This library contains 2 main components:
 - `EncryptionAwareJavaGenerator`
 - `JooqKeyPrimitive`

## Usage

### Code generation

jOOQ needs to generate model classes representing the data stored in a database in order to transact with it.    
In most cases, that means supplying some configuration and a database schema for the jOOQ code generation tool
so it could run, interpret the schema, and generate corresponding classes.  
That configuration can be supplied with the [jOOQ Gradle plugin](https://www.jooq.org/doc/latest/manual/code-generation/codegen-gradle/).

In order to support FLE, you must use the [`EncryptionAwareJavaGenerator`](src/main/kotlin/app/cash/jooq/EncryptionAwareJavaGenerator.kt).

Add the following to your gradle build file in order to add this library to jOOQ's code generation classpath:
```kotlin
dependencies {
  jooqGenerator(Dependencies.cashJooqEncryption)
  jooqGenerator(Dependencies.jooqMetaExtensions)
}
```

Then, make sure you're using the code generator class provided in this library instead of jOOQ's default code generator.  
For example, if you're using the jOOQ Gradle plugin, specify the generator class name like so:
```kotlin
jooq {
  configurations {
    create("main") {  // name of the jOOQ configuration
      jooqConfiguration.apply {
        generator.apply {
          name = "com.squareup.cash.jooq.EncryptionAwareJavaGenerator"
          target.apply {
            // ...
          }
          database.apply {
            // ...
          }
        }
      }
    }
  }
}
```

Using the `EncryptionAwareJavagenerator` causes any column in the database that's eligible for encryption to support it.  
The criteria for a column to support encryption are:
 - The field must have a type of `varbinary`
 - The field must not have a forced type converter associated with

Columns eligible for encryption will have a [`Converter`](https://www.jooq.org/javadoc/latest/org.jooq/org/jooq/Converter.html)
associated with them.  
See [`ColumnEncryptionConverter`](src/main/kotlin/app/cash/jooq/ColumnEncryptionConverter.kt) for more details.

### Encryption key setup

The next step is to let the `ColumnEncryptionConverter` know which key to use when encrypting/decrypting values.

In order to do that, we must call the `RealJooqKeyPrimitive.initialize` function.  
This function accepts 2 maps, mapping from a column name to an implementation of an encryption primitive.

This library uses Tink encryption primitives, specifically the [`AEAD`](https://github.com/google/tink/blob/master/docs/PRIMITIVES.md#authenticated-encryption-with-associated-data) 
and [`DeterministicAEAD`](https://github.com/google/tink/blob/master/docs/PRIMITIVES.md#deterministic-authenticated-encryption-with-associated-data) primitives.

Example:
```kotlin
// Initialize the keys you need in your application's main/bootstrap section
val ssnEncryptionKey = KeysetHandle.read(/* ... */)
  .getPrimitive(Aead::class.java)
val jooqNonDeterministicKeyMap = mapOf("myTable.ssn" to ssnEncryptionKey)

val emailAddressEncryptionKey = KeysetHandle.read(/* ... */)
  .getPrimitive(DeterministicAead::class.java)
val jooqIndexableKeyMap = mapOf("myTable.emailAddress" to emailAddressEncryptionKey)

// Make sure this statement is executed before any other database interactions
RealJooqKeyPrimitive.initialize(jooqNonDeterministicKeyMap, jooqIndexableKeyMap)
```

#### Indexable encrypted columns

In some cases, there's a need to be able to query encrypted columns.  
Although we can't support complex query operators like `BETWEEN`, `<`, `>` and `LIKE`, we can support basic equality queries.  
Make sure to set up a `DeterministicAEAD` key to any column that needs to be queried in such a way.

## Limitations

### Querying encrypted columns

Please note that while encrypting data using [`AEAD`](https://github.com/google/tink/blob/master/docs/PRIMITIVES.md#authenticated-encryption-with-associated-data), it'll make it very hard to query.  
For example, simple equality conditions like `SELECT * FROM table WHERE email = "some@value"` will no longer work.

The only way to use encrypted columns in a query is iff:

1. The data is encrypted with a [`DeterministicAEAD`](https://github.com/google/tink/blob/master/docs/PRIMITIVES.md#deterministic-authenticated-encryption-with-associated-data) key
2. Only the `=` operator is used

It is your responsibility to make sure columns are configured with the appropriate key types to enable querying encrypted data.

### Key rotation

While using [`DeterministicAEAD`](https://github.com/google/tink/blob/master/docs/PRIMITIVES.md#deterministic-authenticated-encryption-with-associated-data) encryption enables simple queries on encrypted data,
rotating these keys will break that capability.  
By rotating a key, new data gets encrypted using new key material while the decryption operation on existing encrypted data will try to use the latest key material and fallback to older versions of the key.

When using simple equality queries on a column where the key was rotated may lead to missing data in the query results.

### Renaming tables and columns

This encryption library uses the table and column names of the data being encrypted as the encryption context, 
or [Associated Data](https://github.com/google/tink/blob/master/docs/PRIMITIVES.md#authenticated-encryption-with-associated-data).  
Renaming the name of the table or the column of where the encrypted data is stored will result
in decryption errors.

Instead of renaming a column with statements like `ALTER TABLE table RENAME column TO new_column`,
use the following steps:

1. Create a new column `new_column` and configure the encryption key for it
2. Copy all existing data in the table from `column` to `new_column`
3. Delete `column`

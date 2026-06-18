package app.cash.jooq

import org.jooq.Name
import org.jooq.codegen.GeneratorStrategy
import org.jooq.codegen.JavaGenerator
import org.jooq.codegen.JavaWriter
import org.jooq.impl.DSL
import org.jooq.meta.ColumnDefinition
import org.jooq.meta.DefaultColumnDefinition
import org.jooq.meta.DefaultDataTypeDefinition
import org.jooq.meta.TableDefinition

/**
 * Extends the default jOOQ provided [JavaGenerator] that creates the model classes used in the ORM.
 *
 * This generator is encryption aware. It'll search for columns in the database schema
 * that are eligible for encryption, and attach a [ColumnEncryptionConverter] to them.
 * A column is considered eligible for encryption if:
 * 1. It doesn't already have a converter associated with it
 * 2. It's declared as `varbinary` in the database schema
 */
class EncryptionAwareJavaGenerator : JavaGenerator() {
  override fun generateTable(table: TableDefinition, outputWriter: JavaWriter) {
    val encryptionAwareTableDefinition = EncryptionAwareTableDefinition(table)
    super.generateTable(encryptionAwareTableDefinition, outputWriter)
    if (encryptionAwareTableDefinition.containsEncryptedColumn) {
      outputWriter.ref("app.cash.jooq.ColumnEncryptionConverter")
    }
  }

  override fun generateRecord(table: TableDefinition) {
    val outputWriter = newJavaWriter(getFile(table, GeneratorStrategy.Mode.RECORD))
    val encryptionAwareTableDefinition = EncryptionAwareTableDefinition(table)
    super.generateRecord(encryptionAwareTableDefinition, outputWriter)
    closeJavaWriter(outputWriter)
  }

  override fun generatePojo(table: TableDefinition) {
    val outputWriter = newJavaWriter(getFile(table, GeneratorStrategy.Mode.POJO))
    val encryptionAwareTableDefinition = EncryptionAwareTableDefinition(table)
    super.generatePojo(encryptionAwareTableDefinition, outputWriter)
    closeJavaWriter(outputWriter)
  }

  inner class EncryptionAwareTableDefinition(
    private val table: TableDefinition
  ) : TableDefinition by table {

    internal var containsEncryptedColumn: Boolean = false

    private val supportedTypes =
      listOf(
        "binary varying", // DDL dialect
        "binary large object",
        "varbinary", // Mysql dialect
        "tinyblob",
        "blob",
        "mediumblob",
        "longblob",
      )

    override fun getColumns(): MutableList<ColumnDefinition> {
      return table.columns.map { column ->
        val userType: Name = DSL.name(column.type.javaType)
        when {
          !userType.empty() ->
            column
          supportedTypes.any { supportedType -> column.type.type.equals(supportedType, true) } -> {
            containsEncryptedColumn = true
            DefaultColumnDefinition(
              table,
              column.name,
              column.position,
              DefaultDataTypeDefinition(
                column.database,
                column.schema,
                column.type.type,
                null as Number?,
                null as Number?,
                null as Number?,
                null as Boolean?,
                null as String?,
                null as Name?,
                "new ColumnEncryptionConverter(\"${table.name.lowercase()}\", \"${column.name.lowercase()}\")",
                null as String?,
                "byte[]"
              ),
              column.isIdentity,
              column.comment
            )
          }
          else ->
            column
        }
      }.toMutableList()
    }
  }
}

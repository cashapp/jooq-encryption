package app.cash.jooq

import org.jooq.Name
import org.jooq.Record
import org.jooq.Table
import org.jooq.codegen.GeneratorStrategy
import org.jooq.codegen.JavaGenerator
import org.jooq.codegen.JavaWriter
import org.jooq.impl.DSL
import org.jooq.meta.CatalogDefinition
import org.jooq.meta.CheckConstraintDefinition
import org.jooq.meta.ColumnDefinition
import org.jooq.meta.Database
import org.jooq.meta.DefaultColumnDefinition
import org.jooq.meta.DefaultDataTypeDefinition
import org.jooq.meta.DefaultIdentityDefinition
import org.jooq.meta.Definition
import org.jooq.meta.EmbeddableDefinition
import org.jooq.meta.ForeignKeyDefinition
import org.jooq.meta.IdentityDefinition
import org.jooq.meta.IndexDefinition
import org.jooq.meta.PackageDefinition
import org.jooq.meta.ParameterDefinition
import org.jooq.meta.SchemaDefinition
import org.jooq.meta.TableDefinition
import org.jooq.meta.UniqueKeyDefinition

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
  ) : TableDefinition {

    internal var containsEncryptedColumn: Boolean = false

    private val supportedTypes = listOf("varbinary", "binary varying")

    override fun getDatabase(): Database = table.database

    override fun getCatalog(): CatalogDefinition = table.catalog

    override fun getSchema(): SchemaDefinition = table.schema

    override fun getPackage(): PackageDefinition = table.`package`

    override fun getName(): String = table.name

    override fun getInputName(): String = table.inputName

    override fun getOutputName(): String = table.outputName

    override fun getComment(): String = table.comment

    override fun getDefinitionPath(): MutableList<Definition> = table.definitionPath

    override fun getQualifiedName(): String = table.qualifiedName

    override fun getQualifiedInputName(): String = table.qualifiedInputName

    override fun getQualifiedOutputName(): String = table.qualifiedOutputName

    override fun getQualifiedNamePart(): Name = table.qualifiedNamePart

    override fun getQualifiedInputNamePart(): Name = table.qualifiedInputNamePart

    override fun getQualifiedOutputNamePart(): Name = table.qualifiedOutputNamePart

    override fun getOverload(): String = table.overload ?: ""

    override fun getSource(): String = table.source

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

    override fun getColumn(columnName: String): ColumnDefinition {
      return columns.find { columnDef -> columnDef.name.equals(name) }!!
    }

    override fun getColumn(columnName: String, ignoreCase: Boolean): ColumnDefinition {
      return columns.find { columnDef -> columnDef.name.equals(name, ignoreCase) }!!
    }

    override fun getColumn(columnIndex: Int): ColumnDefinition {
      return columns[columnIndex]
    }

    override fun isSynthetic(): Boolean = table.isSynthetic

    override fun getEmbeddables(): MutableList<EmbeddableDefinition> = table.embeddables

    override fun getReferencedEmbeddables(): MutableList<EmbeddableDefinition> = table.referencedEmbeddables

    override fun getIndexes(): MutableList<IndexDefinition> = table.indexes

    override fun getPrimaryKey(): UniqueKeyDefinition = table.primaryKey

    override fun getUniqueKeys(): MutableList<UniqueKeyDefinition> = table.uniqueKeys

    override fun getUniqueKey(name: String): UniqueKeyDefinition = table.getUniqueKey(name)

    override fun getKeys(): MutableList<UniqueKeyDefinition> = table.keys

    override fun getKey(name: String): UniqueKeyDefinition = table.getKey(name)

    override fun getForeignKeys(): MutableList<ForeignKeyDefinition> = table.foreignKeys

    override fun getForeignKeys(tableDefinition: TableDefinition?): MutableList<ForeignKeyDefinition> = table.getForeignKeys(tableDefinition)

    override fun getCheckConstraints(): MutableList<CheckConstraintDefinition> = table.checkConstraints

    override fun getIdentity(): IdentityDefinition? = columns
      .find { column -> column.isIdentity }
      .let { column ->
        if (column == null) {
          null
        } else {
          DefaultIdentityDefinition(column)
        }
      }

    override fun getParentTable(): TableDefinition = table.parentTable

    override fun getChildTables(): MutableList<TableDefinition> = table.childTables

    override fun getTable(): Table<Record> = table.table

    override fun getParameters(): MutableList<ParameterDefinition> = table.parameters

    override fun isTemporary(): Boolean = table.isTemporary

    override fun isView(): Boolean = table.isView

    override fun isMaterializedView(): Boolean = table.isMaterializedView

    override fun isTableValuedFunction(): Boolean = table.isTableValuedFunction
  }
}

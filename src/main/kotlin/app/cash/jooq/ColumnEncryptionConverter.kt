package app.cash.jooq

import org.jooq.Converter

/**
 * Encrypts a given string to and encodes it into a byte array and back for the specified column.
 *
 * If there is no encryption key associated with the fully qualified column name,
 * nothing will be encrypted.
 */
class ColumnEncryptionConverter(
  tableName: String,
  columnName: String
) : Converter<ByteArray, ByteArray> {
  private val fullyQualifiedColumnName = "$tableName.$columnName"

  /**
   * This is the common AAD being used with AEAD encryption for columns.
   * It's a map of string->string values, that's serialized to a byte array, and contains:
   * 1. "table_name" -> the table name
   * 2. "column_name" -> the column name
   *
   * The map is then sorted, and each key-value pair is mapped to a string like
   * `"key=value"`, and all pairs are then concatenated with `|`.
   * Finally, the entire string of "key1=value1|key2=value2|..." is serialized to a byte array.
   */
  private val aad = mapOf(
    "table_name" to tableName,
    "column_name" to columnName
  ).asSequence()
    .sortedBy { (k, v) -> k + v }
    .map { (k, v) -> "$k=$v" }
    .joinToString("|")
    .toByteArray(Charsets.UTF_8)

  private val encryptionPrimitive: JooqKeyPrimitive by lazy {
    RealJooqKeyPrimitive.getInstance()
  }

  override fun from(source: ByteArray): ByteArray {
    return encryptionPrimitive.decrypt(fullyQualifiedColumnName, source, aad)
  }

  override fun to(source: ByteArray): ByteArray {
    val encrypted = encryptionPrimitive.encrypt(fullyQualifiedColumnName, source, aad)
    return if (!encrypted.contentEquals(source)) {
      encrypted
    } else {
      return source
    }
  }

  override fun fromType() = ByteArray::class.java

  override fun toType() = ByteArray::class.java

}

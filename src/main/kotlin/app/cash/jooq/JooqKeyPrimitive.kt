package app.cash.jooq

/**
 * This interface defines the functions required to encrypt and decrypt a column's value
 * with a specific key name.
 *
 * The key name is expected to be the fully qualified name of the column being encrypted/decrypted.
 */
interface JooqKeyPrimitive {

  /**
   * Encrypt the given [data] with an encryption key named [columnName].
   *
   * If there was no key associated/named with the given column name,
   * **this function is expected to return the original unmodified data**.
   */
  fun encrypt(columnName: String, data: ByteArray, context: ByteArray = byteArrayOf()): ByteArray

  /**
   * Decrypt the given [data] with the encryption key named [columnName].
   *
   * If there was no key associated/named with the given column name,
   * **this function is expected to return the original unmodified data**.
   */
  fun decrypt(columnName: String, data: ByteArray, context: ByteArray = byteArrayOf()): ByteArray
}

package app.cash.jooq

import com.google.crypto.tink.Aead
import com.google.crypto.tink.DeterministicAead
import org.jooq.exception.ConfigurationException

/**
 * Implements the [JooqKeyPrimitive] interface using 2 Tink key maps:
 * - [aeadKeys] should be used to encrypt columns that are not searchable.
 * - [daeadKeys] should be used to encrypt columns that might be indexed, or searched for specific values.
 */
class RealJooqKeyPrimitive private constructor(
  private val aeadKeys: Map<String, Aead>,
  private val daeadKeys: Map<String, DeterministicAead>
) : JooqKeyPrimitive {

  override fun encrypt(columnName: String, data: ByteArray, context: ByteArray): ByteArray {
    return aeadKeys[columnName]?.encrypt(data, context)
      ?: daeadKeys[columnName]?.encryptDeterministically(data, context)
      ?: data
  }

  override fun decrypt(columnName: String, data: ByteArray, context: ByteArray): ByteArray {
    return aeadKeys[columnName]?.decrypt(data, context)
      ?: daeadKeys[columnName]?.decryptDeterministically(data, context)
      ?: data
  }

  companion object {
    private var instance: RealJooqKeyPrimitive? = null

    /**
     * Initialize encryption keys and which column names they should be associated with.
     * If the key maps were already initialized, this function will not change any state.
     */
    fun initialize(
      aeadKeys: Map<String, Aead>,
      daeadKeys: Map<String, DeterministicAead>
    ) {
      if (instance == null) {
        val columnNameIntersection = aeadKeys.keys.intersect(daeadKeys.keys)
        if (columnNameIntersection.isNotEmpty()) {
          throw ConfigurationException(
            "Found ${columnNameIntersection.size} " +
              "columns with more than one key:\n " +
              columnNameIntersection.joinToString("\n")
          )
        }
        instance = RealJooqKeyPrimitive(aeadKeys, daeadKeys)
      }
    }

    /**
     * Used for testing
     */
    internal fun isInitialized(): Boolean {
      return instance != null
    }

    /**
     * Returns the singleton instance of this class
     */
    fun getInstance(): RealJooqKeyPrimitive {
      return instance!!
    }
  }
}

@file:Suppress("UNCHECKED_CAST")

package app.cash.jooq

import app.cash.jooq.generated.Tables
import org.assertj.core.api.Assertions.assertThat
import org.jooq.Converter
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class EncryptedCustomersKotlinTest {

  companion object {
    private const val plaintextEmailAddress = "yoav@squareup.com"
    private val plaintextEmailAddressBytes = plaintextEmailAddress.encodeToByteArray()

    @BeforeAll
    @JvmStatic
    fun setup() {
      Util.initializeKeys()
    }
  }

  @Test
  fun testEncryptsDecryptsConfiguredColumn() {
    val converter = Tables.ENCRYPTED_CUSTOMERS.EMAIL_ADDRESS.converter as Converter<ByteArray, ByteArray>
    val encrypted = converter.to(plaintextEmailAddressBytes)
    assertThat(encrypted).isNotEqualTo(plaintextEmailAddressBytes)
    val decrypted = converter.from(encrypted)
    assertThat(decrypted).isEqualTo(plaintextEmailAddressBytes)
  }

  @Test
  fun testDoesntEncryptOrDecryptForcedTypeColumn() {
    val converter = Tables.FORCED_TYPE_CUSTOMERS.EMAIL_ADDRESS.converter as Converter<ByteArray, String>
    val emailAddress = converter.to(plaintextEmailAddress)
    assertThat(emailAddress).isEqualTo(plaintextEmailAddressBytes)
    val notDecrypted = converter.from(emailAddress)
    assertThat(notDecrypted).isEqualTo(plaintextEmailAddress)
  }

  @Test
  fun testDoesntEncryptOrDecryptUnprotectedColumn() {
    val converter = Tables.UNPROTECTED_CUSTOMERS.EMAIL_ADDRESS.converter as Converter<String, String>
    val emailAddress = converter.to(plaintextEmailAddress)
    assertThat(emailAddress).isEqualTo(plaintextEmailAddress)
    val notDecrypted = converter.from(emailAddress)
    assertThat(notDecrypted).isEqualTo(plaintextEmailAddress)
  }
} 
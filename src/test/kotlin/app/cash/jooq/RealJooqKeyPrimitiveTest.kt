package app.cash.jooq

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

internal class RealJooqKeyPrimitiveTest {

  companion object {
    @BeforeAll
    @JvmStatic
    fun setup() {
      Util.initializeKeys()
    }
  }

  @Test
  fun testInitialized() {
    assertThatCode { RealJooqKeyPrimitive.getInstance() }.doesNotThrowAnyException()
  }

  @Test
  fun testDoesNotEncryptUnknownColumn() {
    val instance = RealJooqKeyPrimitive.getInstance()
    val source = byteArrayOf(1, 2, 3, 4, 5, 6)
    val encrypted = instance.encrypt("not_configured", source, byteArrayOf())
    assertThat(encrypted).isEqualTo(source)
  }

  @Test
  fun testDoesNotDecryptUnknownColumn() {
    val instance = RealJooqKeyPrimitive.getInstance()
    val source = byteArrayOf(1, 2, 3, 4, 5, 6)
    val decrypted = instance.decrypt("not_configured", source, byteArrayOf())
    assertThat(decrypted).isEqualTo(source)
  }

  @Test
  fun testEncryptionRoundTrip() {
    val instance = RealJooqKeyPrimitive.getInstance()
    val source = byteArrayOf(1, 2, 3, 4, 5, 6)
    val encrypted = instance.encrypt("encrypted_customers.email_address", source, byteArrayOf())
    assertThat(encrypted).isNotEqualTo(source)
    val decrypted = instance.decrypt("encrypted_customers.email_address", encrypted, byteArrayOf())
    assertThat(decrypted).isEqualTo(source)
  }
}

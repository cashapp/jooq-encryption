package app.cash.jooq

import com.google.crypto.tink.Aead
import com.google.crypto.tink.DeterministicAead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.daead.DeterministicAeadConfig

internal class Util {
  companion object {
    fun initializeKeys() {
      if (!RealJooqKeyPrimitive.isInitialized()) {
        AeadConfig.register()
        DeterministicAeadConfig.register()
        val aeadKey: Aead = KeysetHandle.generateNew(KeyTemplates.get("AES256_GCM"))
          .getPrimitive(Aead::class.java)
        val daeadKey = KeysetHandle.generateNew(KeyTemplates.get("AES256_SIV"))
          .getPrimitive(DeterministicAead::class.java)
        RealJooqKeyPrimitive.initialize(
          mapOf("some_aead_column" to aeadKey),
          mapOf(
            "encrypted_customers.email_address" to daeadKey,
            "forced_type_customers.email_address" to daeadKey,
            "unprotected_customers.email_address" to daeadKey
          )
        )
      }
    }
  }
}

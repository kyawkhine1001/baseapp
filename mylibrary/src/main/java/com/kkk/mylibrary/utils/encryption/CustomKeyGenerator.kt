package com.kkk.mylibrary.utils.encryption

import android.util.Base64
import android.util.Log.i
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object CustomKeyGenerator {

    external fun decryptAccessKey(): String

    private external fun decryptIVKey(): String

    private external fun decryptSecreteKey(): String

    private external fun decryptAlgorithm(): String

    private external fun decryptTransformation(): String

    private var algorithm: String? = null
    private var transformation: String? = null

    init {
        algorithm = decryptAlgorithm()
        transformation = decryptTransformation()
    }

    @Throws(Exception::class)
    fun initDecryptCipher(cipherText: ByteArray?, key: SecretKey, iv: ByteArray?): String? {
        val cipher: Cipher = Cipher.getInstance(transformation)

        val keySpec = SecretKeySpec(key.encoded, algorithm)

        val ivSpec = IvParameterSpec(iv)

        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)

        var decryptedText: ByteArray? = null
        try {
            decryptedText = cipher.doFinal(cipherText)
        } catch (e: Exception) {
            i("Exception",e.printStackTrace().toString())
        }
        return decryptedText?.let { String(it) }
    }

    fun getToken(token: String?): String? {
        val keyGenerator: KeyGenerator = KeyGenerator.getInstance(algorithm)
        keyGenerator.init(256)
        val key = SecretKeySpec(decryptSecreteKey().toByteArray(), algorithm)
        token?.let {
            return initDecryptCipher(
                Base64.decode(token, Base64.DEFAULT),
                key,
                decryptIVKey().toByteArray()
            )
        }
        return null
    }

    @Throws(Exception::class)
    fun initEncryptCipher(cipherText: ByteArray?, key: SecretKey, iv: ByteArray?): ByteArray? {
        val cipher: Cipher = Cipher.getInstance(transformation)

        val keySpec = SecretKeySpec(key.encoded, algorithm)

        val ivSpec = IvParameterSpec(iv)

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)

        var decryptedText: ByteArray? = null
        try {
            decryptedText = cipher.doFinal(cipherText)
        } catch (e: Exception) {
            i("Exception",e.printStackTrace().toString())
        }
        return decryptedText
    }

    fun getEncryptedUserId(userId : Long): String? {
        val keyGenerator: KeyGenerator = KeyGenerator.getInstance(algorithm)
        keyGenerator.init(256)
        val key = SecretKeySpec(decryptSecreteKey().toByteArray(), algorithm)
        val timeStamp = "kkk@2022.${
            System.currentTimeMillis().toString().substring(0, 10)
        }.${userId}"
        return Base64.encodeToString(
            initEncryptCipher(
                timeStamp.toByteArray(),
                key,
                decryptIVKey().toByteArray()
            ), Base64.DEFAULT
        )
    }
}
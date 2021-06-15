package alektas.marvelheroes.data.utils

import java.security.MessageDigest

fun String.toMd5(): String = MessageDigest.getInstance("MD5").digest(this.toByteArray()).toHex()

fun ByteArray.toHex(): String = joinToString("") { "%02x".format(it) }
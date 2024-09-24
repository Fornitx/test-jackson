package com.example.utils

import kotlin.io.encoding.Base64

fun ByteArray.toBase64(): String = Base64.encode(this)

fun String.fromBase64(): ByteArray = Base64.decode(this)

package com.example

import com.fasterxml.jackson.dataformat.cbor.databind.CBORMapper
import com.fasterxml.jackson.dataformat.ion.IonObjectMapper
import com.fasterxml.jackson.dataformat.smile.databind.SmileMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.junit.jupiter.api.Test
import java.nio.charset.Charset
import java.time.Instant

class RawStringTest {
    private val charset = Charset.defaultCharset()
    private val pair1 = "a" to Long.MAX_VALUE
    private val pair2 = "Ж" to Instant.now()

    private val jsonMapper = jacksonObjectMapper().registerModule(JavaTimeModule())
//        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    private val cborMapper = CBORMapper().registerModule(JavaTimeModule()).registerKotlinModule()
//        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    private val ionMapper = IonObjectMapper().registerModule(JavaTimeModule()).registerKotlinModule()
//        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    private val smileMapper = SmileMapper().registerModule(JavaTimeModule()).registerKotlinModule()
//        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    @Test
    fun test() {
        println(jsonMapper.writeValueAsBytes(pair1).toString(charset))
        println(jsonMapper.writeValueAsBytes(pair2).toString(charset))
        println()
        println(cborMapper.writeValueAsBytes(pair1).toString(charset))
        println(cborMapper.writeValueAsBytes(pair2).toString(charset))
        println()
        println(ionMapper.writeValueAsBytes(pair1).toString(charset))
        println(ionMapper.writeValueAsBytes(pair2).toString(charset))
        println()
        println(smileMapper.writeValueAsBytes(pair1).toString(charset))
        println(smileMapper.writeValueAsBytes(pair2).toString(charset))
    }
}

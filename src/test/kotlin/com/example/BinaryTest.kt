package com.example

import com.example.model.DemoRequest
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.avro.AvroMapper
import com.fasterxml.jackson.dataformat.avro.schema.AvroSchemaGenerator
import com.fasterxml.jackson.dataformat.cbor.databind.CBORMapper
import com.fasterxml.jackson.dataformat.ion.IonObjectMapper
import com.fasterxml.jackson.dataformat.protobuf.ProtobufMapper
import com.fasterxml.jackson.dataformat.smile.databind.SmileMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import java.time.Instant
import kotlin.io.encoding.Base64

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class BinaryTest {
    private val obj = DemoRequest(Long.MAX_VALUE, "abc", Instant.now(), setOf("a", "b"))
    private val jsonMapper = jacksonObjectMapper().registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    @Order(1)
    @Test
    fun proto() {
        printJson()

        val mapper = ProtobufMapper().registerModule(JavaTimeModule()).registerKotlinModule() as ProtobufMapper
        val schema = mapper.generateSchemaFor(DemoRequest::class.java)

        val bytes = mapper.writer(schema).writeValueAsBytes(obj)
        println(bytes.size)
        println(Base64.encode(bytes))

        val newObj = mapper.readerFor(DemoRequest::class.java).with(schema).readValue<DemoRequest>(bytes)
        println(newObj)
    }

    @Order(2)
    @Test
    fun avro() {
        printJson()

        val mapper = AvroMapper().registerModule(JavaTimeModule()).registerKotlinModule() as AvroMapper
        val gen = AvroSchemaGenerator()
        mapper.acceptJsonFormatVisitor(DemoRequest::class.java, gen)
        val schema = gen.generatedSchema

        val bytes = mapper.writer(schema).writeValueAsBytes(obj)
        println(bytes.size)
        println(Base64.encode(bytes))

        val newObj = mapper.readerFor(DemoRequest::class.java).with(schema).readValue<DemoRequest>(bytes)
        println(newObj)
    }

    @Order(3)
    @Test
    fun cbor() {
        printJson()

        val mapper = CBORMapper().registerModule(JavaTimeModule()).registerKotlinModule() as CBORMapper

        val bytes = mapper.writeValueAsBytes(obj)
        println(bytes.size)
        println(Base64.encode(bytes))

        val newObj = mapper.readValue<DemoRequest>(bytes)
        println(newObj)
    }

    @Order(4)
    @Test
    fun ion() {
        printJson()

        val mapper = IonObjectMapper().registerModule(JavaTimeModule()).registerKotlinModule() as IonObjectMapper

        val bytes = mapper.writeValueAsBytes(obj)
        println(bytes.size)
        println(Base64.encode(bytes))

        val newObj = mapper.readValue<DemoRequest>(bytes)
        println(newObj)
    }

    @Order(5)
    @Test
    fun smile() {
        printJson()

        val mapper = SmileMapper().registerModule(JavaTimeModule()).registerKotlinModule() as SmileMapper

        val bytes = mapper.writeValueAsBytes(obj)
        println(bytes.size)
        println(Base64.encode(bytes))

        val newObj = mapper.readValue<DemoRequest>(bytes)
        println(newObj)
    }

    private fun printJson() {
        val json = jsonMapper.writeValueAsString(obj)
        println(json.length)
        println(json)
    }
}

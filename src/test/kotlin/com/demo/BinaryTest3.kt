package com.demo

import com.demo.model.SessionInfoDto
import com.demo.utils.measureBlock
import com.demo.utils.toBase64
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
import java.time.ZoneId

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class BinaryTest3 {
    private val obj = SessionInfoDto(ZoneId.systemDefault(), ByteArray(100) { Byte.MAX_VALUE })
    private val jsonMapper = jacksonObjectMapper().registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    @Order(1)
    @Test
    fun proto() {
        printJson()

        val mapper = ProtobufMapper().registerModule(JavaTimeModule()).registerKotlinModule() as ProtobufMapper
        val schema = mapper.generateSchemaFor(SessionInfoDto::class.java)

        val objectWriter = mapper.writer(schema)
        val bytes = objectWriter.writeValueAsBytes(obj)
        println(bytes.size)
        println(bytes.toBase64())

        measureBlock { objectWriter.writeValueAsBytes(obj) }

        val objectReader = mapper.readerFor(SessionInfoDto::class.java).with(schema)
        val newObj = objectReader.readValue<SessionInfoDto>(bytes)
        println(newObj)

        measureBlock { objectReader.readValue<SessionInfoDto>(bytes) }
    }

    @Order(2)
    @Test
    fun avro() {
        printJson()

        val mapper = AvroMapper().registerModule(JavaTimeModule()).registerKotlinModule() as AvroMapper
        val gen = AvroSchemaGenerator()
        mapper.acceptJsonFormatVisitor(SessionInfoDto::class.java, gen)
        val schema = gen.generatedSchema

        val objectWriter = mapper.writer(schema)
        val bytes = objectWriter.writeValueAsBytes(obj)
        println(bytes.size)
        println(bytes.toBase64())

        measureBlock { objectWriter.writeValueAsBytes(obj) }

        val objectReader = mapper.readerFor(SessionInfoDto::class.java).with(schema)
        val newObj = objectReader.readValue<SessionInfoDto>(bytes)
        println(newObj)

        measureBlock { objectReader.readValue<SessionInfoDto>(bytes) }
    }

    @Order(3)
    @Test
    fun cbor() {
        printJson()

        val mapper = CBORMapper().registerModule(JavaTimeModule()).registerKotlinModule()

        val bytes = mapper.writeValueAsBytes(obj)
        println(bytes.size)
        println(bytes.toBase64())

        measureBlock { mapper.writeValueAsBytes(obj) }

        val newObj = mapper.readValue<SessionInfoDto>(bytes)
        println(newObj)

        measureBlock { mapper.readValue<SessionInfoDto>(bytes) }
    }

    @Order(4)
    @Test
    fun ion() {
        printJson()

        val mapper = IonObjectMapper().registerModule(JavaTimeModule()).registerKotlinModule()

        val bytes = mapper.writeValueAsBytes(obj)
        println(bytes.size)
        println(bytes.toBase64())

        measureBlock { mapper.writeValueAsBytes(obj) }

        val newObj = mapper.readValue<SessionInfoDto>(bytes)
        println(newObj)

        measureBlock { mapper.readValue<SessionInfoDto>(bytes) }
    }

    @Order(5)
    @Test
    fun smile() {
        printJson()

        val mapper = SmileMapper().registerModule(JavaTimeModule()).registerKotlinModule()

        val bytes = mapper.writeValueAsBytes(obj)
        println(bytes.size)
        println(bytes.toBase64())

        measureBlock { mapper.writeValueAsBytes(obj) }

        val newObj = mapper.readValue<SessionInfoDto>(bytes)
        println(newObj)

        measureBlock { mapper.readValue<SessionInfoDto>(bytes) }
    }

    private fun printJson() {
        val json = jsonMapper.writeValueAsString(obj)
        println(json.length)
        println(json)
    }
}

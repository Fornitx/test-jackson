package com.example.model

import java.time.Instant

data class DemoRequest(
    val msgId: Long,
    val msg: String,
    val timestamp: Instant,
    val payload: Set<String>
)

package com.example.model

import java.time.Instant

data class DemoResponse(
    val msgId: Long,
    val msg: String,
    val timestamp: Instant,
    val pair: Pair<Boolean, Boolean>,
    val payload: Map<String, Any>
)

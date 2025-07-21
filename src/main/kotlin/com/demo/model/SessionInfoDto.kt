package com.demo.model

import java.time.ZoneId

data class SessionInfoDto(
    val zoneId: ZoneId,
    val bytes: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SessionInfoDto

        if (zoneId != other.zoneId) return false
        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = zoneId.hashCode()
        result = 31 * result + bytes.contentHashCode()
        return result
    }
}

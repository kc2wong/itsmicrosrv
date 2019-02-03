package com.exiasoft.itscommon.util

import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class DateTimeUtil {
    companion object {

        fun date2LocalDate(date: Date?): LocalDate? {
            return when (date != null) {
                true -> timestamp2LocalDateTime(Timestamp(date.time))?.toLocalDate()
                false -> null
            }
        }

        fun timestamp2LocalDateTime(timestamp: Timestamp?): LocalDateTime? {
            return if (timestamp != null) timestamp.toLocalDateTime() else null
        }

        fun localDateTime2Timestamp(localDateTime: LocalDateTime?): Timestamp? {

            return localDateTime?.let {
                val defaultZoneId = ZoneId.systemDefault()
                Timestamp.from(it.atZone(defaultZoneId).toInstant())
            }
        }

        fun getCurrentDate() : LocalDate {
            return getCurrentDateTime(false).toLocalDate()
        }

        fun getCurrentDateTime(withNano: Boolean = false) : LocalDateTime {
            return if (withNano) LocalDateTime.now() else LocalDateTime.now().withNano(0)
        }

    }
}
package contacts

import java.time.format.DateTimeFormatter
import java.time.LocalDateTime


lateinit var validPhoneNumber: Regex

abstract class Contact (
    var phoneNumber: String = ""
) {

    var creationDateTime = getTimestampString()
    var lastEditDateTime = creationDateTime

    fun getPhoneNumb() = phoneNumber
    fun getCreatDateTime() = creationDateTime
    fun getLastEdit() = lastEditDateTime

    fun updateLastEditDateTime() {
        lastEditDateTime = getTimestampString()
    }
    fun setPhoneNumb(newNumber: String) { phoneNumber = newNumber }
    fun hasNumber(): Boolean = validPhoneNumber.matches(phoneNumber)

    abstract fun getAllPropertyNames(): List<String>
    abstract fun getProperty(property: String): String
    abstract fun setProperty(property: String, value: String)
}

private fun getTimestampString(): String {
    val now = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
    return formatter.format(now)
}
package contacts

import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties

class Person (
    var name: String,
    var surname: String,
    var birthDate: String = "[no data]",
    var gender: String = "[no data]",
    phoneNumber: String
): Contact(phoneNumber) {

    fun getPersonName() = name
    fun getPersonSurname() = surname
    fun getPersonBirthDate() = birthDate
    fun getPersonGender() = gender

    fun setPersonName(newName: String) { name = newName}
    fun setPersonSurname(newSurname: String) {surname = newSurname}
    fun setPersonBirthDate(newBirthDate: String) { birthDate = newBirthDate }
    fun setPersonGender(newGender: String) { gender = newGender }

    override fun getAllPropertyNames(): List<String> {
        val personProps = this::class.memberProperties.map { it.name }
        val contactProps = Contact::class.memberProperties.map { it.name }
        return personProps + contactProps
    }

    override fun getProperty(property: String): String {
        // Search for the field in the current class
        this::class.members.find { it.name == property }?.let { field ->
            if (field is KProperty<*>) {
                return field.getter.call(this)?.toString() ?: ""
            }
        }
        // If the property was not found in the current class, search in the superclass
        Contact::class.members.find { it.name == property }?.let { field ->
            if (field is KProperty<*>) {
                return field.getter.call(this)?.toString() ?: ""
            }
        }
        // If the property was not found in either class, return an empty string
        return ""
    }


    override fun setProperty(property: String, value: String) {
        val variable = this::class.members.find { it.name == property }
        if (variable is KMutableProperty<*>) {
            variable.setter.call(this, value)
        }
    }

    override fun toString(): String = "Name: $name\n" +
            "Surname: $surname\n" +
            "Birth date: $birthDate\n" +
            "Gender: $gender\n" +
            "Number: ${getPhoneNumb()}\n" +
            "Time created: ${getCreatDateTime()}\n" +
            "Time last edit: ${getLastEdit()}"
}
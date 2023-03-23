package contacts

import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties

class Organization (
    var orgName: String,
    var address: String,
    phoneNumber: String
): Contact(phoneNumber) {

    fun getOrganizationName() = orgName
    fun getOrganizationAddress() = address

    fun setOrganizationName(newName: String) { orgName = newName }
    fun setOrganizationAddress(newAddress: String) { address = newAddress }

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

    override fun toString(): String = "Organization name: $orgName\n" +
            "Address: $address\n" +
            "Number: ${getPhoneNumb()}\n" +
            "Time created: ${getCreatDateTime()}\n" +
            "Time last edit: ${getLastEdit()}"
}
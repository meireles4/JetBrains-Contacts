package contacts

import java.time.LocalDateTime

var phoneBook = PhoneBook()

fun main() {

    do {
        print("[menu] Enter action (add, list, search, count, exit): ")
        val action = readln()

        when (action) {
            "add" -> addContact()
            "list" -> listContacts()
            "search" -> searchContacts()
            "count" -> countContacts()
        }

        println()
    } while (action != "exit")
}

fun searchContacts() {

    while (true) {
        print("Enter search query: ")

        //read query from system.in
        val query = readln()

        //make query to phoneBook
        val queryResult = doQuery(query)

        //Print result of query (only names)
        val result = queryResult.mapIndexed {
                index, contact -> when (contact) {
            is Person -> "${index + 1}. ${contact.getPersonName()} ${contact.getPersonSurname()}"
            is Organization ->  "${index + 1}. ${contact.getOrganizationName()}"
            else -> println("Error! Bad class. Person or Organization Class expected!")
        }
        }.joinToString("\n")

        println("Found ${queryResult.size} results:")
        println(result + "\n")

        print("[search] Enter action ([number], back, again): ")
        val input  = readln()

        when (input) {
            "back" -> return
            "again" -> continue
            else -> {
                if (queryResult.isNotEmpty()) {
                    println(queryResult[input.toInt() - 1])
                    editContact(queryResult[input.toInt() - 1])
                }
                return
            }
        }
    }
}

fun doQuery(query: String): List<Contact> {
    val size = phoneBook.countContacts()
    val resultList = mutableListOf<Contact>()

    for (index in 0 until size){
        val contact = phoneBook.getContact(index)
        val properties = contact.getAllPropertyNames()
        var allPropertyValues = ""
        for (p in properties){
            allPropertyValues += contact.getProperty(p).lowercase()
        }
        if (allPropertyValues.contains(query.lowercase())) {
            resultList.add(contact)
        }
    }

    return resultList
}

fun listContacts() {
    phoneBook.listNames()
    println()

    print("[list] Enter action ([number], back): ")
    var input = readln()
    if (input == "back")
        return

    val contact = phoneBook.getContact(input.toInt()-1)
    println(contact.toString() + "\n")

    editContact(contact)

}

fun  editContact(contact: Contact) {
    do {
        print("[record] Enter action (edit, delete, menu): ")
        val input = readln()

        when (input) {
            "edit" -> {
                when(contact) {
                    is Person -> editPerson(contact)
                    is Organization -> editOrganization(contact)
                }
                println("Saved")
                println(contact.toString())
            }
            "delete" -> {
                phoneBook.removeContact(contact)
                println("Contact Removed")
                return
            }
        }

        println()

    } while (input != "menu")
}

fun editOrganization(contact: Organization) {
    print("Select a field (name, address, number): ")
    val field = readln()

    when (field) {
        "name" -> {
            print("Enter name: ")
            val newName = readln()
            contact.setOrganizationName(newName)
        }
        "address" -> {
            print("Enter address: ")
            val address = readln()
            contact.setOrganizationAddress(address)
        }
        "number" -> {
            print("Enter number: ")
            val number = validateNumber(readln())
            contact.setPhoneNumb(number)
        }
    }
}

fun editPerson(contact: Person) {
    print("Select a field (name, surname, birth, gender, number): ")
    val field = readln()

    when (field) {
        "name" -> {
            print("Enter name: ")
            val name = readln()
            contact.setPersonName(name)
        }
        "surname" -> {
            print("Enter surname: ")
            val surname = readln()
            contact.setPersonSurname(surname)
        }
        "birth" -> {
            print("Enter birth: ")
            val birth = validateBirthDate(readln())
            contact.setPersonBirthDate(birth)
        }
        "gender" -> {
            print("Enter gender: ")
            val gender = validateGender(readln())
            contact.setPersonGender(gender)
        }
        "number" -> {
            print("Enter number: ")
            val number = validateNumber(readln())
            contact.setPhoneNumb(number)
        }
    }
}

fun countContacts() {
    println("The Phone Book has ${phoneBook.countContacts()} records.")
}

fun addContact() {

    print("Enter the type (person, organization): ")
    val type = readln()

    when (type) {
        "person" -> addPerson()
        "organization" -> addOrganization()
    }
}

fun addOrganization() {
    print("Enter the organization name: ")
    val orgName = readln()

    print("Enter the address:")
    val address = readln()

    print("Enter the number : ")
    var number = readln()
    number = validateNumber(number)

    val newOrg = Organization(orgName, address, number)

    phoneBook.addOrganization(newOrg)
}

fun addPerson() {
    print("Enter the name: ")
    val name = readln()

    print("Enter the surname: ")
    val surname = readln()

    print("Enter the birth date: ")
    var birthDate = readln()
    birthDate = validateBirthDate(birthDate)

    print("Enter the gender (M, F): ")
    var gender = readln()
    gender = validateGender(gender)

    print("Enter the number : ")
    var number = readln()
    number = validateNumber(number)

    val newPerson = Person(name, surname, birthDate, gender, number)
    phoneBook.addPerson(newPerson)
}

fun validateNumber(number: String): String {
    val groupSymbol = "[0-9a-zA-Z]"
    val firstGroup = "$groupSymbol+"
    val group = "$groupSymbol{2,}"
    val pattern = Regex("\\+?($firstGroup([ -]\\($group\\))?|\\($firstGroup\\))([ -]$group)*")

    return if (number.matches(pattern)) number
    else {
        println("Bad number!")
        "[no data]"
    }
}

fun validateGender(gender: String): String {
    return if (gender == "M" || gender == "F")
        gender
    else {
        println("Bad gender!")
        "[no data]"
    }
}

fun validateBirthDate(birthDate: String): String {
    return try {
        LocalDateTime.parse(birthDate).toString()
    } catch (e: Exception) {
        println("Bad birth date!")
        "[no data]"
    }
}

package contacts

class PhoneBook() {

    private var contactList = mutableListOf<Contact>()

    fun addPerson(person: Person) {
        contactList.add(person)
        println("The record added.")
    }
    fun addOrganization(org: Organization) {
        contactList.add(org)
        println("The record added.")
    }

    fun getContact(index: Int): Contact = contactList[index]

//    fun removeContact(index: Int) {
//        contactList.removeAt(index-1)
//        println("The record removed!")
//    }

    fun removeContact(contact: Contact) {
        contactList.remove(contact)
        println("The record removed!")
    }

    fun countContacts() = contactList.count()

    fun listNames() {

        val result = contactList.mapIndexed {
                index, contact -> when (contact) {
                is Person -> "${index + 1}. ${contact.getPersonName()} ${contact.getPersonSurname()}"
                is Organization ->  "${index + 1}. ${contact.getOrganizationName()}"
                else -> println("Error! Bad class. Person or Organization Class expected!")
                }
        }.joinToString("\n")

        println(result)
    }
}
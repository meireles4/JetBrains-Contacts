import kotlinx.datetime.*

fun isLeapYear(year: String): Boolean {
    // Write your code here
    return try {
        val numberOfDays = "$year-02-29T00:00:00Z".toInstant()
        true
    } catch (e: Exception){
        false
    }

}

fun main() {
    val year = readln()
    println(isLeapYear(year))
}
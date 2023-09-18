import org.jsoup.Jsoup

fun main() {
    val locations = listOf(
        "altnine",
        "altcsdh",
        "altcmdh",
        "altpdh",
        "altrodh",
        "altbjqm",
        "altslugstop",
        "altglobal",
        "altmerrillmarket",
        "altoakes",
        "altperkem",
        "altperkbe",
        "altperkpsb",
        "altucentercafe",
        "altportermarket",
        "altstevenson",
        "altterra"
    )
    for (i in locations) {
        println(i)
        getDiningHours(i)
    }
}

fun getDiningHours(location: String) {
    val page = Jsoup.connect("https://dining.ucsc.edu/eat/").get()

    val days = page.select("div#${location} > p:has(strong)")

    val daysRemovedPatterns = Regex("<p><strong>|</strong></p>")
    val daysList = days.map { it.toString().replace(daysRemovedPatterns, "") }

    val hours = page.select("div#${location} > ul")
    val hoursList = mutableListOf<List<String>>()
    for (i in hours) {
        val items = i.select("li")
        val hoursRemovedPatterns = Regex("<li>|</li>| \\(limited entree options\\)\\*")
        val itemsList = items.map { it.toString().replace(hoursRemovedPatterns, "") }
        hoursList.add(itemsList)
    }

    if (daysList.size == hoursList.size) {
        for (i in daysList.indices) {
            println(daysList[i])
            println(hoursList[i])
            println()
        }
    } else {
        println("${daysList.size} ${hoursList.size}")
    }
}

fun getNonDiningHours(location: String) {
}
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
        if (i == "altnine" || i=="altcsdh" || i=="altcmdh" || i=="altpdh" || i=="altrodh") {
            getDiningHours(i)
        } else {
            getNonDiningHours(i)
        }
    }


    getNonDiningHours("altoakes")
}

fun getDiningHours(location: String) {
    val page = Jsoup.connect("https://dining.ucsc.edu/eat/").get()

    val days = page.select("div#${location} > p:has(strong)")

    val daysRemovedPatterns = Regex("<p><strong>|</strong></p>")
    val daysList = days.map { it.toString().replace(daysRemovedPatterns, "") }

    val hours = page.select("div#${location} > ul")
    val hoursRemovedPattern = Regex("<li>|</li>| \\(limited entree options\\)\\*")
    val hoursList = hours.map { i ->
        val items = i.select("li")
        items.map { it.toString().replace(hoursRemovedPattern, "") }
    }

    if (daysList.size == hoursList.size) {
        for (i in daysList.indices) {
            println(daysList[i])
            println(hoursList[i])
        }
    } else {
        println("${daysList.size} ${hoursList.size}")
    }
}

fun getNonDiningHours(location: String) {
    val page = Jsoup.connect("https://dining.ucsc.edu/eat/").get()
    val hours = page.select("div#${location} > table > tbody > tr > td")

    val hoursList = mutableListOf<String>()
    if (hours.size % 2 == 0) {
        val hoursRemovedPatterns = Regex("<td>|</td>")
        for (i in 0..<hours.size step 2) {
            hoursList.add("${hours[i].toString().replace(hoursRemovedPatterns,"")}: ${hours[i+1].toString().replace(hoursRemovedPatterns,"")}")
        }
    } else {
        println("error: hours list changed syntax")
    }
    println(hoursList)
}
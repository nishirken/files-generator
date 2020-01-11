package main

fun parseArgs(args: Array<String>): ArrayList<Pair<String, ArrayList<String>>> {
    var xs = ArrayList<Pair<String, ArrayList<String>>>()
    var fst = ""
    var snd = ArrayList<String>()
    for (arg in args) {
        if (arg.startsWith("-")) {
            if (snd.size != 0) {
                xs.add(Pair<String, ArrayList<String>>(fst, snd))
            }
            fst = arg
            snd = ArrayList()
            continue
        }
        snd.add(arg)
    }
    xs.add(Pair<String, ArrayList<String>>(fst, snd))
    return xs
}

fun findValue(key: String, args: ArrayList<Pair<String, ArrayList<String>>>): ArrayList<String>? {
    for (arg in args) {
        val k = arg.first
        if (k.endsWith(key)) {
            return arg.second
        }
    }
    return null
}

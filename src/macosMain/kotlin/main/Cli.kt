package main

import platform.posix.free

typealias ParsedArguments = ArrayList<Pair<String, ArrayList<String>>>

class Arguments(private val rawArgs: Array<String>) {
    private val reservedArguments: Array<String> = arrayOf("postfix", "name", "n", "path", "p")
    private val parsed: ParsedArguments = parseArgs()

    fun getPostfixArgument(): String? {
        return getSingleValue(findValue { it == "postfix" })
    }

    fun getNameArgument(): String? {
        return getSingleValue(findValue { it == "name" || it == "n" })
    }

    fun getPathArgument(): String? {
        return getSingleValue(findValue { it == "path" || it == "p" })
    }

    fun getSetAliasArgument(): Pair<String, String>? {
        val alias = findValue { it == "set-alias" }
        if (alias == null || alias.size < 2) {
            return null
        }
        return Pair(alias[0], alias[1])
    }

    fun getAlias(): String? {
        val freeArguments = parsed.filter { !reservedArguments.contains(it.first) }
        return if (freeArguments.isNotEmpty()) freeArguments[0].first else null
    }

    private fun findValue(f: (key: String) -> Boolean): ArrayList<String>? {
        for (arg in parsed) {
            val key = arg.first
            if (f(key)) {
                return arg.second
            }
        }
        return null
    }

    private fun getSingleValue(values: ArrayList<String>?): String? {
        return if (values == null) null else values[0]
    }

    private fun parseArgs(): ParsedArguments {
        var xs = ArrayList<Pair<String, ArrayList<String>>>()
        var fst = ""
        var snd = ArrayList<String>()
        for (arg in rawArgs) {
            if (arg.startsWith("-")) {
                if (snd.size != 0) {
                    xs.add(Pair(fst, snd))
                }
                fst = arg.replace("^(-?-)".toRegex(), "")
                snd = ArrayList()
                continue
            }
            snd.add(arg)
        }
        xs.add(Pair(fst, snd))
        return xs
    }
}

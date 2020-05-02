package main

import platform.AudioToolbox.AudioUnitProperty
import platform.posix.free

typealias ParsedArguments = List<Pair<String, List<String>>>

class Arguments(private val rawArgs: Array<String>) {
    private val reservedArguments: Array<String> = arrayOf("postfix", "name", "n", "path", "p", "without-test", "wt", "directory", "d")
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

    fun getWithDirectory(): Boolean {
        return findValue { it == "directory" || it == "d" } != null
    }

    fun getWithoutTestArgument(): Boolean {
        return findValue { it == "without-test" || it == "wt" } != null
    }

    fun getSetAliasArgument(): Pair<String, String>? {
        val alias = findValue { it == "set-alias" }
        if (alias == null || alias.size < 2) {
            return null
        }
        return Pair(alias[0], alias[1])
    }

    fun getAlias(): String? {
        val keys = parsed.map { it.first }
        val freeArguments = keys.filter { !reservedArguments.contains(it) }
        val hasPostfix = keys.contains("postfix")
        return if (freeArguments.isNotEmpty() && !hasPostfix) freeArguments[0] else null
    }

    private fun findValue(f: (key: String) -> Boolean): List<String>? {
        for (arg in parsed) {
            val key = arg.first
            if (f(key)) {
                return arg.second
            }
        }
        return null
    }

    private fun getSingleValue(values: List<String>?): String? {
        return if (values == null) null else values[0]
    }

    private fun parseArgs(): ParsedArguments {
        return collect(rawArgs.toList(), ArrayList())
    }

    private fun collect(args: List<String>, acc: ParsedArguments): ParsedArguments {
        if (args.isEmpty()) {
            return acc
        }

        val clearedKey = args.first().replace("^(-?-)".toRegex(), "")
        val values = args.drop(1).takeWhile { !it.startsWith("-") }
        val rest = args.drop(1).dropWhile { !it.startsWith("-") }
        return collect(rest, acc.plus(Pair(clearedKey, values)))
    }
}

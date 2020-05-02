package main

import kotlin.test.Test
import kotlin.test.assertTrue

class ArgumentsTests {
    @Test
    fun `return postfix argument if exists`() {
        val postfix = "State"
        val args = Arguments(arrayOf("--postfix", postfix))
        assertTrue { args.getPostfixArgument() == postfix }
    }

    @Test
    fun `return null postfix if not exists`() {
        val args = Arguments(arrayOf("-n", "Name"))
        assertTrue { args.getPostfixArgument() == null }
    }

    @Test
    fun `return name argument if exists`() {
        val name = "Name"
        assertTrue { Arguments(arrayOf("--name", name)).getNameArgument() == name }
        assertTrue { Arguments(arrayOf("-n", name)).getNameArgument() == name }
    }

    @Test
    fun `return null name if not exists`() {
        val args = Arguments(arrayOf("--postfix", "State"))
        assertTrue { args.getNameArgument() == null }
    }

    @Test
    fun `return path if exists`() {
        val path = "/Users"
        assertTrue { Arguments(arrayOf("--path", path)).getPathArgument() == path }
        assertTrue { Arguments(arrayOf("-p", path)).getPathArgument() == path }
    }

    @Test
    fun `return null path if not exists`() {
        val args = Arguments(arrayOf("-n", "Name"))
        assertTrue { args.getPathArgument() == null }
    }

    @Test
    fun `return setAlias if exists`() {
        val key = "s"
        val value = "State"
        val args = Arguments(arrayOf("--set-alias", key, value))
        assertTrue { args.getSetAliasArgument() == Pair(key, value) }
    }

    @Test
    fun `return null setAlias if not exists`() {
        val args = Arguments(arrayOf("--name", "Name"))
        assertTrue { args.getSetAliasArgument() == null }
    }

    @Test
    fun `return true without test if exists`() {
        assertTrue { Arguments(arrayOf("--without-test")).getWithoutTestArgument() }
        assertTrue { Arguments(arrayOf("-n", "Name", "-p", "path", "-wt")).getWithoutTestArgument() }
    }

    @Test
    fun `return false without test if not exists`() {
        val args = Arguments(arrayOf("-p", "/Users", "-n", "Name"))
        assertTrue { !args.getWithoutTestArgument() }
    }

    @Test
    fun `return alias if exists in the end, skip rest`() {
        val args = Arguments(arrayOf("-p", "/Users", "-n", "Name", "-s", "-k"))
        println(args.getAlias())
        assertTrue { args.getAlias() == "s" }
    }

    @Test
    fun `return null alias if not exists`() {
        val args = Arguments(arrayOf("-p", "/Users", "-n", "Name"))
        assertTrue { args.getAlias() == null }
    }

    @Test
    fun `return null alias if postfix exists`() {
        val args = Arguments(arrayOf("-p", "/Users", "-n", "Name", "--postfix", "State", "-s"))
        assertTrue { args.getAlias() == null }
    }

    @Test
    fun `return alias if exists in the mid`() {
        val args = Arguments(arrayOf("-p", "/Users", "-s", "-n", "Name"))
        assertTrue { args.getAlias() == "s" }
    }

    @Test
    fun `return null directory if not exists`() {
        val args = Arguments(arrayOf("-p", "/Users", "-n", "Name", "--postfix", "State", "-s"))
        assertTrue { !args.getWithDirectory() }
    }

    @Test
    fun `return directory if exists`() {
        assertTrue { Arguments(arrayOf("-p", "/Users", "-s", "-n", "Name", "--directory")).getWithDirectory() }
        assertTrue { Arguments(arrayOf("-p", "/Users", "-s", "-n", "Name", "-d")).getWithDirectory() }
    }
}

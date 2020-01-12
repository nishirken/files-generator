@file:kotlinx.cinterop.InteropStubs
@file:Suppress("UNUSED_VARIABLE", "UNUSED_EXPRESSION")
package interop

import kotlin.native.SymbolName
import kotlinx.cinterop.internal.*
import kotlinx.cinterop.*

// NOTE THIS FILE IS AUTO-GENERATED

@CCall("knifunptr_interop0_getCwd")
external fun getCwd(): CPointer<ByteVar>?

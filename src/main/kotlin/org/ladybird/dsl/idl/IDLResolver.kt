package org.ladybird.dsl.idl

import com.intellij.psi.PsiElement
import com.intellij.psi.util.descendantsOfType
import org.ladybird.dsl.common.findChildrenOfType
import org.ladybird.dsl.idl.psi.IDLDeclaration
import org.ladybird.dsl.idl.psi.api.IDLImportStatement
import org.ladybird.dsl.idl.psi.mixins.resolveFile

class IDLResolver(private val file: IDLFile) {
    fun findDeclaration(name: String, visitedFiles: MutableSet<IDLFile> = mutableSetOf(file)): IDLDeclaration? {
        file.descendantsOfType<IDLDeclaration>().forEach {
            if (it.name == name) return it
        }

        file.findChildrenOfType<IDLImportStatement>().forEach {
            val file = it.resolveFile() ?: return@forEach
            if (file in visitedFiles)
                return@forEach
            visitedFiles.add(file)
            val result = IDLResolver(file).findDeclaration(name, visitedFiles)
            if (result != null)
                return result
        }

        return null
    }

    fun resolveIdent(ident: PsiElement): IDLDeclaration? {
        val name = ident.text
        if (name in builtinTypes)
            return null

        if (name.startsWith("Promise<") ||
            name.startsWith("FrozenArray<") ||
            name.startsWith("ObservableArray<") ||
            name.startsWith("record<") ||
            name.startsWith("sequence<") ||
            name.startsWith("maplike<") ||
            name.startsWith("setlike<")
        )
            return null

        return findDeclaration(name)
    }

    companion object {
        private val builtinTypes = setOf(
            "short",
            "long",
            "long long",
            "unsigned short",
            "unsigned long",
            "unsigned long long",
            "float",
            "double",
            "unrestricted float",
            "unrestricted double",
            "boolean",
            "byte",
            "octet",
            "bigint",
            "WindowProxy",
            "ByteString",
            "DOMString",
            "USVString",
            "CSSOMString",
            "ArrayBuffer",
            "SharedArrayBuffer",
            "ArrayBufferView",
            "AllowSharedBufferSource",
            "BufferSource",
            "DataView",
            "Int8Array",
            "Int16Array",
            "Int32Array",
            "Uint8Array",
            "Uint16Array",
            "Uint32Array",
            "Uint8ClampedArray",
            "BigInt64Array",
            "BigUint64Array",
            "Float32Array",
            "Float64Array",
            "Function",
            "any",
            "object",
            "symbol",

            // TODO: This should be an IDL file, but isn't (https://dom.spec.whatwg.org/#callbackdef-eventlistener)
            "EventListener",
        )
    }
}

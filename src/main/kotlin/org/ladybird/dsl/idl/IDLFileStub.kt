package org.ladybird.dsl.idl

import com.intellij.psi.stubs.PsiFileStubImpl
import com.intellij.psi.tree.IStubFileElementType

class IDLFileStub(file: IDLFile?) : PsiFileStubImpl<IDLFile>(file) {
    object Type : IStubFileElementType<IDLFileStub>("IDLFile", IDLLanguage) {
        // Should be incremented when lexer, parser, or stub tree changes
        override fun getStubVersion() = 1
    }
}

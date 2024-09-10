package org.ladybird.dsl.ipc

import com.intellij.psi.stubs.PsiFileStubImpl
import com.intellij.psi.tree.IStubFileElementType

class IPCFileStub(file: IPCFile?) : PsiFileStubImpl<IPCFile>(file) {
    object Type : IStubFileElementType<IPCFileStub>("IPCFile", IPCLanguage) {
        // Should be incremented when lexer, parser, or stub tree changes
        override fun getStubVersion() = 1
    }
}

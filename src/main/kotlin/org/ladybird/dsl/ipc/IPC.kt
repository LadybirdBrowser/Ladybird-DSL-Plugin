package org.ladybird.dsl.ipc

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader
import com.intellij.psi.FileViewProvider

object IPCLanguage : Language("Ladybird IPC") {
    val FILE_ICON = IconLoader.getIcon("/META-INF/ipc.png", IPCLanguage::class.java)
    val FILE_ICON_FOR_MARKER = IconLoader.getIcon("/META-INF/ipc_without_border.png", IPCLanguage::class.java)
}

object IPCFileType : LanguageFileType(IPCLanguage) {
    override fun getName() = "Ladybird IPC"

    override fun getDescription() = "The Ladybird IPC file type"

    override fun getDefaultExtension() = "ipc"

    override fun getIcon() = IPCLanguage.FILE_ICON
}

class IPCFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, IPCLanguage) {
    override fun getFileType() = IPCFileType
}

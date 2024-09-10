package org.ladybird.dsl.idl

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader
import com.intellij.psi.FileViewProvider

object IDLLanguage : Language("Ladybird IDL") {
    val FILE_ICON = IconLoader.getIcon("/META-INF/idl.svg", IDLLanguage::class.java)
}

object IDLFileType : LanguageFileType(IDLLanguage) {
    override fun getName() = "Ladybird IDL"

    override fun getDescription() = "The Ladybird IDL file type"

    override fun getDefaultExtension() = "idl"

    override fun getIcon() = IDLLanguage.FILE_ICON
}

class IDLFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, IDLLanguage) {
    override fun getFileType() = IDLFileType
}


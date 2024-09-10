package org.ladybird.dsl.idl.psi.mixins

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiReference
import org.ladybird.dsl.idl.IDLFile
import org.ladybird.dsl.idl.project.idlProject
import org.ladybird.dsl.idl.psi.IDLNamedElement
import org.ladybird.dsl.idl.psi.api.IDLImportStatement
import org.ladybird.dsl.idl.psi.singleRef
import kotlin.io.path.Path

abstract class IDLImportStatementMixin(node: ASTNode) : IDLNamedElement(node), IDLImportStatement {
    override fun getReference(): PsiReference? = singleRef(IDLImportStatement::resolveFile)

    override fun getNameIdentifier() = importPath
}

fun IDLImportStatement.resolveFile(): IDLFile? =
    idlProject.resolveImportedFile(containingFile.originalFile.virtualFile, Path(importPath.text.drop(1).dropLast(1)))

package org.ladybird.dsl.idl

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.CustomFoldingBuilder
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.refactoring.suggested.endOffset
import com.intellij.refactoring.suggested.startOffset
import org.ladybird.dsl.common.nextSiblingOfType
import org.ladybird.dsl.common.prevSiblingOfType
import org.ladybird.dsl.idl.psi.IDLPsiElement
import org.ladybird.dsl.idl.psi.api.*

class IDLBlockFoldingBuilder : CustomFoldingBuilder() {
    override fun buildLanguageFoldRegions(
        descriptors: MutableList<FoldingDescriptor>,
        root: PsiElement,
        document: Document,
        quick: Boolean
    ) {
        val visitor = FoldingVisitor(descriptors)
        PsiTreeUtil.processElements(root) {
            it.accept(visitor)
            true
        }
    }

    override fun getLanguagePlaceholderText(node: ASTNode, range: TextRange) = "{ ... }"

    override fun isRegionCollapsedByDefault(node: ASTNode) = false

    private class FoldingVisitor(private val descriptors: MutableList<FoldingDescriptor>) : IDLVisitor() {
        override fun visitPsiElement(o: IDLPsiElement) {
        }

        override fun visitCallbackInterface(o: IDLCallbackInterface) {
            super.visitCallbackInterface(o)
            descriptors += FoldingDescriptor(o, TextRange(o.openCurly.startOffset, o.closeCurly.endOffset))
        }

        override fun visitInterface(o: IDLInterface) {
            super.visitInterface(o)
            descriptors += FoldingDescriptor(o, TextRange(o.openCurly.startOffset, o.closeCurly.endOffset))
        }

        override fun visitInterfaceMixin(o: IDLInterfaceMixin) {
            super.visitInterfaceMixin(o)
            descriptors += FoldingDescriptor(o, TextRange(o.openCurly.startOffset, o.closeCurly.endOffset))
        }

        override fun visitNamespace(o: IDLNamespace) {
            super.visitNamespace(o)
            descriptors += FoldingDescriptor(o, TextRange(o.openCurly.startOffset, o.closeCurly.endOffset))
        }

        override fun visitDictionary(o: IDLDictionary) {
            super.visitDictionary(o)
            descriptors += FoldingDescriptor(o, TextRange(o.openCurly.startOffset, o.closeCurly.endOffset))
        }

        override fun visitPartialDictionary(o: IDLPartialDictionary) {
            super.visitPartialDictionary(o)
            descriptors += FoldingDescriptor(o, TextRange(o.openCurly.startOffset, o.closeCurly.endOffset))
        }

        override fun visitEnum(o: IDLEnum) {
            super.visitEnum(o)
            descriptors += FoldingDescriptor(o, TextRange(o.openCurly.startOffset, o.closeCurly.endOffset))
        }
    }
}

class IDLImportFoldingBuilder : CustomFoldingBuilder() {
    override fun buildLanguageFoldRegions(
        descriptors: MutableList<FoldingDescriptor>,
        root: PsiElement,
        document: Document,
        quick: Boolean
    ) {
        val visitor = FoldingVisitor(descriptors)
        PsiTreeUtil.processElements(root) {
            it.accept(visitor)
            true
        }
    }

    override fun getLanguagePlaceholderText(node: ASTNode, range: TextRange): String {
        return "#import ..."
    }

    override fun isRegionCollapsedByDefault(node: ASTNode) = node.psi is IDLImportStatement

    private class FoldingVisitor(private val descriptors: MutableList<FoldingDescriptor>) : IDLVisitor() {
        override fun visitPsiElement(o: IDLPsiElement) {
        }

        override fun visitImportStatement(o: IDLImportStatement) {
            super.visitImportStatement(o)

            // Only do this for the first import statement
            if (o.prevSiblingOfType<IDLImportStatement>() != null)
                return

            val lastImportStatement = generateSequence(o) { it.nextSiblingOfType<IDLImportStatement>() }.last()
            descriptors += FoldingDescriptor(o.parent, TextRange(o.startOffset, lastImportStatement.endOffset))
        }
    }
}

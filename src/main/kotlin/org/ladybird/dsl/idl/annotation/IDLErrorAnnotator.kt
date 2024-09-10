package org.ladybird.dsl.idl.annotation

import ai.grazie.utils.dropPrefix
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import org.ladybird.dsl.common.DSLAnnotator
import org.ladybird.dsl.common.prevSiblings
import org.ladybird.dsl.idl.psi.IDLDeclaration
import org.ladybird.dsl.idl.psi.api.*
import java.net.MalformedURLException
import java.net.URL

class IDLErrorAnnotator : DSLAnnotator() {
    override fun annotate(element: PsiElement) {
        if (element is IDLIdent) {
            if (element.reference != null && element.reference?.resolve() == null) {
                element.highlightError("Unknown type ${element.text}")
                return
            }
        }

        if (element !is IDLInterfaceMixin && element !is IDLInterface && element !is IDLCallbackInterface)
            return

        // Warn for interfaces that don't have a spec link
        val comments = element.parent
            .prevSiblings()
            .takeWhile { it is PsiWhiteSpace || it is PsiComment || it is IDLExtendedAttributeList }
            .filterIsInstance<PsiComment>()
            .map { it.text }

        for (comment in comments) {
            try {
                URL(comment.dropPrefix("//").trim())
                return
            } catch (e: MalformedURLException) {
                continue
            }
        }

        val target = (element as? IDLDeclaration)?.nameIdentifier ?: element
        target.highlightError("This declaration has no spec link")
    }
}

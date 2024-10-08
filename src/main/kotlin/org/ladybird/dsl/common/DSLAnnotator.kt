package org.ladybird.dsl.common

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

abstract class DSLAnnotator : Annotator {
    private lateinit var holder: AnnotationHolder

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        this.holder = holder
        annotate(element)
    }

    abstract fun annotate(element: PsiElement)

    private fun newAnnotation(severity: HighlightSeverity, message: String? = null) = if (message == null) {
        holder.newSilentAnnotation(severity)
    } else holder.newAnnotation(severity, message)

    fun PsiElement.highlight(attribute: TextAttributesKey) {
        newAnnotation(HighlightSeverity.INFORMATION)
            .range(this)
            .textAttributes(attribute)
            .create()
    }

    fun TextRange.highlight(attribute: TextAttributesKey) {
        newAnnotation(HighlightSeverity.INFORMATION)
            .range(this)
            .textAttributes(attribute)
            .create()
    }

    fun PsiElement.highlightError(message: String) {
        newAnnotation(HighlightSeverity.ERROR, message)
            .range(this)
            .create()
    }
}

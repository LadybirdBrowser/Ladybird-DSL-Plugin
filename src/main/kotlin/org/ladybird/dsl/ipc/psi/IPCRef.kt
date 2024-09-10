package org.ladybird.dsl.ipc.psi

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult

abstract class IPCRef<T : IPCNameIdentifierOwner>(element: T) : PsiPolyVariantReferenceBase<T>(element) {
    open fun multiResolve(): List<PsiElement> = listOfNotNull(singleResolve())

    open fun singleResolve(): PsiElement? =
        error("No implementation of singleResolve() for ref ${this::class.simpleName}")

    final override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        return (element.reference as IPCRef<*>).multiResolve().map(::PsiElementResolveResult).toTypedArray()
    }

    final override fun calculateDefaultRangeInElement(): TextRange {
        val identifier = element.nameIdentifier ?: return TextRange.EMPTY_RANGE
        check(identifier.parent == element)
        return TextRange.from(identifier.startOffsetInParent, identifier.textLength)
    }

    override fun handleElementRename(newElementName: String): PsiElement {
        element.nameIdentifier?.let {
            it.replace(IPCPsiFactory(it.project).createIdentifier(newElementName))
            return it
        }

        return super.handleElementRename(newElementName)
    }
}

inline fun <T : IPCNameIdentifierOwner> T.singleRef(crossinline producer: (T) -> PsiElement?) =
    object : IPCRef<T>(this) {
        override fun singleResolve() = producer(element)
    }

inline fun <T : IPCNameIdentifierOwner> T.multiRef(crossinline producer: (T) -> List<PsiElement>) =
    object : IPCRef<T>(this) {
        override fun multiResolve() = producer(element)
    }

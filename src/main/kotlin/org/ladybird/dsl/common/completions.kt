package org.ladybird.dsl.common

import com.intellij.openapi.util.Key
import com.intellij.patterns.*
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.util.ProcessingContext

typealias PsiPattern = ElementPattern<out PsiElement>

fun psiElement(type: IElementType): PsiElementPattern.Capture<PsiElement> = PlatformPatterns.psiElement(type)

inline fun <reified T : PsiElement> psiElement(): PsiElementPattern.Capture<T> =
    PlatformPatterns.psiElement(T::class.java)

inline operator fun <reified T> ProcessingContext.set(key: Key<T>, value: T) = this.put(key, value)

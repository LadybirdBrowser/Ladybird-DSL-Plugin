package me.mattco.serenityos.idl

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType

fun PsiElement.prevSiblings() = generateSequence(this.prevSibling) { it.prevSibling }.filter { it !is PsiWhiteSpace }
fun PsiElement.nextSiblings() = generateSequence(this.nextSibling) { it.nextSibling }.filter { it !is PsiWhiteSpace }

inline fun <reified T> PsiElement.prevSiblingOfType() = prevSiblings().find { it is T } as T?
inline fun <reified T> PsiElement.nextSiblingOfType() = nextSiblings().find { it is T } as T

inline fun <reified T> PsiElement.prevSiblingsOfType() = prevSiblings().filterIsInstance<T>()
inline fun <reified T> PsiElement.nextSiblingsOfType() = nextSiblings().filterIsInstance<T>()

val PsiElement.allChildren: Sequence<PsiElement>
    get() = generateSequence(firstChild) { it.nextSibling }

inline fun <reified T : PsiElement> PsiElement.findChildrenOfType() = allChildren.filterIsInstance<T>()

inline fun <reified T : PsiElement> PsiElement.findChildOfType(): T? = findChildrenOfType<T>().singleOrNull()

fun PsiElement.findChildOfType(type: IElementType): PsiElement? = findChildrenOfType(type).singleOrNull()

fun PsiElement.findChildrenOfType(type: IElementType): List<PsiElement> =
    allChildren.filter { it.elementType == type }.toList()

inline fun <reified T : PsiElement> PsiElement.findNotNullChildOfType(): T = findChildrenOfType<T>().single()

inline fun <reified T : PsiElement> PsiElement.descendantOfType(strict: Boolean = true): T? =
    PsiTreeUtil.findChildOfType(this, T::class.java, strict)

inline fun <reified T : PsiElement> PsiElement.descendentsOfType(): Collection<T> =
    PsiTreeUtil.findChildrenOfType(this, T::class.java)

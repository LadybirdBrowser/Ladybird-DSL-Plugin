package org.ladybird.dsl.common

import com.intellij.lang.Language
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage

abstract class DSLColorSettingsPage(private val language: Language) : ColorSettingsPage {
    abstract fun getTemplate(): String

    abstract fun getAttributes(): Map<String, TextAttributesKey>

    abstract override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>

    override fun getDemoText(): String {
        val regex = """([A-Z_]+)\{([^)]+?)}""".toRegex()
        return getTemplate().replace(regex) {
            val tag = it.groups[1]!!.value
            check(tag in additionalHighlightingTagToDescriptorMap) {
                "Unknown color page tag $tag"
            }
            val content = it.groups[2]!!.value
            "<$tag>$content</$tag>"
        }
    }

    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

    override fun getDisplayName() = language.displayName

    override fun getAttributeDescriptors() =
        getAttributes().map { AttributesDescriptor(it.key, it.value) }.toTypedArray()
}

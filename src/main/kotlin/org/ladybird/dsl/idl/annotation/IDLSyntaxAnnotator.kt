package org.ladybird.dsl.idl.annotation

import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.refactoring.suggested.endOffset
import com.intellij.refactoring.suggested.startOffset
import org.ladybird.dsl.common.DSLAnnotator
import org.ladybird.dsl.idl.Highlights
import org.ladybird.dsl.idl.psi.api.*

class IDLSyntaxAnnotator : DSLAnnotator(), DumbAware {
    override fun annotate(element: PsiElement) {
        when (element) {
            is IDLImportPath ->
                TextRange(element.openAngle.startOffset, element.closeAngle.endOffset).highlight(Highlights.IMPORT_PATH)
            is IDLExtendedAttribute -> element.identifier.highlight(Highlights.EXTENDED_ATTRIBUTE_NAME)
            is IDLPrimitiveType -> element.textRange.highlight(Highlights.PRIMITIVE_TYPE)
            is IDLUndefinedLiteral -> element.highlight(Highlights.UNDEFINED)
            is IDLNullLiteral -> element.highlight(Highlights.NULL)
            is IDLAttributeName -> element.highlight(Highlights.ATTRIBUTE_NAME)
            is IDLOperationName -> element.highlight(Highlights.FUNCTION_NAME)
            is IDLEnum -> element.identifier.highlight(Highlights.ENUM_NAME)
            is IDLCallbackInterface -> element.identifier.highlight(Highlights.INTERFACE_NAME)
            is IDLInterface -> element.identifier.highlight(Highlights.INTERFACE_NAME)
            is IDLInterfaceMixin -> element.identifier.highlight(Highlights.INTERFACE_NAME)
            is IDLDictionary -> element.identifier.highlight(Highlights.DICTIONARY_NAME)
            is IDLAnyType,
            is IDLPromiseTypeName,
            is IDLStringType,
            is IDLObjectType,
            is IDLSymbolType,
            is IDLBufferRelatedType,
            is IDLFrozenArrayTypeName,
            is IDLObservableArrayTypeName,
            is IDLRecordTypeName,
            is IDLPlatformType -> element.textRange.highlight(Highlights.PLATFORM_TYPE)
            is IDLSequenceType ->
                TextRange(element.startOffset, element.openAngle.startOffset).highlight(Highlights.PLATFORM_TYPE)
            is IDLUnionSeparator -> element.highlight(Highlights.KEYWORD)
        }
    }
}

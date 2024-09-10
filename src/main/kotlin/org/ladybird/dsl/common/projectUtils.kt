package org.ladybird.dsl.common

import com.intellij.openapi.project.BaseProjectDirectories.Companion.getBaseDirectories
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

// TODO: Dependent on folder name?
val Project.isLadybird: Boolean
    get() = name == "Ladybird"

val Project.userlandDirectory: VirtualFile?
    get() = getBaseDirectories().singleOrNull()?.findChild("Userland")

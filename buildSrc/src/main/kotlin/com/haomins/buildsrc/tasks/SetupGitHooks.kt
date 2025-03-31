package com.haomins.buildsrc.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import java.io.ByteArrayOutputStream
import javax.inject.Inject

abstract class SetupGitHooks : DefaultTask() {

    @get:Inject
    abstract val execOperations: ExecOperations

    init {
        group = "git"
        description = "Sets up Git hooks to use the .githooks directory"
    }

    @TaskAction
    fun setupGitHooks() {
        val hooksPath = ".githooks"
        val currentHooksPath = ByteArrayOutputStream().also { outputStream ->
            execOperations.exec {
                commandLine("git", "config", "core.hooksPath")
                standardOutput = outputStream
            }
        }.toString().trim()

        if (currentHooksPath != hooksPath) {
            execOperations.exec {
                commandLine("git", "config", "core.hooksPath", hooksPath)
            }
            println("Git hooks path set to $hooksPath")
        } else {
            println("Git hooks path is already set to $hooksPath")
        }
    }
}
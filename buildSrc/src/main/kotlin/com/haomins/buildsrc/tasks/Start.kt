package com.haomins.buildsrc.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class Start : DefaultTask() {

    init {
        group = "project startup"
        description =
            "You should run this when initially started working on the project"
        setDependsOn()
    }

    @TaskAction
    fun start() {
        println("Configuring project setup...")
    }

    private fun setDependsOn() {
        dependsOn("setupGitHooks")
    }
}
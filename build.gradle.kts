plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.composeHotReload) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.spotless)
}

subprojects {
    apply(plugin = "com.diffplug.spotless")

    spotless {
        kotlin {
            target("**/*.kt")
            licenseHeaderFile(
                rootProject.file("spotless/copyright.kt"),
                "^(package|object|import|interface)",
            )
            trimTrailingWhitespace()
            endWithNewline()

            // example if you later decide to enforce Compose rules
            // ktlint().customRuleSets(
            //     listOf("io.nlopez.compose.rules:ktlint:${libs.versions.ktlintComposeRules.get()}")
            // )
        }
        format("kts") {
            target("**/*.kts")
            licenseHeaderFile(rootProject.file("spotless/copyright.kt"), "(^(?![\\/ ]\\*).*$)")
        }
        format("misc") {
            target("**/*.md", "**/.gitignore")
            trimTrailingWhitespace()
            leadingTabsToSpaces()
            endWithNewline()
        }
    }
}

group = "com.zamulabs"
version = properties["version"] as String

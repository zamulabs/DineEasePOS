/*
 * Copyright 2025 Zamulabs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinX.serialization.plugin)
    id("app.cash.sqldelight") version "2.1.0"
    // Remove Conveyor if you don’t intend to use it
    // id("dev.hydraulic.conveyor") version "1.12"
}

kotlin {
    jvm()
    jvmToolchain(17)

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            api(libs.koin.core)
            implementation(libs.koin.compose)

            implementation(libs.kotlinX.serializationJson)
            implementation(libs.material3.window.size.multiplatform)

            implementation(libs.sqlDelight.runtime)
            implementation(libs.coroutines.extensions)
            implementation(libs.primitive.adapters)

            api(libs.multiplatformSettings.noArg)
            api(libs.multiplatformSettings.coroutines)

            api(libs.napier)
            implementation(libs.kotlinX.dateTime)
            implementation(libs.koalaplot.core)
            implementation(libs.stdlib)
            implementation(libs.androidx.navigation.compose)

            // Compose Data Table (Material 3)
            implementation("com.seanproctor:data-table-material3:0.11.4")

            // Ktor
            implementation(libs.ktorCore)
            implementation(libs.ktorSerialization)
            implementation(libs.ktorLogging)
            implementation(libs.contentNegotiation)
            implementation(libs.ktorJson)
            implementation(libs.ktorCio)
            implementation(libs.ktorAuth)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(libs.sqlite.driver)

            // Compose SplitPane
            implementation("org.jetbrains.compose.components:components-splitpane-desktop:1.6.11")

            // Toaster for Windows
            implementation(libs.toast4j)

            // JNA for Linux
            implementation("de.jangassen:jfa:1.2.0") {
                exclude(group = "net.java.dev.jna", module = "jna")
            }
            implementation(libs.jna)
        }
    }
}

sqldelight {
    databases {
        create("DineEaseDatabase") {
            packageName.set("com.zamulabs.dineeasepos.database")
        }
    }
}

group = "com.zamulabs"
version = properties["version"] as String

compose.desktop {
    application {
        mainClass = "com.zamulabs.dineeasepos.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Exe, TargetFormat.Deb)
            packageName = "DineEasePOS"
            packageVersion = properties["version"] as String
            description = "Point of Sale system for restaurants"
            vendor = "Zamulabs"
            copyright = "© 2025 Zamulabs"
            licenseFile.set(project.file("LICENSE"))

            macOS {
                outputBaseDir.set(buildDir.resolve("mac/installation"))
                iconFile.set(file("src/jvmMain/composeResources/drawable/launcher_icons/macos.icns"))
                bundleID = "com.zamulabs.dineeasepos"
            }
            windows {
                outputBaseDir.set(buildDir.resolve("win/installation"))
                iconFile.set(file("src/jvmMain/composeResources/drawable/launcher_icons/windowsos.ico"))
                dirChooser = true
                perUserInstall = true
                upgradeUuid = "3d6b3c64-2f90-4c6d-9b8f-13f9a71c2e11" // Generate a GUID once
            }
            linux {
                outputBaseDir.set(buildDir.resolve("linux/installation"))
                iconFile.set(file("src/jvmMain/composeResources/drawable/launcher_icons/linuxos.png"))
                debMaintainer = "support@zamulabs.com"
                menuGroup = "DineEasePOS"
                appCategory = "Office"
            }

            modules("java.instrument", "java.management", "java.prefs", "java.sql", "jdk.unsupported")

            buildTypes.release.proguard {
                isEnabled = true
                obfuscate.set(false)
                optimize.set(true)
                configurationFiles.from(project.file("proguard-rules.pro"))
            }
        }
    }
}

// region Work around temporary Compose bugs.
configurations.all {
    attributes {
        attribute(Attribute.of("ui", String::class.java), "awt")
    }
}
// endregion

/*
 * Copyright 2025 Zamulabs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinX.serialization.plugin)
    id("app.cash.sqldelight") version "2.1.0"
}

kotlin {
    jvm()
    
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

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(libs.sqlite.driver)

            // Compose SplitPane (Desktop-only) - updated to match Compose 1.6.x APIs and fixes
            implementation("org.jetbrains.compose.components:components-splitpane-desktop:1.6.11")

            // Toaster for Windows
            implementation(libs.toast4j)

            // JNA for Linux
            implementation("de.jangassen:jfa:1.2.0") {
                // not excluding this leads to a strange error during build:
                // > Could not find jna-5.13.0-jpms.jar (net.java.dev.jna:jna:5.13.0)
                exclude(group = "net.java.dev.jna", module = "jna")
            }

            // JNA for Windows
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
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "DineEasePOS"
            packageVersion = project.version as String

            // Platform-specific output dirs
            macOS {
                outputBaseDir.set(buildDir.resolve("mac/installation"))
                iconFile.set(file("src/jvmMain/composeResources/drawable/launcher_icons/macos.icns"))
            }

            windows {
                outputBaseDir.set(buildDir.resolve("win/installation"))
                iconFile.set(file("src/jvmMain/composeResources/drawable/launcher_icons/windowsos.ico"))
            }

            linux {
                outputBaseDir.set(buildDir.resolve("linux/installation"))
                iconFile.set(file("src/jvmMain/composeResources/drawable/launcher_icons/linuxos.png"))
            }

            modules("java.instrument", "java.management", "java.prefs", "java.sql", "jdk.unsupported")
        }
    }
}

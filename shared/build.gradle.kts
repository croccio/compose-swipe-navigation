@file:OptIn(ExperimentalComposeLibrary::class, ExperimentalKotlinGradlePluginApi::class)

import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kover)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

group = libs.versions.library.group.get()
version = libs.versions.library.version.get()

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }
        }
        publishLibraryVariants("release", "debug")
        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "csnavigation"
            isStatic = true
        }
    }

    cocoapods {
        name = "csnavigation"
        summary = ""
        homepage = "https://github.com/croccio/compose-swipe-navigation.git"
        authors = "croccio"
        version = libs.versions.library.version.get()
        ios.deploymentTarget = "16"
        framework {
            baseName = "csnavigation"
            isStatic = false
            transitiveExport = true
        }
        specRepos {
            url("https://github.com/croccio/compose-swipe-navigation.git")
        }
        publishDir = rootProject.file("./")
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "csnavigation"
        binaries.library()
    }

    jvm("desktop")

    applyDefaultHierarchyTemplate()

    sourceSets {
        val commonMain by getting
        val wasmJsMain by getting
        val nativeMain by getting
        val desktopMain by getting
        val androidMain by getting
        val androidUnitTest by getting

        val jvmMain by creating {
            dependsOn(commonMain)
        }
        androidMain.dependsOn(jvmMain)
        androidMain.dependencies { }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }

        val nonJvmMain by creating {
            dependsOn(commonMain)
            nativeMain.dependsOn(this)
            wasmJsMain.dependsOn(this)
            dependencies { }
        }
        desktopMain.dependsOn(jvmMain)
        desktopMain.dependencies { }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidUnitTest.dependencies {
            implementation(libs.robolectric)
            implementation(libs.androidx.test.core)
            implementation(libs.androidx.ui.test.manifest)
            implementation(libs.ui.test.junit4.android)
        }
    }
}

android {
    namespace = libs.versions.library.group.get()
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    beforeEvaluate {
        libraryVariants.all {
            compileOptions {
                isCoreLibraryDesugaringEnabled = true
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    pom {
        name = "csnavigation"
        description =
            "CSNavigation (Compose Swipe Navigation) implement a custom navigation system based on viewpager to\n" +
                    "allow swipe-back navigation between screens."
        inceptionYear = "2025"
        url = "https://github.com/croccio/"
        licenses {
            license {
                name = "MIT"
                url = "https://github.com/croccio/compose-swipe-navigation/blob/main/LICENSE"
                distribution =
                    "https://github.com/croccio/compose-swipe-navigation/blob/main/LICENSE"
            }
        }
        developers {
            developer {
                id = "croccio"
                name = "croccio"
                url = "https://github.com/croccio/"
            }
        }
        scm {
            url = "https://github.com/croccio/compose-swipe-navigation/"
            connection = "scm:git:git://github.com/croccio/compose-swipe-navigation.git"
            developerConnection =
                "scm:git:ssh://git@github.com/croccio/compose-swipe-navigation.git"
        }
    }
}

tasks.register("createTag") {
    val libVersion = libs.versions.library.version.get()
    doLast {
        exec { commandLine = listOf("git", "tag", libVersion) }
        exec { commandLine = listOf("git", "push", "--tags") }
    }
}

kover {
    reports {
        filters {
            excludes {
                classes("*.generated.*")
            }
        }
    }
}
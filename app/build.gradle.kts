import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTask

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("org.jetbrains.dokka") version "1.9.10"
}

buildscript {
    dependencies {
        classpath(libs.dokka.base)
    }
}

android {
    namespace = "org.mazinapp.rubengarcia"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.mazinapp.rubengarcia"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    tasks {
        val dokkaHtml by getting(DokkaTask::class) {
            outputDirectory.set(buildDir.resolve("./docs"))
        }
    }

    tasks.withType<DokkaTask>().configureEach {
        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            footerMessage = "(c) 2023 Rubén García Segoviano"
            separateInheritedMembers = true
            mergeImplicitExpectActualDeclarations = false
            suppressObviousFunctions = true

            dokkaSourceSets {
                named("main") {
                    displayName.set("FormulaStats por Rubén García")
                }
            }
        }
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    compileOnly(libs.sqliteassethelper)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.circleimageview)
    implementation(libs.annotation)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.androidx.legacy.support.v4)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.lottie)
    implementation(libs.chip.navigation.bar)
    implementation(libs.android.documentation.plugin)
}

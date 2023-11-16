/**
 * The first section in the build configuration applies the Android Gradle plugin
 * to this build and makes the android block available to specify
 * Android-specific build options.
 */
plugins {
    id("com.android.application")
    id("kotlin-android") // id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    //kotlin("jvm") version "1.9.20"
    //id("kotlin-android-extensions")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

}
// Keep the following configuration in order to target Java 8.
// https://developer.android.com/studio/write/java8-support
/*java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile>().configureEach {
    jvmTargetValidationMode.set(org.jetbrains.kotlin.gradle.dsl.jvm.JvmTargetValidationMode.WARNING)
}*/
// https://stackoverflow.com/a/75671745/3369131
kotlin {
    jvmToolchain(21)
}
/**
 * The android block is where you configure all your Android-specific
 * build options.
 */
android {
    /**
     * The app's namespace. Used primarily to access app resources.
     */
    namespace = "cl.loopa.android.oldays"

    /**
     * compileSdk specifies the Android API level Gradle should use to
     * compile your app. This means your app can use the API features included in
     * this API level and lower.
     */
    compileSdk = 34

    /**
     * The defaultConfig block encapsulates default settings and entries for all
     * build variants and can override some attributes in main/AndroidManifest.xml
     * dynamically from the build system. You can configure product flavors to override
     * these values for different versions of your app.
     */
    defaultConfig {
        // Uniquely identifies the package for publishing.
        applicationId = "cl.loopa.android.oldays"
        // Defines the minimum API level required to run the app.
        //osmbonuspack actual pide 16.
        //Google Maps SDK v3 pide API16
        //Glide pide API14 https://bumptech.github.io/glide/doc/download-setup.html
        minSdk = 21
        // Specifies the API level used to test the app.
        //osmbonuspack actual pide 16.
        //Google Maps SDK v3 pide API16
        //Glide pide API14 https://bumptech.github.io/glide/doc/download-setup.html
        targetSdk = 34
        // Defines the version number of your app.
        versionCode = 12
        // Defines a user-friendly version name for your app.
        versionName = "@string/app_ver" // X.Y.Z; X = Major, Y = minor, Z = Patch level

        //https://developer.android.com/studio/build?hl=es-419#module-level

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    /**
     * The buildTypes block is where you can configure multiple build types.
     * By default, the build system defines two build types: debug and release. The
     * debug build type is not explicitly shown in the default build configuration,
     * but it includes debugging tools and is signed with the debug key. The release
     * build type applies ProGuard settings and is not signed by default.
     */
    buildTypes {
        /**
         * By default, Android Studio configures the release build type to enable code
         * shrinking, using minifyEnabled, and specifies the default ProGuard rules file.
         */
        //getByName("release") {
        release {
            // Enables code shrinking, obfuscation, and optimization for only
            // your project's release build type.
            // https://developer.android.com/studio/build/shrink-code.html
            isMinifyEnabled = true
            // Enables resource shrinking, which is performed by the
            // Android Gradle plugin.
            isShrinkResources = true

            // Includes the default ProGuard rules files that are packaged with
            // the Android Gradle plugin. To learn more, go to the section about
            // R8 configuration files.
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            multiDexEnabled = true

        }
        getByName("debug") {
            isDebuggable = true
            multiDexEnabled = true
        }
        /**
         * The productFlavors block is where you can configure multiple product flavors.
         * This lets you create different versions of your app that can
         * override the defaultConfig block with their own settings. Product flavors
         * are optional, and the build system does not create them by default.
         *
         * This example creates a free and paid product flavor. Each product flavor
         * then specifies its own application ID, so that they can exist on the Google
         * Play Store, or an Android device, simultaneously.
         *
         * If you declare product flavors, you must also declare flavor dimensions
         * and assign each flavor to a flavor dimension.
         */
        // https://developer.android.com/studio/build?hl=es-419#module-level
    }
    //"For each module that uses view binding, set the viewBinding to true
    //https://developer.android.com/topic/libraries/view-binding/migration#kts
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
    // https://stackoverflow.com/a/76121852/3369131
    kotlinOptions {
        jvmTarget = "1.8"
    }
    //buildToolsVersion = "34.0.0"
}

/**
 * The dependencies block in the module-level build configuration file
 * specifies dependencies required to build only the module itself.
 * To learn more, go to Add build dependencies.
 */
dependencies {

    //Android Core has HtmlCompat @Sagar Balyan https://stackoverflow.com/a/37905107/3369131
    //implementation("androidx.core:core-ktx:1.13.0-alpha01")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")

    implementation("com.google.android.material:material:1.10.0")

    //implementation("androidx.fragment:fragment:1.6.2")
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // Kotlin plugin should be enabled before 'kotlin-android-extensions'
    //implementation(projects.feature.interests)
    //implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlin_version}")
    //implementation("androidx.multidex:multidex:2.0.1")
    //#io19 https://www.youtube.com/watch?v=29gLA90m6Gk
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    //implementation("androidx.constraintlayout:constraintlayout:2.2.0-alpha13")

    //Starting with Android 8, background check restrictions make this class no longer useful.
    //implementation("androidx.legacy:legacy-support-v4:1.0.0")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.vectordrawable:vectordrawable:1.1.0")

    // Jetpack Navigation (Google I/O'19) https://youtu.be/JFGq0asqSuA?t=300
    // https://developer.android.com/jetpack/androidx/releases/navigation#declaring_dependencies
    //implementation("androidx.navigation:navigation-common-ktx:2.6.2")
    //implementation("androidx.navigation:navigation-runtime-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")

    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // Teléfonos usados no tienen última versión,
    // Mínimo debiese ser v9.2.1. Emuladores < API19 tienen Google Play Services 9.2
    // en v9.6 aparecieron los estilos para ocultar los POI y darle color al mapa
    // en v16 arreglaron incompatibilidad con Android X
    // Google Maps SDK v3 pide API16
    // Elijo v9.6.1 en vez de v9.2.1 porque todavía no sé cómo detectar si tiene instalado v9.6.1+
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    // OSMBonusPack
    // https://github.com/MKergall/osmbonuspack/wiki/HowToInclude
    // La versión osmbonuspack:6.5.3 pide API16, 6.5.2 pide API14, última es 6.6.0
    implementation("com.github.MKergall:osmbonuspack:6.9.0")
    // OSMBonusPack 6.5.2 uses okhttp:3.7.0
    implementation("com.squareup.okhttp3:okhttp:4.7.2")
    implementation("com.squareup.okio:okio:3.6.0")
    // GLIDE
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("androidx.databinding:databinding-runtime:8.1.3")
    // Skip this if you don't want to use integration libraries or configure Glide.
    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")
    // OSMBonusPack 6.5.2 uses okhttp:3.7.0
    implementation("com.github.bumptech.glide:okhttp3-integration:4.11.0")


    // TouchImageView for Android (for pinch zooming photos)
    // https://github.com/MikeOrtiz/TouchImageView#download
    implementation("com.github.MikeOrtiz:TouchImageView:3.6") // Android X

    testImplementation("junit:junit:4.13.2")
    //androidTestImplementation("androidx.test:runner:1.6.0-alpha04")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    //androidTestImplementation("androidx.test.espresso:espresso-core:3.6.0-alpha01")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
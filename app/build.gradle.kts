plugins {
    id("com.android.application")
}

android {
    namespace = "com.duje.wifibook"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.duje.wifibook"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}


dependencies {

    //val room_version = "2.6.1"



    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //implementation ("com.github.yuriy-budiyev:code-scanner:2.3.0")

    //implementation("androidx.room:room-runtime:$room_version")
    //annotationProcessor("androidx.room:room-compiler:$room_version")
    implementation ("com.github.yuriy-budiyev:code-scanner:2.3.2")
    implementation ("com.google.code.gson:gson:2.8.6")
    implementation ("androidx.room:room-runtime:2.5.0")
    annotationProcessor ("androidx.room:room-compiler:2.5.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
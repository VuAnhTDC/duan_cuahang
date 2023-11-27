plugins {
    id("com.android.application")
    id("com.google.gms.google-services") version "4.4.0" apply true
}

android {
    namespace = "com.example.duancuahang"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.duancuahang"
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

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.squareup.picasso:picasso:2.8")
    implementation("com.android.volley:volley:1.2.1")
    //implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("id.zelory:compressor:3.0.1")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-auth:22.2.0")
    implementation("com.google.code.gson:gson:2.8.7")
    implementation("com.google.firebase:firebase-appcheck-playintegrity")
    //implementation ("com.github.User:Repo:Tag")
    implementation("com.github.dhaval2404:imagepicker:2.1")
    implementation("de.hdodenhof:circleimageview:3.1.0")


    implementation("com.google.android.gms:play-services-base:17.0.0")
    implementation("com.google.android.gms:play-services-basement:17.0.0")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")


//    implementation ("com.google.firebase:firebase-bom:29.0.0")
//    implementation (platform("com.google.firebase:firebase-bom:29.0.0"))

//    appCheck {
//        factory {
//            tokenProviders = ["safety-net"]
//        }
//    }
}


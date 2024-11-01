plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.flashcat"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.flashcat"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-database:20.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //add avt circle
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    //add flip card
    implementation("com.wajahatkarim:EasyFlipView:3.0.3")
    //zoom layout
    implementation("com.github.skydoves:transformationlayout:1.1.3")
    //button
    implementation("com.github.traex.rippleeffect:library:1.3")
    //SwipeSelector
    //implementation("com.roughike:swipe-selector:1.0.6")
    //time
    implementation ("com.jakewharton.threetenabp:threetenabp:1.3.1")
    //api
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //goi y
    implementation ("androidx.cursoradapter:cursoradapter:1.0.0")

    //image
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //pie chat
    implementation("com.nineoldandroids:library:2.4.0")
    implementation("com.github.blackfizz:eazegraph:1.2.5l@aar")
}
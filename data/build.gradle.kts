plugins {
    id(libs.plugins.common.android.library.get().pluginId)
}

android {
    namespace = "id.ak.mytaskmanager.data"
}

dependencies {
    implementation(projects.domain)
    implementation(libs.room)
    ksp(libs.room.compiler)
    androidTestImplementation(libs.room.test)
}
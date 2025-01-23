plugins {
    id(libs.plugins.ui.library.get().pluginId)
}

android {
    namespace = "id.ak.mytaskmanager.view"
}

dependencies {
    implementation(projects.uiCommon)
    implementation(projects.domain)
}
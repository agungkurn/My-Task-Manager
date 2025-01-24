plugins {
    id(libs.plugins.ui.library.get().pluginId)
}

android {
    namespace = "id.ak.mytaskmanager.edit"
}

dependencies {
    implementation(projects.uiCommon)
    implementation(projects.domain)
}
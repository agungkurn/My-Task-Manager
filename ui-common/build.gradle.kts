plugins {
    id(libs.plugins.ui.library.get().pluginId)
}

android {
    namespace = "id.ak.mytaskmanager.ui_common"
}

dependencies {
    api(projects.common)
}
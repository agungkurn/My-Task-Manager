plugins {
    id(libs.plugins.kotlin.library.get().pluginId)
}

dependencies {
    implementation(projects.common)
}
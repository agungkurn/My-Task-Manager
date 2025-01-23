pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

// enable access project's module dependency
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "MyTaskManager"
include(":app")
include(":common")
include(":ui-common")
include(":data")
include(":domain")
include(":ui:home")
include(":ui:create")
include(":ui:view")

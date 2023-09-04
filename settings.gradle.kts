pluginManagement {
    repositories {
        google()
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

rootProject.name = "ComposeToyProject"
include(":app")
include(":stopwatch")
include(":mywebbrowser")
include(":mygallery")
include(":levelmeasuring")
include(":xylophone")

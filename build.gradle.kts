// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    alias(libs.plugins.jetbrains.kotlin.serialization) apply false
    alias(libs.plugins.google.ksp) apply false
    alias(libs.plugins.google.dagger.hilt) apply false
    alias(libs.plugins.android.library) apply false
}

subprojects {
    tasks.whenTaskAdded {
        if (name == "unitTestClasses") {
            // Task already exists, no need to do anything
            return@whenTaskAdded
        }
    }
    
    // Create a compatibility task for unitTestClasses if it doesn't exist
    afterEvaluate {
        if (tasks.findByName("unitTestClasses") == null) {
            tasks.register("unitTestClasses") {
                group = "verification"
                description = "Compatibility task for unitTestClasses"
                dependsOn(tasks.matching { it.name.contains("UnitTest") && it.name.contains("compile") })
            }
        }
    }
}

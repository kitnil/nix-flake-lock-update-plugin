import org.jenkinsci.gradle.plugins.jpi.JpiExtension
import org.jenkinsci.gradle.plugins.jpi.JpiLicense
import org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask
import org.jetbrains.kotlin.gradle.internal.KaptTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("org.jenkins-ci.jpi") version "0.43.0"
}

group = "com.kitnil.nixflakelockupdate"
version = "0.6.3"

jenkinsPlugin {
    jenkinsVersion.set("2.303.3")
    displayName = "Nix flake update"
    url = "https://github.com/kitnil/nix-flake-update"
    gitHubUrl = "https://github.com/kitnil/nix-flake-update"
    shortName = "nix-flake-update"
    description = "Update nix flake lock file."

    developers {
        developer {
            id.set("neworldlt")
            name.set("Oleg Pykhalov")
            email.set("go.wigust@gmail.com")
            url.set("https://github.com/kitnil/")
            organization.set("kitnil")
            organizationUrl.set("https://github.com/kitnil/")
            timezone.set("Moscow GMT+3")
        }
    }

    licenses(closureOf<JpiExtension.Licenses> {
        license(closureOf<JpiLicense> {
            setProperty("name", "MIT")
            setProperty("url", "https://raw.githubusercontent.com/jenkinsci/nix-flake-lock-update/master/LICENSE")
            setProperty("distribution", "repo")
        })
    })
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.vinted:slf4j-streamadapter:1.0.0")

    // sezpoz is used to process extension annotations
    kapt("net.java.sezpoz:sezpoz:1.13")

    testImplementation("org.jenkins-ci.plugins:pipeline-utility-steps:2.10.0")
    testImplementation("org.jenkins-ci.plugins.workflow:workflow-job:2.42")
    testImplementation("org.jenkins-ci.plugins.workflow:workflow-cps:2.93")
    testImplementation("org.jenkins-ci.plugins.workflow:workflow-basic-steps:2.24")
    testImplementation("org.eclipse.jgit:org.eclipse.jgit:${Versions.jgit}")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

// Config was ported from https://github.com/jenkinsci/doktor-plugin/blob/master/build.gradle.kts
kapt {
    correctErrorTypes = true
}

tasks.withType(KotlinCompile::class.java).all {
    dependsOn("localizer")

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.withType(KaptTask::class.java).all {
    outputs.upToDateWhen { false }
}

tasks.withType(KaptGenerateStubsTask::class.java).all {
    outputs.upToDateWhen { false }
}

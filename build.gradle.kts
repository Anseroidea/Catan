plugins {
    java
    id("org.openjfx.javafxplugin") version "0.0.10"
}

group = "org.antai"
version = "1"

repositories {
    mavenCentral()
}

javafx {
    version = "18"
    modules("javafx.controls", "javafx.fxml", "javafx.base", "javafx.graphics", "javafx.media", "javafx.web", "javafx.swing")
}
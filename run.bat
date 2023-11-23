@echo off
set JAVA_HOME=C:\Program Files\Java\jdk-17\bin
set PATH_TO_FX=C:\JAVA sdks\javafx-sdk-21\bin

java --module-path "%PATH_TO_FX%" --add-modules javafx.base,javafx.controls,javafx.graphics,javafx.media,javafx.fxml -jar latice.jar

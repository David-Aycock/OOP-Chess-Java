@echo off
setlocal

set PATH_TO_FX="%~dp0libs\javafx-sdk-17.0.9\lib"
set JAR_PATH="%~dp0dist\Better_Chess.jar"
java --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml -jar %JAR_PATH%

endlocal
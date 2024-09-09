@echo off
echo Adding JavaFX runtime to the module path...
echo Creating custom Java runtime image using jlink...
"C:\Program Files\Java\jdk-17\bin\jlink.exe" --module-path "target\lib;C:\Program Files\javafx-sdk-17.0.6\lib;target\pathsgame-1.0-SNAPSHOT.jar" --add-modules org.example.pathsgame --output custom-runtime
if errorlevel 1 exit /b %errorlevel%

set USER_HOME=%HOMEDRIVE%%HOMEPATH%
echo Preparing application files...
xcopy /s /y target\lib temp_app\lib
xcopy /s /y "C:\Program Files\javafx-sdk-17.0.6\lib" temp_app\lib
xcopy /s /y target\classes\logo.png temp_app\classes

echo Creating application image using jpackage...
"C:\Program Files\Java\jdk-17\bin\jpackage.exe" --type app-image --input temp_app --dest images --name Paths_Game --main-jar pathsgame-1.0-SNAPSHOT.jar --main-class org.example.pathsgame.PathGameApplication --runtime-image custom-runtime --icon temp_app\logo.png
if errorlevel 1 exit /b %errorlevel%

echo Creating standalone MSI installer using jpackage...
"C:\Program Files\Java\jdk-17\bin\jpackage.exe" --type msi --app-image images\Paths_Game --dest installers --name Paths_Game --icon temp_app\logo.png --win-shortcut
if errorlevel 1 exit /b %errorlevel%

echo Done. The standalone MSI installer has been created in the "installers" folder.
pause

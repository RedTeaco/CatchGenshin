@ECHO OFF
SETLOCAL

:BEGIN
CLS
CD "%~dp0" >nul 2>&1
	POWERSHELL.exe -ExecutionPolicy Bypass -File ".\GetURL.ps1"
	TIMEOUT /t 2 >nul
	GOTO MAIN


:MAIN
CLS
java -Dfile.encoding=utf8 -jar CatchGenshin.jar
GOTO EOF

:EOF
pause
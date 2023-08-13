@echo off
setlocal enabledelayedexpansion
cd tools~/windows
set IMG_DIR=../img
FOR /L %%i IN (0,1,9) DO (
	set test=0000%%i
	set test=!test:~-4!
	tester.exe java -jar ../../solution~.jar < ../in/!test!.txt > ../out/!test!.txt
	vis.exe ../in/!test!.txt ../out/!test!.txt
	move vis.png %IMG_DIR%/!test!.png > nul 2>nul
	move vis.html %IMG_DIR%/!test!.html > nul 2>nul
	echo.
)

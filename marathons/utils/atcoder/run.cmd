cd tools~/windows
FOR /L %%i IN (0,1,9) DO (
	tester.exe java -jar ../../solution~.jar < ../in/000%%i.txt > ../out/000%%i.txt
	vis.exe ../in/000%%i.txt ../out/000%%i.txt
	move vis.png ../img/000%%i.png
)

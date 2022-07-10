#!/bin/bash
#wget -nc https://www.topcoder.com/contest/arena/ContestAppletProd7.2.jnlp
wget -nc https://www.topcoder.com/contest/classes/7.2/arena-client-combined.jar
mkdir arena-jar-contents
cd arena-jar-contents
jar -xf ../arena-client-combined.jar
rm META-INF/TOPCODER.RSA
rm META-INF/TOPCODER.SF 
jar --create --file ../arena-modified.jar *
cd ..
rm -rf arena-jar-contents
echo "#!/bin/bash" > run-arena.sh
echo "java -cp arena-modified.jar com.topcoder.client.contestApplet.runner.generic www.topcoder.com 5001 http://tunnel1.topcoder.com/tunnel?dummy TopCoder" >> run-arena.sh
chmod +x run-arena.sh

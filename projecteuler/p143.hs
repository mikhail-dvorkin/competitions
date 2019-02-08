import Comb
import List
import Math

s' = [(195,264,325),(264,325,440),(360,1015,3864),(384,805,1520),(390,528,650),(435,1656,4669),(455,1824,2145),(520,3105,8184),(528,650,880),(585,792,975),(688,5187,21840),(720,2030,7728),(765,1064,5016),(768,1610,3040),(780,1056,1300),(792,975,1320),(817,3440,25935),(870,3312,9338),(885,9499,12600),(910,3648,4290),(975,1320,1625),(1029,15680,87720),(1040,6210,16368),(1056,1300,1760),(1080,3045,11592),(1152,2415,4560),(1170,1584,1950),(1272,2065,4928),(1305,4968,14007),(1320,1625,2200),(1357,1800,19320),(1365,1848,2275),(1365,5472,6435),(1376,10374,43680),(1440,4060,15456),(1530,2128,10032),(1536,3220,6080),(1560,2112,2600),(1560,9315,24552),(1584,1950,2640),(1634,6880,51870),(1740,6624,18676),(1755,2376,2925),(1770,18998,25200),(1785,8415,11704),(1800,5075,19320),(1820,7296,8580),(1848,2275,3080),(1920,4025,7600),(1950,2640,3250),(2064,15561,65520),(2080,12420,32736),(2112,2600,3520),(2145,2904,3575),(2160,6090,23184),(2175,8280,23345),(2275,9120,10725),(2295,3192,15048),(2304,4830,9120),(2340,3168,3900),(2376,2925,3960),(2409,42735,58400),(2451,10320,77805),(2520,7105,27048),(2535,3432,4225),(2544,4130,9856),(2600,15525,40920),(2610,9936,28014),(2640,3250,4400),(2655,28497,37800),(2688,5635,10640),(2714,3600,38640),(2730,3696,4550),(2730,10944,12870),(2752,20748,87360),(2880,8120,30912),(2904,3575,4840),(2925,3960,4875),(3045,11592,32683),(3060,4256,20064),(3072,6440,12160),(3120,4224,5200),(3120,18630,49104),(3120,33488,38955),(3128,16320,24955),(3168,3900,5280),(3185,12768,15015),(3240,9135,34776),(3264,4991,26040),(3315,4488,5525),(3432,4225,5720),(3456,7245,13680),(3480,13248,37352),(3510,4752,5850),(3515,6528,14800),(3540,37996,50400),(3570,16830,23408),(3600,10150,38640),(3640,14592,17160),(3640,21735,57288),(3696,4550,6160),(3705,5016,6175),(3705,15600,59024),(3816,6195,14784),(3825,5320,25080),(3840,8050,15200),(3864,7296,15295),(3900,5280,6500),(3915,14904,42021),(3960,4875,6600),(3960,11165,42504),(4071,5400,57960),(4095,5544,6825),(4095,16416,19305),(4160,24840,65472),(4200,10880,15211),(4224,5200,7040),(4224,8855,16720),(4256,5005,20064),(4290,5808,7150),(4320,12180,46368),(4350,16560,46690),(4425,47495,63000),(4485,6072,7475),(4488,5525,7480),(4550,18240,21450),(4590,6384,30096),(4608,9660,18240),(4641,34200,70720),(4680,6336,7800),(4680,13195,50232),(4680,27945,73656),(4752,5850,7920),(4784,5565,59731),(4785,18216,51359),(4875,6600,8125),(4992,10465,19760),(5005,20064,23595),(5016,6175,8360),(5040,14210,54096),(5070,6864,8450),(5088,8260,19712),(5096,11520,30429),(5200,31050,81840),(5220,19872,56028),(5265,7128,8775),(5280,6500,8800),(5310,56994,75600),(5355,7448,35112),(5355,22933,65520),(5355,25245,35112),(5376,11270,21280),(5400,5423,57960),(5400,15225,57960),(5428,7200,77280),(5460,7392,9100),(5460,21888,25740),(5544,6825,9240),(5655,7656,9425),(5655,21528,60697),(5720,34155,90024),(5733,6800,98515),(5760,12075,22800),(5760,16240,61824),(5808,7150,9680),(5850,7920,9750),(5915,23712,27885),(5985,8640,12376),(5985,12376,91200),(6045,8184,10075),(6072,7475,10120),(6090,23184,65366),(6120,8512,9405),(6120,8512,40128),(6120,17255,65688),(6144,12880,24320),(6195,66493,88200),(6240,8448,10400),(6240,66976,77910),(6256,32640,49910),(6307,6765,7208),(6336,7800,10560),(6360,10325,24640),(6370,25536,30030),(6435,8712,10725),(6480,18270,69552),(6525,24840,70035),(6528,9982,52080),(6528,13685,25840),(6600,8125,11000),(6630,8976,11050),(6825,9240,11375),(6825,27360,32175),(6840,19285,73416),(6864,8450,11440),(6885,9576,45144),(6912,14490,27360),(6960,15631,63665),(6960,26496,74704),(7020,9504,11700),(7030,13056,29600),(7128,8775,11880),(7140,33660,46816),(7200,20300,77280),(7215,9768,12025),(7280,29184,34320),(7296,15295,28880),(7392,9100,12320),(7395,28152,79373),(7410,10032,12350),(7560,21315,81144),(7605,10296,12675),(7616,8415,11704),(7632,12390,29568),(7650,10640,50160),(7656,9425,12760),(7680,16100,30400),(7728,14592,30590),(7735,31008,36465),(7752,17575,32640),(7800,10560,13000),(7830,29808,84042),(7920,9750,13200),(7920,22330,85008),(7995,10824,13325),(8064,16905,31920),(8184,10075,13640),(8190,11088,13650),(8190,32832,38610),(8265,31464,88711),(8280,23345,88872),(8385,11352,13975),(8400,21760,30422),(8415,11704,55176),(8448,10400,14080),(8448,17710,33440),(8512,10010,40128),(8580,11616,14300),(8645,34656,40755),(8712,10725,14520),(8715,38368,72960),(8775,11880,14625),(8832,18515,34960),(8904,14455,34496),(8925,42075,58520),(8970,12144,14950),(8976,11050,14960),(9100,36480,42900),(9165,12408,15275),(9180,12768,60192),(9216,19320,36480),(9240,11375,15400),(9360,12672,15600),(9384,48960,74865),(9425,21063,30160),(9504,11700,15840),(9555,12936,15925),(9555,38304,45045),(9600,20125,38000),(9750,13200,16250),(9768,12025,16280),(9792,14973,78120),(9945,13464,16575),(9945,13832,65208),(9984,20930,39520),(10010,40128,47190),(10032,12350,16720),(10140,13728,16900),(10176,16520,39424),(10192,23040,60858),(10296,12675,17160),(10335,13992,17225),(10368,21735,41040),(10465,41952,49335),(10530,14256,17550),(10545,19584,44400),(10560,13000,17600),(10608,35805,71995),(10710,14896,70224),(10710,50490,70224),(10725,14520,17875),(10752,22540,42560),(10787,49200,71760),(10824,13325,18040),(10920,14784,18200),(10920,24225,58072),(10920,43776,51480),(11088,13650,18480),(11115,15048,18525),(11136,23345,44080),(11310,15312,18850),(11352,13975,18920),(11375,45600,53625),(11448,18585,44352),(11475,15960,75240),(11505,15576,19175),(11520,24150,45600),(11592,21888,45885),(11616,14300,19360),(11700,15840,19500),(11830,47424,55770),(11880,14625,19800),(11895,16104,19825),(11904,24955,47120),(11970,17280,24752),(12090,16368,20150),(12144,14950,20240),(12240,17024,18810),(12240,17024,80256),(12285,16632,20475),(12285,49248,57915),(12288,25760,48640),(12383,38760,58377),(12408,15275,20680),(12480,16896,20800),(12495,58905,81928),(12600,32640,45633),(12614,13530,14416),(12672,15600,21120),(12672,26565,50160),(12675,17160,21125),(12720,20650,49280),(12740,51072,60060),(12768,15015,60192),(12870,17424,21450),(12936,15925,21560),(13056,27370,51680),(13065,17688,21775),(13195,52896,62205),(13200,16250,22000),(13260,17952,22100),(13440,28175,53200),(13455,18216,22425),(13464,16575,22440),(13464,27265,39360),(13650,18480,22750),(13650,54720,64350),(13728,16900,22880),(13824,28980,54720),(13845,18744,23075),(13992,17225,23320),(13992,22715,54208),(14040,19008,23400),(14060,26112,59200),(14105,56544,66495),(14208,29785,56240),(14235,19272,23725),(14256,17550,23760),(14430,19536,24050),(14520,17875,24200),(14560,58368,68640),(14592,30590,57760),(14625,19800,24375),(14784,18200,24640),(14820,20064,24700),(14841,31535,55440),(14976,31395,59280),(15015,20328,25025),(15015,60192,70785),(15048,18525,25080),(15210,20592,25350),(15232,16830,23408),(15264,24780,59136),(15312,18850,25520),(15360,32200,60800),(15405,20856,25675),(15456,29184,61180),(15470,62016,72930),(15504,35150,65280),(15576,19175,25960),(15600,21120,26000),(15744,33005,62320),(15795,21384,26325),(15840,19500,26400),(15925,63840,75075),(15990,21648,26650),(16104,19825,26840),(16120,28119,38976),(16128,33810,63840),(16185,21912,26975),(16320,48776,65149),(16368,20150,27280),(16380,22176,27300),(16380,65664,77220),(16512,34615,65360),(16536,26845,64064),(16575,16905,35728),(16575,22440,27625),(16632,20475,27720),(16770,22704,27950),(16800,43520,60844),(16896,20800,28160),(16896,35420,66880),(16965,22968,28275),(17043,38080,58520),(17160,21125,28600),(17160,23232,28600),(17280,36225,68400),(17355,23496,28925),(17424,21450,29040),(17424,32725,33915),(17550,23760,29250),(17575,32640,74000),(17664,37030,69920),(17688,21775,29480),(17745,24024,29575),(17808,28910,68992),(17940,24288,29900),(17952,22100,29920),(17955,25920,37128),(18048,37835,71440),(18135,24552,30225),(18216,22425,30360),(18330,24816,30550),(18360,25536,28215),(18432,38640,72960),(18480,22750,30800),(18525,25080,30875),(18720,25344,31200),(18744,23075,31240),(18850,42126,60320),(18915,25608,31525),(18921,20295,21624),(19008,23400,31680),(19110,25872,31850),(19272,23725,32120),(19305,26136,32175),(19500,26400,32500),(19536,24050,32560),(19600,26741,31395),(19695,26664,32825),(19800,24375,33000),(19890,26928,33150),(20064,24700,33440),(20085,27192,33475),(20280,27456,33800),(20328,25025,33880),(20475,27720,34125),(20520,29393,42432),(20592,25350,34320),(20670,27984,34450),(20856,25675,34760),(20865,28248,34775),(21060,28512,35100),(21120,26000,35200),(21255,28776,35425),(21384,26325,35640),(21450,29040,35750),(21645,29304,36075),(21648,26650,36080),(21840,29568,36400),(21912,26975,36520),(22035,29832,36725),(22176,27300,36960),(22230,30096,37050),(22425,30360,37375),(22440,27625,37400),(22620,30624,37700),(22704,27950,37840),(22815,30888,38025),(22848,25245,35112),(22968,28275,38280),(23010,31152,38350),(23205,31416,38675),(23232,28600,38720),(23400,31680,39000),(23496,28925,39160),(23595,31944,39325),(23760,29250,39600),(23790,32208,39650),(23940,34560,49504),(23985,32472,39975),(24024,29575,40040),(24180,32736,40300),(24288,29900,40480),(24375,33000,40625),(24480,34048,37620),(24552,30225,40920),(24570,33264,40950),(24765,33528,41275),(24816,30550,41360),(24960,33792,41600),(25080,30875,41800),(25155,34056,41925),(25228,27060,28832),(25344,31200,42240),(25350,34320,42250),(25545,34584,42575),(25608,31525,42680),(25740,34848,42900),(25872,31850,43120),(25935,35112,43225),(26130,35376,43550),(26136,32175,43560),(26325,35640,43875),(26400,32500,44000),(26520,35904,44200),(26664,32825,44440),(26715,36168,44525),(26910,36432,44850),(26928,33150,44880),(27105,36696,45175),(27192,33475,45320),(27300,36960,45500),(27456,33800,45760),(27495,37224,45825),(27690,37488,46150),(27720,34125,46200),(27885,37752,46475),(27984,34450,46640),(28080,38016,46800),(28248,34775,47080),(28275,38280,47125),(28470,38544,47450),(28512,35100,47520),(28665,38808,47775),(28776,35425,47960),(28860,39072,48100),(29040,35750,48400),(29055,39336,48425),(29250,39600,48750),(29304,36075,48840),(29445,39864,49075),(29568,36400,49280),(29640,40128,49400),(29832,36725,49720),(29835,40392,49725),(30464,33660,46816),(30600,42560,47025),(31535,33825,36040)]

good (p, q) = isSqr (p * p + q * q + p * q)

n = 110000

a x = filter (\y -> good (x, y)) [(x + 1)..(n - 2 * x)]

b x = map (\(y, z) -> (x, y, z)) $ filter good $ pairs $ a x

s = concatMap b [1..n]

ans = sum $ nub $ takeWhile (<= n) $ sort $ map (\(x, y, z) -> x + y + z) s'
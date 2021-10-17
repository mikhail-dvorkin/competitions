#!/bin/bash
cd ..
#git format-patch --relative 49f45513b99507be51184d223a32f51fab88c4a6 --stdout > my.diff
#git diff --relative 49f45513b99507be51184d223a32f51fab88c4a6 * > my.diff
#diff -urN topcoderTheir topcoderMy > my.diff
diff -urN topcoderTheir topcoderMy | sed -r 's/((---|\+\+\+)\s+\S+)\s+[-+0-9.: ]+$/\1/' > topcoder/my.diff

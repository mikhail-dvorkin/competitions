#!/bin/bash
cd ..
cp -R topcoderTheir topcoderPatched
cd topcoderPatched
patch -i ../topcoder/my.diff

#git format-patch --relative 49f45513b99507be51184d223a32f51fab88c4a6 --stdout > !my.patch
git diff --relative 49f45513b99507be51184d223a32f51fab88c4a6 * > !my.patch

Directory Encoder
----------------

Simple Scala application that recursively traverses a directory/folder and produces the necessarry commands to copy the directory into an encoded equivalent. This takes care of both files and folders. A prefix is added to each file, also used as the base folder for the encoded files.

Having a directory:

```
a/
a/foo.txt
a/bar.txt
a/b/
a/b/c/
a/b/d/
a/b/d/foo.txt
a/b/d/bar.txt
```

and a base "PREFIX", the endocing scheme produces:

```
PREFIX/
PREFIX/0001/
PREFIX/0001/PREFIX.0001.txt
PREFIX/0001/PREFIX.0002.txt
PREFIX/0001/0001/
PREFIX/0001/0001/0001/
PREFIX/0001/0001/0002/
PREFIX/0001/0001/0002/PREFIX.0001.0001.0002.0001.txt
PREFIX/0001/0001/0002/PREFIX.0001.0001.0002.0002.txt
```
Two TSV files are produced with the mappings from the old to the new directory structure ("file-maps.tsv" and "folder-maps.tsv"). All the necessary commands are written in a file "commands.sh". With a few minor mods the app can do the same for windows. 

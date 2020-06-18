#!/bin/bash

git status 

message="update today"
if [ -n "$1" ]; then
	message=$1
fi

git pull
git add *
git commit -m "$message ` date -d now +"%F %T"`"
git push

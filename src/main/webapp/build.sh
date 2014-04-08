#!/bin/bash

log_msg() {
    RED=$(tput setaf 1)
    GREEN=$(tput setaf 2)
    NORMAL=$(tput sgr0)
    MSG="$1"
    let COL=$(tput cols)-${#MSG}

    printf "%${COL}s"  "$GREEN[DONE]$NORMAL"
    echo ""
}

FILES=$(ls src/ | grep -v ^footer.html$ | grep -v ^header.html$ | grep -v ~$)
for FILE in $FILES
do
echo -n "building $FILE..."
cat src/header.html > $FILE
cat src/$FILE >> $FILE
cat src/footer.html >> $FILE
echo "building $FILE..."
done

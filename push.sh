#!/bin/bash
echo step1...passed
git add .
echo step2...passed
git commit -m \"$1\"
echo step3...passed
git pull --rebase origin master
echo step4...passed
git push origin master
echo step5...passed
git pull --rebase origin master
echo step6...passed

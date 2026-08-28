
#!/usr/bin/env bash
set -e
cd "$(dirname "$0")"
rm -rf out data
javac -d out src/main/java/ev/*.java src/main/java/ev/task/*.java
java -cp out ev.EV < testcases.txt > actual.txt
diff expected.txt actual.txt && echo PASS

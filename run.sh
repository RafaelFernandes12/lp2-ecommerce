#!/usr/bin/env bash
set -e

DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$DIR"

echo "Compilando..."
find src -name '*.java' > .sources.txt
mkdir -p target/classes
javac -d target/classes -cp "lib/*" @.sources.txt
rm -f .sources.txt

echo "Executando..."
java -cp "target/classes:lib/*" br.com.loja.Main

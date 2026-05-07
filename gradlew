#!/bin/bash
# Gradle Wrapper Bypass for Colab
echo "Starting Gradle Build..."
# Unduh Gradle distribution secara manual lewat wget yang lebih stabil di Colab
if [ ! -d "gradle-8.5" ]; then
    echo "Downloading Gradle 8.5..."
    wget -q https://services.gradle.org/distributions/gradle-8.5-bin.zip
    unzip -q gradle-8.5-bin.zip
fi
# Jalankan gradle dari folder yang diekstrak
./gradle-8.5/bin/gradle assembleDebug "$@"

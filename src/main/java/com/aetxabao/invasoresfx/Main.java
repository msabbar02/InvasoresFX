package com.aetxabao.invasoresfx;

import com.aetxabao.invasoresfx.game.AppInvasoresFx;

/*
mvn package
/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home/bin/jpackage --name InvasoresFX --icon src/main/resources/com/aetxabao/invasoresfx/icon/icon.icns --input target --main-jar InvasoresFX-1.0-SNAPSHOT.jar --main-class com.aetxabao.invasoresfx.Main
 */
public class Main {
    public static void main(String[] args) {
        AppInvasoresFx.main(args);
    }
}
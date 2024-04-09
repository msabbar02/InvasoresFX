module com.aetxabao.invasoresfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires log4j;

    opens com.aetxabao.invasoresfx to javafx.fxml;
    exports com.aetxabao.invasoresfx;
    exports com.aetxabao.invasoresfx.sprite;
    opens com.aetxabao.invasoresfx.sprite to javafx.fxml;
    exports com.aetxabao.invasoresfx.util;
    opens com.aetxabao.invasoresfx.util to javafx.fxml;
    exports com.aetxabao.invasoresfx.game;
    opens com.aetxabao.invasoresfx.game to javafx.fxml;
    exports com.aetxabao.invasoresfx.sprite.weaponry;
    opens com.aetxabao.invasoresfx.sprite.weaponry to javafx.fxml;
}
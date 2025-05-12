module com.yubocao.markdowntest {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.yubocao.markdowntest to javafx.fxml;
    exports com.yubocao.markdowntest;
}
package com.example.estore;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class showDialogue {
    public static void show(String title, String msg) {
        Dialog<String> dia = new Dialog<>();
        dia.setTitle(title);
        ButtonType button = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dia.setContentText(msg);
        dia.getDialogPane().getButtonTypes().add(button);
        dia.showAndWait();
    }
}

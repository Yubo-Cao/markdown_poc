package com.yubocao.markdowntest;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.text.HitInfo;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class SelectableTextFlow extends TextFlow {
    private HitInfo startHit = null;
    private HitInfo endHit = null;
    private Path selectionPath = null;
    private Pane parentPane;

    public SelectableTextFlow(Pane parent) {
        super();
        this.parentPane = parent;
        this.setCursor(Cursor.TEXT);

        this.setOnMouseClicked(event -> toggleSelection());
        this.setOnMousePressed(event -> handleMousePressed(event));
        this.setOnMouseDragged(event -> handleMouseDragged(event));
    }

    public void setupCopyAccelerator(Scene scene) {
        KeyCombination keyComb = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
        scene.getAccelerators().put(keyComb, () -> copySelectedText());
    }

    public void addText(Text... texts) {
        this.getChildren().addAll(texts);
    }

    private void toggleSelection() {
        if (selectionPath != null) {
            parentPane.getChildren().remove(selectionPath);
            selectionPath = null;
        }
    }

    private void handleMousePressed(MouseEvent event) {
        if (selectionPath != null) {
            parentPane.getChildren().remove(selectionPath);
            selectionPath = null;
        }

        startHit = this.hitTest(new Point2D(event.getX(), event.getY()));
        endHit = startHit;
    }

    private void handleMouseDragged(MouseEvent event) {
        if (startHit != null) {
            endHit = this.hitTest(new Point2D(event.getX(), event.getY()));
            updateSelectionHighlight();
        }
    }

    private void updateSelectionHighlight() {
        if (selectionPath != null) {
            parentPane.getChildren().remove(selectionPath);
        }

        if (startHit != null && endHit != null) {
            PathElement[] elements = this.rangeShape(Math.min(startHit.getCharIndex(), endHit.getCharIndex()),
                    Math.max(startHit.getCharIndex() + 1, endHit.getCharIndex() + 1));

            Path path = new Path();
            path.getElements().addAll(elements);

            path.setFill(Color.LIGHTBLUE.deriveColor(0, 1, 1, 0.5));
            path.setStroke(null);

            path.setLayoutX(this.getLayoutX());
            path.setLayoutY(this.getLayoutY());
            path.setCursor(Cursor.TEXT);
            path.setOnMouseClicked(event -> toggleSelection());

            parentPane.getChildren().add(path);
            selectionPath = path;
        }
    }

    private void copySelectedText() {
        if (startHit != null && endHit != null) {
            int startIndex = Math.min(startHit.getCharIndex(), endHit.getCharIndex());
            int endIndex = Math.max(startHit.getCharIndex() + 1, endHit.getCharIndex() + 1);

            String fullText = getTextFlowContent();

            if (startIndex >= 0 && endIndex <= fullText.length() && startIndex < endIndex) {
                String selectedText = fullText.substring(startIndex, endIndex);
                ClipboardContent content = new ClipboardContent();
                content.putString(selectedText);
                Clipboard.getSystemClipboard().setContent(content);
            }
        }
    }

    private String getTextFlowContent() {
        StringBuilder sb = new StringBuilder();
        for (Node node : this.getChildren()) {
            if (node instanceof Text) {
                sb.append(((Text) node).getText());
            }
        }
        return sb.toString();
    }
}
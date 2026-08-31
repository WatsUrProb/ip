package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Represents one message in the chat between the user and NOVA.
 */
public class DialogBox extends HBox {

    private final Label message;

    /**
     * Creates a dialog box.
     *
     * @param text message to display
     * @param isUser whether the message belongs to the user
     */
    public DialogBox(String text, boolean isUser) {
        message = new Label(text);

        message.setWrapText(true);
        message.setMaxWidth(400);
        message.setPadding(
                new javafx.geometry.Insets(10)
        );

        if (isUser) {
            setAlignment(Pos.CENTER_RIGHT);

            message.setStyle(
                    "-fx-background-color: #dbeafe;"
                            + "-fx-background-radius: 12;"
            );
        } else {
            setAlignment(Pos.CENTER_LEFT);

            message.setStyle(
                    "-fx-background-color: #eeeeee;"
                            + "-fx-background-radius: 12;"
            );
        }

        getChildren().add(message);
    }
}
package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import nova.Nova;

/**
 * Main GUI window for Nova.
 */
public class MainWindow extends BorderPane {

    private final Nova nova;

    private final VBox dialogContainer;
    private final TextField userInput;
    private final Button sendButton;

    /**
     * Creates the main Nova GUI.
     *
     * @param nova Nova chatbot instance
     */
    public MainWindow(Nova nova) {
        this.nova = nova;

        dialogContainer = new VBox(10);
        dialogContainer.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(dialogContainer);
        scrollPane.setFitToWidth(true);

        userInput = new TextField();
        userInput.setPromptText("Enter a command...");

        sendButton = new Button("Send");

        BorderPane inputArea = new BorderPane();
        inputArea.setCenter(userInput);
        inputArea.setRight(sendButton);
        inputArea.setPadding(new Insets(10));

        setCenter(scrollPane);
        setBottom(inputArea);

        sendButton.setOnAction(event -> handleUserInput());
        userInput.setOnAction(event -> handleUserInput());

        addNovaMessage(
                "Hello! I'm Nova.\n"
                        + "What can I do for you?"
        );
    }

    /**
     * Handles a command entered by the user.
     */
    private void handleUserInput() {
        String input = userInput.getText().trim();

        if (input.isEmpty()) {
            return;
        }

        addUserMessage(input);

        String response = nova.getResponse(input);

        addNovaMessage(response);

        userInput.clear();
    }

    private void addUserMessage(String message) {
        Label label = new Label("You: " + message);
        label.setWrapText(true);

        dialogContainer.getChildren().add(label);
    }

    private void addNovaMessage(String message) {
        Label label = new Label("Nova: " + message);
        label.setWrapText(true);

        dialogContainer.getChildren().add(label);
    }
}
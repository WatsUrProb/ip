package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import gui.DialogBox.DialogType;
import nova.Nova;

/**
 * Main GUI window for Nova.
 */
public class MainWindow extends BorderPane {

    private final Nova nova;

    private final VBox dialogContainer;
    private final TextField userInput;
    private final Button sendButton;
    private final ScrollPane scrollPane;

    /**
     * Creates the main Nova GUI.
     *
     * @param nova Nova chatbot instance
     */
    public MainWindow(Nova nova) {
        this.nova = nova;

        dialogContainer = new VBox(10);
        dialogContainer.setPadding(new Insets(12));

        scrollPane = new ScrollPane(dialogContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        userInput = new TextField();
        userInput.setPromptText("Enter a command...");

        sendButton = new Button("Send");
        sendButton.setDefaultButton(true);

        BorderPane inputArea = new BorderPane();
        inputArea.setCenter(userInput);
        inputArea.setRight(sendButton);
        inputArea.setPadding(new Insets(10));
        inputArea.setMargin(sendButton, new Insets(0, 0, 0, 8));

        setCenter(scrollPane);
        setBottom(inputArea);

        sendButton.setOnAction(event -> handleUserInput());
        userInput.setOnAction(event -> handleUserInput());

        dialogContainer.heightProperty().addListener(
                (observable, oldValue, newValue) ->
                        scrollPane.setVvalue(1.0)
        );

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

        if (isErrorResponse(response)) {
            addErrorMessage(response);
        } else {
            addNovaMessage(response);
        }

        userInput.clear();
    }

    private boolean isErrorResponse(String response) {
        String lowerCaseResponse = response.toLowerCase();

        return lowerCaseResponse.contains("sorry")
                || lowerCaseResponse.contains("error")
                || lowerCaseResponse.contains("cannot")
                || lowerCaseResponse.contains("invalid");
    }

    private void addUserMessage(String message) {
        DialogBox dialogBox =
                new DialogBox(message, DialogType.USER);

        dialogContainer.getChildren().add(dialogBox);
    }

    private void addNovaMessage(String message) {
        DialogBox dialogBox =
                new DialogBox(message, DialogType.NOVA);

        dialogContainer.getChildren().add(dialogBox);
    }

    private void addErrorMessage(String message) {
        DialogBox dialogBox =
                new DialogBox("⚠ " + message, DialogType.ERROR);

        dialogContainer.getChildren().add(dialogBox);
    }
}
package Listgpt;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;

public class Listgpt extends Application {

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    private static TaskList list = new TaskList();
    private static UI userInterface = new UI(list);
    private static Storage storage = new Storage("./data/Listgpt.txt", list);

    /**
     * CLI Interface for ListGPT
     * @param args
     */
    public static void main(String[] args) {
        // Retrieve before running
        storage.retrieve();

        userInterface.run();

        // Save on exit
        storage.store();
    }

    /**
     * GUI Interface for ListGPT
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage stage) {
        //Setting up required components
        storage.retrieve();

        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();

        sendButton = new Button("Send");

        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(userInterface.getResponse("hello"), dukeImage)
        );

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.show();

        stage.setTitle("ListGPT");
        stage.setResizable(true);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);
        stage.setMaxHeight(800.0);
        stage.setMaxWidth(600.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefHeight(41.0);
        userInput.setPrefWidth(324.0);
        userInput.setId("userInput");
        userInput.setLayoutY(558.0);

        sendButton.setPrefHeight(41.0);
        sendButton.setPrefWidth(76.0);
        sendButton.setId("sendButton");
        sendButton.setLayoutX(324.0);
        sendButton.setLayoutY(558.0);
        sendButton.setMnemonicParsing(false);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setLeftAnchor(scrollPane, 1.0);
        AnchorPane.setRightAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(scrollPane, 60.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);
        AnchorPane.setRightAnchor(userInput, 76.0);

        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });
        userInput.setOnAction((event) -> {
            handleUserInput();
        });

        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
    }

    @Override
    public void stop() throws Exception {
        storage.store();
    }

    /**
     * Creates a dialog box containing user input, and appends it to
     * the dialog container. Clears the user input after processing.
     */
    private void handleUserInput() {
        String userText = userInput.getText();

        if (userText.equals("bye")) {
            Platform.exit();
        } else {
            String uiText = userInterface.getResponse(userInput.getText());
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(userText, userImage),
                    DialogBox.getDukeDialog(uiText, dukeImage)
            );
            userInput.clear();
        }
    }
}

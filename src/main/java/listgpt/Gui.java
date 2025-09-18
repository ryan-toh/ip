package listgpt;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * JavaFX User Interface for Listgpt
 */
public class Gui {
    private final TaskList list;
    private final Cli messageInterface;
    private final Storage storage;

    // UI nodes
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    /**
     * Creates a GUI object with the given parameters.
     * @param list a TaskList, must not be null
     * @param messageInterface a CLI object, must not be null
     * @param storage a Storage object, must not be null
     */
    public Gui(TaskList list, Cli messageInterface, Storage storage) {
        assert list != null && messageInterface != null && storage != null;
        this.list = list;
        this.messageInterface = messageInterface;
        this.storage = storage;
    }

    /** Build the entire scene graph and return a ready Scene. */
    public Scene build() {
        // Retrieve before running
        storage.retrieve();

        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(messageInterface.getResponse("hello"), dukeImage)
        );

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        // window layout prefs (the Stage will set title/size; we just size the root)
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

        // event wiring
        sendButton.setOnMouseClicked(e -> handleUserInput());
        userInput.setOnAction(e -> handleUserInput());
        dialogContainer.heightProperty().addListener(o -> scrollPane.setVvalue(1.0));

        return scene;
    }

    /** Persist tasks on app exit. Call from Application.stop(). */
    public void onStop() {
        storage.store();
    }

    private void handleUserInput() {
        String userText = userInput.getText();
        if (userText == null) {
            return;
        }

        if (userText.equals("bye")) {
            // save before exit
            storage.store();
            Platform.exit();
            return;
        }

        String uiText = messageInterface.getResponse(userText);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getDukeDialog(uiText, dukeImage)
        );
        userInput.clear();
    }
}

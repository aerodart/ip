package ev;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a chat bubble pairing a message with the speaker's avatar.
 */
public class DialogBox extends HBox {
    private Label text;
    private ImageView displayPicture;

    /**
     * Constructs a dialog box showing the given message beside the given avatar.
     *
     * @param message the text to display.
     * @param avatar the picture of the speaker.
     */
    private DialogBox(String message, Image avatar) {
        text = new Label(message);
        displayPicture = new ImageView(avatar);

        text.setWrapText(true);
        displayPicture.setFitWidth(100.0);
        displayPicture.setFitHeight(100.0);
        this.setAlignment(Pos.TOP_RIGHT);

        this.getChildren().addAll(text, displayPicture);
    }

    /**
     * Returns a dialog box for the user, with the avatar on the right.
     *
     * @param message the text the user entered.
     * @param avatar the user's picture.
     * @return the dialog box to add to the conversation.
     */
    public static DialogBox getUserDialog(String message, Image avatar) {
        return new DialogBox(message, avatar);
    }

    /**
     * Returns a dialog box for E.V., flipped so the avatar is on the left.
     *
     * @param message the text E.V. replied with.
     * @param avatar E.V.'s picture.
     * @return the dialog box to add to the conversation.
     */
    public static DialogBox getEvDialog(String message, Image avatar) {
        DialogBox box = new DialogBox(message, avatar);

        box.flip();
        return box;
    }

    /**
     * Flips the dialog box so the avatar is on the left and the text on the right.
     */
    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);

        ObservableList<Node> nodes = FXCollections.observableArrayList(this.getChildren());

        FXCollections.reverse(nodes);
        this.getChildren().setAll(nodes);
    }
}

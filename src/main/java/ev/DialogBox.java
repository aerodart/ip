package ev;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * Represents a chat bubble pairing a message with the speaker's avatar.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a dialog box from the FXML view, showing the message beside the avatar.
     *
     * @param text the message to display.
     * @param avatar the picture of the speaker.
     */
    private DialogBox(String text, Image avatar) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));

            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(avatar);
        displayPicture.setClip(new Circle(49.5, 49.5, 49.5));

    }

    /**
     * Returns a dialog box for the user, with the avatar on the right.
     *
     * @param text the message the user entered.
     * @param avatar the user's picture.
     * @return the dialog box to add to the conversation.
     */
    public static DialogBox getUserDialog(String text, Image avatar) {
        return new DialogBox(text, avatar);
    }

    /**
     * Returns a dialog box for E.V., flipped so the avatar is on the left.
     *
     * @param text the message E.V. replied with.
     * @param avatar E.V.'s picture.
     * @return the dialog box to add to the conversation.
     */
    public static DialogBox getEvDialog(String text, Image avatar) {
        DialogBox box = new DialogBox(text, avatar);

        box.flip();
        return box;
    }

    /**
     * Flips the dialog box so the avatar is on the left and the text on the right.
     */
    private void flip() {
        ObservableList<Node> nodes = FXCollections.observableArrayList(this.getChildren());

        Collections.reverse(nodes);
        this.getChildren().setAll(nodes);
        this.setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }
}

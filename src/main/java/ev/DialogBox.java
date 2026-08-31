package ev;

import javafx.geometry.Pos;
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
    public DialogBox(String message, Image avatar) {
        text = new Label(message);
        displayPicture = new ImageView(avatar);

        text.setWrapText(true);
        displayPicture.setFitWidth(100.0);
        displayPicture.setFitHeight(100.0);
        this.setAlignment(Pos.TOP_RIGHT);

        this.getChildren().addAll(text, displayPicture);
    }
}
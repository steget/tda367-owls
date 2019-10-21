package storagesystem.viewcontroller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public abstract class AbstractFader {

    /**
     * Takes in a JavaFX Node and starts with setting the opacity to full and fading out to 0 so the Node no longer shows.
     *
     * @param node          Thing to be faded
     * @param timeInSeconds How long it should take for the opacity to go from full to not visible.
     */


    public static void fadeTransition(Node node, int timeInSeconds) {
        TranslateTransition transition = new TranslateTransition();

        transition.setOnFinished((e) -> {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(timeInSeconds), node);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.play();
        });
        transition.play();
    }
}

package org.controlsfx.control;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;

import java.util.Collection;

import org.controlsfx.control.AutoCompletionBinding.ISuggestionRequest;

import javafx.animation.FadeTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 * A class containing useful customizations for the JavaFX {@link TextField}.
 * Note that this class is experimental and the API may change in future 
 * releases. Note also that this class makes use of the {@link CustomTextField}
 * class.
 * 
 * @see CustomTextField
 */
public class TextFields {
    private static final Duration FADE_DURATION = Duration.millis(350);

    private TextFields() {
        // no-op
    }

    /***************************************************************************
     *                                                                         *
     * Search fields                                                           *
     *                                                                         *
     **************************************************************************/

    /**
     * Creates a TextField that shows a clear button inside the TextField (on
     * the right hand side of it) when text is entered by the user.
     */
    public static TextField createSearchField() {
        final CustomTextField searchField = new CustomTextField();
        searchField.getStyleClass().add("search-field");

        Region clearButton = new Region();
        clearButton.getStyleClass().addAll("graphic");
        StackPane clearButtonPane = new StackPane(clearButton);
        clearButtonPane.getStyleClass().addAll("clear-button");
        clearButtonPane.setOpacity(0.0);
        clearButtonPane.setCursor(Cursor.DEFAULT);

        clearButtonPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                searchField.clear();
            }
        });

        searchField.setRight(clearButtonPane);

        final FadeTransition fader = new FadeTransition(FADE_DURATION, clearButtonPane);
        fader.setCycleCount(1);

        searchField.textProperty().addListener(new InvalidationListener() {

            @Override public void invalidated(Observable arg0) {
                String text = searchField.getText();
                boolean isTextEmpty = text == null || text.isEmpty();
                boolean isButtonVisible = fader.getNode().getOpacity() > 0;

                if (isTextEmpty && isButtonVisible) {
                    setButtonVisible(false);
                } else if (!isTextEmpty && !isButtonVisible) {
                    setButtonVisible(true);
                }
            }

            private void setButtonVisible( boolean visible ) {
                fader.setFromValue(visible? 0.0: 1.0);
                fader.setToValue(visible? 1.0: 0.0);
                fader.play();
            }
        });

        return searchField;
    }


    /***************************************************************************
     *                                                                         *
     * Auto-completion                                                         *
     *                                                                         *
     **************************************************************************/


    /**
     * Create a new auto-completion binding between the given textField and the given suggestion provider.
     * 
     * The {@link TextFields} API has some suggestion-provider builder methods for simple use cases.
     * 
     * @param textField The {@link TextField} to which auto-completion shall be added
     * @param suggestionProvider A suggestion-provider strategy to use
     * @return
     */
    public static <T> AutoCompletionBinding<T> bindAutoCompletion(TextField textField, Callback<ISuggestionRequest, Collection<T>> suggestionProvider){
        return new AutoCompletionTextFieldBinding<>(textField, suggestionProvider);
    }

    /**
     * Create a new auto-completion binding between the given {@link TextField} using the given auto-complete suggestions
     * 
     * @param textField The {@link TextField} to which auto-completion shall be added
     * @param possibleSuggestions Possible auto-complete suggestions
     * @return
     */
    public static <T> AutoCompletionBinding<T> bindAutoCompletion(TextField textField, T... possibleSuggestions){
        return new AutoCompletionTextFieldBinding<>(textField, createSuggestionProvider(possibleSuggestions));
    }

    /**
     * Creates a new simple suggestion provider. 
     * 
     * For more advanced use cases, developers should consider to implement
     * their own SuggestionProvider-Callback strategy.
     * 
     * @param possibleSuggestions Possible auto-complete suggestions
     * @return
     */
    public static <T> Callback<ISuggestionRequest, Collection<T>> createSuggestionProvider(T... possibleSuggestions){
        return SuggestionProvider.create(possibleSuggestions);
    }

    /**
     * Creates a new simple suggestion-provider. 
     * 
     * For more advanced use cases, developers should consider to implement
     * their own SuggestionProvider-Callback strategy.
     * 
     * @param stringConverter Strategy of how to convert T into a String. Default is the toString() method.
     * @param possibleSuggestions Possible auto-complete suggestions
     * @return
     */
    public static <T> Callback<ISuggestionRequest, Collection<T>> createSuggestionProvider(Callback<T, String> stringConverter, T... possibleSuggestions){
        return SuggestionProvider.create(stringConverter, possibleSuggestions);
    }

}


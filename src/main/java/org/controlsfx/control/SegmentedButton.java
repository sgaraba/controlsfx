/**
 * Copyright (c) 2013, ControlsFX
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *     * Neither the name of ControlsFX, any associated website, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL CONTROLSFX BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.controlsfx.control;

import impl.org.controlsfx.skin.SegmentedButtonSkin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

/**
 * The SegmentedButton is a simple control that forces together a group of
 * {@link ToggleButton} instances such that they appear as one collective button
 * (with sub-buttons), rather than as individual buttons. This is better 
 * clarified with a picture:
 * 
 * <br/>
 * <center>
 * <img src="segmentedButton.png"/>
 * </center>
 * 
 * <p>There is very little API on this control, you essentially create
 * {@link ToggleButton} instances as per usual (and don't bother putting them
 * into a {@link ToggleGroup}, as this is done by the SegmentedButton itself), and then 
 * you place these buttons inside the {@link #getButtons() buttons list}. The 
 * long-hand way to code this is as follows:
 * 
 * <pre>
 * {@code 
 * ToggleButton b1 = new ToggleButton("day");
 * ToggleButton b2 = new ToggleButton("week");
 * ToggleButton b3 = new ToggleButton("month");
 * ToggleButton b4 = new ToggleButton("year");
 *       
 * SegmentedButton segmentedButton = new SegmentedButton();    
 * segmentedButton.getButtons().addAll(b1, b2, b3, b4);}</pre>
 * 
 * <p>A slightly shorter way of doing this is to pass the ToggleButton instances
 * into the varargs constructor, as such:
 * 
 * <pre>{@code SegmentedButton segmentedButton = new SegmentedButton(b1, b2, b3, b4);}</pre>
 * 
 * @see ToggleButton
 * @see ToggleGroup
 */
public class SegmentedButton extends Control {
    
    private final ObservableList<ToggleButton> buttons;
    
    /**************************************************************************
     * 
     * Constructors
     * 
     *************************************************************************/
    
    /**
     * Creates a default SegmentedButton instance with no buttons.
     */
    public SegmentedButton() {
        this((ObservableList<ToggleButton>)null);
    }
    
    /**
     * Creates a default SegmentedButton instance with the provided buttons
     * inserted into it.
     * 
     * @param buttons A varargs array of buttons to add into the SegmentedButton
     *      instance.
     */
    public SegmentedButton(ToggleButton... buttons) {
        this(buttons == null ? 
                FXCollections.<ToggleButton>observableArrayList() : 
                FXCollections.observableArrayList(buttons));
    }
    
    /**
     * Creates a default SegmentedButton instance with the provided buttons
     * inserted into it.
     * 
     * @param buttons A list of buttons to add into the SegmentedButton instance.
     */
    public SegmentedButton(ObservableList<ToggleButton> buttons) {
        getStyleClass().add("segmented-button");
        this.buttons = buttons == null ? FXCollections.<ToggleButton>observableArrayList() : buttons;
        setFocusTraversable(true);
    }
    

    
    
    /**************************************************************************
     * 
     * Public API
     * 
     *************************************************************************/
    
    /** {@inheritDoc} */
    @Override protected Skin<?> createDefaultSkin() {
        return new SegmentedButtonSkin(this);
    }
    
    /**
     * Returns the list of buttons that this SegmentedButton will draw together
     * into one 'grouped button'. It is possible to modify this list to add or
     * remove {@link ToggleButton} instances, as shown in the javadoc 
     * documentation for this class.
     */
    public final ObservableList<ToggleButton> getButtons() {
        return buttons;
    }
    
    
    /**************************************************************************
     * 
     * CSS
     * 
     *************************************************************************/
    
    /**
     * {@inheritDoc}
     */
    @Override protected String getUserAgentStylesheet() {
        return SegmentedButton.class.getResource("segmentedbutton.css").toExternalForm();
    }
}
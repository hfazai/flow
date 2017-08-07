/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.demo.views;

import com.vaadin.flow.demo.ComponentDemo;
import com.vaadin.flow.html.Div;
import com.vaadin.flow.html.H3;
import com.vaadin.flow.html.Image;
import com.vaadin.ui.HasClickListeners.ClickEvent;
import com.vaadin.ui.VaadinButton;

/**
 * View for {@link VaadinButton} demo.
 */
@ComponentDemo(name = "Vaadin Button", href = "vaadin-button")
public class VaadinButtonView extends DemoView {

    private Div message;

    @Override
    void initView() {
        createDefaultButton();
        createImageButtonWithAutofocus();
        createImageButtonWithAccessibleLabel();
        createButtonsWithTabIndex();
        createDisabledButton();

        message = new Div();
        message.setId("buttonMessage");
        add(message);
    }

    private void createDefaultButton() {
        // begin-source-example
        // source-example-heading: Default button
        VaadinButton button = new VaadinButton("Vaadin button");

        button.addClickListener(this::showButtonClickedMessage);
        // end-source-example

        add(new H3("Default button"), button);
        button.setId("default-button");
    }

    private void createImageButtonWithAutofocus() {
        // begin-source-example
        // source-example-heading: Button with image and autofocus
        VaadinButton button = new VaadinButton(
                new Image("img/vaadin-logo.svg", "Vaadin logo"));
        button.setAutofocus(true);

        button.addClickListener(this::showButtonClickedMessage);
        // end-source-example

        addCard(new H3("Button with image and autofocus"), button);
        button.setId("image-button");
    }

    private void createImageButtonWithAccessibleLabel() {
        // begin-source-example
        // source-example-heading: Button with ARIA label
        VaadinButton button = new VaadinButton("Accessible");
        button.getElement().setAttribute("aria-label", "Click me");

        button.addClickListener(this::showButtonClickedMessage);
        // end-source-example

        addCard(new H3("Button with ARIA label"), button);
        button.setId("accessible-button");
    }

    private void createButtonsWithTabIndex() {
        // begin-source-example
        // source-example-heading: Buttons with custom tabindex
        VaadinButton button1 = new VaadinButton("1");
        button1.setTabIndex(1);
        button1.addClickListener(this::showButtonClickedMessage);

        VaadinButton button2 = new VaadinButton("2");
        button2.setTabIndex(2);
        button2.addClickListener(this::showButtonClickedMessage);

        VaadinButton button3 = new VaadinButton("3");
        button3.setTabIndex(3);
        button3.addClickListener(this::showButtonClickedMessage);
        // end-source-example

        addCard(new H3("Buttons with custom tabindex"), button3, button2,
                button1);
        button1.setId("button-tabindex-1");
        button2.setId("button-tabindex-2");
        button3.setId("button-tabindex-3");
    }

    private void createDisabledButton() {
        // begin-source-example
        // source-example-heading: Disabled button
        VaadinButton button = new VaadinButton("Disabled");
        button.setDisabled(true);
        // end-source-example

        addCard(new H3("Disabled button"), button);
        button.addClickListener(evt -> message.setText("Button "
                + evt.getSource().getText()
                + " was clicked, but the button is disabled and this shouldn't happen!"));
        button.setId("disabled-button");
    }

    private void showButtonClickedMessage(ClickEvent<VaadinButton> evt) {
        VaadinButton source = evt.getSource();
        source.getParent().ifPresent(parent -> parent.getElement()
                .appendChild(message.getElement()));
        if (source.getElement().getChildCount() > 0
                && !source.getElement().getChild(0).isTextNode()) {
            message.setText("Button with image was clicked.");
        } else {
            message.setText("Button " + source.getText() + " was clicked.");
        }
    }
}
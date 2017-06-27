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
package com.vaadin.components.vaadin.combo.box;

import com.vaadin.ui.Component;
import javax.annotation.Generated;
import com.vaadin.annotations.Tag;
import com.vaadin.annotations.HtmlImport;
import elemental.json.JsonObject;

@Generated({
		"Generator: com.vaadin.generator.ComponentGenerator#0.1.11-SNAPSHOT",
		"WebComponent: vaadin-combo-box-overlay#null", "Flow#0.1.11-SNAPSHOT"})
@Tag("vaadin-combo-box-overlay")
@HtmlImport("frontend://bower_components/vaadin-combo-box/vaadin-combo-box-overlay.html")
public class VaadinComboBoxOverlay<R extends VaadinComboBoxOverlay<R>>
		extends
			Component {

	/**
	 * Description copied from corresponding location in WebComponent:
	 * 
	 * The element to position/align the dropdown by.
	 */
	public JsonObject getPositionTarget() {
		return (JsonObject) getElement().getPropertyRaw("positionTarget");
	}

	/**
	 * Description copied from corresponding location in WebComponent:
	 * 
	 * The element to position/align the dropdown by.
	 * 
	 * @param positionTarget
	 * @return This instance, for method chaining.
	 */
	public R setPositionTarget(elemental.json.JsonObject positionTarget) {
		getElement().setPropertyJson("positionTarget", positionTarget);
		return getSelf();
	}

	/**
	 * Description copied from corresponding location in WebComponent:
	 * 
	 * Vertical offset for the overlay position.
	 */
	public double getVerticalOffset() {
		return getElement().getProperty("verticalOffset", 0.0);
	}

	/**
	 * Description copied from corresponding location in WebComponent:
	 * 
	 * Vertical offset for the overlay position.
	 * 
	 * @param verticalOffset
	 * @return This instance, for method chaining.
	 */
	public R setVerticalOffset(double verticalOffset) {
		getElement().setProperty("verticalOffset", verticalOffset);
		return getSelf();
	}

	/**
	 * Description copied from corresponding location in WebComponent:
	 * 
	 * True if the device supports touch events.
	 */
	public boolean isTouchDevice() {
		return getElement().getProperty("touchDevice", false);
	}

	/**
	 * Description copied from corresponding location in WebComponent:
	 * 
	 * True if the device supports touch events.
	 * 
	 * @param touchDevice
	 * @return This instance, for method chaining.
	 */
	public R setTouchDevice(boolean touchDevice) {
		getElement().setProperty("touchDevice", touchDevice);
		return getSelf();
	}

	/**
	 * Description copied from corresponding location in WebComponent:
	 * 
	 * {@code true} when new items are being loaded.
	 */
	public boolean isLoading() {
		return getElement().getProperty("loading", false);
	}

	/**
	 * Description copied from corresponding location in WebComponent:
	 * 
	 * {@code true} when new items are being loaded.
	 * 
	 * @param loading
	 * @return This instance, for method chaining.
	 */
	public R setLoading(boolean loading) {
		getElement().setProperty("loading", loading);
		return getSelf();
	}

	/**
	 * Description copied from corresponding location in WebComponent:
	 * 
	 * Can be called to manually notify a resizable and its descendant
	 * resizables of a resize change.
	 */
	public void notifyResize() {
		getElement().callFunction("notifyResize");
	}

	/**
	 * Description copied from corresponding location in WebComponent:
	 * 
	 * Used to assign the closest resizable ancestor to this resizable if the
	 * ancestor detects a request for notifications.
	 * 
	 * @param parentResizable
	 */
	public void assignParentResizable(elemental.json.JsonObject parentResizable) {
		getElement().callFunction("assignParentResizable", parentResizable);
	}

	/**
	 * Description copied from corresponding location in WebComponent:
	 * 
	 * Used to remove a resizable descendant from the list of descendants that
	 * should be notified of a resize change.
	 * 
	 * @param target
	 */
	public void stopResizeNotificationsFor(elemental.json.JsonObject target) {
		getElement().callFunction("stopResizeNotificationsFor", target);
	}

	/**
	 * Description copied from corresponding location in WebComponent:
	 * 
	 * This method can be overridden to filter nested elements that should or
	 * should not be notified by the current element. Return true if an element
	 * should be notified, or false if it should not be notified.
	 * 
	 * @param element
	 */
	public void resizerShouldNotify(elemental.json.JsonObject element) {
		getElement().callFunction("resizerShouldNotify", element);
	}

	/**
	 * Description copied from corresponding location in WebComponent:
	 * 
	 * Gets the index of the item with the provided label.
	 * 
	 * @param label
	 */
	public void indexOfLabel(elemental.json.JsonObject label) {
		getElement().callFunction("indexOfLabel", label);
	}

	/**
	 * Description copied from corresponding location in WebComponent:
	 * 
	 * Gets the label string for the item based on the {@code _itemLabelPath}.
	 * 
	 * @param item
	 */
	public void getItemLabel(elemental.json.JsonObject item) {
		getElement().callFunction("getItemLabel", item);
	}

	public void ensureItemsRendered() {
		getElement().callFunction("ensureItemsRendered");
	}

	public void adjustScrollPosition() {
		getElement().callFunction("adjustScrollPosition");
	}

	public void updateViewportBoundaries() {
		getElement().callFunction("updateViewportBoundaries");
	}

	/**
	 * Gets the narrow typed reference to this object. Subclasses should
	 * override this method to support method chaining using the inherited type.
	 * 
	 * @return This object casted to its type.
	 */
	protected R getSelf() {
		return (R) this;
	}
}
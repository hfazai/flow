/*
 * Copyright 2000-2020 Vaadin Ltd.
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
package com.vaadin.flow.data.provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.function.SerializableSupplier;

public class AbstractListDataViewTest {

    private Collection<String> items;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private ListDataProvider<String> dataProvider;

    private AbstractListDataView<String> dataView;

    @Before
    public void init() {

        items = new ArrayList<>(Arrays.asList("first", "middle", "last"));
        dataProvider = DataProvider.ofCollection(items);
        dataView = new ListDataViewImpl(() -> dataProvider, null);
    }

    @Test
    public void createListDataViewInstance_faultyDataProvider_throwsException() {
        DataProvider dataProvider = DataProvider
                .fromCallbacks(query -> Stream.of("one"), query -> 1);
        exceptionRule.expect(IllegalStateException.class);
        exceptionRule.expectMessage(
                "ListDataViewImpl only supports 'ListDataProvider' "
                        + "or it's subclasses, but was given a 'AbstractBackEndDataProvider'");
        new ListDataViewImpl(() -> dataProvider, null);
    }

    @Test
    public void hasNextItem_nextItemAvailable_nextItemFound() {
        Assert.assertTrue("First item should have next item",
                dataView.hasNextItem("first"));
        Assert.assertTrue("Item in middle should have next item",
                dataView.hasNextItem("middle"));
    }

    @Test
    public void hasNextItem_nextItemUnavailable_nextItemNotFound() {
        Assert.assertFalse("No next item for last item should be available",
                dataView.hasNextItem("last"));
    }

    @Test
    public void getNextItem_nextItemAvailable_nextItemFound() {
        Assert.assertEquals("Faulty next item", "last",
                dataView.getNextItem("middle"));
    }

    @Test
    public void getNextItem_nextItemUnavailable_nextItemNotFound() {
        Assert.assertNull("Got next item for last item",
                dataView.getNextItem("last"));
    }

    @Test
    public void hasPrevItem_prevItemAvailable_prevItemFound() {
        Assert.assertTrue("Last item should have previous item",
                dataView.hasPreviousItem("last"));
        Assert.assertTrue("Item in middle should have previous item",
                dataView.hasPreviousItem("middle"));
    }

    @Test
    public void hasPrevItem_prevItemUnavailable_prevItemNotFound() {
        Assert.assertFalse(
                "No previous item for first item should be available",
                dataView.hasPreviousItem("first"));
    }

    @Test
    public void getPrevItem_prevItemAvailable_prevItemFound() {
        Assert.assertEquals("Item in middle should have previous item", "first",
                dataView.getPreviousItem("middle"));
    }

    @Test
    public void getPrevItem_prevItemUnavailable_prevItemNotFound() {
        Assert.assertNull("Got previous item for first index",
                dataView.getPreviousItem("first"));
    }

    @Test
    public void withFilter_filterIsSet_filteredItemsObtained() {
        Assert.assertEquals(items.size(), dataView.getDataSize());
        dataView.withFilter(item -> item.equals("first"));
        Assert.assertEquals("Filter was not applied to data size", 1,
                dataView.getDataSize());
        Assert.assertEquals("Expected item is missing from filtered data",
                "first", dataView.getAllItems().findFirst().get());
    }

    @Test
    public void withFilter_filterReset_allItemsObtained() {
        ((ListDataProvider) dataProvider)
                .setFilter(item -> item.equals("first"));
        dataView.withFilter(null);
        Assert.assertEquals("Filter reset was not applied to data size",
                items.size(), dataView.getDataSize());
        Assert.assertArrayEquals("Filter reset was not applied to data set",
                items.toArray(), dataView.getAllItems().toArray());
    }

    @Test
    public void withSortComparator_sortIsSet_sortedItemsObtained() {
        dataView.withSortComparator(String::compareTo);
        Assert.assertEquals("Unexpected data set order", "first,last,middle",
                dataView.getAllItems().collect(Collectors.joining(",")));
    }

    @Test
    public void getAllItems_noFiltersSet_allItemsObtained() {
        Stream<String> allItems = dataView.getAllItems();
        Assert.assertArrayEquals("Unexpected data set", items.toArray(),
                allItems.toArray());
    }

    @Test
    public void getDataSize_noFiltersSet_dataSizeObtained() {
        Assert.assertEquals("Unexpected size for data", items.size(),
                dataView.getDataSize());
    }

    @Test
    public void isItemPresent_itemPresentedInDataSet_itemFound() {
        Assert.assertTrue("Set item was not found in the data",
                dataView.isItemPresent("first"));
    }

    @Test
    public void isItemPresent_itemNotPresentedInDataSet_itemNotFound() {
        Assert.assertFalse("Non existent item found in data",
                dataView.isItemPresent("absent item"));
    }

    @Test
    public void addItem_itemInDataset() {
        final String newItem = "new Item";
        dataView.addItem(newItem);

        Assert.assertEquals(4, dataView.getDataSize());
        Assert.assertTrue(dataView.isItemPresent(newItem));
        Assert.assertEquals(newItem, dataView.getNextItem("last"));

    }

    @Test
    public void removeItem_itemRemovedFromDataset() {
        dataView.removeItem("middle");

        Assert.assertEquals(2, dataView.getDataSize());
        Assert.assertFalse(dataView.isItemPresent("middle"));
        Assert.assertEquals("last", dataView.getNextItem("first"));
    }


    @Test
    public void dataViewWithItem_rowOutsideSetRequested_exceptionThrown() {
        exceptionRule.expect(IndexOutOfBoundsException.class);
        exceptionRule.expectMessage(
                "Given index 7 is outside of the accepted range '0 - 2'");

        dataView.validateItemIndex(7);
    }

    @Test
    public void dataViewWithItem_negativeRowRequested_exceptionThrown() {
        exceptionRule.expect(IndexOutOfBoundsException.class);
        exceptionRule.expectMessage(
                "Given index -7 is outside of the accepted range '0 - 2'");

        dataView.validateItemIndex(-7);
    }

    @Test
    public void dataViewWithoutItems_exceptionThrown() {
        exceptionRule.expect(IndexOutOfBoundsException.class);
        exceptionRule.expectMessage("Requested index 5 on empty data.");

        dataProvider = DataProvider.ofCollection(Collections.emptyList());
        dataView = new ListDataViewImpl(() -> dataProvider, null);
        dataView.validateItemIndex(5);
    }
    private static class ListDataViewImpl extends AbstractListDataView<String> {

        public ListDataViewImpl(
                SerializableSupplier<DataProvider<String, ?>> dataProviderSupplier,
                Component component) {
            super(dataProviderSupplier, component);
        }
    }
}

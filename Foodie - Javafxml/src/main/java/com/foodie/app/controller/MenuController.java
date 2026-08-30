package com.foodie.app.controller;

import com.foodie.app.model.MenuData.Category;
import com.foodie.app.model.MenuData.MenuItem;
import com.foodie.app.service.MenuService;
import com.foodie.app.service.ServiceLocator;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.math.BigDecimal;
import java.util.Optional;

public class MenuController {
        @FXML private Label totalItemsValue;
    @FXML private Label activeItemsValue;
    @FXML private Label inactiveItemsValue;
    @FXML private Label showingItemsLabel;
    @FXML private TableView<MenuItem> menuTable;
    @FXML private TableColumn<MenuItem, Number> numberColumn;
    @FXML private TableColumn<MenuItem, MenuItem> imageColumn;
    @FXML private TableColumn<MenuItem, MenuItem> itemColumn;
    @FXML private TableColumn<MenuItem, Category> categoryColumn;
    @FXML private TableColumn<MenuItem, String> priceColumn;
    @FXML private TableColumn<MenuItem, Boolean> availabilityColumn;
    @FXML private TableColumn<MenuItem, MenuItem> menuActionsColumn;

    private final MenuService menuService = ServiceLocator.getMenuService();
    private ObservableList<MenuItem> allItems;
    private FilteredList<MenuItem> filteredItems;
    private Category activeCategory;
        
    @FXML
    private void initialize() {
        configureTable();
        allItems = FXCollections.observableArrayList();
        filteredItems = new FilteredList<>(allItems);
        menuTable.setItems(filteredItems);
        
        menuService.getMenuItems().thenAccept(items -> {
            Platform.runLater(() -> {
                allItems.setAll(items);
                refreshCounts();
            });
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    private void configureTable() {
        menuTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        numberColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getTableView().getItems().indexOf(cell.getValue()) + 1));
        imageColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue()));
        itemColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue()));
        categoryColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().category()));
        priceColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper("Rs. " + (cell.getValue().price() != null ? cell.getValue().price().stripTrailingZeros().toPlainString() : "0")));
        availabilityColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().isActive()));
        menuActionsColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue()));

        imageColumn.setCellFactory(column -> new ImageBadgeCell());
        itemColumn.setCellFactory(column -> new ItemDetailsCell());
        categoryColumn.setCellFactory(column -> new CategoryCell());
        availabilityColumn.setCellFactory(column -> new AvailabilityCell());
        menuActionsColumn.setCellFactory(column -> new MenuActionsCell());
    }

    @FXML
    private void handleCategoryFilter(javafx.event.ActionEvent event) {
        ToggleButton button = (ToggleButton) event.getSource();
        String value = String.valueOf(button.getUserData());
        activeCategory = "ALL".equals(value) ? null : Category.valueOf(value);
        filteredItems.setPredicate(item -> activeCategory == null || item.category() == activeCategory);
        showingItemsLabel.setText("Showing " + filteredItems.size() + " items");
    }

    @FXML private void handleAddItem() { openItemModal(null); }

    private void openItemModal(MenuItem editing) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(editing == null ? "Add Menu Item" : "Edit Menu Item");
        dialog.setHeaderText(editing == null ? "Create a new food item" : "Update menu item");
        dialog.initOwner(menuTable.getScene().getWindow());

        ButtonType saveType = new ButtonType(editing == null ? "Add Item" : "Save Changes", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveType, ButtonType.CANCEL);
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/com/foodie/app/style/app.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("menu-item-dialog");

        TextField name = new TextField(editing == null ? "" : editing.name());
        name.setPromptText("e.g. Grilled Chicken Burger");
        TextArea description = new TextArea(editing == null ? "" : editing.description());
        description.setPromptText("Short item description");
        description.setPrefRowCount(2);
        ComboBox<Category> category = new ComboBox<>(FXCollections.observableArrayList(Category.values()));
        category.setConverter(new javafx.util.StringConverter<>() {
            @Override public String toString(Category value) { return value == null ? "" : value.displayName(); }
            @Override public Category fromString(String value) { return null; }
        });
        category.setValue(editing == null ? Category.BURGERS : editing.category());
        TextField price = new TextField(editing == null ? "" : editing.price().stripTrailingZeros().toPlainString());
        price.setPromptText("Price in Rs.");
        CheckBox active = new CheckBox("Available for ordering");
        active.setSelected(editing == null || editing.isActive());

        GridPane form = new GridPane();
        form.setHgap(12);
        form.setVgap(10);
        form.setPadding(new Insets(4));
        form.addRow(0, new Label("Item name"), name);
        form.addRow(1, new Label("Description"), description);
        form.addRow(2, new Label("Category"), category);
        form.addRow(3, new Label("Price"), price);
        form.add(active, 1, 4);
        GridPane.setHgrow(name, javafx.scene.layout.Priority.ALWAYS);
        dialog.getDialogPane().setContent(form);
        dialog.getDialogPane().setPrefWidth(500);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isEmpty() || result.get() != saveType) return;

        try {
            if (name.getText().isBlank()) throw new IllegalArgumentException("Item name is required.");
            BigDecimal parsedPrice = new BigDecimal(price.getText().trim());
            if (parsedPrice.signum() < 0) throw new IllegalArgumentException("Price cannot be negative.");
            long id = editing == null ? allItems.stream().mapToLong(MenuItem::id).max().orElse(0) + 1 : editing.id();
            MenuItem saved = new MenuItem(id, name.getText().trim(), description.getText().trim(),
                    category.getValue(), parsedPrice, active.isSelected() ? "Available" : "Inactive");
            if (editing == null) {
                menuService.addMenuItem(saved).thenAccept(res -> Platform.runLater(() -> {
                    allItems.add(res);
                    refreshCounts();
                }));
            } else {
                menuService.updateMenuItem(String.valueOf(editing.id()), saved).thenAccept(res -> Platform.runLater(() -> {
                    allItems.set(allItems.indexOf(editing), res);
                    refreshCounts();
                }));
            }
        } catch (NumberFormatException exception) {
            showError("Enter a valid numeric price.");
        } catch (IllegalArgumentException exception) {
            showError(exception.getMessage());
        }
    }

    private void deleteItem(MenuItem item) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + item.name() + "?", ButtonType.CANCEL, ButtonType.OK);
        confirm.setHeaderText("Remove menu item");
        confirm.initOwner(menuTable.getScene().getWindow());
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            menuService.deleteMenuItem(String.valueOf(item.id())).thenAccept(v -> Platform.runLater(() -> {
                allItems.remove(item);
                refreshCounts();
            }));
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.initOwner(menuTable.getScene().getWindow());
        alert.setHeaderText("Unable to save item");
        alert.showAndWait();
    }

    private void refreshCounts() {
        totalItemsValue.setText(String.valueOf(allItems.size()));
        long active = allItems.stream().filter(MenuItem::isActive).count();
        activeItemsValue.setText(String.valueOf(active));
        inactiveItemsValue.setText(String.valueOf(allItems.size() - active));
        showingItemsLabel.setText("Showing " + filteredItems.size() + " items");
    }

                
    

         


    private class ImageBadgeCell extends TableCell<MenuItem, MenuItem> {
        private final Label badge = new Label();
        public ImageBadgeCell() { badge.getStyleClass().add("food-badge"); }
        @Override protected void updateItem(MenuItem item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) setGraphic(null);
            else { badge.setText(item.name().substring(0, 1)); setGraphic(badge); }
        }
    }

    private class ItemDetailsCell extends TableCell<MenuItem, MenuItem> {
        @Override protected void updateItem(MenuItem item, boolean empty) {
            super.updateItem(item, empty);
            setText(empty || item == null ? null : item.name());
        }
    }

    private class CategoryCell extends TableCell<MenuItem, Category> {
        @Override protected void updateItem(Category cat, boolean empty) {
            super.updateItem(cat, empty);
            setText(empty || cat == null ? null : cat.name());
        }
    }

    private class AvailabilityCell extends TableCell<MenuItem, Boolean> {
        private final Label badge = new Label();
        @Override protected void updateItem(Boolean active, boolean empty) {
            super.updateItem(active, empty);
            if (empty || active == null) setGraphic(null);
            else {
                badge.setText(active ? "Active" : "Inactive");
                badge.getStyleClass().setAll(active ? "active-summary-label" : "inactive-summary-label");
                setGraphic(badge);
            }
        }
    }

    private class MenuActionsCell extends TableCell<MenuItem, MenuItem> {
        private final Button edit = new Button("Edit");
        private final Button delete = new Button("Delete");
        private final javafx.scene.layout.HBox box = new javafx.scene.layout.HBox(6, edit, delete);
        public MenuActionsCell() {
            edit.getStyleClass().add("customer-action-btn");
            edit.setTooltip(new javafx.scene.control.Tooltip("Edit menu item"));
            delete.getStyleClass().add("customer-action-btn");
            delete.setTooltip(new javafx.scene.control.Tooltip("Delete menu item"));
            edit.setOnAction(e -> openItemModal(getItem()));
            delete.setOnAction(e -> deleteItem(getItem()));
        }
        @Override protected void updateItem(MenuItem item, boolean empty) {
            super.updateItem(item, empty);
            setGraphic(empty || item == null ? null : box);
        }
    }
}



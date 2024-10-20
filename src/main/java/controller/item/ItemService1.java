package controller.item;

import javafx.collections.ObservableList;
import dto.Item;

public interface ItemService1 {
    boolean addItem(Item item);
    boolean updateItem(Item item);
    Item searchItem(String itemCode);
    boolean deleteItem(String itemCode);

    ObservableList<Item>getAllItem();
}

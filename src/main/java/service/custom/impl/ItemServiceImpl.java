package service.custom.impl;

import entity.ItemEntity;
import javafx.collections.ObservableList;
import dto.Item;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.ItemDao;
import service.custom.ItemService;
import util.DaoType;

public class ItemServiceImpl implements ItemService {
    @Override
    public boolean addItem(Item item) {
        System.out.println("Item Service : "+ item);
        ItemDao itemDao = DaoFactory.getInstance().getDaoType(DaoType.ITEM);
        return itemDao.save( new ModelMapper().map(item, ItemEntity.class));

    }

    @Override
    public boolean updateItem(Item item) {
        return false;
    }

    @Override
    public Item searchItem(String itemCode) {
        return null;
    }

    @Override
    public boolean deleteItem(String itemCode) {
        return false;
    }

    @Override
    public ObservableList<Item> getAllItem() {
        return null;
    }
}

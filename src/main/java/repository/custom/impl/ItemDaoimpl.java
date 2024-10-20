package repository.custom.impl;

import entity.ItemEntity;
import repository.custom.ItemDao;
import util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class ItemDaoimpl implements ItemDao {
    @Override
    public boolean save(ItemEntity item) {
        System.out.println("Item Repository : "+item);
        String SQL = "INSERT INTO item VALUES(?,?,?,?,?)";

        try {
            return CrudUtil.execute(
                    SQL,
                    item.getItemCode(),
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQty()

            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean update(ItemEntity entity, String s) {
        return false;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    @Override
    public List<ItemEntity> findAll() {
        return List.of();
    }
}

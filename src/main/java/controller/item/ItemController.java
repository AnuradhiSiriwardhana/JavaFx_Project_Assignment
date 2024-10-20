package controller.item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dto.Item;
import dto.OrderDetail;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemController implements ItemService {

    @Override
    public boolean addItem(Item item) {
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
    public boolean updateItem(Item item) {
        String SQL = "UPDATE item SET Description=?,PackSize=?,UnitPrice=?,QtyOnHand=? WHERE ItemCode=?";

        try {
           return CrudUtil.execute(SQL,
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQty(),
                    item.getItemCode()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Item searchItem(String itemCode) {
        String SQL ="SELECT * FROM item WHERE ItemCode=?";
        ResultSet resultSet = null;
        try {
            resultSet = CrudUtil.execute(SQL,itemCode);
            resultSet.next();

                return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    public boolean deleteItem(String itemCode) {
        String SQL = "DELETE FROM item WHERE ItemCode=?";
        try {
            return CrudUtil.execute(SQL,itemCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public ObservableList<Item> getAllItem() {

        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();
        String SQL = "select * FROM item";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);

            while(resultSet.next()){
                itemObservableList.add(new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5)

                ));
            }
            return itemObservableList;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
  public ObservableList<String>getItemCode(){
      ObservableList<Item> allItem = getAllItem();
      ObservableList<String> itemCodes = FXCollections.observableArrayList();

      allItem.forEach(item -> {
          itemCodes.add(item.getItemCode());
      });
      return itemCodes;
  }

    public boolean updateStock(List<OrderDetail> orderDetails) throws SQLException {
        for (OrderDetail orderDetail : orderDetails){
            if(!updateStock(orderDetail)){
                return false;
            }
        }
        return true;
    }
    public boolean updateStock(OrderDetail orderDetails) throws SQLException {
        return CrudUtil.execute("UPDATE item set QtyOnHand = QtyOnHand-? WHERE ItemCode=?",
                orderDetails.getQty(),
                orderDetails.getItemCode()
                );


    }
}

package controller.customer.order;

import model.OrderDetail;
import util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailController {


    public static boolean addOrderDetail(List<OrderDetail> orderDetails) throws SQLException {
        for(OrderDetail orderDetail : orderDetails){
            boolean isOrderDetailAdd = addOrderDetail(orderDetail);
            if (!isOrderDetailAdd){
                return false;
            }
        }
        return true;
    }

    public static boolean addOrderDetail(OrderDetail orderDetail) throws SQLException {
        return CrudUtil.execute("INSERT INTO orderdetil VALUES(?,?,?,?)",
                orderDetail.getOrderId(),
                orderDetail.getItemCode(),
                orderDetail.getQty(),
                orderDetail.getDiscount()
        );
    }


}

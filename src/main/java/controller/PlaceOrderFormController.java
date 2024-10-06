package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.customer.CustomerController;
import controller.item.ItemController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.CartTM;
import model.Customer;
import model.Item;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQTY;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblTime;

    @FXML
    private TableView<CartTM> tblCart;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtOrderId;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXTextField txtStock;

    @FXML
    private JFXTextField txtUnitPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newValue) ->{

            loadItemDate(newValue);
        } );
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newValue) ->{

            loadCustomerData(newValue);
        } );

        loadDateAndTime();
        loadCustomerIds();
        loadItemCodes();
    }


    ObservableList<CartTM> cart = FXCollections.observableArrayList();

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        Double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        Integer qty = Integer.parseInt(txtQty.getText());

        Double total = unitPrice*qty;

        cart.add(
                new CartTM(
                cmbItemCode.getValue(),
                txtDescription.getText(),
                qty,
                unitPrice,
                total

           )
        );
        tblCart.setItems(cart);
        calcNetTotal();
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }
    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            lblTime.setText(
                    now.getHour() + " : " + now.getMinute() + " : " + now.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void loadCustomerIds(){
        cmbCustomerId.setItems(new CustomerController().getCustomerIds());
    }
    private void loadItemCodes(){
        cmbItemCode.setItems(new ItemController().getItemCode());
    }
    private void loadItemDate(String itemCode){
        Item item = new ItemController().searchItem(itemCode);

        txtDescription.setText(item.getDescription());
        txtStock.setText(item.getQty().toString());
        txtUnitPrice.setText(item.getUnitPrice().toString());

    }
    private void loadCustomerData(String customerId){
        Customer customer = new CustomerController().searchCustomer(customerId);

        txtName.setText(customer.getName());
        txtCity.setText(customer.getCity());
        txtSalary.setText(customer.getSalary().toString());


    }
    private void calcNetTotal() {
        Double total = 0.0;

        for (CartTM cartTM : cart) {
            total += cartTM.getTotal();
        }
        lblNetTotal.setText(total.toString());

    }
}

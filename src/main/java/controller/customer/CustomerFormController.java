package controller.customer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import util.CrudUtil;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXComboBox<String> cmbTitle;

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colCity;

    @FXML
    private TableColumn colDob;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colPostalCode;

    @FXML
    private TableColumn colProvince;

    @FXML
    private TableColumn colSalary;

    @FXML
    private TableColumn colTitle;

    @FXML
    private DatePicker dateDOB;

    @FXML
    private TableView<Customer> tblCustomers;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtCustomerId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    private JFXTextField txtProvince;

    @FXML
    private JFXTextField txtSalary;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> customerTitleList = FXCollections.observableArrayList();
        customerTitleList.add("Mr");
        customerTitleList.add("Mrs");
        customerTitleList.add("Miss");
        customerTitleList.add("Ms");
        cmbTitle.setItems(customerTitleList);
        loadTable();

        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observableValue, customer, newValue) -> {
           if(newValue!=null){
               setValueToText(newValue);
           }
        });
    }

    private void setValueToText(Customer newValue) {
        txtCustomerId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        cmbTitle.setValue(newValue.getTitle());
        txtProvince.setText(newValue.getProvince());
        txtPostalCode.setText(newValue.getPostalCode());
        txtSalary.setText(newValue.getSalary().toString());
        dateDOB.setValue(newValue.getDob());
        txtAddress.setText(newValue.getAddress());
        txtCity.setText(newValue.getCity());
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {


        Customer customer = new Customer(
                txtCustomerId.getText(),
                cmbTitle.getValue(),
                txtName.getText(),
                dateDOB.getValue(),
                Double.parseDouble(txtSalary.getText()),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText()
        );

        String SQL = "INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)";

        try {

            boolean isCustomerAdd = CrudUtil.execute(
                    SQL,
                    customer.getId(),
                    customer.getTitle(),
                    customer.getName(),
                    customer.getDob(),
                    customer.getSalary(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode()

                    );
            if(isCustomerAdd){
                new Alert(Alert.AlertType.INFORMATION,"Customer Added:)").show();
                loadTable();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Customer Not Added:(").show();
        }

        System.out.println(customer);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
       String SQL = "DELETE FROM customer WHERE CustID =?";
        try {

            boolean isDeleted = CrudUtil.execute(SQL,txtCustomerId.getText());
            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION,""+txtCustomerId.getText()+": Customer Deleted !!").show();
                loadTable();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,""+txtCustomerId.getText()+": Customer Deleted !!").show();

        }
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String SQL = "SELECT * FROM customer WHERE CustID=?";
        try {

            ResultSet resultSet = CrudUtil.execute(SQL,txtCustomerId.getText());
            resultSet.next();

            setValueToText(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getDouble(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)

            ));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        Customer customer = new Customer(
                txtCustomerId.getText(),
                cmbTitle.getValue(),
                txtName.getText(),
                dateDOB.getValue(),
                Double.parseDouble(txtSalary.getText()),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText()
        );
         String SQL = "Update customer SET CustName=?,CustTitle=?,DOB=?,salary=?,CustAddress=?,City=?,Province=?,PostalCode=? WHERE CustID=?";
        Connection connection = null;
        try {

            boolean isUpdated = CrudUtil.execute(
                    SQL,
                    customer.getName(),
                    customer.getTitle(),
                    customer.getDob(),
                    customer.getSalary(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode(),
                    customer.getId()
                    );

            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Customer updated!").show();
                loadTable();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Customer not updated!").show();

        }


    }
    private void loadTable(){

        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));



        try {

            String SQL = "SELECT * FROM customer";

            ResultSet resultSet = CrudUtil.execute(SQL);

            while (resultSet.next()){
               Customer customer = new Customer(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getDate("DOB").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("CustAddress"),
                        resultSet.getString("City"),
                        resultSet.getString("Province"),
                        resultSet.getString("PostalCode")

                        );
               customerObservableList.add(customer);
            }
            tblCustomers.setItems(customerObservableList);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}

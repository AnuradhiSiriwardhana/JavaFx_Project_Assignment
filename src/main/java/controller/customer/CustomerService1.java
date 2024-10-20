package controller.customer;

import javafx.collections.ObservableList;
import dto.Customer;

public interface CustomerService1 {
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    Customer searchCustomer(String id);
    boolean deleteCustomer(String id);

    ObservableList<Customer>getAllCustomers();

}

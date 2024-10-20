package repository.custom.impl;

import entity.CustomerEntity;
import repository.custom.CustomerDao;

import java.util.List;

public class CustomerDaoimpl implements CustomerDao {
    @Override
    public boolean save(CustomerEntity entity) {
        System.out.println("Repository : "+ entity);
        return false;
    }

    @Override
    public boolean update(CustomerEntity entity, String s) {
        return false;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    @Override
    public List<CustomerEntity> findAll() {
        return List.of();
    }
}

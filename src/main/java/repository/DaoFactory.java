package repository;

import repository.custom.impl.CustomerDaoimpl;
import repository.custom.impl.ItemDaoimpl;
import util.DaoType;

public class DaoFactory {

    private static DaoFactory instance;

    private DaoFactory(){}

    public <T extends SuperDao>T getDaoType(DaoType daoType){
        switch (daoType){
            case CUSTOMER:return (T) new CustomerDaoimpl();
            case ITEM:return (T) new ItemDaoimpl();
        }
        return null;
    }


    public static DaoFactory getInstance(){
        return instance!=null?instance:(instance=new DaoFactory());
    }
}

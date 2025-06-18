package com.example.qlkho.dao;

import com.example.qlkho.entity.OrderDetail;
import com.example.qlkho.entity.XML.OrderDetailXML;
import com.example.qlkho.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailDao {
    private final String FILE_PATH = "src/main/resources/orderDetails.xml";
    private List<OrderDetail> orderDetailList;
    private final ProductDao productDao;
    private final OrderDao orderDao;
    public OrderDetailDao() {
        productDao = new ProductDao();
        orderDao = new OrderDao();
        orderDetailList = readFromXML();
        if (orderDetailList == null) {
            orderDetailList = new ArrayList<>();
        }
        System.out.println(orderDetailList);
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    private List<OrderDetail> readFromXML() {
        List<OrderDetail> result = new ArrayList<>();

        OrderDetailXML orderDetailXML = (OrderDetailXML) FileUtils.readXMLFile(FILE_PATH, OrderDetailXML.class);

        if (orderDetailXML != null) {
            result = orderDetailXML.getOrderDetailList();
        }
        return result;
    }

    private void writeToXML(List<OrderDetail> orderDetailList) {
        OrderDetailXML orderDetailXML = new OrderDetailXML();
        orderDetailXML.setOrderDetailList(orderDetailList);
        FileUtils.writeXMLtoFile(FILE_PATH, orderDetailXML);
    }

    public List<OrderDetail> getProductByOrderId(String orderId) {
        if(orderDetailList != null && !orderDetailList.isEmpty()){
            orderDetailList.clear();
            orderDetailList = readFromXML();
        }

        List<OrderDetail> result = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList) {
            if (orderDetail.getOrderId().equals(orderId)) {
                result.add(orderDetail);
            }
        }
        return result;
    }


    public void add(OrderDetail orderDetail) {
        orderDetailList.forEach(p -> {
            if (p.getProductId().equals(orderDetail.getProductId()) && p.getOrderId().equals(orderDetail.getOrderId())) {
                throw new IllegalArgumentException("Sản phẩm đã tồn tại trong đơn hàng, bạn chỉ có thể cập nhật số lượng");
            }
        });


        orderDetailList.add(orderDetail);
        productDao.decreaseProductQuantity(orderDetail.getProductId(), Integer.parseInt(orderDetail.getProductQuantity()));
        writeToXML(orderDetailList);

        orderDao.getOrders().forEach(p -> {
            if (p.getId().equals(orderDetail.getOrderId())) {
                p.setTotalMoney(String.valueOf(totalMoney(getProductByOrderId(orderDetail.getOrderId()))));
                orderDao.writeXML();
            }
        });
    }

    public void delete(OrderDetail orderDetail) {
        String orderDetailId = orderDetail.getId();
        OrderDetail existingOrderDetail = orderDetailList.stream().filter(p -> p.getId().equals(orderDetailId)).findFirst().orElse(null);
        orderDetailList.remove(existingOrderDetail);

        productDao.increaseProductQuantity(orderDetail.getProductId(), Integer.parseInt(orderDetail.getProductQuantity()));
        writeToXML(orderDetailList);

        orderDao.getOrders().forEach(p -> {
            if (p.getId().equals(orderDetail.getOrderId())) {
                p.setTotalMoney(String.valueOf(totalMoney(getProductByOrderId(orderDetail.getOrderId()))));
                orderDao.writeXML();
            }
        });
    }

    public void update(OrderDetail orderDetail){
        OrderDetail existingOrderDetail = orderDetailList.stream().filter(p -> p.getProductId().equals(orderDetail.getProductId()) && p.getOrderId().equals(orderDetail.getOrderId())).findFirst().orElse(null);
        if (existingOrderDetail == null) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại trong đơn hàng");
        }

        int returnQuantity = Integer.parseInt(existingOrderDetail.getProductQuantity()) - Integer.parseInt(orderDetail.getProductQuantity());
        productDao.increaseProductQuantity(existingOrderDetail.getProductId(), returnQuantity);

        orderDetailList.remove(existingOrderDetail);
        orderDetailList.add(orderDetail);
        writeToXML(orderDetailList);



        orderDao.getOrders().forEach(p -> {
            if (p.getId().equals(orderDetail.getOrderId())) {
                p.setTotalMoney(String.valueOf(totalMoney(getProductByOrderId(orderDetail.getOrderId()))));
                orderDao.writeXML();
            }
        });
    }

    private float totalMoney(List<OrderDetail> orderDetails){
        float total = 0;
        for (OrderDetail orderDetail : orderDetails) {
            total += Float.parseFloat(orderDetail.getProductPrice()) * Integer.parseInt(orderDetail.getProductQuantity());
        }
        return total;
    }


    public void deleteOrderDetailByOrderId(String id) {
        List<OrderDetail> orderDetails = getProductByOrderId(id);
        orderDetails.forEach(p -> {
            productDao.increaseProductQuantity(p.getProductId(), Integer.parseInt(p.getProductQuantity()));
            orderDetailList.remove(p);
        });
        writeToXML(orderDetailList);
    }

    public List<String> getAddedProductIds(String orderId) {
        List<String> addedProductIds = new ArrayList<>();
        List<OrderDetail> details = getProductByOrderId(orderId);
        
        for (OrderDetail detail : details) {
            addedProductIds.add(detail.getProductId());
        }
        
        return addedProductIds;
    }
}

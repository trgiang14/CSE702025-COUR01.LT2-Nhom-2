package com.example.qlkho.dao;

import com.example.qlkho.entity.ImportOrder;
import com.example.qlkho.entity.ImportOrderDetail;
import com.example.qlkho.entity.XML.ImportOrderDetailXML;
import com.example.qlkho.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImportOrderDetailDao {
    private final String FILE_PATH = "src/main/resources/importOrderDetails.xml";

    private List<ImportOrderDetail> importOrderDetails;

    public ImportOrderDetailDao() {
        importOrderDetails = readXML();
        if (importOrderDetails == null) {
            importOrderDetails = new ArrayList<>();
        }
    }

    public List<ImportOrderDetail> getImportOrderDetails() {
        if (importOrderDetails != null && !importOrderDetails.isEmpty()) {
            importOrderDetails.clear();
            importOrderDetails = readXML();
        }

        return importOrderDetails;
    }

    private List<ImportOrderDetail> readXML() {
        List<ImportOrderDetail> result = new ArrayList<>();

        ImportOrderDetailXML importOrderDetailXML = (ImportOrderDetailXML) FileUtils.readXMLFile(FILE_PATH, ImportOrderDetailXML.class);

        if (importOrderDetailXML != null) {
            result = importOrderDetailXML.getImportOrderDetailList();
        }

        return result;
    }

    public void writeXML() {
        ImportOrderDetailXML importOrderDetailXML = new ImportOrderDetailXML();
        importOrderDetailXML.setImportOrderDetailList(importOrderDetails);
        FileUtils.writeXMLtoFile(FILE_PATH, importOrderDetailXML);
    }

    private boolean isImportOrderDetailExist(ImportOrderDetail importOrderDetail) {
        return importOrderDetails.stream().anyMatch(od -> Objects.equals(od.getId(), importOrderDetail.getId()));
    }

    public void addImportOrderDetail(ImportOrderDetail importOrderDetail) {
        if (isImportOrderDetailExist(importOrderDetail)) {
            throw new IllegalArgumentException("Mã chi tiết đơn nhập hàng đã tồn tại!");
        }

        importOrderDetails.add(importOrderDetail);
        writeXML();
    }

    public void updateImportOrderDetail(ImportOrderDetail importOrderDetail) {
        for (ImportOrderDetail od : importOrderDetails) {
            if (Objects.equals(od.getId(), importOrderDetail.getId())) {
                importOrderDetails.set(importOrderDetails.indexOf(od), importOrderDetail);
            }
        }

        writeXML();
    }

    public void deleteImportOrderDetail(ImportOrderDetail importOrderDetail) {
        importOrderDetails.removeIf(od -> Objects.equals(od.getId(), importOrderDetail.getId()));
        writeXML();
    }

    public void deleteImportOrderDetailByImportOrderId(String importOrderId) {
        importOrderDetails.removeIf(od -> Objects.equals(od.getImportOrderId(), importOrderId));
        writeXML();
    }

    public List<ImportOrderDetail> getProductByImportOrderId(String importOrderId) {
        List<ImportOrderDetail> result = new ArrayList<>();

        for (ImportOrderDetail od : importOrderDetails) {
            if (Objects.equals(od.getImportOrderId(), importOrderId)) {
                result.add(od);
            }
        }

        return result;
    }

    public void calculateTotalMoney(String importOrderId) {
        ImportOrderDao importOrderDao = new ImportOrderDao();
        List<ImportOrderDetail> list = getProductByImportOrderId(importOrderId);

        double totalMoney = 0;

        for (ImportOrderDetail od : list) {
            totalMoney += Double.parseDouble(od.getTotalPrice());
        }

        List<ImportOrder> importOrders = importOrderDao.getImportOrders();
        for (ImportOrder importOrder : importOrders) {
            if (Objects.equals(importOrder.getId(), importOrderId)) {
                importOrder.setTotalMoney(String.valueOf(totalMoney));
                importOrderDao.updateImportOrder(importOrder);
                break;
            }
        }
    }

    public List<String> getAddedProductIds(String importOrderId) {
        List<String> addedProductIds = new ArrayList<>();
        List<ImportOrderDetail> details = getProductByImportOrderId(importOrderId);
        
        for (ImportOrderDetail detail : details) {
            addedProductIds.add(detail.getProductId());
        }
        
        return addedProductIds;
    }
} 
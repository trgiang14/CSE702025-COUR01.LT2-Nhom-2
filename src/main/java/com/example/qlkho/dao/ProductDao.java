package com.example.qlkho.dao;

import com.example.qlkho.entity.Product;
import com.example.qlkho.entity.XML.ProductXML;
import com.example.qlkho.utils.FileUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductDao {
    private final String FILE_PATH = "src/main/resources/products.xml";

    private List<Product> products;

    public ProductDao() {
        products = readFromXML();
        if (products == null) {
            products = new ArrayList<>();
        }

    }

    public List<Product> getProducts() {
        products.sort(Comparator.comparing(Product::getId));
        return products;
    }

    private boolean checkExpiredDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate orderDate = LocalDate.parse(date, formatter);

            LocalDate currentDate = LocalDate.now();

            return orderDate.isAfter(currentDate);
        } catch (Exception e) {
            System.out.println("Invalid date format: " + e.getMessage());
            return false;
        }

    }

    private List<Product> readFromXML() {
        List<Product> result = new ArrayList<>();

        ProductXML productXML = (ProductXML) FileUtils.readXMLFile(FILE_PATH, ProductXML.class);

        if (productXML != null) {
            result = productXML.getProductList();
        }
        return result;
    }

    private void writeToXML() {
        ProductXML productXML = new ProductXML();
        productXML.setProductList(products);
        FileUtils.writeXMLtoFile(FILE_PATH, productXML);
    }

    public void addProduct(Product product) throws IllegalArgumentException {
        if (!checkExpiredDate(product.getProductExpireDate())) {
            throw new IllegalArgumentException("Hạn sử dụng phải lơn hơn ngày hiện tại.");
        }
        products.add(product);
        writeToXML();
    }

    public void updateProduct(String oldId, Product newProduct) throws IllegalArgumentException {
        Product existingProduct = getProductById(oldId);
        if (!checkExpiredDate(newProduct.getProductExpireDate())) {
            throw new IllegalArgumentException("Hạn sử dụng phải lơn hơn ngày hiện tại.");
        }
        if (existingProduct == null) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại");
        }
        if (!oldId.equals(newProduct.getId()) && getProductById(newProduct.getId()) != null) {
            throw new IllegalArgumentException("Mã sản phẩm đã tồn tại.");
        }
        products.remove(existingProduct);
        products.add(newProduct);
        writeToXML();
    }

    public void deleteProduct(Product id) throws IllegalArgumentException {
        Product product = getProductById(id.getId());
        if (product == null) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại.");
        }
        products.remove(product);
        writeToXML();
    }

    public List<String> getListByType(String type) {
        List<String> result = new ArrayList<>();

        switch (type) {
            case "category":
                for (Product product : products) {
                    if (!result.contains(product.getProductCategory())) {
                        result.add(product.getProductCategory());
                    }
                }
                break;
            case "supplier":
                for (Product product : products) {
                    if (!result.contains(product.getProductSupplier())) {
                        result.add(product.getProductSupplier());
                    }
                }
                break;
            case "none":
                result.clear();
                break;
        }

        return result;
    }

    public List<Product> getProductsById(String id) {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getId().toLowerCase().trim().contains(id)) {
                result.add(product);
            }
        }
        return result;

    }


    public List<Product> getProductBy(String keyword) {
        List<Product> result = new ArrayList<>();

        for (Product product : products) {
            if (product.getProductCategory().equals(keyword) || product.getProductSupplier().equals(keyword)) {
                result.add(product);
            }
        }

        return result;
    }

    public void decreaseProductQuantity(String id, int quantity) throws IllegalArgumentException {
        Product product = getProductById(id);
        if (product == null) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại.");
        }
        if (Integer.parseInt(product.getQuantity()) < quantity) {
            throw new IllegalArgumentException("Số lượng sản phẩm không đủ.");
        }
        product.setQuantity(String.valueOf(Integer.parseInt(product.getQuantity()) - quantity));
        writeToXML();
    }

    public void increaseProductQuantity(String id, int quantity) throws IllegalArgumentException {
        Product product = getProductById(id);
        if (product == null) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại.");
        }
        product.setQuantity(String.valueOf(Integer.parseInt(product.getQuantity()) + quantity));
        writeToXML();
    }

    public List<String> getAllProductsId() {
        List<String> result = new ArrayList<>();

        for (Product product : products) {
            result.add(product.getId());
        }
        return result;
    }

    public Product getProductById(String id) {
        if (products != null) {
            products.clear();
        }

        products = readFromXML();
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }


}

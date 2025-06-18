package com.example.qlkho.entity.XML;

import com.example.qlkho.entity.ImportOrder;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "importOrders")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportOrderXML {
    @XmlElement(name = "importOrder")
    private List<ImportOrder> importOrderList;

    public List<ImportOrder> getImportOrderList() {
        return importOrderList;
    }

    public void setImportOrderList(List<ImportOrder> importOrderList) {
        this.importOrderList = importOrderList;
    }
} 
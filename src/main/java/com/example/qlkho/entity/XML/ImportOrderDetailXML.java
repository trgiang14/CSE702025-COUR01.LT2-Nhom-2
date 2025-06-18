package com.example.qlkho.entity.XML;

import com.example.qlkho.entity.ImportOrderDetail;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "importOrderDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportOrderDetailXML {
    @XmlElement(name = "importOrderDetail")
    private List<ImportOrderDetail> importOrderDetailList;

    public List<ImportOrderDetail> getImportOrderDetailList() {
        return importOrderDetailList;
    }

    public void setImportOrderDetailList(List<ImportOrderDetail> importOrderDetailList) {
        this.importOrderDetailList = importOrderDetailList;
    }
} 
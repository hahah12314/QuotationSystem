package com.wanshu.quotation.dto;

import com.wanshu.cost.entity.*;
import com.wanshu.quotation.entity.Company;
import com.wanshu.quotation.entity.Customer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuotationFormDto {
    private Company company;
    private Customer customer;
    private Material material;
    private Integer quantity;
    private Integer auditStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long quotationId;

    @Data
    public static class Company  {
        private String name;
        private String responsiblePerson;
        private String contactInfo;
    }

    @Data
    public static class Customer  {
        private String name;
        private String contactInfo;
        private String email;
        private double minPrice;
        private double maxPrice;
        private String requirements;
        private String address;
        private String paymentMethod;
    }

    @Data
    public static class Material {
        private Integer quotedMaterialId;
        private String type;
        private Integer historyMaterialId;
        private CustomDetails customDetails;
        private HistoryDetails historyDetails;
        private QuotedMaterials quotedMaterials;
    }

    @Data
    public static class CustomDetails {
        private RawMaterials rawMaterials;
        private PaintingCost paintingCost;
        private MaterialCost materialCost;
        private CuttingCost cuttingCost;
        private ProcessingCost processingCost;
        private SurfaceTreatment surfaceTreatment;
    }


    @Data
    public static class QuotedMaterials {
        private String materialCode;
        private String materialName;
        private double unitPrice;
        private int quantity;
        private String specification;
        private int isExternalProcurement;
    }

    @Data
    public static class HistoryDetails {
        private RawMaterials rawMaterials;
        private PaintingCost paintingCost;
        private MaterialCost materialCost;
        private CuttingCost cuttingCost;
        private ProcessingCost processingCost;
        private SurfaceTreatment surfaceTreatment;
    }
}

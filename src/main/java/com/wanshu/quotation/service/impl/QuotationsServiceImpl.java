package com.wanshu.quotation.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanshu.common.util.PageUtils;
import com.wanshu.cost.dto.RawMaterialQueryDto;
import com.wanshu.cost.entity.*;
import com.wanshu.cost.mapper.*;
import com.wanshu.quotation.dto.QuotationFormDto;
import com.wanshu.quotation.dto.QuotationQueryDto;
import com.wanshu.quotation.entity.Company;
import com.wanshu.quotation.entity.Customer;
import com.wanshu.quotation.entity.Quotations;
import com.wanshu.quotation.mapper.CompanyMapper;
import com.wanshu.quotation.mapper.CustomerMapper;
import com.wanshu.quotation.mapper.QuotationsMapper;
import com.wanshu.quotation.service.IQuotationsService;
import com.wanshu.quotation.vo.RawMaterialQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 报价单表 服务实现类
 * </p>
 *
 * @author hehe
 * @since 2024-10-13
 */
@Service
public class QuotationsServiceImpl extends ServiceImpl<QuotationsMapper, Quotations> implements IQuotationsService {

    @Autowired
    private PaintingCostMapper paintingCostMapper;
    @Autowired
    private RawMaterialsMapper rawMaterialsMapper;
    @Autowired
    private ProcessingCostMapper processingCostMapper;
    @Autowired
    private SurfaceTreatmentMapper surfaceTreatmentMapper;
    @Autowired
    private CuttingCostMapper cuttingCostMapper;
    @Autowired
    private MaterialCostMapper materialCostMapper;
    @Autowired
    private MaterialsMapper materialsMapper;
    @Autowired
    private QuotationsMapper quotationsMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    @Transactional
    public PageUtils queryPageQuotation(QuotationQueryDto quotationQueryDto) {
        QueryWrapper<Quotations> wrapper = new QueryWrapper<>();
//        wrapper.eq("audit_status", 1);

        Page<Quotations> page = new Page<>(quotationQueryDto.getPageIndex(), quotationQueryDto.getPageSize());
        Page<Quotations> quotationPage = quotationsMapper.selectPage(page, wrapper);

        List<QuotationFormDto> result = new ArrayList<>();
        for (Quotations quotation : quotationPage.getRecords()) {
            QuotationFormDto quotationFormDto = new QuotationFormDto();
            quotationFormDto.setQuotationId(quotation.getId());
            // 设置公司信息
            Company company = companyMapper.selectById(quotation.getCompanyId());
            QuotationFormDto.Company companyDto = new QuotationFormDto.Company();
            BeanUtils.copyProperties(company, companyDto);
            quotationFormDto.setCompany(companyDto);

            // 设置客户信息
            Customer customer = customerMapper.selectById(quotation.getCustomerId());
            QuotationFormDto.Customer customerDto = new QuotationFormDto.Customer();
            BeanUtils.copyProperties(customer, customerDto);
            quotationFormDto.setCustomer(customerDto);

            // 设置材料信息
            QuotationFormDto.Material materialDto = new QuotationFormDto.Material();
            if (quotation.getMaterialId() != null) {
                materialDto.setQuotedMaterialId(quotation.getMaterialId());
                // 获取 QuotedMaterials 信息
                QuotationFormDto.QuotedMaterials quotedMaterials = new QuotationFormDto.QuotedMaterials();
                // 这里假设有一个方法可以获取 QuotedMaterials 信息
                Materials materials = materialsMapper.selectById(quotation.getMaterialId());

                BeanUtils.copyProperties(materials, quotedMaterials);
                // 获取 RawMaterials 信息
                RawMaterials rawMaterials = rawMaterialsMapper.selectById(quotation.getRawMaterialId());

                quotedMaterials.setQuantity(quotation.getQuantity());
                quotedMaterials.setSpecification(rawMaterials.getSpecification());
                materialDto.setQuotedMaterials(quotedMaterials);
            }


            if (quotation.getRawMaterialId() != null) {
                materialDto.setType("history");
                materialDto.setHistoryMaterialId(quotation.getRawMaterialId());

                // 获取 CustomDetails 信息
                QuotationFormDto.CustomDetails customDetails = new QuotationFormDto.CustomDetails();

                // 获取 RawMaterials 信息
                RawMaterials rawMaterials = rawMaterialsMapper.selectById(quotation.getRawMaterialId());

                customDetails.setRawMaterials(rawMaterials);

                // 获取 PaintingCost 信息
                PaintingCost paintingCost = paintingCostMapper.selectByDetailId(rawMaterials.getDetailId());

                customDetails.setPaintingCost(paintingCost);

                // 获取 MaterialCost 信息
                MaterialCost materialCost = materialCostMapper.selectByDetailId(rawMaterials.getDetailId());

                customDetails.setMaterialCost(materialCost);

                // 获取 CuttingCost 信息
                CuttingCost cuttingCost = cuttingCostMapper.selectByDetailId(rawMaterials.getDetailId());

                customDetails.setCuttingCost(cuttingCost);

                // 获取 ProcessingCost 信息
                ProcessingCost processingCost = processingCostMapper.selectByDetailId(rawMaterials.getDetailId());

                customDetails.setProcessingCost(processingCost);

                // 获取 SurfaceTreatment 信息
                SurfaceTreatment surfaceTreatment = surfaceTreatmentMapper.selectByDetailId(rawMaterials.getDetailId());
                customDetails.setSurfaceTreatment(surfaceTreatment);

                materialDto.setCustomDetails(customDetails);
            }

            quotationFormDto.setAuditOpinion(quotation.getAuditOpinion());

            quotationFormDto.setMaterial(materialDto);
            quotationFormDto.setAuditStatus(quotation.getAuditStatus());
            quotationFormDto.setCreateTime(quotation.getCreateTime());
            quotationFormDto.setUpdateTime(quotation.getUpdateTime());

            result.add(quotationFormDto);
        }
        System.out.println("审核的"+result);


        return new PageUtils(result, (int) quotationPage.getTotal(), quotationQueryDto.getPageSize(), quotationQueryDto.getPageIndex());
    }

    @Override
    @Transactional
    public RawMaterialQueryVo queryRawMaterial(RawMaterialQueryDto queryDto) {
        QueryWrapper<RawMaterials> wrapper = new QueryWrapper<>();
        if (StringUtils.isNoneEmpty(queryDto.getSpecification())) {
            wrapper.like("specification", queryDto.getSpecification());
        }
        RawMaterials rawMaterials = rawMaterialsMapper.selectOne(wrapper);
        Integer detailId = rawMaterials.getDetailId();
        System.out.println(detailId);
        MaterialCost materialCost = materialCostMapper.selectById(detailId);
        CuttingCost cuttingCost = cuttingCostMapper.selectById(detailId);
        ProcessingCost processingCost = processingCostMapper.selectById(detailId);
        SurfaceTreatment surfaceTreatment = surfaceTreatmentMapper.selectById(detailId);
        PaintingCost paintingCost = paintingCostMapper.selectById(detailId);
        RawMaterialQueryVo rawMaterialQueryVo = new RawMaterialQueryVo();
        List<MaterialCost> materialCostList = materialCostMapper.selectList(
                new QueryWrapper<MaterialCost>()
                        .in("detail_id", detailId)
        );
        materialCost = materialCostList.get(0);
        if (rawMaterials != null) {
            BeanUtils.copyProperties(rawMaterials, rawMaterialQueryVo);
        }
        if (materialCost != null) {
            BeanUtils.copyProperties(materialCost, rawMaterialQueryVo);
        }
        if (cuttingCost != null) {
            BeanUtils.copyProperties(cuttingCost, rawMaterialQueryVo);
        }
        if (processingCost != null) {
            BeanUtils.copyProperties(processingCost, rawMaterialQueryVo);
        }
        if (surfaceTreatment != null) {
            BeanUtils.copyProperties(surfaceTreatment, rawMaterialQueryVo);
        }
        if (paintingCost != null) {
            BeanUtils.copyProperties(paintingCost, rawMaterialQueryVo);
        }
        System.out.println(rawMaterialQueryVo);

        return rawMaterialQueryVo;
    }

    @Override
    @Transactional
    public void saveQuotation(QuotationFormDto quotationForm) {
// 保存公司信息
        Company company = new Company();
        BeanUtils.copyProperties(quotationForm.getCompany(), company);
        companyMapper.insert(company);
        Quotations quotations = new Quotations();
        quotations.setCompanyId(company.getId());
        Customer customer = new Customer();
        BeanUtils.copyProperties(quotationForm.getCustomer(), customer);
        customerMapper.insert(customer);
        quotations.setCustomerId(customer.getId());
        if (quotationForm.getMaterial().getQuotedMaterialId() != null) {
            quotations.setMaterialId(quotationForm.getMaterial().getQuotedMaterialId());
            if (quotationForm.getMaterial().getQuotedMaterials() != null) {
                quotations.setQuantity(quotationForm.getMaterial().getQuotedMaterials().getQuantity());
            }
        }
        System.out.println("aaa");


        // 保存原材料信息
        if (Objects.equals(quotationForm.getMaterial().getType(), "custom") && quotationForm.getMaterial().getCustomDetails() != null && quotationForm.getMaterial().getCustomDetails().getRawMaterials() != null) {
            RawMaterials rawMaterials = new RawMaterials();
            BeanUtils.copyProperties(quotationForm.getMaterial().getCustomDetails().getRawMaterials(), rawMaterials);
            rawMaterials.setDetailId(null);
            rawMaterialsMapper.insert(rawMaterials);
            Integer detailId = rawMaterials.getDetailId();
            quotations.setRawMaterialId(detailId);
            // 保存喷漆成本信息
            if (quotationForm.getMaterial().getCustomDetails().getPaintingCost() != null) {
                PaintingCost paintingCost = new PaintingCost();
                BeanUtils.copyProperties(quotationForm.getMaterial().getCustomDetails().getPaintingCost(), paintingCost);
                paintingCost.setId(null);
                paintingCost.setDetailId(detailId);
                paintingCostMapper.insert(paintingCost);

            }

            // 保存材料成本信息
            if (quotationForm.getMaterial().getCustomDetails().getMaterialCost() != null) {
                MaterialCost materialCost = new MaterialCost();
                BeanUtils.copyProperties(quotationForm.getMaterial().getCustomDetails().getMaterialCost(), materialCost);
                materialCost.setDetailId(detailId);
                materialCost.setId(null);
                materialCostMapper.insert(materialCost);
            }

            // 保存切割成本信息
            if (quotationForm.getMaterial().getCustomDetails().getCuttingCost() != null) {
                CuttingCost cuttingCost = new CuttingCost();
                BeanUtils.copyProperties(quotationForm.getMaterial().getCustomDetails().getCuttingCost(), cuttingCost);
                cuttingCost.setDetailId(detailId);
                cuttingCost.setId(null);
                cuttingCostMapper.insert(cuttingCost);
            }

            // 保存加工成本信息
            if (quotationForm.getMaterial().getCustomDetails().getProcessingCost() != null) {
                ProcessingCost processingCost = new ProcessingCost();
                BeanUtils.copyProperties(quotationForm.getMaterial().getCustomDetails().getProcessingCost(), processingCost);
                processingCost.setDetailId(detailId);
                processingCost.setId(null);
                processingCostMapper.insert(processingCost);
            }

            // 保存表面处理信息
            if (quotationForm.getMaterial().getCustomDetails().getSurfaceTreatment() != null) {
                SurfaceTreatment surfaceTreatment = new SurfaceTreatment();
                BeanUtils.copyProperties(quotationForm.getMaterial().getCustomDetails().getSurfaceTreatment(), surfaceTreatment);
                surfaceTreatment.setDetailId(detailId);
                surfaceTreatment.setId(null);
                surfaceTreatmentMapper.insert(surfaceTreatment);
            }

        } else if (Objects.equals(quotationForm.getMaterial().getType(), "history") ) {
            if (quotationForm.getMaterial().getHistoryMaterialId() != null) {
                quotations.setRawMaterialId(quotationForm.getMaterial().getHistoryMaterialId());
            }
            quotations.setRawMaterialId(quotationForm.getMaterial().getHistoryMaterialId());
        }

        quotations.setAuditStatus(0);

        quotations.setCreateTime(LocalDateTime.now());
        quotations.setUpdateTime(LocalDateTime.now());
        quotationsMapper.insert(quotations);

    }

    @Override
    @Transactional
    public List<QuotationFormDto> queryAuditPageQuotation() {
        QueryWrapper<Quotations> wrapper = new QueryWrapper<>();
        wrapper.in("audit_status", Arrays.asList(0, -1));

        List<Quotations> quotations = quotationsMapper.selectList(wrapper);

        List<QuotationFormDto> result = new ArrayList<>();
        for (Quotations quotation : quotations) {
            QuotationFormDto quotationFormDto = new QuotationFormDto();
            quotationFormDto.setQuotationId(quotation.getId());
            // 设置公司信息
            Company company = companyMapper.selectById(quotation.getCompanyId());
            QuotationFormDto.Company companyDto = new QuotationFormDto.Company();
            BeanUtils.copyProperties(company, companyDto);
            quotationFormDto.setCompany(companyDto);

            // 设置客户信息
            Customer customer = customerMapper.selectById(quotation.getCustomerId());
            QuotationFormDto.Customer customerDto = new QuotationFormDto.Customer();
            BeanUtils.copyProperties(customer, customerDto);
            quotationFormDto.setCustomer(customerDto);

            // 设置材料信息
            QuotationFormDto.Material materialDto = new QuotationFormDto.Material();
            if (quotation.getMaterialId() != null) {
                materialDto.setQuotedMaterialId(quotation.getMaterialId());
                // 获取 QuotedMaterials 信息
                QuotationFormDto.QuotedMaterials quotedMaterials = new QuotationFormDto.QuotedMaterials();
                // 这里假设有一个方法可以获取 QuotedMaterials 信息
                // quotedMaterials = getQuotedMaterialsById(quotation.getMaterialId());
                materialDto.setQuotedMaterials(quotedMaterials);
                quotedMaterials.setQuantity(quotation.getQuantity());
            }

            if (quotation.getRawMaterialId() != null) {
                materialDto.setType("history");
                materialDto.setHistoryMaterialId(quotation.getRawMaterialId());

                // 获取 CustomDetails 信息
                QuotationFormDto.CustomDetails customDetails = new QuotationFormDto.CustomDetails();

                // 获取 RawMaterials 信息
                RawMaterials rawMaterials = rawMaterialsMapper.selectById(quotation.getRawMaterialId());

                customDetails.setRawMaterials(rawMaterials);

                // 获取 PaintingCost 信息
                PaintingCost paintingCost = paintingCostMapper.selectByDetailId(rawMaterials.getDetailId());

                customDetails.setPaintingCost(paintingCost);

                // 获取 MaterialCost 信息
                MaterialCost materialCost = materialCostMapper.selectByDetailId(rawMaterials.getDetailId());

                customDetails.setMaterialCost(materialCost);

                // 获取 CuttingCost 信息
                CuttingCost cuttingCost = cuttingCostMapper.selectByDetailId(rawMaterials.getDetailId());

                customDetails.setCuttingCost(cuttingCost);

                // 获取 ProcessingCost 信息
                ProcessingCost processingCost = processingCostMapper.selectByDetailId(rawMaterials.getDetailId());

                customDetails.setProcessingCost(processingCost);

                // 获取 SurfaceTreatment 信息
                SurfaceTreatment surfaceTreatment = surfaceTreatmentMapper.selectByDetailId(rawMaterials.getDetailId());
                customDetails.setSurfaceTreatment(surfaceTreatment);

                materialDto.setCustomDetails(customDetails);
            }
            quotationFormDto.setAuditOpinion(quotation.getAuditOpinion());

            quotationFormDto.setMaterial(materialDto);
            quotationFormDto.setAuditStatus(quotation.getAuditStatus());
            quotationFormDto.setCreateTime(quotation.getCreateTime());
            quotationFormDto.setUpdateTime(quotation.getUpdateTime());

            result.add(quotationFormDto);
        }
        System.out.println(result);

        return result;
    }

    @Override
    public void auditQuotation(Long quotationId) {
        QueryWrapper<Quotations> wrapper=new QueryWrapper<>();
        wrapper.eq("id",quotationId);
        Quotations quotations = quotationsMapper.selectOne(wrapper);
        quotations.setAuditStatus(1);
        quotationsMapper.updateById(quotations);
    }

    @Override
    public String deleteQuotation(int id) {
        QueryWrapper<Quotations> wrapper=new QueryWrapper<>();
        wrapper.eq("id",id);

        quotationsMapper.delete(wrapper);

        return "success";
    }

    @Override
    @Transactional
    public boolean updateQuotation(QuotationFormDto quotationFormDto) {
        QueryWrapper<Quotations> wrapper=new QueryWrapper<>();
        wrapper.eq("id",quotationFormDto.getQuotationId());
        Quotations quotations =quotationsMapper.selectOne(wrapper);
        QueryWrapper<Company> wrapper1=new QueryWrapper<>();
        wrapper1.eq("id",quotations.getCompanyId());
        QueryWrapper<Customer> wrapper2=new QueryWrapper<>();
        wrapper2.eq("id",quotations.getCustomerId());
        Company company = new Company();
        BeanUtils.copyProperties(quotationFormDto.getCompany(),company);
        company.setId(quotations.getCompanyId());
        Customer customer = new Customer();
        BeanUtils.copyProperties(quotationFormDto.getCustomer(),customer);
        customer.setId(quotations.getCustomerId());
        companyMapper.update(company,wrapper1);
        customerMapper.update(customer,wrapper2);
        quotations.setAuditOpinion(quotationFormDto.getAuditOpinion());
        quotations.setUpdateTime(LocalDateTime.now());


        BeanUtils.copyProperties(quotationFormDto,quotations);
        quotations.setAuditStatus(quotationFormDto.getAuditStatus());
        quotationsMapper.update(quotations,wrapper);
        return true;

    }


}


package org.hackathon.aidtracker.mybatis.service.impl;

import org.hackathon.aidtracker.mybatis.been.DemanBeenDetail;
import org.hackathon.aidtracker.mybatis.been.DemandBeen;
import org.hackathon.aidtracker.mybatis.been.DonateSuppliesStatusBeen;
import org.hackathon.aidtracker.mybatis.mapper.SupplierMapper;
import org.hackathon.aidtracker.mybatis.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 功能描述:捐赠方service实现层
 *
 * @author 许小狼
 * @date 2020/3/7 1:07 下午
 */
@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    SupplierMapper supplierMapper;

    //获取需求列表总数
    @Override
    public int quarryDemandtotalRows() {
        return supplierMapper.quarryDemandtotalRows();
    }

    //获取需求列表
    @Override
    public List<DemandBeen> quarryDemandList(Map<String, Object> pages) {
        return supplierMapper.quarryDemandList(pages);
    }

    //获取需求列表详情页
    @Override
    public DemanBeenDetail quarryDemanDetail(String demandId) {
        return supplierMapper.quarryDemanDetail(demandId);
    }

    //提交捐赠物资表
    @Override
    public int subDonateSupplies(DonateSuppliesStatusBeen donSupStat) {
        Map<String,Object> subDonSupMap = new HashMap<String,Object>();
        subDonSupMap.put("donSupStaId",donSupStat.getDonSupStaId());
        subDonSupMap.put("donateResName",donSupStat.getDonateResName());
        subDonSupMap.put("donateResType",donSupStat.getDonateResType());
        subDonSupMap.put("donateResManufacturer",donSupStat.getDonateResManufacturer());
        subDonSupMap.put("donateResNum",donSupStat.getDonateResNum());
        subDonSupMap.put("donateResStatus",donSupStat.getDonateResStatus());
        subDonSupMap.put("donateResShipMth",donSupStat.getDonateResShipMth());
        subDonSupMap.put("donateResPro",donSupStat.getDonateResPro());
        subDonSupMap.put("donateResCity",donSupStat.getDonateResCity());
        subDonSupMap.put("donaterName",donSupStat.getDonaterName());
        subDonSupMap.put("donaterWxId",donSupStat.getDonaterWxId());
        subDonSupMap.put("donaterQqId",donSupStat.getDonaterQqId());
        subDonSupMap.put("donaterPhone",donSupStat.getDonaterPhone());
        subDonSupMap.put("donaterEmail",donSupStat.getDonaterEmail());
        subDonSupMap.put("donaterNote",donSupStat.getDonaterNote());
        subDonSupMap.put("demandId",donSupStat.getDemandId());
        subDonSupMap.put("donateSupSta",0);

        int leftNum = supplierMapper.quarryLiftNum(donSupStat.getDemandId());

        Map<String,Object> upDateDemLeftNumMap = new HashMap<String,Object>();

        if(leftNum >=  Integer.parseInt(donSupStat.getDonateResNum())){
            int newLeftNum = leftNum - Integer.parseInt(donSupStat.getDonateResNum());
            upDateDemLeftNumMap.put("donateResNum",newLeftNum);
            upDateDemLeftNumMap.put("demandId",donSupStat.getDemandId());
        } else {
            return 4;
        }





        int i = supplierMapper.subDonateSupplies(subDonSupMap);
        if(i == 1){
            int j = supplierMapper.upDateDemLeftNum(upDateDemLeftNumMap);
            if (j == 1){
                return 1;
            } else {
                return 3;
            }
        } else {
            return 2;
        }
    }
}

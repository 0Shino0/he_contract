package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.fabric.FabricUtils;
import com.ruoyi.system.service.IFabricService;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Network;

import javax.annotation.Resource;

/**
 * @author Henriport
 * @since 2021/11/15
 */
public class IFabricServiceImpl implements IFabricService {
    @Resource
    FabricUtils fabricUtils;

    private Contract contract = fabricUtils.getContract();

    private Network network = fabricUtils.getNetwork();

    public byte[] getContractById(String id) throws ContractException {
        return contract.evaluateTransaction("getContractById",id);
    }

}

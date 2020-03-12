package org.hackathon.aidtracker.system.service;

import org.hackathon.aidtracker.util.Encrypt;
import org.hackathon.aidtracker.system.dao.SysUserRepo;
import org.hackathon.aidtracker.system.entity.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class SysUserService {

    private SysUserRepo sysUserRepo;
    private Logger logger = LoggerFactory.getLogger(SysUserService.class);

    @Autowired
    public SysUserService(SysUserRepo sysUserRepo){
        this.sysUserRepo=sysUserRepo;
    }
    public SysUser fill(String baseToken,SysUser sysUser){
        try {
            String decode = Encrypt.ins().decode(baseToken);
            long before = new Date(Long.parseLong(decode)).getTime();
            long now = new Date().getTime();
            if(before>now){
                logger.info("invalid base token!");
                return null;
            }
        } catch (NumberFormatException e) {
            logger.info("illegal attempt access of register interface");
            e.printStackTrace();
            return null;
        }
        Optional<SysUser> byId = sysUserRepo.findById(sysUser.getId());
        if(byId.isPresent()){
            SysUser user = byId.get();
            sysUser.setNickName(user.getNickName());
            sysUser.setAvatarUrl(user.getAvatarUrl());
            sysUser.setOpenId(user.getOpenId());
            return sysUserRepo.save(sysUser);
        }
        return null;
    }
}

package com.jbit.service.Provider;

import com.jbit.mapper.SmbmsProviderMapper;
import com.jbit.pojo.SmbmsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private SmbmsProviderMapper smbmsProviderMapper;

    @Override
    public int add(SmbmsProvider provider) {
        return smbmsProviderMapper.add(provider);
    }

    @Override
    public List<SmbmsProvider> getProvideLike(Map map) {
        return smbmsProviderMapper.getProvideLike(map);
    }

    @Override
    public List<SmbmsProvider> getProviderList(Map map) {
        return smbmsProviderMapper.getProviderList(map);
    }

    @Override
    public int deleteProviderById(String delId) {
        return smbmsProviderMapper.deleteProviderById(delId);
    }

    @Override
    public int modify(SmbmsProvider provider) {
        return smbmsProviderMapper.modify(provider);
    }
}

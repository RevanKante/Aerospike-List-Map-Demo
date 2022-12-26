package com.micronaut.configuration;


import com.aerospike.client.AerospikeClient;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.mapper.tools.AeroMapper;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;


@Singleton
public class AeroMapperConfiguration {

    @Inject
    AerospikeConfiguration aerospikeConfiguration;

    AeroMapper mapper = null;

    AerospikeClient client = null;

    @PostConstruct
    public void aerospikeClient() {
        ClientPolicy policy = new ClientPolicy();
        policy.writePolicyDefault.sendKey = true;


        client = new AerospikeClient(policy, aerospikeConfiguration.getHost(), aerospikeConfiguration.getPort());
        mapper = new AeroMapper.Builder(client).build();
    }

    public AeroMapper getMapper() {
        return this.mapper;
    }

    public AerospikeClient getClient() {
        return this.client;
    }
}

package com.yufu.yepshop.common;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Properties;

/**
 * @author wang
 * @date 2022/1/6 0:30
 */
@Service
@Component
public class GenerateId implements IdentifierGenerator, Configurable {

    public SnowflakeIdWorker snowFlakeIdWorker;

    public String workid = "1";

    public String dataid = "1";

    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {
        snowFlakeIdWorker = new SnowflakeIdWorker(Long.parseLong(workid), Long.parseLong(dataid));
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        Long id = snowFlakeIdWorker.nextId();
        if (id != null) {
            return id;
        } else {
            return null;
        }
    }
}

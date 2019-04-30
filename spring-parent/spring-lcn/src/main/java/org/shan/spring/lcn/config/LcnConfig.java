package org.shan.spring.lcn.config;

import com.codingapi.tx.config.service.TxManagerTxUrlService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by amanda.shan on 2018/12/21.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages={"com.codingapi.tx.*"})
public class LcnConfig  implements TxManagerTxUrlService {

    @Value("${lcn.tm.url}")
    private String url;

    @Override
    public String getTxUrl() {
        return url;
    }
}

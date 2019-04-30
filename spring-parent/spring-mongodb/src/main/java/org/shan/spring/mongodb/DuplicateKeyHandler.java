package org.shan.spring.mongodb;

import java.util.List;

/**
 * Created by amanda.shan on 2019/1/31.
 */
public interface DuplicateKeyHandler {

    void process(List<String> keys);
}

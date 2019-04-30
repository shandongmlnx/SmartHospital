package org.shan.spring.base;


import com.mlnx.common.entity.BaseExceptionMsg;
import com.mlnx.common.entity.Response;
import com.mlnx.common.entity.ResponseData;
import com.mlnx.common.utils.MyLog;

/**
 * Created by amanda.shan on 2017/2/28.
 */
public class BaseController {

    protected MyLog logger = MyLog.getLog(this.getClass());

    protected Response result() {
        return new Response();
    }

    protected ResponseData result(Object obj) {
        return new ResponseData(obj);
    }

    protected Response result(BaseExceptionMsg exceptionMsg) {
        return new Response(exceptionMsg);
    }

}

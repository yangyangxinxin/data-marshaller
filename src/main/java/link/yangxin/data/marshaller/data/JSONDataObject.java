package link.yangxin.data.marshaller.data;

import link.yangxin.data.marshaller.common.DataMarshaller;

/**
 * @author yangxin
 * @date 2018/11/28
 */
public class JSONDataObject implements DataObject {

    @Override
    public String marshal() {
        return DataMarshaller.marshalJSON(this);
    }
}
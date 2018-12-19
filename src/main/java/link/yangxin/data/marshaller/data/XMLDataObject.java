package link.yangxin.data.marshaller.data;

import link.yangxin.data.marshaller.common.DataMarshaller;

/**
 * @author yangxin
 * @date 2018/11/28
 */
public class XMLDataObject implements DataObject {
    @Override
    public String marshal() {
        return DataMarshaller.marshalXML(this);
    }
}
package link.yangxin.data.marshaller.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import link.yangxin.data.marshaller.data.JSONDataObject;
import link.yangxin.data.marshaller.data.XMLDataObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据装配工具
 *
 * @author yangxin
 * @date 2018/11/28
 */
public class DataMarshaller {

    private static SAXParserFactory spf;

    static {
        spf = SAXParserFactory.newInstance();
        try {
            spf.setFeature("http://xml.org/sax/features/external-general-entities", false);
            spf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        } catch (ParserConfigurationException | SAXNotRecognizedException | SAXNotSupportedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 序列化JSON
     *
     * @param dataObject
     * @return
     */
    public static String marshalJSON(JSONDataObject dataObject) {
        return JSON.toJSONString(dataObject);
    }

    /**
     * 反序列化JSON
     *
     * @param data
     * @param clazz
     * @return
     */
    public static <T extends JSONDataObject> T unMarshalJSON(String data, Class<T> clazz) {
        return JSON.toJavaObject(JSON.parseObject(data), clazz);
    }

    /**
     * 序列化XML
     *
     * @param dataObject
     * @return
     */
    public static String marshalXML(XMLDataObject dataObject) {
        try {
            JAXBContext context = JAXBContext.newInstance(dataObject.getClass());
            Marshaller marshal = context.createMarshaller();
            // todo 格式化，上线可以去掉，测试的时候使用便于查看
            marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshal.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
            StringWriter sw = new StringWriter();
            marshal.marshal(dataObject, sw);
            return sw.toString();
        } catch (JAXBException e) {
            throw new RuntimeException("转换XML时出现异常", e);
        }
    }

    /**
     * 反序列化XML
     *
     * @param data
     * @param clazz
     * @return
     */
    public static <T extends XMLDataObject> T unMarshalXML(String data, Class<T> clazz) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Source xmlSource = new SAXSource(spf.newSAXParser().getXMLReader(), new InputSource(new StringReader(data)));
            return (T) unmarshaller.unmarshal(xmlSource);
        } catch (JAXBException | SAXException | ParserConfigurationException e) {
            throw new RuntimeException("转换XML时出现异常", e);
        }
    }

    /**
     * xml转换为map
     *
     * @param xml
     * @return
     */
    public static Map<String, Object> xmlToMap(String xml) {
        Map<String, Object> map = new HashMap<>();
        Document doc = null;
        try {
            // 将字符串转为XML
            doc = DocumentHelper.parseText(xml);
            // 获取根节点
            Element rootElt = doc.getRootElement();

            // 获取根节点下所有节点
            @SuppressWarnings("unchecked")
            List<Element> list = rootElt.elements();

            // 遍历节点
            for (Element element : list) {
                // 节点的name为map的key，text为map的value
                map.put(element.getName(), element.getText());
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException("转换XML出现异常！", e);
        }
    }

    /**
     * json转换为map
     *
     * @param json
     * @return
     */
    public Map<String, Object> jsonToMap(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        Map<String, Object> map = new HashMap<>(jsonObject.size());
        for (String s : jsonObject.keySet()) {
            map.put(s, jsonObject.get(s));
        }
        return map;
    }


}

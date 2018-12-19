import com.alibaba.fastjson.JSON;
import link.yangxin.data.marshaller.common.DataMarshaller;

/**
 * @author yangxin
 * @date 2018/11/28
 */
public class App {

    public static void main(String[] args) {
        Message message = new Message();
        message.setToUserName("a");
        message.setFromUserName("b");
        message.setCreateTime("c");
        message.setMsgType("d");
        message.setContent("e");
        message.setMsgId("f");
        String marshal = message.marshal();
        System.out.println(marshal);

        String str = "<xml>\n" +
                "    <ToUserName>a</ToUserName>\n" +
                "    <FromUserName>b</FromUserName>\n" +
                "    <CreateTime>c</CreateTime>\n" +
                "    <MsgType>d</MsgType>\n" +
                "    <Content>e</Content>\n" +
                "    <MsgId>f</MsgId>\n" +
                "</xml>";
        Message xmlDataObject = DataMarshaller.unMarshalXML(str, Message.class);
        System.out.println(JSON.toJSONString(xmlDataObject));

    }

}
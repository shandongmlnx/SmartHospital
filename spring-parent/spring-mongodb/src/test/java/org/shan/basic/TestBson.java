package org.shan.basic;

import org.bson.BsonWriter;
import org.bson.Document;
import org.springframework.util.StringUtils;

/**
 * Created by amanda.shan on 2019/1/21.
 */
public class TestBson {

    static void testBsonWriter() {
        BsonWriter writer = null;

        writer.writeStartDocument();
        writer.writeName("a");
        writer.writeString("MongoDB");
        writer.writeName("b");
        writer.writeStartArray();
        writer.writeStartDocument();
        writer.writeName("c");
        writer.writeInt32(1);
        writer.writeEndDocument();
        writer.writeEndArray();
        writer.writeEndDocument();
    }

    static void document() {
        Document document = new Document();
        document.append("name", "shan");

        System.out.println(document.toJson());

    }

    public static void main(String[] args) {

        // dup key: { : "shan0", : 0 }

        String s = " [BulkWriteError{index=0, code=11000, message='E11000 duplicate key error collection: test" +
                ".SimpleBean index: name_1_age_1 dup key: { : \"shan0\", : 0 }', details={ }}]. ;";

        System.out.println(s);

        System.out.println(s.indexOf("dup key: "));

        String s1 = s.substring(s.indexOf("dup key: "));
        System.out.println(s1);

        String s2 = s1.substring(s1.indexOf("{") + 1, s1.indexOf("}"));
        System.out.println(s2);

        System.out.println("==========================");
        String[] split = s2.split(":");
        for (int i = 0; i < split.length; i++) {
            if (!StringUtils.isEmpty(split[i].trim()))
                System.out.println(split[i].trim().replace("\"", "").replace(",", ""));
        }

    }
}

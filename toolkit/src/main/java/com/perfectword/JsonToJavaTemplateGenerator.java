package com.perfectword;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfectword.classinfo.ClassInfo;
import com.perfectword.classinfo.Type;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonToJavaTemplateGenerator {
    public static void main(String[] args) {

    }

    public void generate(String jsonTemplate) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonTemplate);
        processNode(rootNode);
    }

    public ClassInfo processNode(JsonNode jsonNode) {
        ClassInfo cur = new ClassInfo();
        if (jsonNode.isArray()) {

        } else if (jsonNode.isIntegralNumber()) {
            cur.type = Type.Integer;
        } else if (jsonNode.isFloatingPointNumber()) {
            cur.type = Type.Float;
        } else if (jsonNode.isBoolean()) {
            cur.type = Type.Boolean;
        } else if (jsonNode.isTextual() || jsonNode.isBinary()) {
            cur.type = Type.String;
        } else if (jsonNode.isContainerNode()) {
            cur.type = Type.Object;
            var attributes = new HashMap<String, ClassInfo>();
            for (Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields(); it.hasNext(); ) {
                var field = it.next();
                var fieldName = field.getKey();
                var fieldValue = field.getValue();
                var fieldType = processNode(fieldValue);
                attributes.put(fieldName, fieldType);
            }
            attributes.keySet().stream().sorted().forEach(fieldName->
                    cur.attributes.put(fieldName, attributes.get(fieldName)));
        }
        return cur;
    }
}

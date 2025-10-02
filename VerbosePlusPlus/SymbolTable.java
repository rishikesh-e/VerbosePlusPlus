package VerbosePlusPlus;

import java.util.Map;
import java.util.HashMap;

public class SymbolTable {
    Map<String, String> types = new HashMap<>();
    Map<String, Object> values = new HashMap<>();

    void declare(String name, String type, Object value) {
        if(types.containsKey(name)) {
            throw new RuntimeException("Varibale already defined");
        }
        types.put(name, type);
        values.put(name, value);
    }

    void assign(String name, Object value, String type) {
        if(!types.containsKey(name)) {
            throw new RuntimeException("Varibale not defined");
        }
        if(!types.get(name).equals(type)) {
            throw new RuntimeException("Type mismatch");
        }
        values.put(name, value);
    }

    Object get(String name) {
        if(!values.containsKey(name)) {
            throw new RuntimeException("Varibale not defined");
        }
        return values.get(name);
    }

    String getType(String name) {
        if(!types.containsKey(name)) {
            throw new RuntimeException("Varibale not defined");
        }
        return types.get(name);
    }
}

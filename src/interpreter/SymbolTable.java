package interpreter;

import java.util.Map;
import java.util.HashMap;

public class SymbolTable {
    private Map<String, String> types = new HashMap<>();
    private Map<String, Object> values = new HashMap<>();

    public void declare(String name, String type, Object value) {
        if(types.containsKey(name)) {
            throw new RuntimeException("Varibale already defined");
        }
        types.put(name, type);
        values.put(name, value);
    }

    public void assign(String name, Object value, String type) {
        if(!types.containsKey(name)) {
            throw new RuntimeException("Varibale not defined");
        }
        if(!types.get(name).equals(type)) {
            throw new RuntimeException("Type mismatch");
        }
        values.put(name, value);
    }

    public Object get(String name) {
        if(!values.containsKey(name)) {
            throw new RuntimeException("Varibale not defined");
        }
        return values.get(name);
    }

    public String getType(String name) {
        if(!types.containsKey(name)) {
            throw new RuntimeException("Varibale not defined");
        }
        return types.get(name);
    }
}

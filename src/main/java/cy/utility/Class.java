package cy.utility;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Class {
    static public ArrayList<String> getPrefixMatchedVariableStringValues(java.lang.Class classObj, String prefix) {
        ArrayList<String> stringList = new ArrayList<>();
        Field[] fields = classObj.getDeclaredFields();
        for ( Field field : fields  ) {
            if (field.getName().contains(prefix)) {
                try {
                    stringList.add((String)field.get(classObj));
                } catch ( IllegalAccessException ex ) {
                    System.out.println(ex);
                }
            }
        }
        return stringList;
    }
}

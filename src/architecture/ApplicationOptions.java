
package architecture;

import java.util.HashMap;
import org.apache.commons.lang3.Validate;

/**
 *
 */
public class ApplicationOptions {
    private final HashMap<String, String> mPairs;


    public ApplicationOptions(String[] args) {
        mPairs = new HashMap<>();
        for (String option : args) {
            if (option.matches(".*=.*")) {
                String[] pair = option.split("=", 2);
                Validate.isTrue(pair.length == 2);
                set(pair[0], pair[1]);
            } else {
                System.err.println("Mal formed option : '" + option + "'");
                System.err.println("An option should match '.*=.*'");
            }
        }
    }

    public void set(String key, String value) {
        mPairs.put(key, value);
    }

    public void set(String key, Object value) {
        set(key, value.toString());
    }


    /**
     * Set the value only if this value is not already present.
     *
     * @param key
     * @param value
     */
    public void setIfUnset(String key, String value) {
        if (!mPairs.containsKey(key)) {
            set(key, value);
        }
    }

    public void setIfUnset(String key, Object value) {
        setIfUnset(key, value.toString());
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, T defaultValue) {
        if (mPairs.containsKey(key)) {
            String value = mPairs.get(key);
            if (defaultValue instanceof String) {
                return (T) value;
            } else if (defaultValue instanceof Integer) {
                return (T) new Integer(value);
            } else if (defaultValue instanceof Boolean) {
                return (T) new Boolean(value);
            }
            throw new IllegalArgumentException("This type is not supported yet.");
        } else {
            return defaultValue;
        }
    }

    public String get(String key) {
        return mPairs.get(key);
    }

}

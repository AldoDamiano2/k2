package model;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap; // Importazione aggiunta
import java.util.Map;

public class SanitizationUtil {
    public static String sanitize(String input) {
        if (input == null) {
            return null;
        }
        // Basic HTML sanitization
        return input.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
                    .replaceAll("&", "&amp;").replaceAll("\"", "&quot;")
                    .replaceAll("'", "&#x27;").replaceAll("/", "&#x2F;");
    }

    public static Map<String, String> sanitizeParameters(HttpServletRequest request) {
        Map<String, String> sanitizedParams = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String paramName = entry.getKey();
            String[] paramValues = entry.getValue();
            if (paramValues != null && paramValues.length > 0) {
                // Sanitize each value in the array
                for (int i = 0; i < paramValues.length; i++) {
                    paramValues[i] = sanitize(paramValues[i]);
                }
                sanitizedParams.put(paramName, String.join(",", paramValues));
            } else {
                sanitizedParams.put(paramName, null);
            }
        }
        return sanitizedParams;
    }
}

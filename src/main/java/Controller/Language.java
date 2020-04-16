package Controller;

import java.util.HashMap;
import java.util.Map;

public class Language {
    private static Map<String, String> languages;
    private static String language;

    public Language(String language) {
        languages = new HashMap<>();
        this.language = language;

        languages.put("java", "13");
        languages.put("python", "14");
        languages.put("ruby", "33");
    }

    public String getLanguageCode(String language) {
        return languages.get(language);
    }

    public String getLanguage() {
        return language;
    }
}

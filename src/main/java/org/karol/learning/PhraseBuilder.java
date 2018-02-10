package org.karol.learning;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Stateless
public class PhraseBuilder {
    private Map<String, String> templates;

    public String buildPhrase(String id, Object... args) {
        return MessageFormat.format(templates.get(id), args);
    }

    @PostConstruct
    public void initialize() {
        templates = new HashMap<String, String>();
        templates.put("hello", "Hello, {0}!");
    }
}

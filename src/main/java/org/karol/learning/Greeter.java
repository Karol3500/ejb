package org.karol.learning;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.PrintStream;

@Stateless
public class Greeter {

    @EJB
    private PhraseBuilder phraseBuilder;

    public void greet(PrintStream to, String name) {
        to.println(createGreeting(name));
    }

    public String createGreeting(String name) {
        return phraseBuilder.buildPhrase("hello", name);
    }
}
package org.karol.learning;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(Arquillian.class)
public class GreeterTest {

    @Deployment
    public static WebArchive createDeployment() {
        File[] assertJ = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve("org.assertj:assertj-core")
                .withTransitivity().as(File.class);

        File[] shrinkWrap = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve("org.jboss.shrinkwrap.resolver:" +
                        "shrinkwrap-resolver-impl-maven")
                .withTransitivity().as(File.class);

        return ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(assertJ)
                .addAsLibraries(shrinkWrap)
                .addClass(Greeter.class)
                .addClass(PhraseBuilder.class)
                .addClass(AsynchBean.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    Greeter greeter;

    @Test
    public void should_create_greeting() {
        assertThat("Hello, Earthling!").isEqualTo
                (greeter.createGreeting("Earthling"));
        greeter.greet(System.out, "Earthling");
    }

    @EJB
    AsynchBean asynchBean;

    @Test
    public void shouldProperlyReturnFromAsynchMethod() throws ExecutionException, InterruptedException {
        Future<Integer> number = asynchBean.getNumberAfterTenSeconds();

        assertThat(number.get()).isEqualTo(5);
    }
}
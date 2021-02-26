package br.ce.wcaquino.suites;

import br.ce.wcaquino.servicos.CalculoValorLocacaoTest;
import br.ce.wcaquino.servicos.LocacaoServiceTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LocacaoServiceTest.class,
        CalculoValorLocacaoTest.class
})
public class ExecutionSuites {

    @BeforeClass
    public static void setUp() {
        System.out.println("configurando before class...");
    }

    @AfterClass
    public static void tearDown() {
        System.out.println("desconfigurando after class...");
    }

}

package com.spike;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

class FailingUnitTest {

    @Test
    void testDoIt() throws Exception {

    }

    @Test
    void testfailed() throws Exception {
        //Do Nothing
        fail("Simulation d'une Erreur dans le test");
    }
}

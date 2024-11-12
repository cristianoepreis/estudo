package com.ufbra.sistemaescolar.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PlanoEnsinoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PlanoEnsino getPlanoEnsinoSample1() {
        return new PlanoEnsino()
            .id(1L)
            .ementa("ementa1")
            .bibliografiaBasica("bibliografiaBasica1")
            .bibliografiaComplementar("bibliografiaComplementar1")
            .praticaLaboratorial("praticaLaboratorial1");
    }

    public static PlanoEnsino getPlanoEnsinoSample2() {
        return new PlanoEnsino()
            .id(2L)
            .ementa("ementa2")
            .bibliografiaBasica("bibliografiaBasica2")
            .bibliografiaComplementar("bibliografiaComplementar2")
            .praticaLaboratorial("praticaLaboratorial2");
    }

    public static PlanoEnsino getPlanoEnsinoRandomSampleGenerator() {
        return new PlanoEnsino()
            .id(longCount.incrementAndGet())
            .ementa(UUID.randomUUID().toString())
            .bibliografiaBasica(UUID.randomUUID().toString())
            .bibliografiaComplementar(UUID.randomUUID().toString())
            .praticaLaboratorial(UUID.randomUUID().toString());
    }
}

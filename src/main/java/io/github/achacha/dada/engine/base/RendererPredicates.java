package io.github.achacha.dada.engine.base;

import io.github.achacha.dada.engine.render.BaseWordRenderer;
import org.apache.commons.lang3.RandomUtils;

import java.util.function.Predicate;

public class RendererPredicates {
    /**
     * Occurs percent of the time
     * @param percent int [0,100)
     * @return true if random < percent provided
     */
    public static Predicate<BaseWordRenderer> truePercent(int percent) {
        return (bwr)-> RandomUtils.nextInt(0, 100) < percent;
    }
}

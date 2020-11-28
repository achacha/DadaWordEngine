package io.github.achacha.dada.engine.base;

import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.engine.render.BaseWordRenderer;
import org.apache.commons.lang3.RandomUtils;

import java.util.function.Predicate;

public class RendererPredicates {
    /**
     * Occurs percent of the time
     * @param <T> extends Word
     * @param percent int [0,100)
     * @return {@link Predicate} that is true if random less than percent provided
     */
    public static <T extends Word> Predicate<BaseWordRenderer<T>> trueIfPercent(int percent) {
        return (bwr)-> RandomUtils.nextInt(0, 100) < percent;
    }

    /**
     * Occurs probability of the time
     * @param <T> extends Word
     * @param probability int [0.0,1.0)
     * @return {@link Predicate} that is true if random less than probability provided provided
     */
    public static <T extends Word> Predicate<BaseWordRenderer<T>> trueIfProbability(double probability) {
        return (bwr)-> Math.random() < probability;
    }

    /**
     * Always false
     * @param <T> extends Word
     * @return {@link Predicate} that is always false
     */
    public static <T extends Word> Predicate<BaseWordRenderer<T>> falseAlways() {
        return (bwr)-> false;
    }

    /**
     * Always true
     * @param <T> extends Word
     * @return {@link Predicate} that is always true
     */
    public static <T extends Word> Predicate<BaseWordRenderer<T>> trueAlways() {
        return (bwr)-> true;
    }
}

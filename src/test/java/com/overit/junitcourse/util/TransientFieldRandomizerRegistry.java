package com.overit.junitcourse.util;

import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.annotation.Priority;
import org.jeasy.random.api.Randomizer;
import org.jeasy.random.api.RandomizerRegistry;
import org.jeasy.random.randomizers.misc.SkipRandomizer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * {@link org.jeasy.random.annotation.Randomizer} implementation useful to skip all {@code transient} fields when
 * generating a random class instance.
 */
@SuppressWarnings("unused")
@Priority(1)
public class TransientFieldRandomizerRegistry implements RandomizerRegistry {

    @Override
    public void init(EasyRandomParameters parameters) {
    }

    @Override
    public Randomizer<?> getRandomizer(Field field) {
        if (Modifier.isTransient(field.getModifiers())) {
            return new SkipRandomizer();
        }
        return null;
    }

    @Override
    public Randomizer<?> getRandomizer(Class<?> type) {
        return null;
    }
}

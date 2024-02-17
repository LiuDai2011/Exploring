package Exploring.io;

import Exploring.type.func.Consumer;

public class Loader {
    public Class<?> aClass;

    public Loader(Class<?> aClass) {
        this.aClass = aClass;
    }

    public Object load(Consumer<Object> consumer, Class<?>[] argTypes, Object... args) {
        Object obj;
        try {
            if (argTypes == null)
                obj = aClass.getConstructor().newInstance();
            else
                obj = aClass.getConstructor(argTypes).newInstance(args);
            consumer.run(obj);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
}

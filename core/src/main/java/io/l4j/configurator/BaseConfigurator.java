package io.l4j.configurator;

import io.l4j.core.*;
import io.l4j.exception.ComponentCreationException;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple5;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.l4j.core.Constants.*;
import static java.util.Collections.emptyMap;

@Slf4j
public class BaseConfigurator implements Configurator {

    private static final MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
    private static final MethodHandles.Lookup lookup = MethodHandles.lookup();

    private static final Class<Component> componentClass = Component.class;
    private static final Class<ComponentType> componentTypeClass = ComponentType.class;
    private static final Class<Type> typeClass = Type.class;
    private static final Class<Function> functionClass = Function.class;

    private static final MethodType methodType = MethodType.methodType(void.class);
    private static final MethodType componentTypeMethod = MethodType.methodType(componentTypeClass);
    private static final MethodType typeMethod = MethodType.methodType(typeClass);

    @Override
    public Component createComponent(ComponentModule componentModule,
                                     Map<ComponentType, Map<Type, ComponentInfo<Component>>> components,
                                     ComponentType componentType,
                                     Type type) {
        return Try.of(() -> components
                .getOrDefault(componentType, emptyMap())
                .getOrDefault(type, ComponentInfo.EMPTY)
                .createComponent(componentModule))
                .getOrElseThrow(t -> ComponentCreationException.create(t, componentType, type));
    }

    @Override
    public ComponentInfo createComponentInfo(ComponentModule module, Map<ComponentType, Map<Type, ComponentInfo<Component>>> componentsConfiguration, Component component) {
        return null;
    }

    @Override
    public Optional<Component> findComponent(List<Component> components, ComponentType componentType, Type type) {
        return components.stream()
                .filter(component ->
                        Objects.equals(component.getComponentType(), componentType) &&
                                Objects.equals(component.getType(), type))
                .sorted(Comparator.comparing(Component::priority))
                .findFirst();
    }

    @Override
    public void onCreate(Context context) {
        Map<ComponentType, Map<Type, ComponentInfo<Component>>> components = Arrays.stream(
                context.getModule().getClass().getDeclaredMethods())
                .filter(method -> Component.class.isAssignableFrom(method.getReturnType()) &&
                        method.getName().startsWith(RESOLVE))
                .map(method -> Tuple.of(Class.class.cast(method.getReturnType()), method))
                .map(tuple -> Try.of(() -> findMethods(tuple))
                        .onFailure(t -> log.error("ERROR: ", t)))
                .map(Try::getOrNull)
                .filter(Objects::nonNull)
                .map(tuple -> Try.of(() -> invokeMethods(tuple))
                        .onFailure(t -> log.error("ERROR: ", t)))
                .map(Try::getOrNull)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Pair::getLeft,
                        Collectors.mapping(Pair::getRight,
                                Collectors.toMap(ComponentInfo::getType,
                                        Function.identity(), (f, s) -> f))));

        context.setComponentsConfiguration(components);
    }

    @Override
    public void onConfig(Context context) {

    }

    private Pair<ComponentType, ComponentInfo<Component>> invokeMethods(
            Tuple5<MethodHandle, MethodHandle, MethodHandle, Class<Component>, MethodHandle> tuple) throws Throwable {
        Component component = componentClass.cast(tuple._1().invoke());
        ComponentType componentType = componentTypeClass.cast(tuple._2().invoke(component));
        Type type = typeClass.cast(tuple._3().invoke(component));
        Function factoryMethod = functionClass.cast(tuple._5().invoke());
        ComponentInfo componentInfo = ComponentInfo.builder()
                .componentClass(tuple._4())
                .type(type)
                .factoryMethod(factoryMethod)
                .build();

        return Pair.of(componentType, componentInfo);
    }

    private Tuple5<MethodHandle, MethodHandle, MethodHandle, Class<Component>, MethodHandle> findMethods(
            Tuple2<Class, Method> tuple) throws NoSuchMethodException, IllegalAccessException, LambdaConversionException {
        Class<Component> clazz = tuple._1();
        MethodHandle constructor = publicLookup.findConstructor(clazz, methodType);
        MethodHandle componentType = publicLookup.findVirtual(clazz, GET_COMPONENT_TYPE, componentTypeMethod);
        MethodHandle type = publicLookup.findVirtual(clazz, GET_TYPE, typeMethod);
        MethodHandle target = publicLookup.unreflect(tuple._2());
        MethodType functionType = MethodType.methodType(Function.class);
        MethodHandle factoryMethod = LambdaMetafactory.metafactory(
                lookup,
                APPLY,
                functionType,
                target.type().generic(),
                target,
                target.type())
                .getTarget();

        return Tuple.of(constructor, componentType, type, clazz, factoryMethod);
    }

}

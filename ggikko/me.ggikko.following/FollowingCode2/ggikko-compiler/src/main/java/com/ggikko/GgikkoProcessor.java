package com.ggikko;

import com.google.auto.common.SuperficialValidation;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;

import java.lang.annotation.Annotation;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class GgikkoProcessor extends AbstractProcessor {

    static final Id NO_ID = new Id(-1);

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        Map<TypeElement, BindingSet> bindingMap = findAndParseTargets(env);
        return false;
    }

    private Map<TypeElement, BindingSet> findAndParseTargets(RoundEnvironment env) {
        Map<TypeElement, BindingSet.Builder> builderMap = new LinkedHashMap<>();
        Set<TypeElement> erasedTargetNames = new LinkedHashSet<>();

        scanForRClasses(env);

        // Process each @BindArray element.
        for (Element element : env.getElementsAnnotatedWith(BindArray.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            try {
                parseResourceArray(element, builderMap, erasedTargetNames);
            } catch (Exception e) {
                logParsingError(element, BindArray.class, e);
            }
        }

        // Process each @BindBitmap element.
        for (Element element : env.getElementsAnnotatedWith(BindBitmap.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            try {
                parseResourceBitmap(element, builderMap, erasedTargetNames);
            } catch (Exception e) {
                logParsingError(element, BindBitmap.class, e);
            }
        }

        // Process each @BindBool element.
        for (Element element : env.getElementsAnnotatedWith(BindBool.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            try {
                parseResourceBool(element, builderMap, erasedTargetNames);
            } catch (Exception e) {
                logParsingError(element, BindBool.class, e);
            }
        }

        // Process each @BindColor element.
        for (Element element : env.getElementsAnnotatedWith(BindColor.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            try {
                parseResourceColor(element, builderMap, erasedTargetNames);
            } catch (Exception e) {
                logParsingError(element, BindColor.class, e);
            }
        }

        // Process each @BindDimen element.
        for (Element element : env.getElementsAnnotatedWith(BindDimen.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            try {
                parseResourceDimen(element, builderMap, erasedTargetNames);
            } catch (Exception e) {
                logParsingError(element, BindDimen.class, e);
            }
        }

        // Process each @BindDrawable element.
        for (Element element : env.getElementsAnnotatedWith(BindDrawable.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            try {
                parseResourceDrawable(element, builderMap, erasedTargetNames);
            } catch (Exception e) {
                logParsingError(element, BindDrawable.class, e);
            }
        }

        // Process each @BindFloat element.
        for (Element element : env.getElementsAnnotatedWith(BindFloat.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            try {
                parseResourceFloat(element, builderMap, erasedTargetNames);
            } catch (Exception e) {
                logParsingError(element, BindFloat.class, e);
            }
        }

        // Process each @BindInt element.
        for (Element element : env.getElementsAnnotatedWith(BindInt.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            try {
                parseResourceInt(element, builderMap, erasedTargetNames);
            } catch (Exception e) {
                logParsingError(element, BindInt.class, e);
            }
        }

        // Process each @BindString element.
        for (Element element : env.getElementsAnnotatedWith(BindString.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            try {
                parseResourceString(element, builderMap, erasedTargetNames);
            } catch (Exception e) {
                logParsingError(element, BindString.class, e);
            }
        }

        // Process each @BindView element.
        for (Element element : env.getElementsAnnotatedWith(BindView.class)) {
            // we don't SuperficialValidation.validateElement(element)
            // so that an unresolved View type can be generated by later processing rounds
            try {
                parseBindView(element, builderMap, erasedTargetNames);
            } catch (Exception e) {
                logParsingError(element, BindView.class, e);
            }
        }

        // Process each @BindViews element.
        for (Element element : env.getElementsAnnotatedWith(BindViews.class)) {
            // we don't SuperficialValidation.validateElement(element)
            // so that an unresolved View type can be generated by later processing rounds
            try {
                parseBindViews(element, builderMap, erasedTargetNames);
            } catch (Exception e) {
                logParsingError(element, BindViews.class, e);
            }
        }

        // Process each annotation that corresponds to a listener.
        for (Class<? extends Annotation> listener : LISTENERS) {
            findAndParseListener(env, listener, builderMap, erasedTargetNames);
        }

        // Associate superclass binders with their subclass binders. This is a queue-based tree walk
        // which starts at the roots (superclasses) and walks to the leafs (subclasses).
        Deque<Map.Entry<TypeElement, BindingSet.Builder>> entries =
                new ArrayDeque<>(builderMap.entrySet());
        Map<TypeElement, BindingSet> bindingMap = new LinkedHashMap<>();
        while (!entries.isEmpty()) {
            Map.Entry<TypeElement, BindingSet.Builder> entry = entries.removeFirst();

            TypeElement type = entry.getKey();
            BindingSet.Builder builder = entry.getValue();

            TypeElement parentType = findParentType(type, erasedTargetNames);
            if (parentType == null) {
                bindingMap.put(type, builder.build());
            } else {
                BindingSet parentBinding = bindingMap.get(parentType);
                if (parentBinding != null) {
                    builder.setParent(parentBinding);
                    bindingMap.put(type, builder.build());
                } else {
                    // Has a superclass binding but we haven't built it yet. Re-enqueue for later.
                    entries.addLast(entry);
                }
            }
        }

        return bindingMap;
    }

}

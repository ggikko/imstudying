package ggikko.me.bottomsheet;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class GgikkoBottomSheetKnife {

    public static void bind(@NonNull Activity target) {
        try {
            createBinding(target);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void createBinding(Object target) throws ClassNotFoundException,
            NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {

        Class<?> targetClass = target.getClass();
        String targetName = targetClass.getName();
        String targetClassName = targetClass.getSimpleName();

        Log.e("ggikko", "target : " + targetClassName);

        if (targetName.startsWith("android.") || targetName.startsWith("java.")) {
            return;
        }
        Class<?> bindingClass = Class.forName(targetName + "_BottomSheet");
        Log.e("ggikko", "target2 : " + targetClassName);
        Constructor<?> constructor = bindingClass.getConstructor(targetClass);
        Log.e("ggikko", "target3 : " + targetClassName);
        constructor.newInstance(target);
        Log.e("ggikko", "target4 : " + targetClassName);
    }
}

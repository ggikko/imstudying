package me.ggikko;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import lombok.extern.slf4j.Slf4j;
/**
 * Created by ggikko on 2017. 1. 27..
 */

@SupportedSourceVersion(SourceVersion.RELEASE_7)
@AutoService(Processor.class)
public class GgikkoCodeGenProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(BottomSheet.class.getName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        for (Element element : env.getElementsAnnotatedWith(BottomSheet.class)) {
            if (element.getKind() != ElementKind.FIELD) {
                error(element, Constant.MUST_ANNOTATE_INTO_FIELD, BottomSheet.class.getSimpleName());
                return true;
            }
        }

        Set<? extends Element> elementsAnnotatedWith = env.getElementsAnnotatedWith(BottomSheet.class);

        for (Element e : elementsAnnotatedWith) {

//            Set<VariableElement> fields = ElementFilter.fieldsIn(elementsAnnotatedWith);
//
            String title = "title";
            String content = "content";
//
//            for (VariableElement element : fields) {
            title = e.getAnnotation(BottomSheet.class).title();
            content = e.getAnnotation(BottomSheet.class).content();
//            }

            Element enclosingElement = e.getEnclosingElement();

            String className = enclosingElement.getSimpleName().toString();



            TypeSpec.Builder builder = TypeSpec.classBuilder(className + "_BottomSheet");

//            ggikko.me.bottomsheet.BasicBottomSheetDialogFragment

            ClassName targetName = ClassName.get("ggikko.me.bottomsheetbinding", className);
            System.out.printf("asdfasdfdasfdsafdsaf\n\n\n\nasdfasdfasdfsadfasdfds\n\n\n");
            ClassName bottomSheetName = ClassName.get("ggikko.me.bottomsheet", "BasicBottomSheetDialogFragment");


            MethodSpec targetMethod = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(targetName, "target")
                    .addCode("target.greetingBottomSheet = new $T", bottomSheetName)
                    .addCode(".Builder(target)")
                    .addCode(".title(\"Greeting\")")
                    .addCode(".content(\"hoho\")")
                    .addCode(".build();")
                    .build();

            builder.addMethod(targetMethod);

//            builder.addMethod(whatsMyName(title + content));

//            builder.addField(Basicbottom.class, "greeting", Modifier.PRIVATE, Modifier.FINAL)

//            builder.addField(Basicbo)

//
//            MethodSpec meth = MethodSpec.constructorBuilder()
//                    .addModifiers(Modifier.PUBLIC)
//                    .addParameter(String.class, e.getSimpleName().toString())
//                    .addStatement("target.greetingBottomSheet = new BasicBottomSheetDialogFragment")
//                    .addStatement(".Builder(target)")
//                    .addStatement(".title($N)", title)
//                    .addStatement(".content($N)", content)
//                    .addStatement(".build();")
//                    .build();

//            builder.addMethod(meth);

            String packageName = processingEnv.getElementUtils().getPackageOf(enclosingElement).getQualifiedName().toString();

            JavaFile javaFile = JavaFile.builder(packageName, builder.build()).build();
            write(javaFile);
        }


        /**
         * 1. class
         * 2. private field
         * 3. UiThread Annotation
         * 4. constructor + target
         * 5. target. + fieldname = new field class . Builder.... title content.. build
         *
         *
         */
//        public class MainActivity_Ggikko {
//
//            private MainActivity target;
//
//            @UiThread
//            public MainActivity_Ggikko(MainActivity target) {
//                target.greetingBottomSheet = new BasicBottomSheetDialogFragment
//                        .Builder(target)
//                        .title("Greeting")
//                        .content("hoho")
//                        .build();
//            }
//        }

        return false;
    }

    private static MethodSpec whatsMyName(String name) {
        return MethodSpec.methodBuilder(name)
                .returns(String.class)
                .addStatement("return $S", name)
                .build();
    }

    private void write(JavaFile javaFile) {
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void error(Element e, String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
    }
}

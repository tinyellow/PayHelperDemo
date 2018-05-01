package com.payhelper.compiler;

import com.google.auto.service.AutoService;
import com.pay.annotation.APPLICATION_ID;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class PayCallbackProcessor extends AbstractProcessor {


    ProcessingEnvironment processingEnvironment;
    private Messager mMessager;
    private Elements mElementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        this.processingEnvironment = processingEnvironment;
        this.mMessager = processingEnvironment.getMessager();
        this.mElementUtils = processingEnvironment.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(APPLICATION_ID.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        try {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "processing...");
        String applicationId = null;
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(APPLICATION_ID.class);
        if (elements != null && elements.size() > 0){
            Element element = elements.iterator().next();
            applicationId = element.getAnnotation(APPLICATION_ID.class).value();
            mMessager.printMessage(Diagnostic.Kind.NOTE, "applicationId = "+applicationId);
        }
        if (applicationId == null || applicationId.length() == 0) return false;
        TypeElement payCallbackActivity = mElementUtils.getTypeElement("com.littleyellow.payhelper.weixin.PayCallbackActivity");

        TypeSpec payEntryActivity = TypeSpec.classBuilder("WXPayEntryActivity")
                .addModifiers(Modifier.PUBLIC)
                .superclass(ClassName.get(payCallbackActivity))
                .build();

            JavaFile javaFile = JavaFile.builder(applicationId+".wxapi", payEntryActivity)
                    .build();
            javaFile.writeTo(processingEnvironment.getFiler());
        } catch (Exception e) {
            e.printStackTrace();
            mMessager.printMessage(Diagnostic.Kind.ERROR, "Exception="+e.getMessage());
        }
        return false;
    }
}

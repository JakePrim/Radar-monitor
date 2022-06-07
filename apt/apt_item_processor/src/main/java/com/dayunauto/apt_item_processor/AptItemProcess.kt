package com.dayunauto.apt_item_processor

import com.dayunauto.apt_item_create.ItemAuto
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import java.io.IOException
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement


class AptItemProcess : AbstractProcessor() {
    override fun getSupportedAnnotationTypes(): Set<String> {
        val types = LinkedHashSet<String>()
        types.add(ItemAuto::class.java.canonicalName)
        return types
    }

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        var moduleName = ""

        /**
         * 多模块中的名称配置
         *  kapt {
         *   arguments {
         *    arg("AROUTER_MODULE_NAME", project.getName())
         *    arg("ITEM_MODULE_NAME", project.getName())
         *   }
         * }
         */
        val options = processingEnv.options
        if (options.isNotEmpty()) {
            moduleName = options[Const.MODULE_NAME].toString()
        }
        System.out.println("======>start:${moduleName}")

        //构建类型
        val type = getType(HashMap::class.java, String::class.javaObjectType, Class::class.java)

        /**
         * 定义方法信息
         */
        val constructorBuilder = MethodSpec.methodBuilder("cacheType")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(Void.TYPE)
            .addParameter(type, "map")//HashMap<String,KClass> map
            .addStatement(
                "\$T.out.println(\$S)",
                System::class.java,
                "Hello, ItemType auto process @ItemAuto annotation!"
            )
        val elements = roundEnv?.getElementsAnnotatedWith(ItemAuto::class.java)
        elements?.forEach { element ->
            val item: ItemAuto = element.getAnnotation(ItemAuto::class.java)
            if (element is TypeElement) {
                val packageElement = processingEnv.elementUtils.getPackageOf(element)
                val className = ClassName.get(
                    packageElement.qualifiedName.toString(),
                    element.simpleName.toString()
                )
                //map.put(type,class)
                constructorBuilder.addStatement(
                    "map.put(\$S,\$T.class)",
                    item.type, className
                )
            }
        }


        /**
         * 定义类
         */
        val helloWorld = TypeSpec.classBuilder(Const.ITEM_NAME)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addMethod(constructorBuilder.build())
            .build()

        /**
         * 定义包名
         */
        val javaFile = JavaFile.builder(Const.PACKAGE + ".${moduleName}", helloWorld)
            .build()

        try {
            javaFile.writeTo(processingEnv.filer)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        System.out.println("=======>end : ${moduleName}")
        return false
    }


    override fun getSupportedSourceVersion(): SourceVersion? {
        return SourceVersion.latestSupported()
    }
}
package com.sdjzu.account.utils;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 继承并扩展org.springframework.beans.BeanUtils工具，实现多个bean之间属性拷贝。
 * 支持String与ObjectId互相转换
 *
 * @author mindgazer
 * @date 2018/09/12
 */
@Slf4j
public class BeanUtilEx extends BeanUtils {

    /**
     * 字段类型：ObjectId
     */
    private static final String FIELD_TYPE_OBJID = "objectid";

    /**
     * 字段类型：String
     */
    private static final String FIELD_TYPE_STR = "string";

    /**
     * 支持自动类型转换的类型常量定义
     */
    private static final Set<String> FORCE_COPY_TYPES = new HashSet<>(Arrays.asList(FIELD_TYPE_OBJID, FIELD_TYPE_STR));

    /**
     * 创建目标对象，将src的属性值拷贝到dst中同名、同类型属性中
     *
     * @param source           源对象
     * @param targetClass      目标对象类类型
     * @param ignoreProperties 拷贝过程中需要忽略的属性
     * @return 目标哦对象
     */
    public static <T> T copyAndGet(Object source, Class<T> targetClass, String... ignoreProperties) {
        try {
            T target = targetClass.newInstance();
            copyProperties(source, target, ignoreProperties);
            return target;
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 创建目标对象，将src的属性值拷贝到dst中同名、同类型属性中
     *
     * @param source      源对象
     * @param targetClass 目标对象类类型
     * @return 目标对象
     */
    public static <T> T copyAndGet(Object source, Class<T> targetClass) {
        try {
            T target = targetClass.newInstance();
            copyProperties(source, target);
            // 添加枚举描述复制
            return target;
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 将src列表中所有对象，复制到一个T类型的新列表中
     *;
     * @param source      元对象列表
     * @param targetClass 目标对象类类型
     * @param <T>         泛型
     * @return 目标对象列表
     */
    public static <R, T> List<T> copyAndGetList(List<R> source, Class<T> targetClass) {
        if (source == null) {
            return Lists.newArrayList();
        }
        return source.stream().map(e -> copyAndGet(e, targetClass)).collect(Collectors.toList());
    }

    /**
     * 将源对象列表中的所有元素的属性值复制到目标对象中同名同类型属性中
     *
     * @param source           源对象
     * @param targetClass      目标对象类类型
     * @param <T>              泛型
     * @param ignoreProperties 忽略的、不需要copy的字段
     * @return 目标类型的元素列表
     */
    public static <R, T> List<T> copyAndGetList(List<R> source, Class<T> targetClass,
                                                @Nullable String... ignoreProperties) {
        if (source == null) {
            return Lists.newArrayList();
        }
        return source.stream().map(e -> copyAndGet(e, targetClass, ignoreProperties)).collect(Collectors.toList());
    }

    /**
     * 将源对象中的属性值复制到目标对象中同名同类型的属性中
     *
     * @param source           源对象
     * @param target           目标对象
     * @param ignoreProperties 拷贝过程中需要忽略的属性
     */
    public static void copyObject(Object source, Object target, @Nullable String... ignoreProperties) {
        PropertyDescriptor[] targetPds = getPropertyDescriptors(target.getClass());
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    // 要求set方法参数类型与get方法返回值类型一致，才会调用set方法来设置value
                    if (readMethod != null &&
                        ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            if ((value instanceof Collection)) {
                                writeMethod.invoke(target, value);
                            } else {
                                writeMethod.invoke(target, value);
                            }
                        } catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from " + "source to target",
                                    ex);
                        }
                    }
                }
            }
        }

    }

    /**
     * 将源对象列表中的所有元素的属性值复制到目标对象中同名同类型属性中
     *
     * @param source           源对象
     * @param targetClass      目标对象类类型
     * @param <T>              泛型
     * @param ignoreProperties 忽略的、不需要copy的字段
     * @return 目标类型的元素列表
     */
    public static <R, T> List<T> copyObjectList(List<R> source, Class<T> targetClass,
                                                @Nullable String... ignoreProperties) {
        if (source == null) {
            return Lists.newArrayList();
        }
        return source.stream().map(e -> copyAndGet(e, targetClass, ignoreProperties)).collect(Collectors.toList());
    }

    /**
     * 将src对象属性值拷贝到target对象同名同类型属性中，但仅拷贝非空值属性
     *
     * @param source           源对象
     * @param target           目标对象
     * @param ignoreProperties 该数组中的属性会被忽略掉
     */
    public static void copyObjectWithNonNullProps(Object source, Object target, @Nullable String... ignoreProperties) {
        PropertyDescriptor[] targetPds = getPropertyDescriptors(target.getClass());
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    // 要求set方法参数类型与get方法返回值类型一致，才会调用set方法来设置value
                    if (readMethod != null &&
                        ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            // 非空值才写入
                            if (value != null) {
                                if ((value instanceof Collection)) {
                                    if (!((Collection) value).isEmpty()) {
                                        writeMethod.invoke(target, value);
                                    }
                                } else {
                                    writeMethod.invoke(target, value);
                                }
                            }
                        } catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from " + "source to target",
                                    ex);
                        }
                    }
                }
            }
        }

    }

    /**
     * 根据字段名称寻找目标字段
     *
     * @param allFields       所有字段
     * @param targetFieldName 目标字段名称
     * @return 寻找到的目标字段
     */
    private static Field findFieldByName(List<Field> allFields, String targetFieldName) {
        Field targetField = null;
        for (Field field : allFields) {
            if (field.getName().equals(targetFieldName)) {
                targetField = field;
                break;
            }
        }
        return targetField;
    }

    /**
     * 标记有该注解的字段，在使用BeanUtilEx工具执行copy的时候，会自动将String与ObjectId字段互相转换。
     * 需要注意，该注解标记的属性，其值会被转换和复制到另一个对象中
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    @Inherited
    public @interface ForceCopy {
    }

    /**
     * 标记有该注解的地方，在使用BeanUtilEx工具执行copy的时候，会将Enum的description复制给特殊属性。
     * 需要注意，该注解标记的属性与目标属性，应当是包含在同一个对象中
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(value = {ElementType.FIELD})
    @Inherited
    public @interface EnumCast {

        /**
         * @return 返回源字段中的描述字段名称，默认为description
         */
        String srcDescFieldName() default "description";

        /**
         * @return 返回目标字段中的描述字段名称
         */
        String targetDescFieldName() default "";
    }

}
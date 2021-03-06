package capital.scalable.restdocs.jackson;

import capital.scalable.restdocs.constraints.ConstraintReader;
import capital.scalable.restdocs.javadoc.JavadocReader;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static capital.scalable.restdocs.util.TypeUtil.determineArrayOfType;
import static capital.scalable.restdocs.util.TypeUtil.resolveAllTypes;
import static org.slf4j.LoggerFactory.getLogger;

class FieldDocumentationVisitorWrapper implements JsonFormatVisitorWrapper {
    private static final Logger log = getLogger(FieldDocumentationVisitorWrapper.class);

    static final List<Class<?>> supportedObjectClass = Arrays.asList(new Class<?>[] {LocalDate.class, LocalDateTime.class});

    private SerializerProvider provider;
    private final FieldDocumentationVisitorContext context;
    private final String path;
    private final InternalFieldInfo fieldInfo;
    private final TypeRegistry typeRegistry;
    private final TypeFactory typeFactory;

    FieldDocumentationVisitorWrapper(FieldDocumentationVisitorContext context, String path,
            InternalFieldInfo fieldInfo, TypeRegistry typeRegistry, TypeFactory typeFactory) {
        this(null, context, path, fieldInfo, typeRegistry, typeFactory);
    }

    FieldDocumentationVisitorWrapper(SerializerProvider provider,
            FieldDocumentationVisitorContext context, String path, InternalFieldInfo fieldInfo,
            TypeRegistry typeRegistry, TypeFactory typeFactory) {
        this.provider = provider;
        this.context = context;
        this.path = path;
        this.fieldInfo = fieldInfo;
        this.typeRegistry = typeRegistry;
        this.typeFactory = typeFactory;
    }

    public static FieldDocumentationVisitorWrapper create(JavadocReader javadocReader,
            ConstraintReader constraintReader, DeserializationConfig deserializationConfig,
            TypeRegistry typeRegistry, TypeFactory typeFactory) {
        FieldDocumentationVisitorContext context = new FieldDocumentationVisitorContext(
                javadocReader, constraintReader, deserializationConfig);
        return new FieldDocumentationVisitorWrapper(context, "", null, typeRegistry, typeFactory);
    }

    @Override
    public SerializerProvider getProvider() {
        return provider;
    }

    @Override
    public void setProvider(SerializerProvider provider) {
        this.provider = provider;
    }

    @Override
    public JsonObjectFormatVisitor expectObjectFormat(JavaType type) throws JsonMappingException {
        if (supportedObjectClass.contains(type.getRawClass())) {
            addFieldIfPresent(type.getRawClass().getSimpleName());
        }
        else {
            addFieldIfPresent("Object");
        }
        if (shouldExpand() && (topLevelPath() || !wasVisited(type))) {
            log.trace("({}) {} expanding", path, toString(type));
            return new FieldDocumentationObjectVisitor(provider, context, path,
                    withVisitedType(type), typeFactory);
        } else {
            log.trace("({}) {} NOT expanding", path, toString(type));
            return new JsonObjectFormatVisitor.Base();
        }
    }

    @Override
    public JsonArrayFormatVisitor expectArrayFormat(JavaType arrayType)
            throws JsonMappingException {
        JavaType contentType = arrayType.getContentType();
        addFieldIfPresent(determineArrayOfType(contentType));
        if (contentType != null && shouldExpand() && (topLevelPath() || !wasVisited(contentType))) {
            log.trace("({}) {} expanding array", path, toString(contentType));
            // do not add this type to visited now, it will be done in expectObjectFormat for
            // content type of this array
            return new FieldDocumentationArrayVisitor(provider, context, path,
                    typeRegistry, typeFactory);
        } else {
            log.trace("({}) {} NOT expanding array", path, "<unknown>");
            return new JsonArrayFormatVisitor.Base();
        }
    }

    @Override
    public JsonStringFormatVisitor expectStringFormat(JavaType type) throws JsonMappingException {
        addFieldIfPresent("String");
        return new JsonStringFormatVisitor.Base();
    }

    @Override
    public JsonNumberFormatVisitor expectNumberFormat(JavaType type) throws JsonMappingException {
        addFieldIfPresent("Decimal");
        return new JsonNumberFormatVisitor.Base();
    }

    @Override
    public JsonIntegerFormatVisitor expectIntegerFormat(JavaType type) throws JsonMappingException {
        addFieldIfPresent("Integer");
        return new JsonIntegerFormatVisitor.Base();
    }

    @Override
    public JsonBooleanFormatVisitor expectBooleanFormat(JavaType type) throws JsonMappingException {
        addFieldIfPresent("Boolean");
        return new JsonBooleanFormatVisitor.Base();
    }

    @Override
    public JsonNullFormatVisitor expectNullFormat(JavaType type) throws JsonMappingException {
        addFieldIfPresent("null");
        return new JsonNullFormatVisitor.Base();
    }

    @Override
    public JsonAnyFormatVisitor expectAnyFormat(JavaType type) throws JsonMappingException {
        if (supportedObjectClass.contains(type.getRawClass())) {
            addFieldIfPresent(type.getRawClass().getSimpleName());
        }
        else {
            addFieldIfPresent("Object");
        }
        return new JsonAnyFormatVisitor.Base();
    }

    @Override
    public JsonMapFormatVisitor expectMapFormat(JavaType type) throws JsonMappingException {
        addFieldIfPresent("Map");
        return new JsonMapFormatVisitor.Base(provider);
    }

    public List<FieldDescriptor> getFields() {
        return context.getFields();
    }

    private void addFieldIfPresent(String jsonType) {
        if (fieldInfo != null) {
            context.addField(fieldInfo, jsonType);
        }
    }

    private boolean shouldExpand() {
        return fieldInfo == null || fieldInfo.shouldExpand();
    }

    private TypeRegistry withVisitedType(JavaType javaType) {
        // add all subtypes when going deeper
        // they will be eventually analysed at current level
        List<JavaType> allTypes = resolveAllTypes(javaType, typeFactory);
        return typeRegistry.withVisitedTypes(allTypes);
    }

    private boolean wasVisited(JavaType type) {
        return typeRegistry.wasVisited(type);
    }

    private boolean topLevelPath() {
        return "".equals(path);
    }

    private String toString(JavaType type) {
        return type.getRawClass().getSimpleName();
    }
}

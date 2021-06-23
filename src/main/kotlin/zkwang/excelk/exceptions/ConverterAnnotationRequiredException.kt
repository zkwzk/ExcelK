package zkwang.excelk.exceptions

class ConverterAnnotationRequiredException(typeName: String, fieldName: String) :
    Throwable(message = "For $fieldName inside the type $typeName, Converter annotation is required if Column annotation is specific")

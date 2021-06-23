package zkwang.excelk.exceptions

class SheetNameAnnotationRequiredException(typeName: String) :
    Throwable(message = "For type $typeName, SheetName annotation is required")

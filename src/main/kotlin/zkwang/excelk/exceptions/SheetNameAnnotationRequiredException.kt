package zkwang.excelk.exceptions

class SheetNameAnnotationRequiredException(typeName: String) :
    Throwable(message = "For $typeName, SheetName annotation is required")

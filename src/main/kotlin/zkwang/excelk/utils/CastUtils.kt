package zkwang.excelk.utils

inline fun <reified T: Any> cast(any: Any): T = T::class.javaObjectType.cast(any)
inline fun <reified T> List<*>.asListOfType(): List<T>? =
    if (all { it is T })
        @Suppress("UNCHECKED_CAST")
        this as List<T> else
        null

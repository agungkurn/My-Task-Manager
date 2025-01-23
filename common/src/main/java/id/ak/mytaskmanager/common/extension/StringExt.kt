package id.ak.mytaskmanager.common.extension

fun String.capitalizeWords(): String {
    return split(" ").map { it.replaceFirstChar { it.uppercase() } }.joinToString(separator = " ")
}
fun readLinesFromResourceFile(fileName: String): List<String> {
    return ClassLoader.getSystemClassLoader().getResource(fileName)
            ?.readText()
            ?.lines() ?: error("Unable to read lines of file with name $fileName")
}
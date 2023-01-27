package uz.anorbank.spring6rpc.exception

class InvalidParamsException : Exception("Invalid method parameter(s)")

class InvalidWrapperException : Exception("Result wrapper must be reactive type (Mono/Flux)")

class JsonRpcVersionValidationException(version: String) :
    Exception("JSON-RPC version incorrect. Supported version: $version")

class MethodNotFoundException : Exception("The method does not exist / is not available")

class MethodParamsMetaDataException : Exception("Method params meta data read error")

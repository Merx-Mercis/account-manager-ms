package persei9.accountManagerMs.exceptions

class BadRequestException(message: String?): Exception(message)

class CommandNotFoundException(message: String?): Exception(message)

class ConflictException(message: String?): Exception(message)

class NotFoundException(message: String?): Exception(message)

class ServiceException(message: String?): Exception(message)

class UnauthorizedException(message: String): Exception(message)

package persei9.accountManagerMs.handlers

import persei9.accountManagerMs.dtos.services.CommandRequestDto

interface IDomainCommandHandler {
    fun handle(command: CommandRequestDto): Any
}
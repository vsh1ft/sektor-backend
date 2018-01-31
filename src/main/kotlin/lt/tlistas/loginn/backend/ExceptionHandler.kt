package lt.tlistas.loginn.backend

import com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER
import lt.tlistas.core.api.exception.GeocodeGatewayException
import lt.tlistas.core.api.exception.LocationNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import reactor.core.publisher.Mono
import java.util.logging.Level

class ExceptionHandler : WebExceptionHandler {
    override fun handle(exchange: ServerWebExchange?, ex: Throwable?): Mono<Void> {
        when (ex!!) {
            is GeocodeGatewayException -> {
                LOGGER.log(Level.WARNING, ex.message)
                exchange!!.response.statusCode = HttpStatus.INTERNAL_SERVER_ERROR
            }
            is LocationNotFoundException -> {
                LOGGER.log(Level.WARNING, ex.message)
                exchange!!.response.statusCode = HttpStatus.UNPROCESSABLE_ENTITY
            }
        }
        return Mono.empty()
    }
}
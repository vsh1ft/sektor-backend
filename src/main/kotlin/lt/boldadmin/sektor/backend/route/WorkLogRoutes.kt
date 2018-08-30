package lt.boldadmin.sektor.backend.route

import lt.boldadmin.sektor.backend.handler.identityconfirmed.WorkLogHandler
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.router

@Configuration
open class WorkLogRoutes(private val workLogHandler: WorkLogHandler) {

    @Bean
    open fun router() = router {
        "/worklog".nest {
            accept(APPLICATION_JSON).nest {
                POST("/log-by-location", workLogHandler::logByLocation)
                POST("/update-description/{intervalId}", workLogHandler::updateDescription)
                GET("/project-name-of-started-work", workLogHandler::getProjectNameOfStartedWork)
                GET("/has-work-started", workLogHandler::hasWorkStarted)
                GET("/interval/{intervalId}/endpoints", workLogHandler::getIntervalEndpoints)
                GET("/interval/{intervalId}/description", workLogHandler::getDescription)
                GET("/interval/{intervalIds}/durations-sum", workLogHandler::getDurationsSum)
                GET("/collaborator/interval-ids", workLogHandler::getIntervalIdsByCollaborator)
            }
        }
        GET("/status", { ok().body(fromObject("OK")) })
    }
}
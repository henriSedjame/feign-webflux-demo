package com.example.server

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.Trigger
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar
import org.springframework.scheduling.support.CronTrigger
import reactor.core.publisher.Sinks
import java.time.LocalDateTime

@Configuration
@EnableScheduling
class RegularTaskConf(val sink: Sinks.Many<String>) : SchedulingConfigurer{

    // lateinit var taskScheduler: TaskScheduler

    override fun configureTasks(taskRegistar: ScheduledTaskRegistrar) {
        ThreadPoolTaskScheduler().apply {
            poolSize = 10
            setThreadNamePrefix("thread-exec_N° ==> ")
            initialize()
        }.let {
            runJob(it)
            taskRegistar.setTaskScheduler(it)
        }
    }

    fun runJob(scheduler: TaskScheduler)  = scheduler.schedule(
        {
            println("${Thread.currentThread().name} +++ Task executed at ${LocalDateTime.now()}")
            sink.tryEmitNext("${Thread.currentThread().name} +++ Task executed at ${LocalDateTime.now()}")
        },
        {
            val cronExp = "0/5 * * * * ?"
            CronTrigger(cronExp).nextExecutionTime(it)
        }
    )

}

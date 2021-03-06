package io.choerodon.asgard.api.controller.v1;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.asgard.api.validator.ScheduleTaskValidator;
import io.choerodon.asgard.api.vo.ScheduleTask;
import io.choerodon.asgard.app.service.ScheduleTaskService;
import io.choerodon.asgard.infra.dto.QuartzTaskDTO;
import io.choerodon.swagger.annotation.Permission;

@RestController
@RequestMapping("/v1/schedules/tasks")
@Api("定时任务定义内部接口")
public class ScheduleTaskController {

    private ScheduleTaskService scheduleTaskService;

    public ScheduleTaskController(ScheduleTaskService scheduleTaskService) {
        this.scheduleTaskService = scheduleTaskService;
    }

    public void setScheduleTaskService(ScheduleTaskService scheduleTaskService) {
        this.scheduleTaskService = scheduleTaskService;
    }

    @Permission(permissionWithin = true)
    @ApiOperation(value = "根据serviceCode和methodCode创建定时任务 -- 内部接口")
    @PostMapping("/internal")
    public ResponseEntity<QuartzTaskDTO> createByServiceCodeAndMethodCode(
            @RequestParam("source_level") String sourceLevel,
            @RequestParam("source_id") Long sourceId,
            @RequestBody @Valid ScheduleTask dto) {
        ScheduleTaskValidator.validatorCreate(dto);
        return new ResponseEntity<>(scheduleTaskService.createByServiceCodeAndMethodCode(dto, sourceLevel, sourceId), HttpStatus.OK);
    }

}

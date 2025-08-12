package com.ale.starblog.admin.common;

import com.ale.starblog.admin.common.utils.AuthenticationUtils;
import com.ale.starblog.framework.common.domain.JsonPageResult;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.support.Option;
import com.ale.starblog.framework.workflow.entity.FlowDefinition;
import com.ale.starblog.framework.workflow.entity.FlowEntity;
import com.ale.starblog.framework.workflow.entity.FlowHistoryInstance;
import com.ale.starblog.framework.workflow.model.AssigneeConfig;
import com.ale.starblog.framework.workflow.model.node.FlowNode;
import com.ale.starblog.framework.workflow.query.HistoryInstanceQuery;
import com.ale.starblog.framework.workflow.service.DefinitionService;
import com.ale.starblog.framework.workflow.service.InstanceService;
import com.ale.starblog.framework.workflow.service.QueryService;
import com.ale.starblog.framework.workflow.service.TaskService;
import com.ale.starblog.framework.workflow.support.FinishTaskParam;
import com.ale.starblog.framework.workflow.support.InstanceExecutionInfo;
import com.ale.starblog.framework.workflow.support.StartInstanceParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程引擎测试接口
 *
 * @author Ale
 * @version 1.0.0 2025/6/27 15:07
 */
@RestController
@RequestMapping("/flow")
@RequiredArgsConstructor
public class FlowController {

    /**
     * 流程定义服务
     */
    private final DefinitionService definitionService;

    /**
     * 流程实例服务
     */
    private final InstanceService instanceService;

    /**
     * 流程任务服务
     */
    private final TaskService taskService;

    /**
     * 查询服务
     */
    private final QueryService queryService;


    /**
     * 部署流程定义
     *
     * @param flowDefinition 流程定义
     * @return 部署ID
     */
    @PostMapping("/definition")
    public JsonResult<String> deploy(@RequestBody FlowDefinition flowDefinition) {
        return JsonResult.success(JsonResult.GENERIC_SUCCESS_MSG, this.definitionService.deploy(flowDefinition));
    }

    /**
     * 启动流程实例
     *
     * @param startInstanceDTO 启动流程实例参数
     * @return 流程实例ID
     */
    @PostMapping("/instance")
    public JsonResult<String> startInstance(@RequestBody StartInstanceDTO startInstanceDTO) {
        return JsonResult.success(
            JsonResult.GENERIC_SUCCESS_MSG,
            this.instanceService.startInstance(
                StartInstanceParam.builder()
                    .definitionId(startInstanceDTO.getDefinitionId())
                    .businessId(startInstanceDTO.getBusinessId())
                    .businessType(startInstanceDTO.getBusinessType())
                    .variable(startInstanceDTO.getVariable())
                    .starterId(AuthenticationUtils.getLoginUserId().toString())
                    .build()
            ).getId()
        );
    }

    /**
     * 撤销流程实例
     *
     * @param instanceId 流程实例ID
     * @return 是否成功
     */
    @PutMapping("/instance/revoke/{instanceId}")
    public JsonResult<Boolean> revokeInstance(@PathVariable String instanceId) {
        return JsonResult.success(this.instanceService.revoke(instanceId));
    }

    /**
     * 重启流程实例
     *
     * @param instanceId 流程实例ID
     * @return 流程实例ID
     */
    @PutMapping("/instance/restart/{instanceId}")
    public JsonResult<String> restartInstance(@PathVariable String instanceId) {
        return JsonResult.success(
            JsonResult.GENERIC_SUCCESS_MSG,
            this.instanceService.restartInstance(instanceId, null, "1").getId()
        );
    }

    /**
     * 追加节点
     *
     * @param instanceId 实例ID
     * @param flowNode   追加节点
     * @return 是否成功
     */
    @PutMapping("/instance/append-node/{instanceId}")
    public JsonResult<Void> appendNode(@PathVariable String instanceId, @RequestBody FlowNode flowNode) {
        this.instanceService.appendNode(instanceId, flowNode);
        return JsonResult.success();
    }

    /**
     * 完成任务
     *
     * @param completeTaskDTO 完成任务参数
     * @return 是否成功
     */
    @PutMapping("/complete/task")
    public JsonResult<Boolean> completeTask(@RequestBody CompleteTaskDTO completeTaskDTO) {
        return JsonResult.success(
            this.taskService.completeTask(
                FinishTaskParam.builder()
                    .taskId(completeTaskDTO.getTaskId())
                    .assigneeId(completeTaskDTO.getAssigneeId())
                    .comment(completeTaskDTO.getComment())
                    .variable(completeTaskDTO.getVariable())
                    .variableLevel(completeTaskDTO.getVariableLevel())
                    .attachments(completeTaskDTO.getAttachments())
                    .build()
            )
        );
    }

    /**
     * 驳回任务
     *
     * @param rejectTaskDTO 驳回任务参数
     * @return 是否成功
     */
    @PutMapping("/reject/task")
    public JsonResult<Boolean> rejectTask(@RequestBody CompleteTaskDTO rejectTaskDTO) {
        return JsonResult.success(
            this.taskService.rejectTask(
                FinishTaskParam.builder()
                    .taskId(rejectTaskDTO.getTaskId())
                    .assigneeId(rejectTaskDTO.getAssigneeId())
                    .comment(rejectTaskDTO.getComment())
                    .variable(rejectTaskDTO.getVariable())
                    .build()
            )
        );
    }

    /**
     * 转办任务
     *
     * @param transferTaskDTO 转办任务参数
     * @return 是否成功
     */
    @PutMapping("/transfer/task")
    public JsonResult<Boolean> transferTask(@RequestBody TransferTaskDTO transferTaskDTO) {
        return JsonResult.success(
            this.taskService.transferTask(
                transferTaskDTO.getAgentId(),
                FinishTaskParam.builder()
                    .taskId(transferTaskDTO.getTaskId())
                    .assigneeId(transferTaskDTO.getAssigneeId())
                    .comment(transferTaskDTO.getComment())
                    .variable(transferTaskDTO.getVariable())
                    .build()
            )
        );
    }

    /**
     * 委派任务
     *
     * @param transferTaskDTO 委派任务参数
     * @return 是否成功
     */
    @PutMapping("/delegate/task")
    public JsonResult<Boolean> delegateTask(@RequestBody TransferTaskDTO transferTaskDTO) {
        return JsonResult.success(
            this.taskService.delegateTask(
                transferTaskDTO.getAgentId(),
                FinishTaskParam.builder()
                    .taskId(transferTaskDTO.getTaskId())
                    .assigneeId(transferTaskDTO.getAssigneeId())
                    .comment(transferTaskDTO.getComment())
                    .variable(transferTaskDTO.getVariable())
                    .build()
            )
        );
    }

    /**
     * 拿回任务
     *
     * @param reclaimTaskDTO 回收任务参数
     * @return 是否成功
     */
    @PutMapping("/reclaim/task")
    public JsonResult<Boolean> reclaimTask(@RequestBody CompleteTaskDTO reclaimTaskDTO) {
        return JsonResult.success(
            this.taskService.reclaimTask(
                reclaimTaskDTO.getTaskId(),
                reclaimTaskDTO.getAssigneeId()
            )
        );
    }

    /**
     * 撤销任务
     *
     * @param taskId 任务ID
     * @return 是否成功
     */
    @PutMapping("/revoke/task/{taskId}")
    public JsonResult<Boolean> revokeTask(@PathVariable String taskId) {
        return JsonResult.success(
            this.taskService.revokeTask(taskId)
        );
    }

    /**
     * 获取可回退的节点选项
     *
     * @param taskId 流程任务ID
     * @return 可回滚的节点选项
     */
    @GetMapping("/task/rollback-node/{taskId}")
    public JsonResult<List<Option>> fetchRollbackNodeIds(@PathVariable String taskId) {
        return JsonResult.success(
            this.queryService.fetchRollbackNodeOption(taskId)
        );
    }

    /**
     * 回滚任务
     *
     * @param rollbackTaskDTO 回滚任务参数
     * @return 是否成功
     */
    @PutMapping("/rollback/task")
    public JsonResult<Boolean> rollbackTask(@RequestBody RollbackTaskDTO rollbackTaskDTO) {
        return JsonResult.success(
            this.taskService.rollbackTask(
                rollbackTaskDTO.getRollbackNodeId(),
                rollbackTaskDTO.getJumpCurrentNode(),
                rollbackTaskDTO.getRollbackTaskParam()
            )
        );
    }

    /**
     * 添加任务处理人
     *
     * @param taskId          任务ID
     * @param assigneeConfigs 任务处理人
     * @return 是否成功
     */
    @PutMapping("/task/add-assignee/{taskId}")
    public JsonResult<Boolean> addAssignee(@PathVariable String taskId, @RequestBody List<AssigneeConfig> assigneeConfigs) {
        return JsonResult.success(
            this.taskService.addAssignee(
                taskId,
                assigneeConfigs
            )
        );
    }

    /**
     * 移除任务处理人
     *
     * @param taskId      任务ID
     * @param assigneeIds 任务处理人ID
     * @return 是否成功
     */
    @PutMapping("/task/remove-assignee/{taskId}")
    public JsonResult<Boolean> removeAssignee(@PathVariable String taskId, @RequestBody List<String> assigneeIds) {
        return JsonResult.success(
            this.taskService.removeAssignee(
                taskId,
                assigneeIds
            )
        );
    }

    /**
     * 获取用户参与过的流程实例
     *
     * @param pageable   分页参数
     * @param assigneeId 处理人ID
     * @return 历史实例列表
     */
    @GetMapping("/history-instance/participant")
    public JsonResult<JsonPageResult.PageData<FlowHistoryInstance>> fetchAssigneeHistoryInstance(Pageable pageable, @RequestParam String assigneeId) {
        // 其余查询可参考该方式
        HistoryInstanceQuery historyInstanceQuery = this.queryService.createHistoryInstanceQuery();
        Page<FlowHistoryInstance> historyInstancePage = historyInstanceQuery.assigneeId(assigneeId)
            .orderByDesc(FlowEntity.Fields.createdAt)
            .page(pageable);
        return JsonPageResult.of(historyInstancePage);
    }

    /**
     * 获取流程图节点信息
     *
     * @param instanceId 流程实例ID
     * @return 节点信息
     */
    @GetMapping("/instance/node")
    public JsonResult<FlowNode> fetchNodeByInstanceId(@RequestParam String instanceId) {
        FlowNode node = this.queryService.fetchNodeByInstanceId(instanceId);
        return JsonResult.success(node);
    }

    /**
     * 获取流程实例执行信息
     *
     * @param instanceId 流程实例ID
     * @return 执行信息
     */
    @GetMapping("/instance/execution")
    public JsonResult<InstanceExecutionInfo> fetchInstanceExecutionInfo(@RequestParam String instanceId) {
        return JsonResult.success(this.queryService.fetchInstanceExecutionInfo(instanceId));
    }
}

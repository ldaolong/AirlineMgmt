package processes;


public class ActivitiEngine
{
    
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        //  DbSchemaCreate.main(args);
        /**
        ProcessEngineConfiguration
        .createProcessEngineConfigurationFromResourceDefault()
        .setDatabaseSchemaUpdate(ProcessEngineConfigurationImpl.DB_SCHEMA_UPDATE_CREATE)
        .buildProcessEngine();
        **/
        
        // demotest();
        
        //applyVacation();
       // ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
      //  RuntimeService runtimeService = processEngine.getRuntimeService();
       // runtimeService.startProcessInstanceByKey("applyVacationProcess","bizTest");
       // processEngine.getTaskService().createTaskQuery().
    }
    
    /**
    private static void applyVacation()
    {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        
        //create deployment
      //  repositoryService.createDeployment().addClasspathResource("ApplyVacation.bpmn").deploy();
        List<String> list = repositoryService.getDeploymentResourceNames("27501");
        for (String tmp : list)
        {
            System.out.println("deployment resouces: " + tmp);
        }
        
        //  ProcessDefinition  pd = repositoryService.getProcessDefinition("applyVacationProcess:1:27504");
        
        // 启动流程实例
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employeeName", "Kermit");
        variables.put("numberOfDays", new Integer(4));
        variables.put("vacationMotivation", "I'm really tired!");
        variables.put("numberOfDays", new Integer(4));
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("applyVacationProcess",
                variables); //processInstance.getProcessInstanceId()
        
        //reset vars
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
        if(tasks.size()>0){
            Task task = tasks.get(0);
            task.setAssignee("DarrenLi");
            task.setOwner("wang");
            task.setPriority(80);
            task.setCategory("Apply");taskService.saveTask(task);
            
            Map<String, Object> taskVariables = new HashMap<String, Object>();
            taskVariables.put("vacationApproved", "false");
            taskVariables.put("managerMotivation", "We have a tight deadline!");
            taskService.complete(task.getId(), taskVariables);
        }
        //   RuntimeService runtimeService = processEngine.getRuntimeService();
        
        //ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("applyVacationProcess:1:27504");
        
       
        
        tasks = taskService.createTaskQuery()
                .taskCandidateGroup("management")
                .list();
        for (Task task : tasks)
        {
            
            System.out.println("Task available: " + task.getName()+"  assignee: "+ task.getAssignee());
            
        }
        
        List<Task> tasklist = processEngine.getTaskService()//
                .createTaskQuery()
                //
                .taskAssignee("DarrenLi")
                //个人任务的查询
                .list();
        
        System.out.println("DarrenLi tasklist: " + tasklist.size());
        
        if (tasks.size() > 0)
        {
            Task task = tasks.get(0);
            Map<String, Object> taskVariables = new HashMap<String, Object>();
            taskVariables.put("vacationApproved", "false");
            taskVariables.put("managerMotivation", "We have a tight deadline!");
            taskService.complete(task.getId(), taskVariables);
        }
        
        tasks = taskService.createTaskQuery()
                .taskCandidateGroup("management")
                .list();
        for (Task task : tasks)
        {
            
            System.out.println("Task available: " + task.getName());
            
        }
        
        System.out.println("createProcessInstanceQuery: "
                + runtimeService.createProcessInstanceQuery().count());
        if (tasks.size() > 0)
        {
            Task task = tasks.get(0);
            Map<String, Object> taskVariables = new HashMap<String, Object>();
            taskVariables.put("vacationApproved", "false");
            taskVariables.put("managerMotivation", "We have a tight deadline!");
            taskService.complete(task.getId(), taskVariables);
        }
        
        tasks = taskService.createTaskQuery()
                .taskCandidateGroup("management")
                .list();
        System.out.println("tasks: " + tasks.size());
        System.out.println("createProcessInstanceQuery: "
                + runtimeService.createProcessInstanceQuery().count());
        
    }
    
    private static void demotest()
    {
        
        //1 define a engine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        
        //2 load process resource
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment()
                .addClasspathResource("Activiti Test.bpmn")
                .deploy();
        System.out.println("Number of process definitions: "
                + repositoryService.createProcessDefinitionQuery().count());
        
        // repositoryService.createDeployment();
        
        //3
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employeeName", "Darren Li");
        variables.put("numberOfDays", new Integer(4));
        variables.put("vacationMotivation", "I'm really tired!");
        
        // 启动流程实例
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess",
                variables);
        
        System.out.println("Number of process instances: "
                + runtimeService.createProcessInstanceQuery().count());
        
        runtimeService.createExecutionQuery().activityId("");
        
        //4 Fetch all tasks for the management group
        TaskService taskService = processEngine.getTaskService();
        
        //      taskService.addCandidateGroup("usertask2", "management");
        List<Task> tasks = taskService.createTaskQuery()
                .taskCandidateGroup("management")
                .list();
        //      List<Task> tasks = taskService.createTaskQuery().list();
        for (Task task : tasks)
        {
            System.out.println("Task available: " + task.getName());
            
            Map<String, Object> taskVariables = new HashMap<String, Object>();
            taskVariables.put("vacationApproved", "true");
            taskVariables.put("managerMotivation", "No problem!");
            taskService.complete(task.getId(), taskVariables);
            
            System.out.println("Task complete: " + task.getName()
                    + "/employeeName:" + taskVariables.get("employeeName"));
        }
        
        //activiti:candidateGroups="emplyee"
        tasks = taskService.createTaskQuery()
                .taskCandidateGroup("emplyee")
                .list();
        for (Task task : tasks)
        {
            System.out.println("Task available: " + task.getName());
            
            Map<String, Object> taskVariables = new HashMap<String, Object>();
            taskVariables.put("vacationApproved", "true");
            taskVariables.put("managerMotivation", "No problem!");
            taskService.complete(task.getId(), taskVariables);
            
            System.out.println("Task complete: " + task.getName()
                    + "/employeeName:" + taskVariables.get("employeeName"));
        }
        
        // 核实流程是否结束,输出流程结束时间
        
        HistoricProcessInstance historicProcessInstance = processEngine.getHistoryService()
                .createHistoricProcessInstanceQuery()
                
                .processInstanceId("myProcess")
                .singleResult();
        
        System.out.println("Process instance end time: "
                + historicProcessInstance.getEndTime());
        
    }
    **/
}

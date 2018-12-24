package com.darren.machine.domain;

public class AirlineRuleEntity extends BaseEntity
{
    
    private String rule;
    
    public AirlineRuleEntity(){
        
    }
    
    public AirlineRuleEntity(Long id){
        this.id = id;
    }

    public String getRule()
    {
        return rule;
    }

    public void setRule(String rule)
    {
        this.rule = rule;
    }

}

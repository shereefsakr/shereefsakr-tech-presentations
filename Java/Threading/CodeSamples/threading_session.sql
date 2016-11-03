--------------------------------------------------------
--  DDL for Queue QUEUE_200
--------------------------------------------------------   
   
   BEGIN DBMS_AQADM.CREATE_QUEUE_TABLE(
     Queue_table        => 'URLs_QUEUE_TABLE',
     Queue_payload_type => 'SYS.AQ$_JMS_TEXT_MESSAGE',
     storage_clause     => 'PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 TABLESPACE USERS',
     Sort_list          => 'ENQ_TIME',
     Compatible         => '8.1.3');
 

DBMS_AQADM.CREATE_QUEUE(
     Queue_name          => 'URLs_QUEUE',
     Queue_table         => 'URLs_QUEUE_TABLE',
     Queue_type          =>  0,
     Max_retries         =>  5,
     Retry_delay         =>  0,
     dependency_tracking =>  FALSE);
  END;
/

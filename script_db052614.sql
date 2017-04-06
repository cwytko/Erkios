drop database if exists ST_RASScheme;
create database if not exists ST_RASScheme;

USE ST_RASScheme;

create table sra_classRAS (
    cra_idClassification mediumint not null auto_increment,
    cra_nameClass varchar(255) not null,
    PRIMARY KEY (cra_idClassification)
);

insert into sra_classRAS (cra_nameClass) values('Event-Based');
insert into sra_classRAS (cra_nameClass) values('Response-Based');

create table sra_RASSchemes (
    ras_idRAS varchar(7) not null,
    ras_idClassification mediumint,
    ras_nameRAS varchar(20) not null,
    PRIMARY KEY (ras_idRAS),
    FOREIGN KEY (ras_idClassification)
        REFERENCES sra_classRAS (cra_idClassification)
);

insert into sra_RASSchemes values ('10-bus', 1, '10-bus NASPI System');


create table sra_parametersRAS (
    pra_idParameter mediumint not null auto_increment,
    pra_idRAS varchar(7) not null,
    pra_paramContingencies boolean,
    PRIMARY KEY (pra_idParameter),
    FOREIGN KEY (pra_idRAS)
        REFERENCES sra_RASSchemes (ras_idRAS)
);
	
insert into sra_parametersRAS 
(pra_idRAS,pra_paramContingencies) 
values ('10-bus',true);


create table sra_contingencies (
    con_idContingency mediumint not null auto_increment,
    con_idRAS varchar(7),
    con_checkContingency boolean default false,
    con_contingency varchar(255) not null,
    PRIMARY KEY (con_idContingency),
    FOREIGN KEY (con_idRAS)
        REFERENCES sra_RASSchemes (ras_idRAS)
);

insert into sra_contingencies (con_idRAS, con_contingency) values
('10-bus','Bus 1 Failure');
insert into sra_contingencies (con_idRAS, con_contingency) values
('10-bus','Bus 2 Failure');
insert into sra_contingencies (con_idRAS, con_contingency) values
('10-bus','Bus 3 Failure');
insert into sra_contingencies (con_idRAS, con_contingency) values
('10-bus','Bus 4 Failure');
insert into sra_contingencies (con_idRAS, con_contingency) values
('10-bus','Bus 5 Failure');
insert into sra_contingencies (con_idRAS, con_contingency) values
('10-bus','Bus 6 Failure');
insert into sra_contingencies (con_idRAS, con_contingency) values
('10-bus','Bus 7 Failure');
insert into sra_contingencies (con_idRAS, con_contingency) values
('10-bus','Bus 8 Failure');
insert into sra_contingencies (con_idRAS, con_contingency) values
('10-bus','Bus 9 Failure');
insert into sra_contingencies (con_idRAS, con_contingency) values
('10-bus','Bus 10 Failure');
insert into sra_contingencies (con_idRAS, con_contingency) values
('10-bus','Generator 1 Failure');
insert into sra_contingencies (con_idRAS, con_contingency) values
('10-bus','Generator 2 Failure');


create table sra_remedialActions (
    rac_idRemedialAction mediumint not null auto_increment,
    rac_idRAS varchar(7) not null,
    rac_remedialAction varchar(255) not null,
    PRIMARY KEY (rac_idRemedialAction),
    FOREIGN KEY (rac_idRAS)
        REFERENCES sra_RASSchemes (ras_idRAS)
);

insert into sra_remedialActions (rac_idRAS, rac_remedialAction) values ('10-bus','RA 1');
insert into sra_remedialActions (rac_idRAS, rac_remedialAction) values ('10-bus','RA 2');
insert into sra_remedialActions (rac_idRAS, rac_remedialAction) values ('10-bus','RA 3')
insert into sra_remedialActions (rac_idRAS, rac_remedialAction) values ('10-bus','RA 4')
insert into sra_remedialActions (rac_idRAS, rac_remedialAction) values ('10-bus','RA 5')
insert into sra_remedialActions (rac_idRAS, rac_remedialAction) values ('10-bus','RA 6');
insert into sra_remedialActions (rac_idRAS, rac_remedialAction) values ('10-bus','RA 7');
insert into sra_remedialActions (rac_idRAS, rac_remedialAction) values ('10-bus','RA 8');
insert into sra_remedialActions (rac_idRAS, rac_remedialAction) values ('10-bus','RA 9');
insert into sra_remedialActions (rac_idRAS, rac_remedialAction) values ('10-bus','RA 10');

create table sra_RASTest (
    tra_idRASTest mediumint not null auto_increment,
    tra_idRAS varchar(7) not null,
    tra_idClassification mediumint not null,
    tra_dateTimeBeginTest datetime not null,
    tra_resultTest boolean default null,
    tra_dateTimeEndTest datetime default null,
    tra_comment varchar(255) default ' ',
    PRIMARY KEY (tra_idRASTest),
    FOREIGN KEY (tra_idRAS)
        REFERENCES sra_RASSchemes (ras_idRAS),
    FOREIGN KEY (tra_idClassification)
        REFERENCES sra_classRAS (cra_idClassification)
);

create table sra_RASTestContingencies (
    rtc_idRASTestContingency mediumint not null auto_increment,
    rtc_idContingency mediumint not null,
    rtc_idRASTest mediumint not null,
    rtc_contingency varchar(255) not null,
    PRIMARY KEY (rtc_idRASTestContingency),
    FOREIGN KEY (rtc_idContingency)
        REFERENCES sra_contingencies (con_idContingency),
    FOREIGN KEY (rtc_idRASTest)
        REFERENCES sra_RASTest (tra_idRASTest)
);

create table sra_RASTestRemedialActions (
    rtr_idRASTestRemedialAction mediumint not null auto_increment,
    rtr_idRemedialAction mediumint not null,
    rtr_idRASTest mediumint not null,
    rtr_remedialAction varchar(255) not null,
    PRIMARY KEY (rtr_idRASTestRemedialAction),
    FOREIGN KEY (rtr_idRemedialAction)
        REFERENCES sra_remedialActions (rac_idRemedialAction),
    FOREIGN KEY (rtr_idRASTest)
        REFERENCES sra_RASTest (tra_idRASTest)
);



create table sra_disableEnableAction (
    dea_idAction mediumint not null auto_increment,
    dea_Action varchar(7) not null,
    PRIMARY KEY (dea_idAction)
);

insert into sra_disableEnableAction (dea_Action) values ('Enable');
insert into sra_disableEnableAction (dea_Action) values ('Disable');

create table sra_RASTestDisableEnableComponents (
    dec_idDisableEnableComponent mediumint not null auto_increment,
    dec_idRAS varchar(7) not null,
    dec_idAction mediumint not null,
    dec_idRASTest mediumint not null,
    dec_disableEnableSensor boolean not null,
    dec_datetimeSensor datetime not null,
    dec_disableEnableActuator boolean not null,
    dec_datetimeActuator datetime not null,
    dec_resultDisableEnableComponent boolean not null,
    dec_comment varchar(255) not null,
    PRIMARY KEY (dec_idDisableEnableComponent),
    FOREIGN KEY (dec_idRAS)
        REFERENCES sra_RASSchemes (ras_idRAS),
    FOREIGN KEY (dec_idAction)
        REFERENCES sra_disableEnableAction (dea_idAction),
    FOREIGN KEY (dec_idRASTest)
        REFERENCES sra_RASTest (tra_idRASTest)
);

create table sra_componentsEES (
    cee_idComponent mediumint not null auto_increment,
    cee_shortNameComponent varchar(15) not null,
    cee_nameComponent varchar(40) not null,
    PRIMARY KEY (cee_idComponent)
);
insert into sra_componentsEES (cee_shortNameComponent, cee_nameComponent) values ('CA','Central Applications');
insert into sra_componentsEES (cee_shortNameComponent, cee_nameComponent) values ('CTS','Control Test System');
insert into sra_componentsEES (cee_shortNameComponent, cee_nameComponent) values ('TestWan','Test WAN');
insert into sra_componentsEES (cee_shortNameComponent, cee_nameComponent) values ('OpWAN','Operation WAN');
insert into sra_componentsEES (cee_shortNameComponent, cee_nameComponent) values ('LTSSensor','Local Test System Sensor');
insert into sra_componentsEES (cee_shortNameComponent, cee_nameComponent) values ('DAC','Data Acquisition and Control');
insert into sra_componentsEES (cee_shortNameComponent, cee_nameComponent) values ('PTCT','Power Transformer/Current Transformer');
insert into sra_componentsEES (cee_shortNameComponent, cee_nameComponent) values ('SwitchSensor1','Switch Sensor 1');
insert into sra_componentsEES (cee_shortNameComponent, cee_nameComponent) values ('Sensor','Sensor');
insert into sra_componentsEES (cee_shortNameComponent, cee_nameComponent) values ('SwitchSensor2','Switch Sensor 2');
insert into sra_componentsEES (cee_shortNameComponent, cee_nameComponent) values ('LTSActuator','Local Test System Actuator');
insert into sra_componentsEES (cee_shortNameComponent, cee_nameComponent) values ('RAS','Remedial Action Scheme');
insert into sra_componentsEES (cee_shortNameComponent, cee_nameComponent) values ('SwitchActuator','Switch Actuator');
insert into sra_componentsEES (cee_shortNameComponent, cee_nameComponent) values ('Actuator','Actuator');
insert into sra_componentsEES (cee_shortNameComponent, cee_nameComponent) values ('None','None Component');
insert into sra_componentsEES (cee_shortNameComponent, cee_nameComponent) values ('All','All Components');

create table sra_directionLink (
    dil_idDirLink mediumint not null auto_increment,
    dil_directionLink varchar(15) not null,
    PRIMARY KEY (dil_idDirLink)
);

insert into sra_directionLink (dil_directionLink) values ('unidirectional');
insert into sra_directionLink (dil_directionLink) values ('bidirectional');

create table sra_typeSignal (
    tsi_idSignal mediumint not null auto_increment,
    tsi_nameSignal varchar(20) not null,
    PRIMARY KEY (tsi_idSignal)
);

insert into sra_typeSignal (tsi_nameSignal) values ('Digital Signal');
insert into sra_typeSignal (tsi_nameSignal) values ('Test Control Signal');
insert into sra_typeSignal (tsi_nameSignal) values ('Analog Signal');

create table sra_linkEES (
    cee_idLink mediumint not null auto_increment,
    cee_source mediumint,
    cee_destination mediumint,
    cee_directionLink mediumint,
    cee_idSignal mediumint,
    PRIMARY KEY (cee_idLink),
    FOREIGN KEY (cee_source)
        REFERENCES sra_componentsEES (cee_idComponent),
    FOREIGN KEY (cee_destination)
        REFERENCES sra_componentsEES (cee_idComponent),
    FOREIGN KEY (cee_directionLink)
        REFERENCES sra_directionLink (dil_idDirLink),
    FOREIGN KEY (cee_idSignal)
        REFERENCES sra_typeSignal (tsi_idSignal)
);

insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (1,2,2,1);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (2,3,1,2);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (2,4,1,1);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (3,2,1,2);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (3,5,1,2);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (3,11,1,2);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (4,2,1,1);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (4,11,1,1);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (5,3,1,2);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (5,4,1,1);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (5,6,1,1);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (5,8,1,1);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (5,10,1,2);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (6,8,1,3);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (7,8,1,3);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (8,9,1,3);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (9,10,1,1);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (10,5,1,1);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (11,3,1,2);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (11,12,1,1);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (11,13,1,2);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (12,13,1,1);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (13,11,1,1);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (13,14,1,1);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (14,11,1,1);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (9,9,1,1);
insert into sra_linkEES (cee_source,cee_destination,cee_directionLink,cee_idSignal) values (14,14,1,1);

create table sra_typeAction (
    tac_idTypeAction mediumint not null auto_increment,
    tac_typeAction varchar(25) not null,
    PRIMARY KEY (tac_idTypeAction)
);

insert into sra_typeAction (tac_typeAction) values ('CreateMessage');
insert into sra_typeAction (tac_typeAction) values ('EnableComponent');
insert into sra_typeAction (tac_typeAction) values ('DisableComponent');
insert into sra_typeAction (tac_typeAction) values ('SendMessageTest');
insert into sra_typeAction (tac_typeAction) values ('Error');
insert into sra_typeAction (tac_typeAction) values ('SendMessagePTCT');
insert into sra_typeAction (tac_typeAction) values ('SendMessageDAC');
insert into sra_typeAction (tac_typeAction) values ('SendMessageRAS');
insert into sra_typeAction (tac_typeAction) values ('SendMessageCA');
insert into sra_typeAction (tac_typeAction) values ('ReportSuccess');
insert into sra_typeAction (tac_typeAction) values ('LogTest');

create table sra_RASTestLink (
    rtl_idRASTestLink mediumint not null auto_increment,
    rtl_idLinkEES mediumint not null,
    rtl_idRASTest mediumint not null,
    rtl_idTypeAction mediumint not null,
    rtl_resultLink boolean,
    rtl_stateComponent boolean,
    rtl_comment varchar(3500),
    PRIMARY KEY (rtl_idRASTestLink),
    FOREIGN KEY (rtl_idLinkEES)
        REFERENCES sra_linkEES (cee_idLink),
    FOREIGN KEY (rtl_idRASTest)
        REFERENCES sra_RASTest (tra_idRASTest),
    FOREIGN KEY (rtl_idTypeAction)
        REFERENCES sra_typeAction (tac_idTypeAction)
);

create table sra_STCode (
    stc_idSTCode mediumint not null auto_increment,
    stc_codeName varchar(40),
	stc_shortCodeName varchar(20),
    stc_comment varchar(200),
    PRIMARY KEY (stc_idSTCode)
);

insert into sra_STCode (stc_codeName, stc_shortCodeName, stc_comment) values ('Berger Code','Berger','Simple form of coding that appends a special set of bits, called the check bits, to each word of data');
insert into sra_STCode (stc_codeName, stc_shortCodeName, stc_comment) values ('Swap & Compare Code-Duplication','Duplication','This code is based on the concept of completely duplicating the original data to form the codeword');
insert into sra_STCode (stc_codeName, stc_shortCodeName, stc_comment) values ('Residue Code-Checksum','Residue','This checksum composes a word of double length by concatenating 2 consecutive words');

create table sra_parameters (
	spa_idParameter mediumint not null auto_increment,
	spa_nameParameter varchar(30),
	PRIMARY KEY (spa_idParameter)
);

insert into sra_parameters(spa_nameParameter) values ('None');
insert into sra_parameters(spa_nameParameter) values ('CreateMessage');
insert into sra_parameters(spa_nameParameter) values ('EnableComponentSensor');
insert into sra_parameters(spa_nameParameter) values ('DisableComponentSensor');
insert into sra_parameters(spa_nameParameter) values ('EnableComponentActuator');
insert into sra_parameters(spa_nameParameter) values ('DisableComponentActuator');
insert into sra_parameters(spa_nameParameter) values ('Contingency');

create table sra_RASTestSTCode (
    rts_idRASTestSTCode mediumint not null auto_increment,
    rts_idSTCode mediumint not null,
    rts_idComponent mediumint not null,
    rts_idRASTest mediumint not null,
	rts_idTypeAction mediumint not null,
	rts_idCodeWordParamError mediumint default 1,
	rts_codeWordCreateMessage varchar(100),
    rts_codeWordDisableEnableSensor varchar(100),
	rts_codeWordDisableEnableActuator varchar(100),
	rts_codeWordError varchar(100),
    PRIMARY KEY (rts_idRASTestSTCode),
    FOREIGN KEY (rts_idSTCode)
        REFERENCES sra_STCode (stc_idSTCode),
    FOREIGN KEY (rts_idComponent)
        REFERENCES sra_componentsEES (cee_idComponent),
    FOREIGN KEY (rts_idRASTest)
        REFERENCES sra_RASTest (tra_idRASTest),
	FOREIGN KEY (rts_idTypeAction)
		REFERENCES sra_typeAction (tac_idTypeAction),
	FOREIGN KEY (rts_idCodeWordParamError)
		REFERENCES sra_parameters(spa_idParameter)
);

create table sra_RASTestSTCodeContingency (
	rsc_idRASTestSTCodeContingency mediumint not null auto_increment,
	rsc_idContingency mediumint not null,
	rsc_idSTCode mediumint not null,
	rsc_idComponent mediumint,
	rsc_idRASTest mediumint not null,
	rsc_idTypeAction mediumint not null,
	rsc_codeWordContingecy varchar(3500),
	rsc_codeWordError varchar(3500),
	PRIMARY KEY (rsc_idRASTestSTCodeContingency),
	FOREIGN KEY (rsc_idContingency)
		REFERENCES sra_RASTestContingencies (rtc_idRASTestContingency),
	FOREIGN KEY (rsc_idSTCode)
        REFERENCES sra_STCode (stc_idSTCode),
    FOREIGN KEY (rsc_idComponent)
        REFERENCES sra_componentsEES (cee_idComponent),
    FOREIGN KEY (rsc_idRASTest)
        REFERENCES sra_RASTest (tra_idRASTest),
	FOREIGN KEY (rsc_idTypeAction)
		REFERENCES sra_typeAction (tac_idTypeAction)
);

# Database System 

Homebrew SQL database system, built from the ground up. Works with the following SQL [Syntax](#syntax).

## Syntax 
    <sqlCompiler.sqlCommands>        ::=  <CommandType>;

    <CommandType>    ::=  <Use> | <Create> | <Drop> | <Alter> | <Insert> |
                      <Select> | <Update> | <Delete> | <Join>

    <Use>            ::=  USE <DatabaseName>

    <Create>         ::=  <CreateDatabase> | <CreateTable>

    <CreateDatabase> ::=  CREATE DATABASE <DatabaseName>

    <CreateTable>    ::=  CREATE TABLE <TableName> | CREATE TABLE <TableName> ( <AttributeList> )

    <Drop>           ::=  DROP <Structure> <StructureName>

    <Structure>      ::=  DATABASE | TABLE

    <Alter>          ::=  ALTER TABLE <TableName> <AlterationType> <AttributeName>

    <Insert>         ::=  INSERT INTO <TableName> VALUES ( <ValueList> )

    <Select>         ::=  SELECT <WildAttribList> FROM <TableName> |
                          SELECT <WildAttribList> FROM <TableName> WHERE <Condition> 

    <Update>         ::=  UPDATE <TableName> SET <NameValueList> WHERE <Condition> 

    <Delete>         ::=  DELETE FROM <TableName> WHERE <Condition>

    <Join>           ::=  JOIN <TableName> AND <TableName> ON <AttributeName> AND <AttributeName>

    <NameValueList>  ::=  <NameValuePair> | <NameValuePair> , <NameValueList>

    <NameValuePair>  ::=  <AttributeName> = <Value>

    <AlterationType> ::=  ADD | DROP

    <ValueList>      ::=  <Value>  |  <Value> , <ValueList>

    <Value>          ::=  '<StringLiteral>'  |  <BooleanLiteral>  |  <FloatLiteral>  |  <IntegerLiteral>

    <BooleanLiteral> ::=  true | false

    <WildAttribList> ::=  <AttributeList> | *

    <AttributeList>  ::=  <AttributeName> | <AttributeName> , <AttributeList>

    <Condition>      ::=  ( <Condition> ) AND ( <Condition> )  |
                          ( <Condition> ) OR ( <Condition> )   |
                          <AttributeName> <Operator> <Value>

    <Operator>       ::=   ==   |   >   |   <   |   >=   |   <=   |   !=   |   LIKE
    
## Disclaimer
This work was submitted for the COMSM0103 Object Oriented Programming with Java module at the University of Bristol. Please note that no student can use this work without my permission or attempt to pass this work off as their own.

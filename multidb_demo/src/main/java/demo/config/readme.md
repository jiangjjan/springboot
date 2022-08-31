RoutingDataSource extends AbstractRoutingDataSource
使用AbstractRoutingDataSource 实现数据源的切换,
 局限性: 开启事务后不支持切换数据源, 加事务的方法只能对单一的数据源进行操作
 
 使用事务时,一定要与切换数据的aop一起使用,否则会使用默认的数据源
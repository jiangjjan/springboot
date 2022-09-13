package security.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.List;
import java.util.Set;

@RegisterMapper
public interface InsertAllMapper<T> {

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含`id`属性并且必须为自增列
     *
     * @param recordList
     * @return
     */
    @Options(useGeneratedKeys = true)
    @InsertProvider(type = SpecialProvider.class, method = "dynamicSQL")
    int insertAll(List<? extends T> recordList);

      class  SpecialProvider extends MapperTemplate {

        public SpecialProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
            super(mapperClass, mapperHelper);
        }

        /**
         * 批量插入
         *
         * @param ms
         */
        public String insertAll(MappedStatement ms) {
            final Class<?> entityClass = getEntityClass(ms);
            //开始拼sql
            StringBuilder sql = new StringBuilder();
            sql.append("<bind name=\"listNotEmptyCheck\" value=\"@tk.mybatis.mapper.util.OGNL@notEmptyCollectionCheck(list, '" + ms.getId() + " 方法参数为空')\"/>");
            sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass), "list[0]"));
            sql.append(SqlHelper.insertColumns(entityClass, true, false, false));
            sql.append(" VALUES ");
            sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
            sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
            //获取全部列
            Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
            //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
            for (EntityColumn column : columnList) {
                if (!column.isId() && column.isInsertable()) {
                    sql.append(column.getColumnHolder("record") + ",");
                }
            }
            sql.append("</trim>");
            sql.append("</foreach>");

            // 反射把MappedStatement中的设置主键名
            EntityHelper.setKeyProperties(EntityHelper.getPKColumns(entityClass), ms);

            return sql.toString();
        }

        /**
         * 插入，主键id，自增
         *
         * @param ms
         */
        public String insertUseGeneratedKeys(MappedStatement ms) {
            final Class<?> entityClass = getEntityClass(ms);
            //开始拼sql
            StringBuilder sql = new StringBuilder();
            sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass)));
            sql.append(SqlHelper.insertColumns(entityClass, true, false, false));
            sql.append(SqlHelper.insertValuesColumns(entityClass, true, false, false));

            // 反射把MappedStatement中的设置主键名
            EntityHelper.setKeyProperties(EntityHelper.getPKColumns(entityClass), ms);

            return sql.toString();
        }
    }

}

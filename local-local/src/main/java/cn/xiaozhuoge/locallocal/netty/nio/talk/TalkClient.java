package cn.xiaozhuoge.locallocal.netty.nio.talk;

import com.google.common.collect.Lists;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.cnfexpression.MultiAndExpression;
import net.sf.jsqlparser.util.cnfexpression.MultiOrExpression;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaowz
 * @Date 2023/9/28 16:39
 */
public class TalkClient implements Serializable {
    
    public static void main(String[] args) throws Exception {
        String sql = "select * from user where 1=1";
        Select parse = (Select) CCJSqlParserUtil.parse(sql);
        
        PlainSelect plainSelect = (PlainSelect) parse.getSelectBody();
        
        List<Expression> multiAndExpressions = Lists.newArrayList();
        multiAndExpressions.add(new EqualsTo(new Column("name"), new StringValue("123456")));
        multiAndExpressions.add(new InExpression(new Column("age"), new ExpressionList(
                Lists.newArrayList("1", "2", "3").stream().map(DoubleValue::new).collect(Collectors.toList()))));
        
        List<Expression> orExpressions = Lists.newArrayList(new MultiAndExpression(multiAndExpressions),
                new NotEqualsTo(new Column("money"), new StringValue("1000")));
        
        plainSelect.setWhere(new AndExpression(plainSelect.getWhere(), new MultiOrExpression(orExpressions)));
        System.out.println(plainSelect);
    }
}

package demo.springaop;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExpressionEvaluator extends CachedExpressionEvaluator {

	private final Map<ExpressionKey, Expression> keyCache = new ConcurrentHashMap<>(64);

	static ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

	public  EvaluationContext createEvaluationContext(Object rootObject, Method method, Object[] arguments) {
		return new MethodBasedEvaluationContext(rootObject,method,arguments,parameterNameDiscoverer);
	}

	public Object getExpressionValue(String keyExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
		return getExpression(this.keyCache, methodKey, keyExpression).getValue(evalContext);
	}


}

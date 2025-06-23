package expression.generic;

@FunctionalInterface
public interface IGenericParser<T> {
    IGenericExpression<T> parse(String expression, InterfaceGenericMode<T> mode) throws Exception;
}

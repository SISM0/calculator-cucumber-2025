// src/test/java/calculator/CalculatorSteps.java
package calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculatorSteps {

	private Calculator calculator;
	private String opSymbol;
	private List<Double> numbers;
	private double singleInput;

	// Helper to convert List<Double> to List<Expression>
	private List<Expression> asExprs(List<Double> nums) {
		return nums.stream()
				.map(MyNumber::new)
				.collect(Collectors.toList());
	}

	/**
	 * Construit l'Operation correspondant à opSymbol et à la liste numbers.
	 * Traite aussi le cas variadique "quotient" à plus de deux opérandes.
	 */
	private Operation buildOperation() throws IllegalConstruction {
		switch (opSymbol) {
			// binary / variadic full names
			case "+":
			case "sum":
				return new Plus(asExprs(numbers));
			case "-":
			case "difference":
				return new Minus(asExprs(numbers));
			case "*":
			case "product":
				return new Times(asExprs(numbers));
			case "/":
				// cas strict à deux opérandes
				return new Divides(asExprs(numbers));
			case "%":
				return new Modulo(asExprs(numbers));
			case "^":
				return new Power(asExprs(numbers));

			case "quotient":
				// cas variadique : on chaîne les Divides deux à deux
				if (numbers.size() == 2) {
					return new Divides(asExprs(numbers));
				}
				// on commence par diviser les 2 premiers
				Expression expr = new Divides(
						List.of(
								new MyNumber(numbers.get(0)),
								new MyNumber(numbers.get(1))
						)
				);
				// puis on intègre chaque opérande suivante
				for (int i = 2; i < numbers.size(); i++) {
					expr = new Divides(
							List.of(expr, new MyNumber(numbers.get(i)))
					);
				}
				return (Operation) expr;

			// unary
			case "square":
				return new Square(List.of(new MyNumber(singleInput)));
			case "sqrt":
				return new Sqrt(List.of(new MyNumber(singleInput)));
			case "!":
				return new Factorial(List.of(new MyNumber(singleInput)));
			case "fibonacci":
				return new Fibonacci(List.of(new MyNumber(singleInput)));

			default:
				throw new IllegalArgumentException("Symbole inconnu : " + opSymbol);
		}
	}

	@Given("I initialise a calculator")
	public void i_initialise_a_calculator() {
		calculator = new Calculator();
	}

	@Given("an integer operation {string}")
	public void an_integer_operation(String symbol) {
		opSymbol = symbol;
		// on attend les nombres avant de créer l'opération
	}

	@When("I provide a first number {double}")
	public void i_provide_a_first_number(Double d) {
		numbers = List.of(d);
		singleInput = d;
	}

	@When("I provide a second number {double}")
	public void i_provide_a_second_number(Double d) {
		numbers = List.of(numbers.get(0), d);
	}

	@When("I provide a single number {double}")
	public void i_provide_a_single_number(Double d) {
		singleInput = d;
		numbers = List.of(d);
	}

	@Given("the following list of integer numbers")
	public void given_list(DataTable table) {
		numbers = table.asLists().stream()
				.flatMap(List::stream)
				.map(Integer::valueOf)
				.map(Integer::doubleValue)
				.collect(Collectors.toList());
	}

	@Then("its INFIX notation is {string}")
	public void thenInfix(String expected) throws IllegalConstruction {
		Operation op = buildOperation();
		assertEquals(expected, op.toString(Notation.INFIX));
	}

	@Then("its PREFIX notation is {string}")
	public void thenPrefix(String expected) throws IllegalConstruction {
		Operation op = buildOperation();
		assertEquals(expected, op.toString(Notation.PREFIX));
	}

	@Then("its POSTFIX notation is {string}")
	public void thenPostfix(String expected) throws IllegalConstruction {
		Operation op = buildOperation();
		assertEquals(expected, op.toString(Notation.POSTFIX));
	}

	@Then("the operation evaluates to {double}")
	public void then_evaluates_double(Double expected) throws IllegalConstruction {
		Operation op = buildOperation();
		double result = calculator.eval(op);
		assertEquals(expected, result);
	}

	@Then("the sum is {double}")
	public void thenVariadicSum(Double expected) throws IllegalConstruction {
		// sum over the list
		Operation op = new Plus(asExprs(numbers));
		assertEquals(expected, calculator.eval(op));
	}

	@Then("the product is {double}")
	public void thenVariadicProduct(Double expected) throws IllegalConstruction {
		Operation op = new Times(asExprs(numbers));
		assertEquals(expected, calculator.eval(op));
	}

	@Then("the difference is {double}")
	public void thenVariadicDifference(Double expected) throws IllegalConstruction {
		Operation op = new Minus(asExprs(numbers));
		assertEquals(expected, calculator.eval(op));
	}

	@Then("the quotient is {double}")
	public void thenVariadicQuotient(Double expected) throws IllegalConstruction {
		Operation op = new Divides(asExprs(numbers));
		assertEquals(expected, calculator.eval(op));
	}
}

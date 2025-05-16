Feature: Integer Arithmetic Expressions

  Background:
    Given I initialise a calculator

  @binary @fullNotation
  Scenario Outline: Binary operations with full notation
    Given an integer operation "<op>"
    When I provide a first number <a>
    And I provide a second number <b>
    Then its INFIX notation is "<infix>"
    Then its PREFIX notation is "<prefix>"
    Then its POSTFIX notation is "<postfix>"
    And the operation evaluates to <result>

    Examples:
      | op |   a  |   b  |        infix         |       prefix        |        postfix         | result |
      | +  |  4.0 |  5.0 |  ( 4.0 + 5.0 )       |  + ( 4.0, 5.0 )     |  ( 4.0, 5.0 ) +        |  9.0   |
      | -  |  7.0 |  5.0 |  ( 7.0 - 5.0 )       |  - ( 7.0, 5.0 )     |  ( 7.0, 5.0 ) -        |  2.0   |
      | *  |  3.0 |  6.0 |  ( 3.0 * 6.0 )       |  * ( 3.0, 6.0 )     |  ( 3.0, 6.0 ) *        |  18.0  |
      | /  |  9.0 |  2.0 |  ( 9.0 / 2.0 )       |  / ( 9.0, 2.0 )     |  ( 9.0, 2.0 ) /        |  4.5   |
      | %  | 17.0 |  5.0 |  ( 17.0 % 5.0 )      |  % ( 17.0, 5.0 )    |  ( 17.0, 5.0 ) %       |  2.0   |
      | ^  |  2.0 |  5.0 |  ( 2.0 ^ 5.0 )       |  ^ ( 2.0, 5.0 )     |  ( 2.0, 5.0 ) ^        |  32.0  |

  @unary @fullNotation
  Scenario Outline: Unary operations with full notation
    Given an integer operation "<op>"
    When I provide a single number <n>
    Then its INFIX notation is "<infix>"
    Then its PREFIX notation is "<prefix>"
    Then its POSTFIX notation is "<postfix>"
    And the operation evaluates to <result>

    Examples:
      | op         |   n  |           infix             |          prefix           |          postfix           | result |
      | square     |  7.0 |  ( 7.0 )                    |  x² ( 7.0 )               |  ( 7.0 ) x²                | 49.0   |
      | sqrt       |  4.0 |  ( 4.0 )                    |  sqrt ( 4.0 )             |  ( 4.0 ) sqrt              |  2.0   |
      | !          |  5.0 |  ( 5.0 )                    |  ! ( 5.0 )                |  ( 5.0 ) !                 | 120.0  |
      | fibonacci  |  7.0 |  ( 7.0 )                    |  fib ( 7.0 )              |  ( 7.0 ) fib               |  13.0  |

  @variadic
  Scenario: Evaluation over a list of integer numbers
    Given the following list of integer numbers
      | 3 | 4 | 5 |
    And an integer operation "sum"
    Then the operation evaluates to 12.0
    And an integer operation "product"
    Then the operation evaluates to 60.0
    And an integer operation "difference"
    Then the operation evaluates to -6.0
    And an integer operation "quotient"
    Then the operation evaluates to 0.15

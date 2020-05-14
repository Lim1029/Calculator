package com.mingkang.calculator;

import android.util.Log;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

public class FunctionAndOperator {

    Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {

        @Override
        public double apply(double... args) {
            final int arg = (int) args[0];
            if ((double) arg != args[0]) {
                throw new IllegalArgumentException("Operand for factorial has to be an integer");
            }
            if (arg < 0) {
                throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
            }
            double result = 1;
            for (int i = 1; i <= arg; i++) {
                result *= i;
            }
            return result;
        }
    };

//    Function logb = new Function("logb", 2) {
//        @Override
//        public double apply(double... args) {
//            return Math.log(args[0]) / Math.log(args[1]);
//        }
//    };

    Operator combination = new Operator("#|", 2, true, Operator.PRECEDENCE_POWER + 1) {
        @Override
        public double apply(double... args) {

            final int arg1 = (int) args[0];
            final int arg2 = (int) args[1];
            if (args[0] < args[1]) {
                throw new IllegalArgumentException("Operand for combination must have larger n than r");
            }
            if ((double) arg1 != args[0]) {
                throw new IllegalArgumentException("Operand for combination has to be an integer");
            }
            if ((double) arg2 != args[1]) {
                throw new IllegalArgumentException("Operand for combination has to be an integer");
            }
            double n = 1.0, r = 1.0, d = 1.0;
            for (int i = 1; i <= args[0]; i++) {
                n *= i;
            }
            for (int i = 1; i <= args[1]; i++) {
                r *= i;
            }
            for (int i = 1; i <= args[0] - args[1]; i++) {
                d *= i;
            }
            double ans = n / (r * d);
            return ans;
        }
    };

    Operator permutation = new Operator("#&", 2, true, Operator.PRECEDENCE_POWER + 1) {
        @Override
        public double apply(double[] values) {
            final int arg1 = (int) values[0];
            final int arg2 = (int) values[1];
            if (values[0] < values[1]) {
                throw new IllegalArgumentException("Operand for permutation must have larger n than r");
            }
            if ((double) arg1 != values[0] || (double) arg2 != values[1]) {
                throw new IllegalArgumentException("Operand for permutation has to be an integer");
            }
            double n = 1.0, r = 1.0;
            for (int i = 1; i <= values[0]; i++) {
                n *= i;
            }
            for (int i = 1; i <= values[0] - values[1]; i++) {
                r *= i;
            }
            double ans = n / r;
            return ans;
        }
    };

    Operator fraction = new Operator("|", 2, true, Operator.PRECEDENCE_MULTIPLICATION) {
        @Override
        public double apply(double... args) {
            return args[0] / args[1];
        }
    };

    Operator modulo = new Operator("$", 2, true, Operator.PRECEDENCE_MULTIPLICATION + 1) {
        @Override
        public double apply(double... args) {
            final int arg1 = (int) args[0];
            final int arg2 = (int) args[1];
            if ((double) arg1 != args[0] || (double) arg2 != args[1]) {
                throw new IllegalArgumentException("Operand for modulo has to be an integer");
            }
            int ans = arg1 % arg2;
            return ans;
        }
    };

    static int count = 0;

    Function quadratic = new Function("quadratic", 3) {
        private double findPositiveRoot(double a, double b, double d) {
            return (double) (-1 * b + Math.sqrt(d)) / 2 * a;
        }

        private double findNegativeRoot(double a, double b, double d) {
            return (double) (-1 * b - Math.sqrt(d)) / 2 * a;
        }

        private double findRealRoot(double a, double b) {
            return (double) -1 * b / (2 * a);
        }

        private double findImaginaryPart(double a, double d) {
            return (double) Math.sqrt(-d) / (2 * a);
        }

        @Override
        public double apply(double... args) {
            double determinant = Math.pow(args[1], 2) - 4 * args[0] * args[2];
            count++;
            MainActivity.quadraticOperation = true;
            if (determinant < 0) {
                if (count % 2 == 1) {
                    MainActivity.imaginaryRoot = true;
                    return findRealRoot(args[0], args[1]);
                } else {
                    return findImaginaryPart(args[0], determinant);
                }
            } else {
                if (count % 2 == 1) {
                    return findPositiveRoot(args[0], args[1], determinant);
                } else {
                    return findNegativeRoot(args[0], args[1], determinant);
                }
            }
        }
    };

    Function[] myDegreeFunctions = {
            new Function("sin"){public double apply(double... args){return Math.sin(Math.toRadians(args[0]));}},
            new Function("cos"){public double apply(double... args){return Math.cos(Math.toRadians(args[0]));}},
            new Function("quadratic", 3) {
                private double findPositiveRoot(double a, double b, double d) {
                    return (double) (-1 * b + Math.sqrt(d)) / 2 * a;
                }

                private double findNegativeRoot(double a, double b, double d) {
                    return (double) (-1 * b - Math.sqrt(d)) / 2 * a;
                }

                private double findRealRoot(double a, double b) {
                    return (double) -1 * b / (2 * a);
                }

                private double findImaginaryPart(double a, double d) {
                    return (double) Math.sqrt(-d) / (2 * a);
                }
                @Override
                public double apply(double... args) {
                    double determinant = Math.pow(args[1], 2) - 4 * args[0] * args[2];
                    count++;
                    MainActivity.quadraticOperation = true;
                    if (determinant < 0) {
                        if (count % 2 == 1) {
                            MainActivity.imaginaryRoot = true;
                            return findRealRoot(args[0], args[1]);
                        } else {
                            return findImaginaryPart(args[0], determinant);
                        }
                    } else {
                        if (count % 2 == 1) {
                            return findPositiveRoot(args[0], args[1], determinant);
                        } else {
                            return findNegativeRoot(args[0], args[1], determinant);
                        }
                    }
                }
            //etc...
    },
            new Function("logb", 2) {
                @Override
                public double apply(double... args) {
                    return Math.log(args[0]) / Math.log(args[1]);
                }
            }
};
    Function[] myRadianFunctions = {
//            new Function("sin"){public double apply(double... args){return Math.sin(args[0]);}},
//            new Function("cos"){public double apply(double... args){return Math.cos(args[0]);}},
            new Function("quadratic", 3) {
                private double findPositiveRoot(double a, double b, double d) {
                    return (double) (-1 * b + Math.sqrt(d)) / 2 * a;
                }

                private double findNegativeRoot(double a, double b, double d) {
                    return (double) (-1 * b - Math.sqrt(d)) / 2 * a;
                }

                private double findRealRoot(double a, double b) {
                    return (double) -1 * b / (2 * a);
                }

                private double findImaginaryPart(double a, double d) {
                    return (double) Math.sqrt(-d) / (2 * a);
                }
                @Override
                public double apply(double... args) {
                    double determinant = Math.pow(args[1], 2) - 4 * args[0] * args[2];
                    count++;
                    MainActivity.quadraticOperation = true;
                    if (determinant < 0) {
                        if (count % 2 == 1) {
                            MainActivity.imaginaryRoot = true;
                            return findRealRoot(args[0], args[1]);
                        } else {
                            return findImaginaryPart(args[0], determinant);
                        }
                    } else {
                        if (count % 2 == 1) {
                            return findPositiveRoot(args[0], args[1], determinant);
                        } else {
                            return findNegativeRoot(args[0], args[1], determinant);
                        }
                    }
                }
                //etc...
            },
            new Function("logb", 2) {
                @Override
                public double apply(double... args) {
                    return Math.log(args[0]) / Math.log(args[1]);
                }
            }
    };
    Function[] myFunctions = {
//            new Function("sin"){public double apply(double... args){return Math.sin(args[0]);}},
//            new Function("cos"){public double apply(double... args){return Math.cos(args[0]);}},
            new Function("quadratic", 3) {
                private double findPositiveRoot(double a, double b, double d) {
                    return (double) (-1 * b + Math.sqrt(d)) / 2 * a;
                }

                private double findNegativeRoot(double a, double b, double d) {
                    return (double) (-1 * b - Math.sqrt(d)) / 2 * a;
                }

                private double findRealRoot(double a, double b) {
                    return (double) -1 * b / (2 * a);
                }

                private double findImaginaryPart(double a, double d) {
                    return (double) Math.sqrt(-d) / (2 * a);
                }
                @Override
                public double apply(double... args) {
                    double determinant = Math.pow(args[1], 2) - 4 * args[0] * args[2];
                    count++;
                    MainActivity.quadraticOperation = true;
                    if (determinant < 0) {
                        if (count % 2 == 1) {
                            MainActivity.imaginaryRoot = true;
                            return findRealRoot(args[0], args[1]);
                        } else {
                            return findImaginaryPart(args[0], determinant);
                        }
                    } else {
                        if (count % 2 == 1) {
                            return findPositiveRoot(args[0], args[1], determinant);
                        } else {
                            return findNegativeRoot(args[0], args[1], determinant);
                        }
                    }
                }
                //etc...
            },
            new Function("logb", 2) {
                @Override
                public double apply(double... args) {
                    return Math.log(args[0]) / Math.log(args[1]);
                }
            }
    };
}

//    Function sequenceSummation = new Function("Σ",3){
//        @Override
//        public double apply(double... args) {
//
//    };

/*
    Operator sequenceSummation = new Operator("Σ", 1, true, Operator.PRECEDENCE_POWER) {
        @Override
        public double apply(double... args) {
            double sum = 0;
            Expression e = Calculate.solveUsingLibrary().e;
            for(int i=(int)args[0]; i<=(int)args[1]; i++) {
                sum += Calculate.solveUsingLibrary().
            }
            return 0;
        }
    };
}

 */

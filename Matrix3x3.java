/*
Author: Zachery Pan
Date: 5/8-9/24
Purpose: Calculate inverse of 3x3 matrices
 */

public class Matrix3x3 {

    private double[][] matrix;

    public Matrix3x3(double[][] arr) {
        matrix = arr;
    }

    public double determinant() {
        double determinant = 0;

        // Loop through all numbers in first row
        for(int i = 0; i < matrix[0].length; i++) {
            double[][] matrix2x2 = new double[2][2];
            int row2x2 = 0;
            int column2x2 = 0;

            // Find and group 2x2 matrix
            for(int j = 0; j < matrix.length; j++) {
                for(int k = 0; k < matrix[j].length; k++) {
                    if(j != 0 && k != i) {
                        matrix2x2[row2x2][column2x2] = matrix[j][k];
                        column2x2++;
                        if(column2x2 == 2) {
                            column2x2 = 0;
                            row2x2 = 1;
                        }
                    }
                }
            }

            // Calculate determinant of 2x2
            double determinant2x2 = (matrix[0][i]) * (matrix2x2[0][0] * matrix2x2[1][1]) - (matrix2x2[0][1] * matrix2x2[1][0]);
            if(i == 0 || i == 2) { // Addition if column 0 or column 2
                determinant += determinant2x2;
            } else {
                determinant -= determinant2x2;
            }
        }

        return determinant;
    }

    public Matrix3x3 inverse(boolean factored) {
        double[][] inverse = new double[3][3];
        for(int x = 0; x < matrix.length; x++) {
            // Loop through all numbers in each row
            for(int i = 0; i < matrix[x].length; i++) {
                double[][] matrix2x2 = new double[2][2];
                int row2x2 = 0;
                int column2x2 = 0;

                // Find and group 2x2 matrix
                for(int j = 0; j < matrix.length; j++) {
                    for(int k = 0; k < matrix[j].length; k++) {
                        if(j != x && k != i) {
                            matrix2x2[row2x2][column2x2] = matrix[j][k];
                            column2x2++;
                            if(column2x2 == 2) {
                                column2x2 = 0;
                                row2x2 = 1;
                            }
                        }
                    }
                }

                // Calculate determinant of 2x2
                double determinant2x2 = (matrix2x2[0][0] * matrix2x2[1][1]) - (matrix2x2[0][1] * matrix2x2[1][0]);
                if(x == 0 || x == 2) {
                    if(i == 0 || i == 2) {
                        inverse[x][i] = determinant2x2;
                    } else {
                        inverse[x][i] = -1 * determinant2x2;
                    }
                } else {
                    if(i == 0 || i == 2) {
                        inverse[x][i] = -1 * determinant2x2;
                    } else {
                        inverse[x][i] = determinant2x2;
                    }
                }
            }
        }

        // Return a factored matrix (whether to divide by determinant)
        if(!factored) {
            for(int i = 0; i < inverse.length; i++) {
                for(int j = 0; j < inverse[i].length; j++) {
                    inverse[i][j] *= 1 / determinant();
                }
            }
        }

        Matrix3x3 inverseMatrix = new Matrix3x3(inverse);
        return inverseMatrix.transpose();
    }

    public Matrix3x3 transpose() {
        double[][] transposed = new double[3][3];
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return new Matrix3x3(transposed);
    }

    public void display() {
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                System.out.print("|" + matrix[i][j] + "|");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        double[][] arr = {
                {0, 0, -2},
                {0, 3, 0},
                {4, 0, 0}
        };

        Matrix3x3 matrix = new Matrix3x3(arr);
        System.out.println("original (determinant: " + matrix.determinant() + ")");
        matrix.display();

//        Matrix3x3 transposed = matrix.transpose();
//        System.out.println("\ntransposed");
//        transposed.display();

        Matrix3x3 inverse = matrix.inverse(false);
        System.out.println("\ninverse");
        inverse.display();

        Matrix3x3 factoredInverse = matrix.inverse(true);
        System.out.println("\nfactored inverse");
        System.out.println("(1/" + matrix.determinant() + ") * ");
        factoredInverse.display();

    }

}
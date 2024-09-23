public class Triangulo {
    public static void main(String[] args) {        
        double base = ; 
        double altura = 5.0; 
        if (args.length > 0) {
            double base = Double.parseDouble(args[0]); // Cambia este valor según primer argumento
            double altura = Double.parseDouble(args[1]); // Cambia este valor según segundo argumento
        }
        double area = calcularAreaTriangulo(base, altura);
        System.out.println("El área del triángulo es: " + area);
    }

    /**
     * Calcula el área de un triángulo dados su base y altura.
     *
     * @param base la base del triángulo
     * @param altura la altura del triángulo
     * @return el área del triángulo
     */
    public static double calcularAreaTriangulo(double base, double altura) {
        return 0.5 * base * altura;
    }
}

public class TestMyTriangle {
    public static void main(String[] args) {
        //creando 3 puntos
         MyPoint p1 = new MyPoint(5, 5);
         MyPoint p2 = new MyPoint(6, 8);
         MyPoint p3 = new MyPoint(1, 4);

         //pruebas de metodos del punto
        System.out.println("p1: "+p1.toString());
        System.out.println("Distancia entre p1 y un punto xy: "+ p1.distance(8,9));
        System.out.println("Distancia entre p1 y p2: "+p1.distance(p2));
        System.out.println("Distancia  euclideana del p1: "+p1.distance());

        //creando un triangulo
        MyTriangle t1 = new MyTriangle(p1,p2,p3);

        //Creando un triangulo a partir de valores x,y de tres ubicaciones
        MyTriangle t2 = new MyTriangle(0, 0, 4, 4, 8, 0);
        MyTriangle t3 = new MyTriangle(1, 2, 3, 4, 5, 6);

        //pruebas de metodos del triangulo
        System.out.println("t1: "+t1.toString());
        System.out.println("Perimetro de t1: "+t1.getPerimeter());
        System.out.println("Tipo de t1: "+t1.getType());
        System.out.println("t2: "+t2.toString());
        System.out.println("Perimetro de t2: "+t2.getPerimeter());
        System.out.println("Tipo de t2: "+t2.getType());
        System.out.println("t3: "+t3.toString());
        System.out.println("Perimetro de t3: "+t3.getPerimeter());
        System.out.println("Tipo de t3: "+t3.getType());
        System.out.println("Cantidad de triangulos creados: "+MyTriangle.getCount());

    }
}

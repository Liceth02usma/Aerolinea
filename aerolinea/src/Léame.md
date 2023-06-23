# Definiciones

## Abstracion:
se enfoca en la visión externa de un objeto,  separa el comportamiento  específico de un objeto, a esta división que realiza se le conoce como la barrera de abstracción, la cuál se consigue aplicando el principio de mínimo compromiso...
### [Mas Informacion...](https://styde.net/abstraccion-programacion-orientada-a-objetos/)
&nbsp;
### *Ejemplo*:
>Supongamos qué nos están solicitando la abstracción de un vehículo en cuanto a su comportamiento.

>La pregunta que hago es ¿Cuales son los comportamientos que tienen todos los vehículos? con base a esta pregunta, automáticamente fluye nuestra abstracción del comportamiento de un vehículo.

>#### 1. Encender Vehículo
>#### 2. Apagar Vehículo
>#### 3. Acelerar Vehículo
>#### 4. Frenar Vehículo
>#### 5. Retroceder Vehículo
>#### 6. Parabrisas Vehículo

&nbsp;

---
&nbsp;
## Accesores:
Los accesores son de 2 tipos getters y setters los setters sirven para cambiar los valores de los atributos de un objeto, es necesario crearlos ya que los atributos no deben ser accedidos. los getters sirven a diferencia de los setters para obtener la información de los atributos.
### [Mas Informacion...](https://sites.google.com/site/oopbosque/courses/course-d#:~:text=Accesores%3A-,.,accedidos%20directamente%20para%20evitar%20errores)
&nbsp;
### *Ejemplo*:
 >imagina que tienes una clase Persona que tiene un atributo que es la fecha de nacimiento y otro que es la edad, no deberías poder permitir que alguien "de afuera del objeto" cambie la edad, solo que la consulte mediante un método getEdad(). Si podrías hacer que se pudiera cambiar y leer la fecha de nacimiento mediante métodos getFechaNacimiento() y setFechaNacimiento().

 &nbsp;

---
&nbsp;

 ## Agregación:
Como su nombre lo indica la agregación en UML permite representar una relación entre una clase y sus elementos agregados, esto quiere decir que las clases agregadas no afectan el funcionamiento directo de la clase que las contiene.

### [Mas Informacion...](https://virtual.itca.edu.sv/Mediadores/ads/213_tipos_de_relaciones.html)

&nbsp;
### *Ejemplo*:
>Sabemos que a una computadora portátil  se le pueden agregar elementos como micrófono, cámara web e impresora; sin embargo la ausencia de estos elementos no repercute en el funcionamiento básico de dicho portátil.
&nbsp;

---
&nbsp;
## Anotaciones en Java
es aquella característica que le permite incrustar información suplementaria en un archivo fuente. Esta información no cambia las acciones de un programa, pero puede ser utilizada por varias herramientas, tanto durante el desarrollo como durante el despliegue.

### [Mas Informacion...](https://www.tokioschool.com/noticias/anotaciones-en-java/)

&nbsp;
### *Ejemplo*:
>la conocida anotación @Override , que indica que vamos a anular un método de una clase padre.

&nbsp;

---

&nbsp;
## Casting
El casting es un procedimiento para transformar una variable primitiva de un tipo a otro. También se utiliza para transformar un objeto de una clase a otra clase siempre y cuando haya una relación de herencia entre ambas.

### [Mas Informacion...]( https://sites.google.com/site/pro012iessanandres/java/conversion-entre-tipos-primitivos-casting)

&nbsp;
### *Ejemplo*:
>Pasar un entero a un tipo de dato flotante.
&nbsp; 
```
double num1 = 25.5;
float num2 = (float) num1;
float num3 = 17.25;
```
&nbsp;

---
&nbsp;
## Clases
Una clase es una plantilla para la creación de objetos de datos según un modelo predefinido. Las clases se utilizan para representar entidades o conceptos. Cada clase es un modelo que define un conjunto de variables (el estado), y métodos apropiados para operar con dichos datos (el comportamiento).

### [Mas Informacion...](https://aprendiendoarduino.wordpress.com/2017/07/08/clases-y-objetos/)

&nbsp;
### *Ejemplo*:
>Podríamos tener la clase Perro. La clase Perro especificaría que todos los perros tendrían un nombre, color de pelo, una altura.

&nbsp;

---
&nbsp;
## Comma separated values (CSV)

es un archivo de texto que contiene algunos datos. En circunstancias normales, se utiliza un archivo CSV para transferir datos de una aplicación a otra. A modo de explicación, un archivo CSV almacena datos, tanto números como texto en un texto sin formato.La extensión del archivo, por otro lado, ayuda a un sistema operativo a identificar a qué programa, en particular, está vinculado el archivo.

### [Mas Informacion...](https://ecommerce-platforms.com/es/glossary/comma-separated-values-csv)
&nbsp;
### *Ejemplo*:
>por ejemplo, transfiero un archivo con el nombre 'minutos.doc ', la extensión que termina con '.doc' significa que el archivo debe abrirse con Microsoft Word. Lo mismo sucede con un archivo CSV solo que este se abre con Excel

&nbsp;

---
&nbsp;
## Composición
La composición a diferencia de la agregación representa una clase que está compuesta por otras clases que son indispensables para que esta funcione.

### [Mas Informacion...](https://virtual.itca.edu.sv/Mediadores/ads/213_tipos_de_relaciones.html)
&nbsp;
### *Ejemplo*:
>Podríamos decir que la laptop citada en el ejemplo anterior funciona si le quitamos la cámara web; sin embargo no funcionará si le faltase la pantalla, por lo que podemos decir que una laptop está compuesta básicamente de una pantalla y su unidad de procesamiento (CPU).

&nbsp;

---
&nbsp;
## Constructor Copia
es un método constructor como los que ya has utilizado pero con la particularidad de que recibe como parámetro una referencia al objeto cuyo contenido se desea copiar. Este método revisa cada uno de los atributos del objeto recibido como parámetro y se copian todos sus valores en los atributos del objeto que se está creando en ese momento en el método constructor.

### [Mas Informacion...](https://ikastaroak.birt.eus/edu/argitalpen/backupa/20200331/1920k/es/DAMDAW/PROG/PROG06/es_DAMDAW_PROG06_Contenidos/website_54_constructores_de_copia.html)
&nbsp;
### *Ejemplo*:
&nbsp; 
```
 public Punto(Punto p) {  
       this.x = p.obtenerX();  
       this.y = p.obtenerY();
}
```

&nbsp;

---
&nbsp;
## Constructor Parametrizado

Un constructor inicializa un objeto cuando se crea. Tiene el mismo nombre que su clase y es sintácticamente similar a un método. Sin embargo, los constructores no tienen un tipo de devolución explícito.

### [Mas Informacion...](https://javadesdecero.es/poo/constructores-ejemplos/)
&nbsp;
### *Ejemplo*:
&nbsp; 
```
public Fecha(int d, int m, int a) {
        dia = d;
        mes = m;  
        año = a;
}
```
&nbsp;

---
&nbsp;
## Constructor Por Defecto
El constructor por defecto es un constructor sin parámetros que no hace nada. Los atributos del objeto son iniciados con los valores predeterminados por el sistema.

### [Mas Informacion...](http://puntocomnoesunlenguaje.blogspot.com/2012/07/normal-0-21-false-false-false_103.html#:~:text=El%20constructor%20por%20defecto%20es,valores%20predeterminados%20por%20el%20sistema.&text=no%20se%20ha%20definido%20ning%C3%BAn,utilizar%C3%A1%20un%20constructor%20por%20defecto)

&nbsp;
### *Ejemplo*: 
&nbsp;
``` 
public esBisiesto() {

}
``` 

&nbsp;

---
&nbsp;
##  Encapsulación:
es cuando limitamos el acceso o damos un acceso restringido de una propiedad a los elementos que necesita un miembro y no a ninguno más. 
El elemento más común de encapsulamiento son las clases, donde encapsulamos y englobamos tanto métodos como propiedades. 

### [Mas Informacion...](https://www.netmentor.es/entrada/encapsulamiento-poo)

&nbsp;
### *Ejemplo*:
>El elemento más común de encapsulamiento son las clases, donde encapsulamos y englobamos tanto métodos como propiedades. 
Otro ejemplo muy común de encapsulamiento son los getters y setters de las propiedades dentro de una clase. Por defecto nos dan el valor “normal” pero podemos modificarlos para que cambie.

&nbsp;

---
&nbsp;
## Enumeraciones
es un tipo de dato que nos permite crear una variable, que define todos los posibles valores fijos que pueda contener. Un tipo de enumeración consiste en un conjunto de constantes denominado lista de enumeradores

### [Mas Informacion...](https://dominiotic.com/como-implementar-estructuras-y-enumeraciones-en-c/#:~:text=Una%20enumeraci%C3%B3n%20o%20Enum%2C%20es,constantes%20denominado%20lista%20de%20enumeradores)
&nbsp;
### *Ejemplo:*
&nbsp;
``` 
 class enum Color {
    ROJO, 
    VERDE, 
    AZUL;
}
``` 

&nbsp;

---
&nbsp;
## Excepciones (lanzar, capturar y propagar)
Las excepciones son situaciones anómalas que requieren un tratamiento especial. ¡¡No tienen por qué ser errores!!
Si se consigue dominar su programación, la calidad de las aplicaciones que se desarrollen aumentará considerablemente.

### Lanzar
El lanzamiento de una excepción se realiza llamando a la función throw(). Cuando se lanza una excepción, en realidad lo que se hace es crear un objeto de la clase que se le indique a throw(), y precisamente será dicho objeto la excepción en sí.

### [Mas Informacion...](https://unipython.com/lanzar-y-capturar-excepciones-en-java/)

&nbsp;
### *Ejemplo*:
>vamos a crear una clase “Persona” con el método “setEdad” el cual solamente puede recibir por parámetro un número positivo, de lo contrario se va a lanzar una excepción.

&nbsp;
``` 
public class Persona {
 
    private int edad;
 
    public int getEdad() {
        return this.edad;
    }
 
    public void setEdad(int edad) {
        if (edad <= 0)
            throw new RuntimeException("La edad debe ser positiva");
        this.edad = edad;
    }
 
} 
``` 

&nbsp;

### Capturar:
El mecanismo de captura de excepciones, permite "atrapar" el objeto de excepción lanzado por la instrucción e indicar las diferentes acciones a realizar según la clase de excepción producida.

### [Mas Informacion...](https://elvex.ugr.es/decsai/builder/intro/6.html#CAP_EXCEPCIONES)

&nbsp;

### *Ejemplo*:
&nbsp;
``` 
try {
       <bloque de instrucciones críticas>
}
catch (<tipo excepción1> <variable1>) {
       <manejador 1>
}
catch (<tipo excepción2> <variable2>) {
       ...
}
```

&nbsp;

### Propagar:
La propagación de excepciones es el mecanismo recomendado para interceptar errores que se produzcan durante la ejecución de las aplicaciones (divisiones por cero, lectura de archivos no disponibles, etc.)

### [Mas Informacion...](https://itslr.edu.mx/archivos2013/TPM/temas/s2u5.html#:~:text=La%20propagaci%C3%B3n%20de%20excepciones%20es,derivados%20de%20la%20clase%20System)
 
&nbsp;

>El uso de códigos especiales para informar de error suele dificultar la legibilidad del fuente en tanto que se mezclan las instrucciones propias de la lógica del mismo con las instrucciones propias del tratamiento de los errores que pudiesen producirse durante su ejecución. 

>### *Por Ejemplo*:
&nbsp;

``` 
int resultado = obj.Método();  
if (resultado == 0) // Sin errores al ejecutar obj.Método();  
       {...}   
else if (resultado == 1) // Tratamiento de error de código 1  
       {...}  
else if (resultado == 2) // Tratamiento de error de código 2  
       }
```

&nbsp;

---
&nbsp;
## Especialización:
El proceso inverso de la generalización por el cual se definen nuevas clases a partir de otras ya existentes.

### [Mas Informacion...](https://edukativos.com/apuntes/archives/3707#:~:text=El%20proceso%20inverso%20de%20la,de%20generalizaci%C3%B3n%20se%20denomina%20herencia)

&nbsp;
### *Ejemplo*:
>Los carros, motos, camiones y buses tiene ruedas, motores y carrocerías; son las características que definen a un vehículo. Además de las características comunes con los otros miembros de la clase, cada subclase tiene sus propias características.

>Por ejemplo: los camiones tienen una cabina independiente de la caja que transporta la carga; los buses tienen un gran número de asientos independientes para los viajeros que ha de transportar, etc.

&nbsp;

---
&nbsp;
## Generalización:
Es la propiedad que permite compartir información entre dos entidades evitando la redundancia.

En el comportamiento de objetos existen con frecuencia propiedades que son comunes en diferentes objetos y esta propiedad se denomina generalización.

### [Mas Informacion...](https://edukativos.com/apuntes/archives/3707)
&nbsp;
### *Ejemplo*:
>En el ejemplo anterior los carros, motos, camiones y buses tiene ruedas, motores y carrocerías; son las características que definen a un vehículo (generalizacion) pero por separado tienen carateristicas diferentes (Especializacion).

&nbsp;

---
&nbsp;
## Helpers:
 Es una clase Java que incluye el manejo básico de errores, algunas funciones de ayuda, etc. La clase de ayuda contiene funciones que ayudan a ayudar al programa. Esta clase tiene la intención de proporcionar una implementación rápida de funciones básicas de manera que los programadores no tengan que implementarlas una y otra vez

### [Mas Informacion...](https://es.acervolima.com/java-como-crear-tu-propia-clase-de-ayudante/)
&nbsp;
### *Ejemplo*:
>La clase Helper evalua si lo que ingreso es integer.

&nbsp;
``` 
if (HelperClass.isValidInteger(n)){  
       System.out.println("True");  
        else   
            System.out.println("False");  
}
``` 

&nbsp;

---
&nbsp;

## Herencia:
permite que se puedan definir nuevas clases basadas de unas ya existentes a fin de reutilizar el código, generando así una jerarquía de clases dentro de una aplicación. Si una clase deriva de otra, esta hereda sus atributos y métodos y puede añadir nuevos atributos, métodos o redefinir los heredados.

### [Mas Informacion...](https://ifgeekthen.nttdata.com/es/herencia-en-programacion-orientada-objetos)
&nbsp;
### *Ejemplo*: 
>Tenemos una clase llamada Ave, en el que las propiedades serían color, tamaño de alas. Y uno de sus métodos sean volar. La herencia haría que yo cree una clase Paloma que herede todo lo de la clase Ave y le agrega sus propias propiedades y métodos. También podemos crear la clase Gorrión y hará lo mismo que la paloma. Heredará cosas del Ave y le agregará los suyos propios.

&nbsp;

---
&nbsp;
## Interfaces:
Una interfaz es un conjunto de métodos y propiedades que no tiene ninguna implementación. La implementación la va a hacer cada uno de los elementos que herede de la interfaz dependiendo de sus necesidades. 

### [Mas Informacion...](https://es.linkedin.com/learning/c-sharp-programacion-orientada-a-objetos/definicion-de-interfaz-en-programacion-orientada-a-objetos#:~:text=Una%20interfaz%20es%20un%20conjunto,interfaz%20dependiendo%20de%20sus%20necesidades)
&nbsp;
### *Ejemplo*:
### [Enlace al ejemplo...](https://informaticapc.com/poo/interfaces.php)

&nbsp;

---
&nbsp;

## Métodos de clase o estáticos
Un método estático puede llamarse sin tener que crear un objeto de dicha clase.
Igual que los atributos estáticos, un método estático tiene ciertas restricciones:
No puede acceder a los atributos de la clase (salvo que sean estáticos)
No puede utilizar el operador this, ya que este método se puede llamar sin tener que crear un objeto de la clase...

### [Mas Informacion...](https://www.tutorialesprogramacionya.com/javaya/detalleconcepto.php?punto=65&codigo=143&inicio=60)
&nbsp;
### *Ejemplo*:
>Los métodos de la clase Arrays del API de Java son métodos estáticos: no los invocamos sobre un objeto, sino sobre una clase. Otra clase que se basa en métodos estáticos es Math.

&nbsp;

---
&nbsp;
## Métodos de instancia
Un método de instancia es el que se invoca siempre sobre una instancia (objeto) de una clase.

### [Mas Informacion...](https://www.aprenderaprogramar.com/index.php?option=com_content&view=article&id=650:concepto-de-metodos-de-clase-o-static-y-metodos-de-instancia-diferencias-metodo-main-de-java-cu00683b&catid=68&Itemid=188)
&nbsp;
### *Ejemplo*:
>p1.getNombre(); siendo p1 un objeto de tipo Persona es un método de instancia: para invocarlo necesitamos una instancia de persona.

&nbsp;

---
&nbsp;
## modificadores de acceso (private, public, protected, default):
Los modificadores de acceso permiten al diseñador de una clase determinar quien accede a los datos y métodos miembros de una clase. Los modificadores de acceso preceden a la declaración de un elemento miembro de la clase (ya sea atributo o método)

&nbsp;
### private:
Unicamente la clase puede acceder a la propiedad o método.

### [Mas Informacion...](https://picodotdev.github.io/blog-bitix/2020/01/los-modificadores-de-acceso-de-clases-propiedades-y-metodos-en-java/)
&nbsp;
### *Ejemplo*:
&nbsp;
``` 
private int atributo1;//Este atributo es privado  
private int contador = 0; //Contador de registro
``` 
&nbsp;

### Public:
la propiedad o método es accesible desde cualquier método de otra clase.
### [Mas Informacion...](https://picodotdev.github.io/blog-bitix/2020/01/los-modificadores-de-acceso-de-clases-propiedades-y-metodos-en-java/)

&nbsp;
### *Ejemplo*:
&nbsp;
``` 
public class A {  
    public void mostrar(){  
       System.out.println("Java desde Cero");  
    }  
}
```

&nbsp;

### Protected:
las clases del mismo paquete y que heredan de la clase pueden acceder a la propiedad o método.

### [Mas Informacion...](https://picodotdev.github.io/blog-bitix/2020/01/los-modificadores-de-acceso-de-clases-propiedades-y-metodos-en-java/)
&nbsp;
### *Ejemplo*: 
&nbsp;
``` 
protected void mostrar(){  
        System.out.println("Java desde Cero");  
    }
```

&nbsp;
### Default:
package private (valor por defecto si no se indica ninguno): solo las clases en el mismo paquete pueden acceder a la propiedad o método.

### [Mas Informacion...](https://picodotdev.github.io/blog-bitix/2020/01/los-modificadores-de-acceso-de-clases-propiedades-y-metodos-en-java/)
&nbsp;
### *Ejemplo*: 
&nbsp;
```
class DemoDefault {  
    void mostrar()  
    {  
        System.out.println("Hola Mundo!");  
    }  
}
```

&nbsp;

---
&nbsp;
## Multiplicidad:
La multiplicidad o diversificación es uno de los aspectos importantes de las asociaciones entre objetos. Indica la cantidad de objetos de una clase que se relacionan con otro objeto en particular de la clase asociada.

### [Mas Informacion...](https://respuestasrapidas.com.mx/que-es-multiplicidad-en-programacion-orientada-a-objetos/)
&nbsp;
### *Ejemplo*:
>Dada una persona, ¿cuántas mascotas puede tener? Una persona tiene varias mascotas y puede no tener ninguna. Por lo tanto es * (o 0..*)

&nbsp;

---
&nbsp;
## Mutadores
Es un método que le permite cambiar las variables dentro de la clase. Privado es el nivel de acceso para las variables, lo que significa que la única forma de cambiar las variables es mediante el uso de los métodos mutadores...

### [Mas Informacion...](https://ec2-cli.com/es/qu%C3%A9-es-un-m%C3%A9todo-mutador-en-java/)
&nbsp;
### *Ejemplo*: 
&nbsp;
```
public void setAge(int age){  
       this.age = age;  
}
```

&nbsp;

---
&nbsp;
## Navegabilidad:
La navegabilidad es la sencillez con lo que un usuario puede moverse a través del conjunto de un sitio web.

### [Mas Informacion...](https://www.arimetrics.com/glosario-digital/navegabilidad)
&nbsp;
### *Ejemplo*:
>En este sentido, el sitio web de Apple en español http://www.apple.es/ es uno de los ejemplos más notorios, su menú es fácil de utilizar y no requiere de hacer muchos clics para que el usuario llegue a la información que necesita.

&nbsp;

---
&nbsp;
## Parametrizadas:
Estas clases parametrizadas se diferencian de las demás en que en sus nombres tienen un formato diferente a las clases normales ya que incluyen los símbolos de mayor y menor.

### [Mas Informacion...](https://www.juegosoftware.com/2015/02/clases-genericas-o-parametrizadas-en.html)
&nbsp;
### *Ejemplo*:
>Un ejemplo de clase parametrizada es la clase ArrayList que si la buscamos en la biblioteca de clases de Java veremos que viene así ArrayList<E> o la clase HashMap<K, V>. Esto nos indica que son clases parametrizadas o genéricas.

&nbsp;

---
&nbsp;
## Polimorfismo:
El polimorfismo es una relajación del sistema de tipos, de tal manera que una referencia a una clase (atributo, parámetro o declaración local o elemento de un vector) acepta direcciones de objetos de dicha clase y de sus clases derivadas (hijas, nietas, ...)

### [Mas Informacion...](https://desarrolloweb.com/articulos/polimorfismo-programacion-orientada-objetos-concepto.html)
&nbsp;
### *Ejemplo*:
>Tenemos la clase Parking. Dentro de ésta tenemos un método estacionar(). Puede que en un parking tenga que estacionar coches, motos o autobuses. Sin polimorfismo tendría que crear un método que permitiese estacionar objetos de la clase "Coche", otro método que acepte objetos de la clase "Moto" para estacionarlos, etc; puedo decir que recibe como parámetro un objeto de la clase "Vehiculo" y el compilador me aceptará no solamente vehículos genéricos, sino todos aquellos objetos que hayamos creado que hereden de la clase Vehículo, osea, coches, motos, buses, etc.

&nbsp;

---
&nbsp;
## Sobrecarga: 
Significa que tenemos múltiples métodos dentro de una clase los cuales contienen el mismo nombre, pero diferentes parámetros.

### [Mas Informacion...](https://www.netmentor.es/entrada/sobrecarga-metodos)
&nbsp;
### *Ejemplo*:
&nbsp;
```
/* Métodos sobrecargados */  
int calculaSuma(int x, int y, int z){  
    ...  
}  
int calculaSuma(double x, double y, double z){  
    ...  
}
```

&nbsp;

---
&nbsp;

## Sobreescritura
Una subclase sobreescribe un método de su superclase cuando define un método con las mismas características ( nombre, número y tipo de argumentos) que el método de la superclase

### [Mas Informacion...](http://profesores.fi-b.unam.mx/carlos/java/java_basico4_7.html#:~:text=Sobreescritura%20de%20m%C3%A9todos&text=Una%20subclase%20sobreescribe%20un%20m%C3%A9todo,el%20m%C3%A9todo%20de%20la%20superclase)
&nbsp;
### *Ejemplo*:

&nbsp;
``` 
class ClaseA{  
       void miMetodo(int var1, int var2)  
       { ... }  
       String miOtroMetodo( )  
       { ... }  
}  
class ClaseB extends ClaseA{  
       /* Estos métodos sobreescriben a los métodos de la clase padre */  
       void miMetodo (int var1 ,int var2)  
              { ... }  
       String miOtroMetodo( )  
              { ... }  
}
``` 

&nbsp;

---
&nbsp;
## Subclase 
A la clase que hereda la otra clase se conoce como subclase (o una clase derivada, clase extendida o clase hija). La subclase puede agregar sus propios campos y métodos, además de los campos y métodos de la superclase.

### [Mas Informacion...](https://ifgeekthen.nttdata.com/es/herencia-en-programacion-orientada-objetos#:~:text=Subclase%3A%20la%20clase%20que%20hereda,y%20m%C3%A9todos%20de%20la%20superclase)
&nbsp;
### *Ejemplo*:
&nbsp;
``` //Clase para objetos de dos dimensiones 
class DosDimensiones{
       double base;  
       double altura;
       void mostrarDimension(){
              System.out.println("La
              base y altura es: "+base+" 
              y "+altura);
       }
}
//Una subclase de DosDimensiones
para Triangulo
class Triangulo extends DosDimensiones{
       String estilo;
       double area(){
              return base*altura/2; 
       }
       void mostrarEstilo(){
              System.out.println
       ("Triangulo es: "+estilo); 
       }
}
``` 

&nbsp;

---
&nbsp;
## Superclase:
la clase cuyas características se heredan se conoce como superclase (o una clase base o una clase principal).

### [Mas Informacion...](https://ifgeekthen.nttdata.com/es/herencia-en-programacion-orientada-objetos#:~:text=Subclase%3A%20la%20clase%20que%20hereda,y%20m%C3%A9todos%20de%20la%20superclase)

&nbsp;
### *Ejemplo*:
> En el ejemplo anterior la superclase seria DosDimensiones ya que es de donde hereda triangulo.
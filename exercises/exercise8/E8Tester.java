/** Example of using unit tests for this assignment.  To run them on the command line, make
 * sure that the junit-cs211.jar is in the same directory.
 *
 * On Mac/Linux:
 *  javac -cp .:junit-cs211.jar *.java         # compile everything
 *  java -cp .:junit-cs211.jar E8Tester        # run tests
 *
 * On windows replace colons with semicolons: (: with ;)
 *  demo$ javac -cp .;junit-cs211.jar *.java   # compile everything
 *  demo$ java -cp .;junit-cs211.jar E8Tester  # run tests
 */
import org.junit.*;
import static org.junit.Assert.*;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.File;
import java.util.Scanner;

public class E8Tester {
  public static void main(String args[]){
    org.junit.runner.JUnitCore.main("E8Tester");
  }
  // ***** class Point tests **********************************

  @Test(timeout=1000)
  public void test_point1() {
    Integer v1 = Integer.valueOf(10);
    Integer v2 = Integer.valueOf(11);
    Integer v3 = Integer.valueOf(12);
    Point<Integer> p = new Point<Integer>();
    assertEquals("incorrect Point contructor and/or getValue method", null, p.getValue());
    assertEquals("incorrect Point contructor and/or getNextDown method", null, p.getNextDown());
    assertEquals("incorrect Point contructor and/or getNextRight method", null, p.getNextRight());
    p.setValue(v1);
    assertEquals("incorrect Point setValue and/or getValue methods", v1, p.getValue());
    p.setNextDown(new Point<Integer>());
    p.setNextRight(new Point<Integer>());
    p.getNextDown().setValue(v2);
    p.getNextRight().setValue(v3);
    assertEquals("incorrect Point setter/getter methods", v2, p.getNextDown().getValue());
    assertEquals("incorrect Point setter/getter methods", v3, p.getNextRight().getValue());
  }

  @Test(timeout=1000)
  public void test_point2() {
    Float v1 = Float.valueOf(10);
    Float v2 = Float.valueOf(11);
    Float v3 = Float.valueOf(12);
    Point<Float> p = new Point<Float>();
    assertEquals("incorrect Point contructor and/or getValue method", null, p.getValue());
    assertEquals("incorrect Point contructor and/or getNextDown method", null, p.getNextDown());
    assertEquals("incorrect Point contructor and/or getNextRight method", null, p.getNextRight());
    p.setValue(v1);
    assertEquals("incorrect Point setValue and/or getValue methods", v1, p.getValue());
    p.setNextDown(new Point<Float>());
    p.setNextRight(new Point<Float>());
    p.getNextDown().setValue(v2);
    p.getNextRight().setValue(v3);
    assertEquals("incorrect Point setter/getter methods", v2, p.getNextDown().getValue());
    assertEquals("incorrect Point setter/getter methods", v3, p.getNextRight().getValue());
  }

  @Test(timeout=1000)
  public void test_point3() {
    Double v1 = Double.valueOf(10);
    Double v2 = Double.valueOf(11);
    Double v3 = Double.valueOf(12);
    Point<Double> p = new Point<Double>();
    assertEquals("incorrect Point contructor and/or getValue method", null, p.getValue());
    assertEquals("incorrect Point contructor and/or getNextDown method", null, p.getNextDown());
    assertEquals("incorrect Point contructor and/or getNextRight method", null, p.getNextRight());
    p.setValue(v1);
    assertEquals("incorrect Point setValue and/or getValue methods", v1, p.getValue());
    p.setNextDown(new Point<Double>());
    p.setNextRight(new Point<Double>());
    p.getNextDown().setValue(v2);
    p.getNextRight().setValue(v3);
    assertEquals("incorrect Point setter/getter methods", v2, p.getNextDown().getValue());
    assertEquals("incorrect Point setter/getter methods", v3, p.getNextRight().getValue());
  }

  // ***** class MeshIterator tests **********************************
  @Rule public ExpectedException ex = ExpectedException.none();

    @Test(timeout=1000)
    public void test_meshiterator1() throws Exception {
      ex.expect(UnsupportedOperationException.class);
      Mesh<Integer> mesh = new Mesh<>(1,1);
      MeshIterator<Integer> it = new MeshIterator<>(mesh);
      it.remove();
    }

  // ***** class Mesh tests **********************************
  @Test(timeout=1000)
  public void test_mesh1() {
      String expectedOut, actualOut;

      Mesh<Integer> mesh = new Mesh<>(2,2);
      Integer v1 = Integer.valueOf(-5);
      Integer v2 = Integer.valueOf(0);
      Integer v3 = Integer.valueOf(9);
      Integer v4 = Integer.valueOf(16);

      assertNull("Mesh initialization does not assign values to the Points", mesh.getHead().getValue());
      assertTrue("Mesh default direction is \'right\'", mesh.getDirection().equals("right"));
      mesh.getHead().setValue(v1);
      assertEquals("Mesh constructor or the setter/getter methods are incorrect", v1, mesh.getHead().getValue());
      mesh.getHead().getNextRight().setValue(v2);
      assertEquals("Mesh constructor or the setter/getter methods are incorrect", v2, mesh.getHead().getNextRight().getValue());
      mesh.getHead().getNextDown().setValue(v3);
      assertEquals("Mesh constructor or the setter/getter methods are incorrect", v3, mesh.getHead().getNextDown().getValue());
      mesh.getHead().getNextRight().getNextDown().setValue(v4);
      assertEquals("Mesh constructor or the setter/getter methods are incorrect", v4, mesh.getHead().getNextDown().getNextRight().getValue());

      expectedOut = "-5 0 ";
      setCapture();
      for (Integer i : mesh)
        System.out.print(i+" ");
      actualOut = getCapture();
      assertEquals("Mesh iteration is incorrect", expectedOut, actualOut);
      unsetCapture();

      mesh.setDirection("down");
      expectedOut = "-5 9 ";
      setCapture();
      for (Integer i : mesh)
        System.out.print(i+" ");
      actualOut = getCapture();
      assertEquals("Mesh iteration is incorrect", expectedOut, actualOut);
      unsetCapture();

      mesh.setDirection("down_right");
      expectedOut = "-5 16 ";
      setCapture();
      for (Integer i : mesh)
        System.out.print(i+" ");
      actualOut = getCapture();
      assertEquals("Mesh iteration is incorrect", expectedOut, actualOut);
      unsetCapture();

      mesh.setDirection("down");
      expectedOut = "-5 9 16 ";
      setCapture();
      for (Integer i : mesh)
      {
          System.out.print(i+" ");
          mesh.setDirection("right");
      }
      actualOut = getCapture();
      assertEquals("Mesh iteration is incorrect", expectedOut, actualOut);
      unsetCapture();
  }

  @Test(timeout=1000)
  public void test_mesh2() {
      String expectedOut, actualOut;

      Mesh<Float> mesh = new Mesh<>(2,2);
      Float v1 = Float.valueOf(2.99f);
      Float v2 = Float.valueOf(-0.1f);
      Float v3 = Float.valueOf(9);
      Float v4 = Float.valueOf(16);

      assertNull("Mesh initialization does not assign values to the Points", mesh.getHead().getValue());
      assertTrue("Mesh default direction is \'right\'", mesh.getDirection().equals("right"));
      mesh.getHead().setValue(v1);
      assertEquals("Mesh constructor or the setter/getter methods are incorrect", v1, mesh.getHead().getValue());
      mesh.getHead().getNextRight().setValue(v2);
      assertEquals("Mesh constructor or the setter/getter methods are incorrect", v2, mesh.getHead().getNextRight().getValue());
      mesh.getHead().getNextDown().setValue(v3);
      assertEquals("Mesh constructor or the setter/getter methods are incorrect", v3, mesh.getHead().getNextDown().getValue());
      mesh.getHead().getNextRight().getNextDown().setValue(v4);
      assertEquals("Mesh constructor or the setter/getter methods are incorrect", v4, mesh.getHead().getNextDown().getNextRight().getValue());

      expectedOut = "2.99 -0.1 ";
      setCapture();
      for (Float i : mesh)
        System.out.print(i+" ");
      actualOut = getCapture();
      assertEquals("Mesh iteration is incorrect", expectedOut, actualOut);
      unsetCapture();

      mesh.setDirection("down");
      expectedOut = "2.99 9.0 ";
      setCapture();
      for (Float i : mesh)
        System.out.print(i+" ");
      actualOut = getCapture();
      assertEquals("Mesh iteration is incorrect", expectedOut, actualOut);
      unsetCapture();

      mesh.setDirection("down_right");
      expectedOut = "2.99 16.0 ";
      setCapture();
      for (Float i : mesh)
        System.out.print(i+" ");
      actualOut = getCapture();
      assertEquals("Mesh iteration is incorrect", expectedOut, actualOut);
      unsetCapture();

      mesh.setDirection("right");
      expectedOut = "2.99 -0.1 16.0 ";
      setCapture();
      for (Float i : mesh)
      {
          System.out.print(i+" ");
          mesh.setDirection("down");
      }
      actualOut = getCapture();
      assertEquals("Mesh iteration is incorrect", expectedOut, actualOut);
      unsetCapture();

  }

    /** the lines below are for setting up input/output redirection so that the
      * tests can see what is being set to the screen as well as produce its own
      * pseudo-keyboard input.  No test appear below here. */

    static ByteArrayOutputStream localOut, localErr;
    static ByteArrayInputStream localIn;
    static PrintStream sOut, sErr;
    static InputStream sIn;

    @BeforeClass public static void setup() throws Exception {
      sOut = System.out;
      sErr = System.err;
      sIn  = System.in;
    }

    @AfterClass public static void cleanup() throws Exception {
      unsetCapture();
      unsetInput();
    }

    private static void setCapture() {
     localOut = new ByteArrayOutputStream();
     localErr = new ByteArrayOutputStream();
     System.setOut(new PrintStream( localOut ) );
     System.setErr(new PrintStream( localErr ) );
    }

    private static String getCapture() {
     return localOut.toString().replaceAll("\\r?\\n", "\n");
    }

    private static String getCaptureErr() {
     return localErr.toString().replaceAll("\\r?\\n", "\n");
    }

    private static void unsetCapture() {
     System.setOut( null );
     System.setOut( sOut );
     System.setErr( null );
     System.setErr( sErr );
    }

    private static void setInput(String input) {
      localIn = new ByteArrayInputStream(input.getBytes());
      System.setIn(localIn);
    }

    private static void unsetInput() throws IOException {
      if (localIn != null) localIn.close();
      System.setIn( null );
      System.setIn( sIn  );
    }

}
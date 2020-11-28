/** 
 * On Mac/Linux:
 *  javac -cp .:junit-cs211.jar *.java         # compile everything
 *  java -cp .:junit-cs211.jar P5Tester        # run tests
 * 
 * On windows replace colons with semicolons: (: with ;)
 *  javac -cp .;junit-cs211.jar *.java         # compile everything
 *  java -cp .;junit-cs211.jar P5Tester        # run tests
 */
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class P5Tester {
  public static void main(String args[]){
      org.junit.runner.JUnitCore.main("P5Tester");
    }
    private final double ERR = 0.00001;
    //MazePosition tests
    @Test(timeout=1000) public void mazeposition_ctor(){
      MazePosition mp = new MazePosition(0,0);
      assertEquals("incorrect row", 0, mp.getRow());
      assertEquals("incorrect column", 0, mp.getCol());
      mp = new MazePosition(3,4);
      assertEquals("incorrect row",3,mp.getRow());
      assertEquals("incorrect column",4,mp.getCol());      
      // // negative vals are OK
      mp = new MazePosition(-1,-2);
      assertEquals("incorrect row",-1,mp.getRow());
      assertEquals("incorrect column",-2,mp.getCol());      
    }

    @Test(timeout=1000) public void mazeposition_directions(){
      MazePosition mp = new MazePosition(3,4);
      MazePosition tmp = mp.up();
      assertEquals("incorrect row (up)",mp.getRow()-1,tmp.getRow());
      assertEquals("incorrect col (up)",mp.getCol(),tmp.getCol());
      tmp = mp.down();
      assertEquals("incorrect row (down)",mp.getRow()+1,tmp.getRow());
      assertEquals("incorrect col (down)",mp.getCol(),tmp.getCol());
      tmp = mp.left();
      assertEquals("incorrect row (left)",mp.getRow(),tmp.getRow());
      assertEquals("incorrect col (left)",mp.getCol()-1,tmp.getCol());
      tmp = mp.right();
      assertEquals("incorrect row (right)",mp.getRow(),tmp.getRow());
      assertEquals("incorrect col (right)",mp.getCol()+1,tmp.getCol());
    }

    @Test(timeout=1000) public void mazeposition_equals(){
      MazePosition mp = new MazePosition(5,6);
      MazePosition mp2 = new MazePosition(5,6);
      MazePosition mp3 = new MazePosition(6,5);
      Object o = new Object();
      assertTrue("incorrect equals() method1",mp.equals(mp));
      assertTrue("incorrect equals() method2",mp.equals(mp2));
      assertFalse("incorrect equals() method3",mp.equals(mp3));
      assertFalse("incorrect equals() method4",mp.equals(o));
    }
    //Maze tests
    @Test(timeout=1000) public void maze_ctor(){
      char[][] ca_data = {{'*','*','*'},{' ',' ','G'},{'*','*','*'}};
      ArrayList<String> al_data = new ArrayList<String>();
      al_data.add("***");
      al_data.add("  G");
      al_data.add("***");
      Maze m1 = new Maze(al_data);
      Maze m2 = new Maze(ca_data);
    }
    @Test(timeout=1000) public void maze_clear(){
      char[][] ca_data = {{'*',' ','G'}};
      ArrayList<String> al_data = new ArrayList<String>();
      al_data.add("* G");
      Maze m1 = new Maze(al_data);
      Maze m2 = new Maze(ca_data);
      assertFalse("incorrect isClear() method1",m1.isClear(new MazePosition(0,0)));
      assertTrue("incorrect isClear() method2",m1.isClear(new MazePosition(0,1)));
      assertFalse("incorrect isClear() method3",m1.isClear(new MazePosition(0,2)));
      assertFalse("incorrect isClear() method4",m2.isClear(new MazePosition(0,0)));
      assertTrue("incorrect isClear() method5",m2.isClear(new MazePosition(0,1)));
      assertFalse("incorrect isClear() method6",m2.isClear(new MazePosition(0,2)));
    }
    @Test(timeout=1000) public void maze_goal(){
      char[][] ca_data = {{'*',' ','G'}};
      ArrayList<String> al_data = new ArrayList<String>();
      al_data.add("* G");
      Maze m1 = new Maze(al_data);
      Maze m2 = new Maze(ca_data);
      assertFalse("incorrect isGoal() method1",m1.isGoal(new MazePosition(0,0)));
      assertFalse("incorrect isGoal() method2",m1.isGoal(new MazePosition(0,1)));
      assertTrue("incorrect isGoal() method3",m1.isGoal(new MazePosition(0,2)));
      assertFalse("incorrect isGoal() method4",m2.isGoal(new MazePosition(0,0)));
      assertFalse("incorrect isGoal() method5",m2.isGoal(new MazePosition(0,1)));
      assertTrue("incorrect isGoal() method6",m2.isGoal(new MazePosition(0,2)));
    }
    //MazeSolver tests
    @Test(timeout=1000) public void mazesolver_ctor(){
      char[][] ca_data = {{'*',' ','G'}};
      Maze m = new Maze(ca_data);
      MazeSolver ms = new MazeSolver(m);
    }
    private void test_maze(Maze m, MazePosition start, ArrayList<String> solution){
      MazeSolver ms = new MazeSolver(m);
      assertEquals("incorrect maze solution",solution,ms.solve(start,new ArrayList<MazePosition>()));
    }
    @Test(timeout=1000) public void mazesolver_solve(){
      char[][] ca_data = {{'*',' ','G'}};
      Maze m = new Maze(new char[][]{{'*',' ','G'}});
      ArrayList<String> sol = new ArrayList<String>();
      sol.add("Right"); sol.add("Goal!");
      test_maze(m,new MazePosition(0,1),sol);
      // assertEquals("incorrect solution",sol,ms.solve(new MazePosition(0,1),new ArrayList<MazePosition>()));
      test_maze(m,new MazePosition(0,0),null);
      // assertEquals("incorrect solution (not null)",null,ms.solve(new MazePosition(0,0),new ArrayList<MazePosition>()));
      sol.clear(); sol.add("Goal!");
      test_maze(m,new MazePosition(0,2),sol);
      // assertEquals("incorrect solution (at goal)",sol,ms.solve(new MazePosition(0,2),new ArrayList<MazePosition>()));
    }
    @Test(timeout=1000) public void mazesolver_small(){
      ArrayList<String> al_data = new ArrayList<String>();
      al_data.add("****");
      al_data.add("*  G");
      al_data.add("****");
      Maze m = new Maze(al_data);
      test_maze(m,new MazePosition(1,0),null);
      ArrayList<String> sol = new ArrayList<String>();
      sol.add("Right"); sol.add("Right"); sol.add("Goal!");
      test_maze(m,new MazePosition(1,1),sol);
      sol.clear(); sol.add("Goal!");
      test_maze(m,new MazePosition(1,3),sol);
    }
    @Test(timeout=1000) public void mazesolver_med(){
      ArrayList<String> al_data = new ArrayList<String>();
      al_data.add("********G*");
      al_data.add("*        *");
      al_data.add("* ********");
      al_data.add("*        *");
      al_data.add("******** *");
      al_data.add("*        *");
      al_data.add("* ********");
      Maze m = new Maze(al_data);
      ArrayList<String> sol = new ArrayList<String>();
      //up 1
      sol.add("Up"); 
      //right 7
      sol.add("Right"); sol.add("Right"); sol.add("Right"); sol.add("Right"); sol.add("Right"); sol.add("Right"); sol.add("Right");
      //up 2
      sol.add("Up"); sol.add("Up");
      //left 7
      sol.add("Left"); sol.add("Left"); sol.add("Left"); sol.add("Left"); sol.add("Left"); sol.add("Left"); sol.add("Left");
      //up 2
      sol.add("Up"); sol.add("Up");
      //right 7
      sol.add("Right"); sol.add("Right"); sol.add("Right"); sol.add("Right"); sol.add("Right"); sol.add("Right"); sol.add("Right");
      //up 1
      sol.add("Up"); sol.add("Goal!");
      test_maze(m,new MazePosition(6,1),sol);
    }
    @Test(timeout=1000) public void mazesolver_large(){
      ArrayList<String> data = new ArrayList<String>();
      data.add("** *******");
      data.add("*        *");
      data.add("*   *    *");
      data.add("*        *");
      data.add("* *      *");
      data.add("*     *  *");
      data.add("*   *  * *");
      data.add("*   *   **");
      data.add("*        *");
      data.add("********G*");
      Maze m = new Maze(data);
      ArrayList<String> solution = new ArrayList<String>();
      solution.add("Down"); solution.add("Left"); solution.add("Down"); solution.add("Down"); solution.add("Down"); 
      solution.add("Down"); solution.add("Down"); solution.add("Down"); solution.add("Down"); solution.add("Right"); 
      solution.add("Up"); solution.add("Up"); solution.add("Up"); solution.add("Right"); solution.add("Up"); solution.add("Up"); 
      solution.add("Up"); solution.add("Up"); solution.add("Right"); solution.add("Right"); solution.add("Down"); 
      solution.add("Down"); solution.add("Left"); solution.add("Down"); solution.add("Down"); solution.add("Right"); 
      solution.add("Down"); solution.add("Down"); solution.add("Down"); solution.add("Right"); solution.add("Up"); 
      solution.add("Right"); solution.add("Down"); solution.add("Right"); solution.add("Down"); solution.add("Goal!");
      test_maze(m,new MazePosition(0,2),solution);
    }

    @Test(timeout=1000) public void mazesolver_new1() {
      ArrayList<String> bl_data = new ArrayList<String>();
      bl_data.add("** *******");
      bl_data.add("*    **** ");
      bl_data.add("***    ***");
      bl_data.add("******    ");
      bl_data.add("****      ");
      bl_data.add("**G    ***");

      Maze m = new Maze(bl_data);
      ArrayList<String> solution = new ArrayList<String>();
      solution.add("Down"); solution.add("Right"); solution.add("Down"); solution.add("Right"); 
      solution.add("Right");  solution.add("Right");  solution.add("Down");  solution.add("Down");  solution.add("Left");  solution.add("Left");  
      solution.add("Down");  solution.add("Left"); solution.add("Left");  solution.add("Goal!");
      test_maze(m, new MazePosition(0,2), solution);
    }

    @Test(timeout=1000) public void mazesolver_new2() {

      ArrayList<String> cl_data = new ArrayList<String>();
      cl_data.add("***    *****");
      cl_data.add("*  ** **   *");
      cl_data.add("****      **");
      cl_data.add("*G    ** ***");
      cl_data.add("**       ***");
      cl_data.add("******   ***");
      cl_data.add("****    ****");
      cl_data.add("******  ****");
      cl_data.add("*  ***   ***");

      Maze m = new Maze(cl_data);
      ArrayList<String> solution = new ArrayList<String>();
      solution.add("Up"); solution.add("Up"); solution.add("Up"); solution.add("Up"); 
      solution.add("Left");  solution.add("Left");  solution.add("Up");  solution.add("Up");  solution.add("Left");  solution.add("Down");  
      solution.add("Left");  solution.add("Left"); solution.add("Left"); solution.add("Goal!");
      test_maze(m, new MazePosition(8,7), solution);
    }

    @Test(timeout=1000) public void mazesolver_new3() {

      ArrayList<String> dl_data = new ArrayList<String>();
      dl_data.add("************************");
      dl_data.add("***               ******");
      dl_data.add("*** ************* ******");
      dl_data.add("*** ************* ******");
      dl_data.add("*** **          * ******");
      dl_data.add("*** ** ******** * ******");
      dl_data.add("*** **       G* * ******");
      dl_data.add("*** *********** * ******");
      dl_data.add("***             * ******");
      dl_data.add("***************** ******");
      dl_data.add("***               ******");
      dl_data.add("*** ********************");

      Maze m = new Maze(dl_data);
      ArrayList<String> solution = new ArrayList<String>();
      solution.add("Up"); solution.add("Right"); solution.add("Right"); 
      solution.add("Right");  solution.add("Right");  solution.add("Right");  solution.add("Right");  solution.add("Right");  solution.add("Right");  
      solution.add("Right"); solution.add("Right"); solution.add("Right"); solution.add("Right"); 
      solution.add("Right");  solution.add("Right");  solution.add("Up");  solution.add("Up");  solution.add("Up");  solution.add("Up"); 
      solution.add("Up"); solution.add("Up"); solution.add("Up"); solution.add("Up"); 
      solution.add("Up");  solution.add("Left");  solution.add("Left");  solution.add("Left");  solution.add("Left");  solution.add("Left"); 
      solution.add("Left"); solution.add("Left"); solution.add("Left"); solution.add("Left"); 
      solution.add("Left");  solution.add("Left");  solution.add("Left");  solution.add("Left");  solution.add("Left");  solution.add("Down"); 
      solution.add("Down"); solution.add("Down"); solution.add("Down"); solution.add("Down"); 
      solution.add("Down");  solution.add("Down");  solution.add("Right");  solution.add("Right");  solution.add("Right");  solution.add("Right"); 
      solution.add("Right"); solution.add("Right"); solution.add("Right"); solution.add("Right"); 
      solution.add("Right");  solution.add("Right");  solution.add("Right");  solution.add("Right");  solution.add("Up");  solution.add("Up"); 
      solution.add("Up"); solution.add("Up"); solution.add("Left"); solution.add("Left"); 
      solution.add("Left");  solution.add("Left");  solution.add("Left");  solution.add("Left");  solution.add("Left");  solution.add("Left"); 
      solution.add("Left"); solution.add("Down"); solution.add("Down"); solution.add("Right"); 
      solution.add("Right");  solution.add("Right");  solution.add("Right");  solution.add("Right");  solution.add("Right");  solution.add("Right"); solution.add("Goal!"); 
      test_maze(m, new MazePosition(11,3), solution);
    }

    @Test(timeout=1000) public void mazesolver_new4() {

      ArrayList<String> el_data = new ArrayList<String>();
      el_data.add("************************");
      el_data.add("******************G ****");
      el_data.add("*** ****** ****** ******");
      el_data.add("*** ******        ******");
      el_data.add("*** ************* ******");
      el_data.add("*** ** ******** * ******");
      el_data.add("*** **        * * ******");
      el_data.add("*** *********** * ******");
      el_data.add("***             * ******");
      el_data.add("******* ********* ******");
      el_data.add("***               ******");
      el_data.add("*** ********************");

      Maze m = new Maze(el_data);

      test_maze(m, new MazePosition(11, 3), null);
    }


    @Test(timeout=1000) public void mazesolver_new5() {               

      ArrayList<String> fl_data = new ArrayList<String>();
      fl_data.add("*****G***");
      fl_data.add("*** * ***");
      fl_data.add("*** * ***");
      fl_data.add("***   ***");
      fl_data.add("**** ****");
      fl_data.add("***   ***");
      fl_data.add("**** ****");
      fl_data.add("**** ****");

      Maze m = new Maze(fl_data);

      ArrayList<String> solution = new ArrayList<String>();
      solution.add("Up");
      solution.add("Up");
      solution.add("Up");
      solution.add("Up");
      solution.add("Right");
      solution.add("Up");
      solution.add("Up");
      solution.add("Up");
      solution.add("Goal!");

      test_maze(m, new MazePosition(7, 4), solution);
    }

    @Test(timeout=1000) public void mazesolver_new6() {

      ArrayList<String> gl_data = new ArrayList<String>();

      gl_data.add("********************************");
      gl_data.add("***   ***** ***    *************");
      gl_data.add("**  *******     ** *************");
      gl_data.add("** *********** *** * ***********");
      gl_data.add("**    ****     ***   ***********");
      gl_data.add("**** ***** *** ***** ***********");
      gl_data.add("****       **  **G   ***********");
      gl_data.add("****** *************************");

      Maze m = new Maze(gl_data);

      ArrayList<String> solution = new ArrayList<String>();
      solution.add("Up");
      solution.add("Right");
      solution.add("Right");
      solution.add("Right");
      solution.add("Right");
      solution.add("Up");
      solution.add("Up");
      solution.add("Right");
      solution.add("Right");
      solution.add("Right");
      solution.add("Right");
      solution.add("Up");
      solution.add("Up");
      solution.add("Right");
      solution.add("Up");
      solution.add("Right");
      solution.add("Right");
      solution.add("Right");
      solution.add("Down");
      solution.add("Down");
      solution.add("Down");
      solution.add("Right");
      solution.add("Right");
      solution.add("Down");
      solution.add("Down");
      solution.add("Left");
      solution.add("Left");
      solution.add("Left");
      solution.add("Goal!");

      test_maze(m, new MazePosition(7, 6), solution);
    }

    @Test(timeout=1000) public void mazesolver_new7() {

      ArrayList<String> hl_data = new ArrayList<String>();

      hl_data.add("************************G*******************");
      hl_data.add("********   ***** **     ********************");
      hl_data.add("**  ****  ********* ***     ** *************");
      hl_data.add("** ******      **** ******* * **************");
      hl_data.add("*******    **           *** * ** ***********");
      hl_data.add("******* ***** ******* ********** ***********");
      hl_data.add("******          *** ****  ** *   ***********");
      hl_data.add("****** *************************************");

      Maze m = new Maze(hl_data);

      test_maze(m, new MazePosition(7, 6), null);
    }

    @Test(timeout=1000) public void mazesolver_notequals() {

      String s = "Brian";

      MazePosition mp = new MazePosition(0,0);

      assertFalse("two objects are equal", mp.equals(s));
    }

    @Test(timeout=1000) public void mazesolver_toString() {

      MazePosition mp1 = new MazePosition(4, 4);

      assertTrue("toString() is incorrect.", mp1.toString().equals("MazePosition (4, 4)"));
    }

    @Test(timeout=1000) public void mazesolver_constructor2() {

      char[][] temp = {{' ', '*'}, {' ', ' '}};
  
      Maze mp2 = new Maze(temp);

      assertNotNull(mp2);
    }

    @Test(timeout=1000) public void mazesolver_hashcode() {

      MazePosition mp3 = new MazePosition(1, 2);

      assertEquals("hashCode() and toString() are not equal.", mp3.toString().hashCode(), mp3.hashCode());
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
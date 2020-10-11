/** Example of using unit tests for this assignment.  To run them on the command line, make
 * sure that the junit-cs211.jar is in the same directory.
 *
 * On Mac/Linux:
 *  javac -cp .:junit-cs211.jar *.java         # compile everything
 *  java -cp .:junit-cs211.jar P2Tester        # run tests
 *
 * On windows replace colons with semicolons: (: with ;)
 *  demo$ javac -cp .;junit-cs211.jar *.java   # compile everything
 *  demo$ java -cp .;junit-cs211.jar P2Tester  # run tests
 */
import org.junit.*;
import static org.junit.Assert.*;

public class P2Tester {
  public static void main(String args[]){
    org.junit.runner.JUnitCore.main("P2Tester");
  }
  // ***** class Person tests **********************************

  @Test public void test_person1() {
    Person p = new Person();
    Family f = new Family((byte)1, (byte)1);
    assertEquals("invalid Person name", true, p.setName("George Mason"));
    assertEquals("invalid Person birthday", true, p.setBirthday("1725/12/11"));
    assertEquals("invalid Person SSN", true, p.setSSN("123-45-6789"));
    assertEquals("invalid Person income", false, p.setIncome(-0.01f));
    assertEquals("invalid Person income", 0.0, p.getIncome(), 0.1);
    assertEquals("incorrect Person id", 1, p.getId());
    assertEquals("incorrect Person string", "George Mason xxx-xx-6789 1725/**/**", p.toString());
    assertEquals("incorrect setup of deduction()", 0.0, p.deduction(f), 0.1);
  }

  @Test public void test_person2() {
    Person p = new Person();
    Family f = new Family((byte)1, (byte)1);
    assertEquals("invalid Person name", false, p.setName("Donald_Trump"));
    assertEquals("invalid Person name", true, p.setName("Donald Duck"));
    assertEquals("invalid Person birthday", false, p.setBirthday("172a/12/11"));
    assertEquals("invalid Person birthday", true, p.setBirthday("2000/01/01"));
    assertEquals("invalid Person SSN", false, p.setSSN("123-45-6x89"));
    assertEquals("invalid Person SSN", true, p.setSSN("111-22-0000"));
    assertEquals("invalid Person income", true, p.setIncome(9999.99f));
    assertEquals("invalid Person income", 9999.99f, p.getIncome(), 0.1);
    assertEquals("incorrect Person id", 2, p.getId());
    assertEquals("incorrect Person string", "Donald Duck xxx-xx-0000 2000/**/**", p.toString());
    assertEquals("incorrect setup of deduction()", 0.0, p.deduction(f), 0.1);
  }

  @Test public void test_person3() {
    Person p = new Person();
    Family f = new Family((byte)1, (byte)1);
    assertEquals("invalid Person name", false, p.setName("D0nald Trump"));
    assertEquals("invalid Person name", true, p.setName("Donald Trump"));
    assertEquals("invalid Person birthday", false, p.setBirthday("12/23"));
    assertEquals("invalid Person birthday", true, p.setBirthday("0009/91/51"));
    assertEquals("invalid Person SSN", false, p.setSSN("3-45-6789"));
    assertEquals("invalid Person SSN", true, p.setSSN("000-00-1234"));
    assertEquals("invalid Person income", true, p.setIncome(0.0f));
    assertEquals("invalid Person income", 0.0f, p.getIncome(), 0.1);
    assertEquals("incorrect Person id", 3, p.getId());
    assertEquals("incorrect Person string", "Donald Trump xxx-xx-1234 0009/**/**", p.toString());
    assertEquals("incorrect setup of deduction()", 0.0, p.deduction(f), 0.1);
  }


  // ***** class Adult tests **********************************

  @Test public void test_adult1() {
    Adult a = new Adult("George Mason", "2000/01/01", "987-65-4321", 1234.56f, "GMU");
    Family f = new Family((byte)1, (byte)1);
    assertEquals("incorrect Adult income", 1234.56, a.getIncome(), 0.1);
    assertEquals("incorrect Adult string", "George Mason xxx-xx-4321 2000/**/** 1234.56", a.toString());
    assertEquals("incorrect Adult adjusted income", 1140.11, a.adjustedIncome(), 0.1);
    assertEquals("incorrect Adult deduction", 1140.11, a.deduction(f), 0.1);
    assertEquals("incorrect Adult tax withholding", 123.45, a.taxWithheld(), 0.1);
    assertEquals("incorrect Adult employer", "GMU", a.getEmployer());
  }

  @Test public void test_adult2() {
    Adult a = new Adult("George Washington", "1732/02/22", "987-65-4321", 67890.12f, "FBI");
    Family f = new Family((byte)1, (byte)1);
    assertEquals("incorrect Adult income", 67890.12, a.getIncome(), 0.1);
    assertEquals("incorrect Adult string", "George Washington xxx-xx-4321 1732/**/** 67890.12", a.toString());
    assertEquals("incorrect Adult adjusted income", 62696.52, a.adjustedIncome(), 0.1);
    assertEquals("incorrect Adult deduction", 3000, a.deduction(f), 0.1);
    assertEquals("incorrect Adult tax withholding", 7683.51, a.taxWithheld(), 0.1);
    assertEquals("incorrect Adult employer", "FBI", a.getEmployer());
  }

  @Test public void test_adult3() {
    Adult a = new Adult("Andrew Jackson", "1767/03/15", "987-65-1391", 234567.89f, "White House");
    Family f = new Family((byte)1, (byte)1);
    assertEquals("incorrect Adult income", 234567.89, a.getIncome(), 0.1);
    assertEquals("incorrect Adult string", "Andrew Jackson xxx-xx-1391 1767/**/** 234567.89", a.toString());
    assertEquals("incorrect Adult adjusted income", 222629.25, a.adjustedIncome(), 0.1);
    assertEquals("incorrect Adult deduction", 2100, a.deduction(f), 0.1);
    assertEquals("incorrect Adult tax withholding", 36913.57, a.taxWithheld(), 0.1);
    assertEquals("incorrect Adult employer", "White House", a.getEmployer());
  }


  // ***** class Child tests **********************************

  @Test public void test_child1() {
    Child c = new Child("George Mason Jr", "2008/12/01", "187-65-4321", 0.0f, "Fairfax High School", 1000.0f);
    Family f = new Family((byte)3, (byte)2);
    assertEquals("incorrect Child string", "George Mason Jr xxx-xx-4321 2008/**/** Fairfax High School", c.toString());
    assertEquals("incorrect Child income", 0.00, c.getIncome(), 0.1);
    assertEquals("incorrect Child deduction", 0.00, c.deduction(f), 0.1);
    assertEquals("incorrect Child tuition", 1000.00, c.getTuition(), 0.1);
  }

  @Test public void test_child2() {
    Child c = new Child("Home Alone", "2009/22/55", "987-25-4111", 1234.56f, "Woodson High School", 0.0f);
    Family f = new Family((byte)3, (byte)2);
    assertEquals("incorrect Person string", "Home Alone xxx-xx-4111 2009/**/** Woodson High School", c.toString());
    assertEquals("incorrect Child income", 1234.56, c.getIncome(), 0.1);
    assertEquals("incorrect Child deduction", 1234.56, c.deduction(f), 0.1);
    assertEquals("incorrect Child tuition", 0.00, c.getTuition(), 0.1);
  }

  @Test public void test_child3() {
    Child c = new Child("Child Prodigy", "2007/00/41", "000-68-0000", 34567.89f, "Robinson Secondary School", 6789.01f);
    Child c1 = new Child("Child Prodigy", "2007/00/41", "000-68-0000", 34567.89f, "Robinson Secondary School", 6789.01f);
    Child c2 = new Child("Child Prodigy", "2007/00/41", "000-68-0000", 34567.89f, "Robinson Secondary School", 6789.01f);
    Family f = new Family((byte)5, (byte)2);
    f.addMember(c);
    f.addMember(c1);
    f.addMember(c2);
    assertEquals("incorrect Person string", "Child Prodigy xxx-xx-0000 2007/**/** Robinson Secondary School", c.toString());
    assertEquals("incorrect Child income", 34567.89, c.getIncome(), 0.1);
    assertEquals("incorrect Child deduction", 1900.00, c.deduction(f), 0.1);
    assertEquals("incorrect Child tuition", 6789.01, c.getTuition(), 0.1);
  }

  // ***** class Family tests **********************************

  @Test public void test_family1() {
    Adult a = new Adult("George Floyd", "1976/05/01", "789-56-1234", 123456.78f, "Mason");
    Child c1 = new Child("Eleanor Roosvelt", "2000/01/01", "999-65-1111", 0.0f, "Fairfax High School", 1000.0f);
    Child c2 = new Child("George Washington", "1730/01/01", "709-56-1231", 576.78f, "Mason Elementary School", 0.0f);
    Family f = new Family((byte)3, (byte)1);
    f.addMember(a);
    f.addMember(c1);
    f.addMember(c2);
    assertEquals("incorrect Family number of adults", 1, f.getNumAdults());
    assertEquals("incorrect Family number of children", 2, f.getNumChildren());
    assertEquals("incorrect Family filing status", 1, f.getFilingStatus());
    assertEquals("incorrect Family taxable income", 108432.33, f.getTaxableIncome(), 0.1);
    assertEquals("incorrect Family tax credit", 0.0, f.taxCredit(), 0.1);
    assertEquals("incorrect Family tax calculation", 4205.24, f.calculateTax(), 0.1);
  }

  @Test public void test_family2() {
    Adult a1 = new Adult("name1", "1732/02/22", "987-65-4321", 67890.12f, "GMU");
    Adult a2 = new Adult("name2", "1732/02/22", "987-65-4321", 123456.78f, "GMU");
    Child c = new Child("name3", "2007/00/41", "000-68-0000", 567.89f, "Robinson Secondary School", 6789.01f);
    Family f = new Family((byte)3, (byte)2);
    f.addMember(a1);
    f.addMember(a2);
    f.addMember(c);
    assertEquals("incorrect Family number of adults", 2, f.getNumAdults());
    assertEquals("incorrect Family number of children", 1, f.getNumChildren());
    assertEquals("incorrect Family filing status", 2, f.getFilingStatus());
    assertEquals("incorrect Family taxable income", 170918.85, f.getTaxableIncome(), 0.1);
    assertEquals("incorrect Family tax credit", 0.0, f.taxCredit(), 0.1);
    assertEquals("incorrect Family tax calculation", 7727.67, f.calculateTax(), 0.1);
  }

  @Test public void test_family3() {
    Adult a1 = new Adult("name1", "1980/02/22", "987-65-4321", 7890.12f, "GMU");
    Adult a2 = new Adult("name2", "1973/12/09", "123-65-4741", 1346.78f, "GMU");
    Child c1 = new Child("name3", "2001/01/11", "000-68-0001", 0.0f, "Robinson Secondary School", 100.00f);
    Child c2 = new Child("name4", "2003/03/04", "000-68-0002", 0.0f, "Robinson Secondary School", 200.00f);
    Child c3 = new Child("name5", "2006/08/27", "000-68-0003", 0.0f, "Robinson Secondary School", 700.00f);
    Family f = new Family((byte)5, (byte)2);
    f.addMember(a1);
    f.addMember(a2);
    f.addMember(c1);
    f.addMember(c2);
    f.addMember(c3);
    assertEquals("incorrect Family number of adults", 2, f.getNumAdults());
    assertEquals("incorrect Family number of children", 3, f.getNumChildren());
    assertEquals("incorrect Family filing status", 2, f.getFilingStatus());
    assertEquals("incorrect Family taxable income", 4286.52, f.getTaxableIncome(), 0.1);
    assertEquals("incorrect Family tax credit", 428.65, f.taxCredit(), 0.1);
    assertEquals("incorrect Family tax calculation", -923.69, f.calculateTax(), 0.1);
  }

  // ***** class Taxation tests **********************************

  @Test public void test_taxation1() {
    assertEquals("incorrect Taxation parameter", 0.124, Taxation.socialSecurityRate, 0.1);
    assertEquals("incorrect Taxation parameter", 0.029, Taxation.medicareRate, 0.1);
    assertEquals("incorrect Taxation parameter", 137700, Taxation.socialSecurityIncomeLimit, 0.1);
    assertEquals("incorrect Taxation parameter", 3000, Taxation.adultBaseExemption, 0.1);
    assertEquals("incorrect Taxation parameter", 2000, Taxation.childBaseExemption, 0.1);
    assertEquals("incorrect Taxation parameter", 31099, Taxation.medianIncomePerCapita, 0.1);
  }

  @Test public void test_taxation2() {
    assertEquals("incorrect Taxation total number of brackets", 5, Taxation.getNumTaxBrackets());
    assertEquals("incorrect Taxation bracket tax rate", 0.1, Taxation.bracketTaxRate((byte)1,(byte)1), 0.1);
    assertEquals("incorrect Taxation bracket tax rate", 0.12, Taxation.bracketTaxRate((byte)2,(byte)2), 0.1);
    assertEquals("incorrect Taxation bracket tax rate", 0.24, Taxation.bracketTaxRate((byte)3,(byte)3), 0.1);
    assertEquals("incorrect Taxation bracket tax rate", 0.25, Taxation.bracketTaxRate((byte)4,(byte)2), 0.1);
    assertEquals("incorrect Taxation bracket tax rate", 0.32, Taxation.bracketTaxRate((byte)5,(byte)1), 0.1);
  }

  @Test public void test_taxation3() {
    assertEquals("incorrect Taxation bracket tax rate", 0.1, Taxation.bracketTaxRate((byte)1,(byte)1), 0.1);
    Adult a1 = new Adult("name1", "1732/02/22", "987-65-4321", 67890.12f, "GMU");
    Adult a2 = new Adult("name2", "1732/02/22", "987-65-4321", 123456.78f, "GMU");
    Child c = new Child("name3", "2007/00/41", "000-68-0000", 567.89f, "Robinson Secondary School", 6789.01f);
    Family f = new Family((byte)3, (byte)2);
    f.addMember(a1);
    f.addMember(a2);
    f.addMember(c);
    assertEquals("incorrect Taxation max tax bracket", 4, Taxation.maxIncomeTaxBracket(f));
    assertEquals("incorrect Taxation bracket income", 10918.85, Taxation.bracketIncome(f,(byte)4), 0.1);
    assertEquals("incorrect Taxation bracket income", 0.0, Taxation.bracketIncome(f,(byte)5), 0.1);
    assertEquals("incorrect Taxation bracket income", 90000.0, Taxation.bracketIncome(f,(byte)3), 0.1);
  }


  // ***** class TaxYear tests **********************************

  @Test public void test_taxyear1() {
    Adult a1 = new Adult("name1", "1732/02/22", "987-65-4321", 0.00f, "GMU");
    Adult a2 = new Adult("name2", "1732/02/22", "987-65-4321", 1234.56f, "GMU");
    Adult a3 = new Adult("name3", "1732/02/22", "987-65-4321", 13456.78f, "GMU");
    Adult a4 = new Adult("name4", "1732/02/22", "987-65-4321", 23979.54f, "GMU");
    Adult a5 = new Adult("name5", "1732/02/22", "987-65-4321", 67890.12f, "GMU");
    Adult a6 = new Adult("name6", "1732/02/22", "987-65-4321", 123456.78f, "GMU");
    Adult a7 = new Adult("name7", "1976/05/01", "789-56-1234", 145000.98f, "Mason");
    Adult a8 = new Adult("name8", "1732/02/22", "987-65-4321", 267890.12f, "GMU");
    Adult a9 = new Adult("name9", "1732/02/22", "987-65-4321", 312346.78f, "GMU");
    Child c1 = new Child("kid1", "2000/01/01", "999-65-1111", 0.0f, "Fairfax High School", 3300.0f);
    Child c2 = new Child("kid2", "2000/01/01", "999-65-1111", 100.0f, "Fairfax High School", 0.0f);
    Child c3 = new Child("kid3", "2000/01/01", "999-65-1111", 300.0f, "Fairfax High School", 0.0f);
    Child c4 = new Child("kid4", "2000/01/01", "999-65-1111", 900.0f, "Fairfax High School", 900.0f);
    Child c5 = new Child("kid5", "2000/01/01", "999-65-1111", 1600.0f, "Fairfax High School", 1234.0f);
    Child c6 = new Child("kid6", "2000/01/01", "999-65-1111", 7300.0f, "Fairfax High School", 6650.0f);
    Child c7 = new Child("kid7", "2000/01/01", "999-65-1111", 12000.0f, "Fairfax High School", 11999.0f);
    Child c8 = new Child("kid8", "2000/01/01", "999-65-1111", 27000.0f, "Fairfax High School", 100.0f);
    Child c9 = new Child("kid9", "2000/01/01", "999-65-1111", 41560.0f, "Fairfax High School", 8765.0f);
    Family f1 = new Family((byte)2, (byte)1);
    f1.addMember(a1);
    f1.addMember(a2);
    f1.calculateTax();
    Family f2 = new Family((byte)4, (byte)2);
    f2.addMember(a3);
    f2.addMember(a4);
    f2.addMember(c1);
    f2.addMember(c2);
    f2.calculateTax();
    Family f3 = new Family((byte)3, (byte)2);
    f3.addMember(a5);
    f3.addMember(a6);
    f3.addMember(c3);
    f3.calculateTax();
    Family f4 = new Family((byte)6, (byte)2);
    f4.addMember(a7);
    f4.addMember(a8);
    f4.addMember(c4);
    f4.addMember(c5);
    f4.addMember(c6);
    f4.addMember(c7);
    f4.calculateTax();
    Family f5 = new Family((byte)3, (byte)3);
    f5.addMember(a9);
    f5.addMember(c8);
    f5.addMember(c9);
    f5.calculateTax();
    TaxYear y = new TaxYear(100);
    assertEquals("tax filing validation failed", false, y.taxFiling(f1));
    assertEquals("tax filing validation failed", true, y.taxFiling(f2));
    assertEquals("tax filing validation failed", true, y.taxFiling(f3));
    assertEquals("tax filing validation failed", true, y.taxFiling(f4));
    assertEquals("tax filing validation failed", true, y.taxFiling(f5));
    assertEquals("TaxYear error", 4, y.numberOfReturnsFiled());
    assertEquals("TaxYear error", 16, y.numberOfPersonsFiled());
    assertEquals("TaxYear error", 142743.19, y.taxWithheld(), 0.1);
    assertEquals("TaxYear error", 233962.53, y.taxOwed(), 0.1);
    assertEquals("TaxYear error", 91219.33, y.taxDue(), 0.1);
    assertEquals("TaxYear error", 0.0, y.taxCredits(), 0.1);
  }

  @Test public void test_taxyear2() {
    Adult a1 = new Adult("name1", "1732/02/22", "987-65-4321", 0.00f, "GMU");
    Adult a2 = new Adult("name2", "1732/02/22", "987-65-4321", 1234.56f, "GMU");
    Adult a3 = new Adult("name3", "1732/02/22", "987-65-4321", 13456.78f, "GMU");
    Adult a4 = new Adult("name4", "1732/02/22", "987-65-4321", 23979.54f, "GMU");
    Adult a5 = new Adult("name5", "1732/02/22", "987-65-4321", 67890.12f, "GMU");
    Adult a6 = new Adult("name6", "1732/02/22", "987-65-4321", 123456.78f, "GMU");
    Adult a7 = new Adult("name7", "1976/05/01", "789-56-1234", 145000.98f, "Mason");
    Adult a8 = new Adult("name8", "1732/02/22", "987-65-4321", 267890.12f, "GMU");
    Adult a9 = new Adult("name9", "1732/02/22", "987-65-4321", 312346.78f, "GMU");
    Child c1 = new Child("kid1", "2000/01/01", "999-65-1111", 0.0f, "Fairfax High School", 3300.0f);
    Child c2 = new Child("kid2", "2000/01/01", "999-65-1111", 100.0f, "Fairfax High School", 0.0f);
    Child c3 = new Child("kid3", "2000/01/01", "999-65-1111", 300.0f, "Fairfax High School", 0.0f);
    Child c4 = new Child("kid4", "2000/01/01", "999-65-1111", 900.0f, "Fairfax High School", 900.0f);
    Child c5 = new Child("kid5", "2000/01/01", "999-65-1111", 1600.0f, "Fairfax High School", 1234.0f);
    Child c6 = new Child("kid6", "2000/01/01", "999-65-1111", 7300.0f, "Fairfax High School", 6650.0f);
    Child c7 = new Child("kid7", "2000/01/01", "999-65-1111", 12000.0f, "Fairfax High School", 11999.0f);
    Child c8 = new Child("kid8", "2000/01/01", "999-65-1111", 27000.0f, "Fairfax High School", 100.0f);
    Child c9 = new Child("kid9", "2000/01/01", "999-65-1111", 41560.0f, "Fairfax High School", 8765.0f);
    Family f1 = new Family((byte)2, (byte)3);
    f1.addMember(a1);
    f1.addMember(c1);
    f1.calculateTax();
    Family f2 = new Family((byte)4, (byte)2);
    f2.addMember(a2);
    f2.addMember(a3);
    f2.addMember(c2);
    f2.addMember(c3);
    f2.calculateTax();
    Family f3 = new Family((byte)3, (byte)2);
    f3.addMember(a4);
    f3.addMember(a5);
    f3.addMember(c4);
    f3.calculateTax();
    Family f4 = new Family((byte)6, (byte)2);
    f4.addMember(a6);
    f4.addMember(a7);
    f4.addMember(c5);
    f4.addMember(c6);
    f4.addMember(c7);
    f4.addMember(c8);
    f4.calculateTax();
    Family f5 = new Family((byte)2, (byte)2);
    f5.addMember(a8);
    f5.addMember(a9);
    f5.calculateTax();
    Family f6 = new Family((byte)1, (byte)1);
    f6.addMember(c9);
    TaxYear y = new TaxYear(100);
    assertEquals("tax filing validation failed", true, y.taxFiling(f1));
    assertEquals("tax filing validation failed", true, y.taxFiling(f2));
    assertEquals("tax filing validation failed", true, y.taxFiling(f3));
    assertEquals("tax filing validation failed", true, y.taxFiling(f4));
    assertEquals("tax filing validation failed", true, y.taxFiling(f5));
    assertEquals("tax filing validation failed", false, y.taxFiling(f6));
    assertEquals("TaxYear error", 5, y.numberOfReturnsFiled());
    assertEquals("TaxYear error", 17, y.numberOfPersonsFiled());
    assertEquals("TaxYear error", 142866.65, y.taxWithheld(), 0.1);
    assertEquals("TaxYear error", 216255.72, y.taxOwed(), 0.1);
    assertEquals("TaxYear error", 73119.06, y.taxDue(), 0.1);
    assertEquals("TaxYear error", 270.0, y.taxCredits(), 0.1);
  }


  // ***** class Analytics tests **********************************

  @Test public void analytics1() {
    Adult a1 = new Adult("name1", "1732/02/22", "987-65-4321", 0.00f, "GMU");
    Adult a2 = new Adult("name2", "1732/02/22", "987-65-4321", 1234.56f, "GMU");
    Adult a3 = new Adult("name3", "1732/02/22", "987-65-4321", 13456.78f, "GMU");
    Adult a4 = new Adult("name4", "1732/02/22", "987-65-4321", 23979.54f, "GMU");
    Adult a5 = new Adult("name5", "1732/02/22", "987-65-4321", 67890.12f, "GMU");
    Adult a6 = new Adult("name6", "1732/02/22", "987-65-4321", 123456.78f, "GMU");
    Adult a7 = new Adult("name7", "1976/05/01", "789-56-1234", 145000.98f, "Mason");
    Adult a8 = new Adult("name8", "1732/02/22", "987-65-4321", 267890.12f, "GMU");
    Adult a9 = new Adult("name9", "1732/02/22", "987-65-4321", 312346.78f, "GMU");
    Child c1 = new Child("kid1", "2000/01/01", "999-65-1111", 0.0f, "Fairfax High School", 3300.0f);
    Child c2 = new Child("kid2", "2000/01/01", "999-65-1111", 100.0f, "Fairfax High School", 0.0f);
    Child c3 = new Child("kid3", "2000/01/01", "999-65-1111", 300.0f, "Fairfax High School", 0.0f);
    Child c4 = new Child("kid4", "2000/01/01", "999-65-1111", 900.0f, "Fairfax High School", 900.0f);
    Child c5 = new Child("kid5", "2000/01/01", "999-65-1111", 1600.0f, "Fairfax High School", 1234.0f);
    Child c6 = new Child("kid6", "2000/01/01", "999-65-1111", 7300.0f, "Fairfax High School", 6650.0f);
    Child c7 = new Child("kid7", "2000/01/01", "999-65-1111", 12000.0f, "Fairfax High School", 11999.0f);
    Child c8 = new Child("kid8", "2000/01/01", "999-65-1111", 27000.0f, "Fairfax High School", 100.0f);
    Child c9 = new Child("kid9", "2000/01/01", "999-65-1111", 41560.0f, "Fairfax High School", 8765.0f);
    Family f1 = new Family((byte)2, (byte)2);
    f1.addMember(a1);
    f1.addMember(a2);
    f1.calculateTax();
    Family f2 = new Family((byte)4, (byte)2);
    f2.addMember(a3);
    f2.addMember(a4);
    f2.addMember(c1);
    f2.addMember(c2);
    f2.calculateTax();
    Family f3 = new Family((byte)3, (byte)2);
    f3.addMember(a5);
    f3.addMember(a6);
    f3.addMember(c3);
    f3.calculateTax();
    Family f4 = new Family((byte)6, (byte)2);
    f4.addMember(a7);
    f4.addMember(a8);
    f4.addMember(c4);
    f4.addMember(c5);
    f4.addMember(c6);
    f4.addMember(c7);
    f4.calculateTax();
    Family f5 = new Family((byte)3, (byte)3);
    f5.addMember(a9);
    f5.addMember(c8);
    f5.addMember(c9);
    f5.calculateTax();
    TaxYear y = new TaxYear(100);
    assertEquals("tax filing validation failed", true, y.taxFiling(f1));
    assertEquals("tax filing validation failed", true, y.taxFiling(f2));
    assertEquals("tax filing validation failed", true, y.taxFiling(f3));
    assertEquals("tax filing validation failed", true, y.taxFiling(f4));
    assertEquals("tax filing validation failed", true, y.taxFiling(f5));
    Analytics stats = new Analytics(y);
    assertEquals("Analytics error", 192014.20, stats.averageFamilyIncome(), 0.1);
    assertEquals("Analytics error", 53337.28, stats.averagePersonIncome(), 0.1);
    assertEquals("Analytics error", 400939.37, stats.maxFamilyIncome(), 0.1);
    assertEquals("Analytics error", 295080.37, stats.maxPersonIncome(), 0.1);
    assertEquals("Analytics error", 1, stats.familiesBelowPovertyLimit());
    stats.setPovertyThreshold(50000.0f);
    assertEquals("Analytics error", 2, stats.familiesBelowPovertyLimit());
    assertEquals("Analytics error", 1, stats.familyRank(f4));
    assertEquals("Analytics error", 2, stats.familyRank(f5));
  }

}
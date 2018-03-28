package com.nlf.bytecode; 

import com.nlf.App;
import com.nlf.util.IOUtil;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/** 
* Klass Tester. 
* 
* @author <Authors name> 
* @version 1.0 
*/ 
public class KlassTest { 

@Before
public void before() throws Exception {
}

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getConstant(int index) 
* 
*/ 
@Test
public void testGetConstant() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getInterfaces() 
* 
*/ 
@Test
public void testGetInterfaces() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getSuperClass() 
* 
*/ 
@Test
public void testGetSuperClass() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getName() 
* 
*/ 
@Test
public void testGetName() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: isAbstract() 
* 
*/ 
@Test
public void testIsAbstract() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: isInterface() 
* 
*/ 
@Test
public void testIsInterface() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getAccess() 
* 
*/ 
@Test
public void testGetAccess() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getMinorVersion() 
* 
*/ 
@Test
public void testGetMinorVersion() throws Exception {
}

/** 
* 
* Method: getMajorVersion() 
* 
*/ 
@Test
public void testGetMajorVersion() throws Exception {
}

/** 
* 
* Method: getMethods() 
* 
*/ 
@Test
public void testGetMethods() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: decode() 
* 
*/ 
@Test
public void testDecode() throws Exception { 
//TODO: Test goes here... 
} 

byte[] readBytes(String classFile) throws Exception{
  File file = new File(classFile);
  InputStream in = new FileInputStream(file);
  return IOUtil.toBytes(in);
}

/** 
* 
* Method: toString() 
* 
*/ 
@Test
public void testToString() throws Exception {
  System.out.println(App.getProperty("nlf.dao.setting.dir"));
  System.out.println("abc.12".contains("."));
}


} 

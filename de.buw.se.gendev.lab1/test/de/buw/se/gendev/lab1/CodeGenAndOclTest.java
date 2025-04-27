package de.buw.se.gendev.lab1;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.jupiter.api.Test;

class CodeGenAndOclTest {

    protected final String failInst1 = "model/c1/fail.xmi";
    protected final String passInst1 = "model/c1/pass.xmi";

    protected final String failInst2 = "model/c2/fail.xmi";
    protected final String passInst2 = "model/c2/pass.xmi";

    @Test
    void testCodeGenerated() {
        File dir = new File("src-gen/de/buw/se/gendev/lab1/");
        if (!dir.exists()) {
            fail("Directory of generated files does not exist.");
        }

        int numClassGen = 0;

        for (File f : dir.listFiles()) {
            if (isGeneratedInterface(f)) {
                numClassGen++;
            }
        }

        if (numClassGen < 5) {
            fail("Only code for " + numClassGen + " out of 5 generated.");
        }
    }

    @Test
    void testFailInstance1Exists() {
        File f = new File(failInst1);
        if (!f.exists()) {
            fail("Instance that fails validation must be in file " + failInst1);
        }
    }

    @Test
    void testFailInstance1() {
        Diagnostic diag = diagnoseModel(failInst1);
        if (diag.getSeverity() != Diagnostic.WARNING) {
            System.out.println("Validation of " + failInst1 + "should fail with an error.");
            fail("Validation should fail.");
        }
    }

    @Test
    void testPassInstance1Exists() {
        File f = new File(passInst1);
        if (!f.exists()) {
            fail("Instance that passes validation must be in file " + passInst1);
        }
    }

    @Test
    void testPassInstance1() {
        Diagnostic diag = diagnoseModel(passInst1);
        if (diag.getSeverity() != Diagnostic.OK) {
            System.out.println("Validation of " + passInst1 + "should pass.");
            fail("Validation should pass.");
        }
    }

    @Test
    void testFailInstance2Exists() {
        File f = new File(failInst2);
        if (!f.exists()) {
            fail("Instance that fails validation must be in file " + failInst2);
        }
    }

    @Test
    void testFailInstance2() {
        Diagnostic diag = diagnoseModel(failInst2);
        if (diag.getSeverity() != Diagnostic.WARNING) {
            System.out.println("Validation of " + failInst2 + "should fail with an error.");
            fail("Validation should fail.");
        }
    }

    @Test
    void testPassInstance2Exists() {
        File f = new File(passInst2);
        if (!f.exists()) {
            fail("Instance that passes validation must be in file " + passInst2);
        }
    }

    @Test
    void testPassInstance2() {
        Diagnostic diag = diagnoseModel(passInst2);
        if (diag.getSeverity() != Diagnostic.OK) {
            System.out.println("Validation of " + passInst2 + "should pass.");
            fail("Validation should pass.");
        }
    }

    private Diagnostic diagnoseModel(String fileName) {
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("xmi", new XMIResourceFactoryImpl());

        // Obtain a new resource set
        ResourceSet resSet = new ResourceSetImpl();
        registerPackage(resSet);
        // Get the resource
        Resource resource = resSet.getResource(URI.createURI(fileName), true);
        
        // collect diagnostics of all top-level objects in the instance
        BasicDiagnostic diagnose = new BasicDiagnostic();
        for (EObject o : resource.getContents()) {
        	diagnose.add(Diagnostician.INSTANCE.validate(o));
        }

        return diagnose;
    }
    
    private void registerPackage(ResourceSet resSet) {
        try {
            Class<?> c = this.getClass().getClassLoader().loadClass("de.buw.se.gendev.lab1.Lab1Package");
            Field fNS = c.getField("eNS_URI");
            Field fI = c.getField("eINSTANCE");
            resSet.getPackageRegistry().put(fNS.get(null).toString(), fI.get(null));
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private boolean isGeneratedInterface(File f) {
        if (f.isFile()) {
            String content = null;
            try {
                content = Files.readString(f.toPath());
            } catch (IOException e) {
                return false;
            }
            if (!content.contains("interface")) {
                return false;
            }
            if (!content.contains("@model")) {
                return false;
            }
            if (content.contains("Package extends EPackage")) {
                return false;
            }
            if (content.contains("Factory extends EFactory")) {
                return false;
            }
            return true;
        }
        return false;
    }

}

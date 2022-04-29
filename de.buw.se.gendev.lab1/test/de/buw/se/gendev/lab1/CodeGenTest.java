package de.buw.se.gendev.lab1;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.jupiter.api.Test;

class CodeGenTest {

    protected final String failInst = "model/fail.xmi";
    protected final String passInst = "model/pass.xmi";

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
    void testFailInstanceExists() {
        File f = new File(failInst);
        if (!f.exists()) {
            fail("Instance that fails validation must be in file " + failInst);
        }
    }

    @Test
    void testPassInstanceExists() {
        File f = new File(passInst);
        if (!f.exists()) {
            fail("Instance that passes validation must be in file " + passInst);
        }
    }

    @Test
    void testFailInstance() {
        Diagnostic diag = diagnoseModel(failInst);
        if (diag.getSeverity() != Diagnostic.WARNING) {
            System.out.println("Validation of " + failInst + "should fail with an error.");
            fail("Validation should fail.");
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

        return Diagnostician.INSTANCE.validate(resource.getContents().get(0));
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

    @Test
    void testPassInstance() {
        Diagnostic diag = diagnoseModel(passInst);
        if (diag.getSeverity() != Diagnostic.OK) {
            System.out.println("Validation of " + passInst + "should pass.");
            fail("Validation should pass.");
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

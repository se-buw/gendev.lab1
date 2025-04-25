package de.buw.se.gendev.lab1;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.jupiter.api.Test;

/**
 * Check the EMF model whether it contains what is needed.
 *
 */
class EMFModelTest {
    protected static String ecoreFile = "model/lab1.ecore";

    /**
     * Sanity check whether ecore file exists.
     */
    @Test
    void testModelExists() {
        File f = new File(ecoreFile);
        assertTrue(f.exists(), "Make sure that the original file " + ecoreFile + " has not been deleted or renamed.");
    }

    /**
     * Check whether we have at least 5 classes
     */
    @Test
    void testModelValidates() {
        EPackage p = loadPackage();
        Diagnostic d = Diagnostician.INSTANCE.validate(p);
        if (d.getSeverity() != Diagnostic.OK) {
            System.out.println("Issues detected in model: " + printIssues(d));
            fail("Model must validate without issues.");
        }
    }

    protected String printIssues(Diagnostic d) {
        final StringBuilder sb = new StringBuilder("Issues: ");
        for (final Diagnostic child : d.getChildren()) {
            sb.append(System.lineSeparator());
            sb.append(child.getMessage());
        }
        return sb.toString();
    }

    /**
     * Check whether we have at least 5 classes
     */
    @Test
    void testFiveOrMoreClasses() {
        EPackage p = loadPackage();

        int numClasses = p.getEClassifiers().size();

        if (numClasses < 5) {
            System.out.println("Found " + numClasses + " out of 5 classes defined in model.");
            fail("Make sure to define at least 5 classes.");
        }
    }

    /**
     * Check whether total of attributes is at least 8. Counts only attributes with
     * types.
     */
    @Test
    void testNumAttributes() {
        EPackage p = loadPackage();

        int numAttr = 0;

        for (EClassifier c : p.getEClassifiers()) {
            if (c instanceof EClass) {
                for (EAttribute a : ((EClass) c).getEAllAttributes()) {
                    if (a.getEAttributeType() != null) {
                        numAttr++;
                    }
                }
            }
        }

        if (numAttr < 8) {
            System.out.println("Found " + numAttr + " out of 8 attributes of all classes defined in model.");
            fail("Make sure to define at least 8 attributes.");
        }
    }

    /**
     * Check whether total of attributes is at least 8. Counts only attributes with
     * types.
     */
    @Test
    void testNumOCL() {
        EPackage p = loadPackage();

        int numOCL = 0;

        for (EClassifier c : p.getEClassifiers()) {
            if (c instanceof EClass) {
                for (EAnnotation a : ((EClass) c).getEAnnotations()) {
                    if (a.getSource().contains("Ecore/OCL")) {
                        numOCL += a.getDetails().keySet().size();

                    }
                }
            }
        }

        if (numOCL < 2) {
            System.out.println("Found " + numOCL + " out of 2 OCL constraints of all classes defined in model.");
            fail("Make sure to define at least 2 OCL constraints.");
        }
    }

    /**
     * Check whether total of attributes is at least 8. Counts only attributes with
     * types.
     */
    @Test
    void testNumAssocs() {
        EPackage p = loadPackage();

        int numAssoc = 0;

        for (EClassifier c : p.getEClassifiers()) {
            if (c instanceof EClass) {
                for (EReference a : ((EClass) c).getEAllReferences()) {
                    if (a.getName() != null) {
                        numAssoc++;
                    }
                }
            }
        }

        if (numAssoc < 4) {
            System.out.println("Found " + numAssoc + " out of 4 associations of all classes defined in model.");
            fail("Make sure to define at least 4 associations.");
        }
    }

    private EPackage loadPackage() {
        // Create a resource set to hold the resources.
        ResourceSet resourceSet = new ResourceSetImpl();

        Map<String, Object> options = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();

        // Register the appropriate resource factory to handle all file
        // extensions.
        options.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

        URI uri = URI.createFileURI(ecoreFile);

        Resource resource = resourceSet.getResource(uri, true);
        EPackage p = (EPackage) resource.getContents().get(0);
        return p;
    }

}

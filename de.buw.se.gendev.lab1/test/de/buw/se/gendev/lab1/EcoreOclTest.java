package de.buw.se.gendev.lab1;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.helper.OCLHelper;
import org.junit.jupiter.api.Test;

/**
 * Check the EMF model whether it contains what is needed.
 *
 */
class EcoreOclTest {
    protected static String ecoreFile = "model/lab1.ecore";

    /**
     * Sanity check whether ecore file exists.
     * @throws ParserException 
     */
    @Test
    void testModelExists() throws ParserException {
    	OCL ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
    	@SuppressWarnings("rawtypes")
		OCLHelper h = ocl.createOCLHelper();
    	assertNotNull(h);
    }

    @SuppressWarnings("unused")
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

/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ubu.gii.dass.c01.ReusablePool;
import ubu.gii.dass.c01.Reusable;
import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.DuplicatedInstanceException;

/**
 * @author Raul Olles
 *
 */
public class ReusablePoolTest {

	// Crea un pool a utilizar en todo el proceso de prueba
	ReusablePool pool;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Instancia el pool
		pool = ReusablePool.getInstance();

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		// Comprueba si el pool instanciado en @Before es correcto
		assertTrue(pool instanceof ReusablePool);
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 */
	@Test
	public void testAcquireReusable() throws NotFreeInstanceException{
		// Intenta adquirir dos objetos de la clase Reusable
		Reusable reusable1 = pool.acquireReusable();
		Reusable reusable2 = pool.acquireReusable();
		
		// Comprueba que se hayan adquerido
		assertNotEquals(reusable1, null);
		assertNotEquals(reusable2, null);
		
		// Comprueba el salto de excepcion (maximo pool == 2)
		try {
			Reusable reusable3 = pool.acquireReusable();
		} catch (NotFreeInstanceException e) {
			assertEquals(e.getMessage(),"No hay mas instancias reutilizables disponibles. Reintentalo mas tarde");
		}
		
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 */
	@Test
	public void testReleaseReusable() throws NotFreeInstanceException {
		
		// Adquirir dos objetos de la clase Reusable
		Reusable reusable1 = pool.acquireReusable();
		Reusable reusable2 = pool.acquireReusable();

		// Intenta liberar los objetos que NO ESTAN en el pool
		try {
			pool.releaseReusable(reusable1);
		} catch (DuplicatedInstanceException e) {
			assertTrue(false);
		}
		
		try {
			pool.releaseReusable(reusable2);
		} catch (DuplicatedInstanceException e) {
			assertTrue(false);
		}
		
		// Intenta liberar los objetos que SI ESTAN en el pool
		try {
			pool.releaseReusable(reusable1);
		} catch (DuplicatedInstanceException e) {
			assertEquals(e.getMessage(),"Ya existe esa instancia en el pool.");
		}
		
		try {
			pool.releaseReusable(reusable2);
		} catch (DuplicatedInstanceException e) {
			assertEquals(e.getMessage(),"Ya existe esa instancia en el pool.");
		}		
		
	}

}

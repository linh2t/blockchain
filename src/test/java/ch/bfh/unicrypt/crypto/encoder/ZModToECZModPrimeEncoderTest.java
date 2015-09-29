package ch.bfh.unicrypt.crypto.encoder;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

import ch.bfh.unicrypt.crypto.encoder.classes.ZModPrimeToECZModPrime;
import ch.bfh.unicrypt.crypto.encoder.interfaces.Encoder;
import ch.bfh.unicrypt.math.algebra.additive.classes.ECZModElement;
import ch.bfh.unicrypt.math.algebra.additive.classes.ECZModPrime;
import ch.bfh.unicrypt.math.algebra.additive.interfaces.ECElement;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZModElement;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import ch.bfh.unicrypt.math.algebra.additive.parameters.SEC2_ZModPrime;

public class ZModToECZModPrimeEncoderTest {

	@Test
	public void encodeDecodeTest()throws Exception {
		ECZModPrime cyclicGroup = ECZModPrime.getInstance(SEC2_ZModPrime.secp521r1);
		Encoder encoder = ZModPrimeToECZModPrime.getInstance(cyclicGroup,15);
		Element message = encoder.getDomain().getElementFrom(278);
		Element encMessage=encoder.encode(message);
		Element decMessage=encoder.decode(encMessage);

		assertEquals(message, decMessage);
	}


}

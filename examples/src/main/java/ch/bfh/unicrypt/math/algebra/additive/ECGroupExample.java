/*
 * UniCrypt
 *
 *  UniCrypt(tm) : Cryptographical framework allowing the implementation of cryptographic protocols e.g. e-voting
 *  Copyright (C) 2014 Bern University of Applied Sciences (BFH), Research Institute for
 *  Security in the Information Society (RISIS), E-Voting Group (EVG)
 *  Quellgasse 21, CH-2501 Biel, Switzerland
 *
 *  Licensed under Dual License consisting of:
 *  1. GNU Affero General Public License (AGPL) v3
 *  and
 *  2. Commercial license
 *
 *
 *  1. This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *  2. Licensees holding valid commercial licenses for UniCrypt may use this file in
 *   accordance with the commercial license agreement provided with the
 *   Software or, alternatively, in accordance with the terms contained in
 *   a written agreement between you and Bern University of Applied Sciences (BFH), Research Institute for
 *   Security in the Information Society (RISIS), E-Voting Group (EVG)
 *   Quellgasse 21, CH-2501 Biel, Switzerland.
 *
 *
 *   For further information contact <e-mail: unicrypt@bfh.ch>
 *
 *
 * Redistributions of files must retain the above copyright notice.
 */
package ch.bfh.unicrypt.math.algebra.additive;

import ch.bfh.unicrypt.Example;
import ch.bfh.unicrypt.crypto.encoder.classes.ProbabilisticECGroupF2mEncoder;
import ch.bfh.unicrypt.math.algebra.additive.classes.ECPolynomialElement;
import ch.bfh.unicrypt.math.algebra.additive.classes.ECPolynomialField;
import ch.bfh.unicrypt.math.algebra.additive.classes.ECZModElement;
import ch.bfh.unicrypt.math.algebra.additive.classes.ECZModPrime;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZMod;
import ch.bfh.unicrypt.math.algebra.params.classes.SECECCParamsF2m;
import ch.bfh.unicrypt.math.algebra.params.classes.SECECCParamsFp;
import ch.bfh.unicrypt.math.algebra.params.interfaces.StandardECPolynomialFieldParams;
import ch.bfh.unicrypt.math.algebra.params.interfaces.StandardECZModParams;
import java.math.BigInteger;

/**
 *
 * @author Christian Lutz
 * <p>
 */
public class ECGroupExample {

	public static void example1() throws Exception {

		// Example with StandardECZModPrime
		for (StandardECZModParams params : SECECCParamsFp.values()) {

			ECZModPrime ec = ECZModPrime.getInstance(params);
			ECZModElement generator = ec.getDefaultGenerator();
			ec.getRandomElement();
			BigInteger order = ec.getOrder();
			Example.printLine(params.toString());
			Example.printLine(ec);
			Example.printLine(generator.selfApply(order.multiply(ec.getCoFactor()))); // Result should be
			// Infinity element
		}
	}

	public static void example2() throws Exception {
		ECPolynomialField ec = ECPolynomialField.getInstance(SECECCParamsF2m.sect113r1);
		ECPolynomialElement r = ec.getRandomElement();
		Example.printLine(r.getY());
		Example.printLine(r.invert().getY());
		Example.printLine(r.getY());
		Example.printLine(ec.getDefaultGenerator());
		Example.printLine(ec.getDefaultGenerator().invert());

	}

	public static void example3() throws Exception {
		// Example with StandardECPolynomialField

		for (StandardECPolynomialFieldParams params : SECECCParamsF2m.values()) {
			ECPolynomialField ec = ECPolynomialField.getInstance(params);
			ZMod z = ZMod.getInstance(1234567);
			ECPolynomialElement generator = ec.getDefaultGenerator();

			ProbabilisticECGroupF2mEncoder encoder = ProbabilisticECGroupF2mEncoder.getInstance(z, ec);

			ECPolynomialElement m = encoder.encode(z.getElement(105));
			ECPolynomialElement m_generator = m.add(generator);

			BigInteger order = ec.getOrder();

			Example.printLine(params.toString());
			Example.printLine("Message" + m.selfApply(order));
			Example.printLine("Message plus Generator" + m_generator.add(generator).selfApply(order));
			Example.printLine("Gen " + generator.selfApply(order));

			// Result should be Infinity element
		}
	}

	public static void main(final String[] args) {
		Example.runExamples();
	}

}

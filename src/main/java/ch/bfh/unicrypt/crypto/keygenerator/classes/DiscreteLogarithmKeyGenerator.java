/*
 * UniCrypt
 *
 *  UniCrypt(tm): Cryptographical framework allowing the implementation of cryptographic protocols e.g. e-voting
 *  Copyright (c) 2016 Bern University of Applied Sciences (BFH), Research Institute for
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
package ch.bfh.unicrypt.crypto.keygenerator.classes;

import ch.bfh.unicrypt.crypto.keygenerator.abstracts.AbstractKeyPairGenerator;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZMod;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZModElement;
import ch.bfh.unicrypt.math.algebra.general.interfaces.CyclicGroup;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import ch.bfh.unicrypt.math.function.classes.GeneratorFunction;
import ch.bfh.unicrypt.math.function.interfaces.Function;

/**
 *
 * @author R. Haenni
 */
public class DiscreteLogarithmKeyGenerator
	   extends AbstractKeyPairGenerator<ZMod, ZModElement, CyclicGroup, Element> {

	private final Element generator;

	protected DiscreteLogarithmKeyGenerator(CyclicGroup publicKeySpace) {
		super(publicKeySpace.getZModOrder(), publicKeySpace);
		this.generator = publicKeySpace.getDefaultGenerator();
	}

	protected DiscreteLogarithmKeyGenerator(Element generator) {
		super(generator.getSet().getZModOrder(), (CyclicGroup) generator.getSet());
		this.generator = generator;
	}

	public Element getGenerator() {
		return this.generator;
	}

	@Override
	protected Function abstractGetPublicKeyGenerationFunction() {
		return GeneratorFunction.getInstance(this.getGenerator());
	}

	public static DiscreteLogarithmKeyGenerator getInstance(CyclicGroup publicKeySpace) {
		if (publicKeySpace == null) {
			throw new IllegalArgumentException();
		}
		return new DiscreteLogarithmKeyGenerator(publicKeySpace);
	}

	public static DiscreteLogarithmKeyGenerator getInstance(Element generator) {
		if (generator == null) {
			throw new IllegalArgumentException();
		}
		if (generator.getSet().isCyclic() && generator.isGenerator()) {
			return new DiscreteLogarithmKeyGenerator(generator);
		}
		throw new IllegalArgumentException();
	}

}

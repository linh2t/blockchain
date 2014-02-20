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
package ch.bfh.unicrypt.math.algebra.multiplicative.classes;

import ch.bfh.unicrypt.crypto.random.classes.HybridRandomByteSequence;
import ch.bfh.unicrypt.crypto.random.interfaces.RandomByteSequence;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZModPrime;
import ch.bfh.unicrypt.math.helper.factorization.Prime;
import ch.bfh.unicrypt.math.helper.factorization.SafePrime;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rolfhaenni
 */
public class GStarModSafePrime
	   extends GStarModPrime {

	private final static Map<BigInteger, GStarModSafePrime> instances = new HashMap<BigInteger, GStarModSafePrime>();

	protected GStarModSafePrime(SafePrime modulo) {
		super(modulo, Prime.getInstance(modulo.getValue().subtract(BigInteger.ONE).divide(BigInteger.valueOf(2))));
	}

	@Override
	protected ZModPrime defaultGetZModOrder() {
		return ZModPrime.getInstance(this.getOrder());
	}

	@Override
	protected ZStarModPrime defaultGetZStarModOrder() {
		return ZStarModPrime.getInstance(this.getOrder());
	}

	@Override
	public String defaultToStringValue() {
		return this.getModulus().toString();
	}

	public static GStarModSafePrime getInstance(final SafePrime safePrime) {
		if (safePrime == null) {
			throw new IllegalArgumentException();
		}
		GStarModSafePrime instance = GStarModSafePrime.instances.get(safePrime.getValue());
		if (instance == null) {
			instance = new GStarModSafePrime(safePrime);
			GStarModSafePrime.instances.put(safePrime.getValue(), instance);
		}
		return instance;
	}

	public static GStarModSafePrime getInstance(final int modulus) {
		return GStarModSafePrime.getInstance(BigInteger.valueOf(modulus));
	}

	public static GStarModSafePrime getInstance(final BigInteger modulus) {
		return GStarModSafePrime.getInstance(SafePrime.getInstance(modulus));
	}

	public static GStarModSafePrime getRandomInstance(int bitLength) {
		return GStarModSafePrime.getRandomInstance(bitLength, HybridRandomByteSequence.getInstance());
	}

	public static GStarModSafePrime getRandomInstance(int bitLength, RandomByteSequence randomByteSequence) {
		return GStarModSafePrime.getInstance(SafePrime.getRandomInstance(bitLength, randomByteSequence));
	}

}

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
package ch.bfh.unicrypt.math.algebra.multiplicative.classes;

import ch.bfh.unicrypt.ErrorCode;
import ch.bfh.unicrypt.UniCryptRuntimeException;
import ch.bfh.unicrypt.helper.factorization.SafePrime;
import ch.bfh.unicrypt.helper.math.MathUtil;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rolfhaenni
 */
public class ZStarModSafePrime
	   extends ZStarModPrime {

	private final static Map<BigInteger, ZStarModSafePrime> INSTANCES = new HashMap<>();
	private ZStarModElement defaultGenerator;

	private ZStarModSafePrime(SafePrime modulus) {
		super(modulus);
	}

	@Override
	public ZStarModElement getDefaultGenerator() {
		if (this.defaultGenerator == null) {
			if (this.getModulus().equals(MathUtil.FIVE)) {
				// for p=5, the generators are 2 and 3
				this.defaultGenerator = this.getElement(MathUtil.TWO);
			} else {
				// p-2^2 is always a generator
				this.defaultGenerator = this.getElement(this.getModulus().subtract(MathUtil.FOUR));
			}
		}
		return this.defaultGenerator;
	}

	@Override
	public boolean isGenerator(Element element) {
		if (!this.contains(element)) {
			throw new UniCryptRuntimeException(ErrorCode.INVALID_ELEMENT, element);
		}
		BigInteger value = ((Element<BigInteger>) element).getValue();
		return (!value.equals(this.modulus.subtract(MathUtil.ONE)) || this.modulus.equals(MathUtil.TWO))
			   && MathUtil.legendreSymbol(value, this.modulus) == -1;
	}

	public static ZStarModSafePrime getInstance(final long modulus) {
		return ZStarModSafePrime.getInstance(BigInteger.valueOf(modulus));
	}

	public static ZStarModSafePrime getInstance(final BigInteger modulus) {
		if (modulus == null) {
			throw new UniCryptRuntimeException(ErrorCode.NULL_POINTER);
		}
		ZStarModSafePrime instance = ZStarModSafePrime.INSTANCES.get(modulus);
		if (instance == null) {
			instance = new ZStarModSafePrime(SafePrime.getInstance(modulus));
			ZStarModSafePrime.INSTANCES.put(modulus, instance);
		}
		return instance;
	}

	public static ZStarModSafePrime getInstance(final SafePrime modulus) {
		if (modulus == null) {
			throw new UniCryptRuntimeException(ErrorCode.NULL_POINTER);
		}
		ZStarModSafePrime instance = ZStarModSafePrime.INSTANCES.get(modulus.getValue());
		if (instance == null) {
			instance = new ZStarModSafePrime(modulus);
			ZStarModSafePrime.INSTANCES.put(modulus.getValue(), instance);
		}
		return instance;
	}

	public static ZStarModSafePrime getFirstInstance(int bitLength) {
		return ZStarModSafePrime.getInstance(SafePrime.getFirstInstance(bitLength));
	}

}
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
import ch.bfh.unicrypt.helper.cache.Cache;
import ch.bfh.unicrypt.helper.cache.Cache2D;
import ch.bfh.unicrypt.helper.math.MathUtil;
import ch.bfh.unicrypt.helper.prime.Prime;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZModPrime;
import java.math.BigInteger;

/**
 *
 * @author R. Haenni
 */
public class GStarModPrime
	   extends GStarMod {

	private static final long serialVersionUID = 1L;
	private final static Cache2D<BigInteger, BigInteger, GStarModPrime> CACHE = new Cache2D<>(Cache.SIZE_S);

	protected GStarModPrime(Prime modulus, Prime order) {
		super(modulus, order);
	}

	@Override
	public ZModPrime getZModOrder() {
		return ZModPrime.getInstance(this.getOrder());
	}

	@Override
	public ZStarModPrime getZStarModOrder() {
		return ZStarModPrime.getInstance(this.getOrder());
	}

	@Override
	protected boolean abstractContains(final BigInteger value) {
		return value.signum() > 0
			   && value.compareTo(this.modulus) < 0
			   && MathUtil.modExp(value, this.getOrder(), this.modulus).equals(MathUtil.ONE);
	}

	public static GStarModPrime getInstance(long modulus, long order) {
		return GStarModPrime.getInstance(BigInteger.valueOf(modulus), BigInteger.valueOf(order));
	}

	public static GStarModPrime getInstance(BigInteger modulus, BigInteger order) {
		if (modulus == null || order == null) {
			throw new UniCryptRuntimeException(ErrorCode.NULL_POINTER, modulus, order);
		}
		GStarModPrime instance = GStarModPrime.CACHE.get(modulus, order);
		if (instance == null) {
			if (!modulus.subtract(MathUtil.ONE).mod(order).equals(MathUtil.ZERO)) {
				throw new UniCryptRuntimeException(ErrorCode.INCOMPATIBLE_ARGUMENTS, modulus, order);
			}
			instance = new GStarModPrime(Prime.getInstance(modulus), Prime.getInstance(order));
			GStarModPrime.CACHE.put(modulus, order, instance);
		}
		return instance;
	}

	public static GStarModPrime getInstance(Prime modulus, Prime order) {
		if (modulus == null || order == null) {
			throw new UniCryptRuntimeException(ErrorCode.NULL_POINTER, modulus, order);
		}
		GStarModPrime instance = GStarModPrime.CACHE.get(modulus.getValue(), order.getValue());
		if (instance == null) {
			if (!modulus.getValue().subtract(MathUtil.ONE).mod(order.getValue()).equals(MathUtil.ZERO)) {
				throw new UniCryptRuntimeException(ErrorCode.INCOMPATIBLE_ARGUMENTS, modulus, order);
			}
			instance = new GStarModPrime(modulus, order);
			GStarModPrime.CACHE.put(modulus.getValue(), order.getValue(), instance);
		}
		return instance;
	}

}

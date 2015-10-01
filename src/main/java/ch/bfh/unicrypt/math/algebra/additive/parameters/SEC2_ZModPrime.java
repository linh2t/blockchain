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
package ch.bfh.unicrypt.math.algebra.additive.parameters;

import ch.bfh.unicrypt.ErrorCode;
import ch.bfh.unicrypt.UniCryptRuntimeException;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZModElement;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZModPrime;
import java.math.BigInteger;

/**
 * The standard EC groups for curves E(F_p):y²=x³+ax+b over a prime field F_p as defined in "SEC 2: Recommended Elliptic
 * Curve Domain Parameters", Version 2.0, Certicom Research, 2010.
 * <p>
 * @author C. Lutz
 * @author R. Haenni
 */
public enum SEC2_ZModPrime
	   implements ECParameters<ZModPrime, ZModElement> {

	secp160k1(80,
			  "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFAC73",
			  "0",
			  "7",
			  "3B4C382CE37AA192A4019E763036F4F5DD4D7EBB",
			  "938CF935318FDCED6BC28286531733C3F03C4FEE",
			  "100000000000000000001B8FA16DFAB9ACA16B6B3",
			  "1"),
	secp160r1(80,
			  "ffffffffffffffffffffffffffffffff7fffffff",
			  "ffffffffffffffffffffffffffffffff7ffffffc",
			  "1c97befc54bd7a8b65acf89f81d4d4adc565fa45",
			  "4a96b5688ef573284664698968c38bb913cbfc82",
			  "23a628553168947d59dcc912042351377ac5fb32",
			  "100000000000000000001f4c8f927aed3ca752257",
			  "1"),
	secp160r2(80,
			  "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFAC73",
			  "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFAC70",
			  "B4E134D3FB59EB8BAB57274904664D5AF50388BA",
			  "52DCB034293A117E1F4FF11B30F7199D3144CE6D",
			  "FEAFFEF2E331F296E071FA0DF9982CFEA7D43F2E",
			  "100000000000000000000351EE786A818F3A1A16B",
			  "1"),
	secp192k1(96,
			  "fffffffffffffffffffffffffffffffffffffffeffffee37",
			  "0",
			  "3",
			  "db4ff10ec057e9ae26b07d0280b7f4341da5d1b1eae06c7d",
			  "9b2f2f6d9c5628a7844163d015be86344082aa88d95e2f9d",
			  "fffffffffffffffffffffffe26f2fc170f69466a74defd8d",
			  "1"),
	secp192r1(96,
			  "fffffffffffffffffffffffffffffffeffffffffffffffff",
			  "fffffffffffffffffffffffffffffffefffffffffffffffc",
			  "64210519e59c80e70fa7e9ab72243049feb8deecc146b9b1",
			  "188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012",
			  "7192b95ffc8da78631011ed6b24cdd573f977a11e794811",
			  "ffffffffffffffffffffffff99def836146bc9b1b4d22831",
			  "1"),
	secp224k1(112,
			  "fffffffffffffffffffffffffffffffffffffffffffffffeffffe56d",
			  "0",
			  "5",
			  "a1455b334df099df30fc28a169a467e9e47075a90f7e650eb6b7a45c",
			  "7e089fed7fba344282cafbd6f7e319f7c0b0bd59e2ca4bdb556d61a5",
			  "10000000000000000000000000001dce8d2ec6184caf0a971769fb1f7",
			  "1"),
	secp224r1(112,
			  "ffffffffffffffffffffffffffffffff000000000000000000000001",
			  "fffffffffffffffffffffffffffffffefffffffffffffffffffffffe",
			  "b4050a850c04b3abf54132565044b0b7d7bfd8ba270b39432355ffb4",
			  "b70e0cbd6bb4bf7f321390b94a03c1d356c21122343280d6115c1d21",
			  "bd376388b5f723fb4c22dfe6cd4375a05a07476444d5819985007e34",
			  "ffffffffffffffffffffffffffff16a2e0b8f03e13dd29455c5c2a3d",
			  "1"),
	secp256k1(128,
			  "fffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f",
			  "0",
			  "7",
			  "79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798",
			  "483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8",
			  "fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364141",
			  "1"),
	secp256r1(128,
			  "ffffffff00000001000000000000000000000000ffffffffffffffffffffffff",
			  "ffffffff00000001000000000000000000000000fffffffffffffffffffffffc",
			  "5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b",
			  "6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296",
			  "4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5",
			  "ffffffff00000000ffffffffffffffffbce6faada7179e84f3b9cac2fc632551",
			  "1"),
	secp384r1(192,
			  "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffeffffffff0000000000000000ffffffff",
			  "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffeffffffff0000000000000000fffffffc",
			  "b3312fa7e23ee7e4988e056be3f82d19181d9c6efe8141120314088f5013875ac656398d8a2ed19d2a85c8edd3ec2aef",
			  "aa87ca22be8b05378eb1c71ef320ad746e1d3b628ba79b9859f741e082542a385502f25dbf55296c3a545e3872760ab7",
			  "3617de4a96262c6f5d9e98bf9292dc29f8f41dbd289a147ce9da3113b5f0b8c00a60b1ce1d7e819d7a431d7c90ea0e5f",
			  "ffffffffffffffffffffffffffffffffffffffffffffffffc7634d81f4372ddf581a0db248b0a77aecec196accc52973",
			  "1"),
	secp521r1(256,
			  "1ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff",
			  "1fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffc",
			  "51953eb9618e1c9a1f929a21a0b68540eea2da725b99b315f3b8b489918ef109e156193951ec7e937b1652c0bd3bb1bf073573df883d2c34f1ef451fd46b503f00",
			  "c6858e06b70404e9cd9e3ecb662395b4429c648139053fb521f828af606b4d3dbaa14b5e77efe75928fe1dc127a2ffa8de3348b3c1856a429bf97e7e31c2e5bd66",
			  "11839296a789a3bc0045c8a5fb42c7d1bd998f54449579b446817afbd17273e662c97ee72995ef42640c550b9013fad0761353c7086a272c24088be94769fd16650",
			  "1fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffa51868783bf2f966b7fcc0148f709a5d03bb5c9b8899c47aebb6fb71e91386409",
			  "1");

	private final int securityLevel;
	private final String modulus, a, b, gx, gy, order, coFactor;

	// Parameters of the curve y²=x³+ax+b
	private SEC2_ZModPrime(int securityLevel, String modulus, String a, String b, String gx, String gy, String order, String coFactor) {
		this.securityLevel = securityLevel;
		this.modulus = modulus;
		this.a = a;
		this.b = b;
		this.gx = gx;
		this.gy = gy;
		this.order = order;
		this.coFactor = coFactor;
	}

	@Override
	public int getSecurityLevel() {
		return this.securityLevel;
	}

	@Override
	public final ZModPrime getFiniteField() {
		return ZModPrime.getInstance(new BigInteger(this.modulus, 16));
	}

	@Override
	public final ZModElement getA() {
		return this.getFiniteField().getElement(new BigInteger(this.a, 16));
	}

	@Override
	public final ZModElement getB() {
		return this.getFiniteField().getElement(new BigInteger(this.b, 16));
	}

	@Override
	public final ZModElement getGx() {
		return this.getFiniteField().getElement(new BigInteger(this.gx, 16));
	}

	@Override
	public final ZModElement getGy() {
		return this.getFiniteField().getElement(new BigInteger(this.gy, 16));
	}

	@Override
	public final BigInteger getOrder() {
		return new BigInteger(this.order, 16);
	}

	@Override
	public final BigInteger getCoFactor() {
		return new BigInteger(this.coFactor, 16);
	}

	/**
	 * Returns the EC parameters for a given name.
	 * <p>
	 * @param name The name of the parameters
	 * @return The EC parameters
	 */
	public static SEC2_ZModPrime getInstance(String name) {
		if (name == null) {
			throw new UniCryptRuntimeException(ErrorCode.NULL_POINTER, name);
		}
		try {
			return SEC2_ZModPrime.valueOf(name);
		} catch (Exception exception) {
			throw new UniCryptRuntimeException(ErrorCode.OBJECT_NOT_FOUND, exception, name);
		}
	}

}
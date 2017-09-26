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
package ch.bfh.unicrypt.crypto.proofsystem.abstracts;

import ch.bfh.unicrypt.crypto.proofsystem.challengegenerator.classes.FiatShamirSigmaChallengeGenerator;
import ch.bfh.unicrypt.crypto.proofsystem.challengegenerator.classes.MultiValuesNonInteractiveChallengeGenerator;
import ch.bfh.unicrypt.crypto.proofsystem.challengegenerator.interfaces.ChallengeGenerator;
import ch.bfh.unicrypt.crypto.proofsystem.challengegenerator.interfaces.NonInteractiveChallengeGenerator;
import ch.bfh.unicrypt.crypto.proofsystem.challengegenerator.interfaces.SigmaChallengeGenerator;
import ch.bfh.unicrypt.helper.array.classes.ByteArray;
import ch.bfh.unicrypt.helper.array.interfaces.ImmutableArray;
import ch.bfh.unicrypt.helper.converter.classes.ConvertMethod;
import ch.bfh.unicrypt.helper.converter.classes.biginteger.ByteArrayToBigInteger;
import ch.bfh.unicrypt.helper.converter.classes.bytearray.BigIntegerToByteArray;
import ch.bfh.unicrypt.helper.converter.interfaces.Converter;
import ch.bfh.unicrypt.helper.hash.HashMethod;
import ch.bfh.unicrypt.helper.math.MathUtil;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZMod;
import ch.bfh.unicrypt.math.algebra.general.classes.ProductGroup;
import ch.bfh.unicrypt.math.algebra.general.classes.ProductSet;
import ch.bfh.unicrypt.math.algebra.general.classes.Triple;
import ch.bfh.unicrypt.math.algebra.general.classes.Tuple;
import ch.bfh.unicrypt.math.algebra.general.interfaces.CyclicGroup;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Monoid;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Set;
import java.math.BigInteger;

/**
 * This class is an abstract base implementation for shuffle proof systems according to Wikström (@see Wik09, TW10). It
 * covers only the online part; the proof that the commitment to the permutation is indeed a commitment to a valid
 * permutation must be done separately by a
 * {@link ch.bfh.unicrypt.crypto.proofsystem.classes.PermutationCommitmentProofSystem}.
 * <p>
 * Beside the common sigma challenge generator it holds another challenge generator for the creation of the e-vector. In
 * addition it manages the security parameters and the independent generators. The key functionality of the proof must
 * be implemented by the subclass.
 * <p>
 * The elements generated by the challenge generators can be either elements of Z_q or of any smaller ZMod group.
 * <p>
 * @see "Wik09"
 * @see "TW10"
 * @author P. Locher
 */
public abstract class AbstractShuffleProofSystem
	   extends AbstractProofSystem<ProductGroup, Triple, ProductGroup, Tuple, ProductSet, Tuple> {

	/**
	 * See Wik09 Page 14
	 */
	final public static int DEFAULT_KR = 20;

	/**
	 * Holds the sigma challenge generator.
	 */
	final private SigmaChallengeGenerator sigmaChallengeGenerator;

	/**
	 * Holds the challenge generator for the creation of the e-vector.
	 */
	final private ChallengeGenerator eValuesGenerator;

	/**
	 * The underlying cyclic group.
	 */
	final private CyclicGroup cyclicGroup;

	/**
	 * The size of the shuffle.
	 */
	final private int size;

	/**
	 * Security parameter for the e-values: e ∈ [0,...,2^ke - 1].
	 */
	final private int ke;

	/**
	 * Security parameter of challenge (number of bits of challenge).
	 */
	final private int kc;

	/**
	 * Security parameter that decides how well the commitments hide the committed values.
	 */
	final private int kr;

	/**
	 * The independent generators, a tuple of arity {@link #size} + 1.
	 */
	final private Tuple independentGenerators;

	protected AbstractShuffleProofSystem(SigmaChallengeGenerator sigmaChallengeGenerator,
		   ChallengeGenerator eValuesGenerator,
		   CyclicGroup cyclicGroup, int size, int kr, Tuple independentGenerators) {

		this.sigmaChallengeGenerator = sigmaChallengeGenerator;
		this.eValuesGenerator = eValuesGenerator;
		this.cyclicGroup = cyclicGroup;
		this.size = size;
		this.kr = kr;
		this.independentGenerators = independentGenerators;

		this.ke = ((ZMod) ((ImmutableArray<Set>) this.eValuesGenerator.getChallengeSpace()).getFirst()).getModulus()
			   .subtract(MathUtil.ONE).bitLength();
		this.kc = this.sigmaChallengeGenerator.getChallengeSpace().getModulus().subtract(MathUtil.ONE).bitLength();
	}

	//===================================================================================
	// Public Interface
	//
	// Proof:   (e-values, t, c, s)
	@Override
	protected ProductSet abstractGetProofSpace() {
		return ProductSet.getInstance(this.getEValuesGenerator().getChallengeSpace(),
									  this.getCommitmentSpace(),
									  this.getChallengeSpace(),
									  this.getResponseSpace());
	}

	// c: [0,...,2^kc - 1]
	public ZMod getChallengeSpace() {
		return this.getSigmaChallengeGenerator().getChallengeSpace();
	}

	public SigmaChallengeGenerator getSigmaChallengeGenerator() {
		return this.sigmaChallengeGenerator;
	}

	public ChallengeGenerator getEValuesGenerator() {
		return this.eValuesGenerator;
	}

	public CyclicGroup getCyclicGroup() {
		return this.cyclicGroup;
	}

	public int getSize() {
		return this.size;
	}

	public int getKe() {
		return this.ke;
	}

	public int getKc() {
		return this.kc;
	}

	public int getKr() {
		return this.kr;
	}

	public Tuple getIndependentGenerators() {
		return this.independentGenerators;
	}

	public Element getEValues(final Tuple proof) {
		if (!this.getProofSpace().contains(proof)) {
			throw new IllegalArgumentException();
		}
		return proof.getAt(0);
	}

	public Element getCommitment(final Tuple proof) {
		if (!this.getProofSpace().contains(proof)) {
			throw new IllegalArgumentException();
		}
		return proof.getAt(1);
	}

	public Element getChallenge(final Tuple proof) {
		if (!this.getProofSpace().contains(proof)) {
			throw new IllegalArgumentException();
		}
		return proof.getAt(2);
	}

	public Element getResponse(final Tuple proof) {
		if (!this.getProofSpace().contains(proof)) {
			throw new IllegalArgumentException();
		}
		return proof.getAt(3);
	}

	//===================================================================================
	// Helpers
	//
	// Helper to compute the inner product
	// - Additive:       Sum(t1_i*t2_i)
	// - Multiplicative: Prod(t1_i^(t2_i))
	protected static Element computeInnerProduct(Tuple t1, Tuple t2) {
		if (!t1.getSet().isGroup() || t1.getArity() < 1) {
			throw new IllegalArgumentException();
		}
		Element innerProduct = ((Monoid) t1.getSet().getAt(0)).getIdentityElement();
		for (int i = 0; i < t1.getArity(); i++) {
			innerProduct = innerProduct.apply(t1.getAt(i).selfApply(t2.getAt(i)));
		}
		return innerProduct;
	}

	//===================================================================================
	// Helpers to create spaces
	//
	// [0,...,2^kc - 1] \subseteq Z
	protected static ZMod createChallengeSpace(int k) {
		return ZMod.getInstance(MathUtil.powerOfTwo(k));
	}

	// [0,...,2^ke - 1]^N \subseteq Z^N
	protected static ProductGroup createEValuesGeneratorChallengeSpace(int k, int size) {
		return ProductGroup.getInstance(createChallengeSpace(k), size);
	}

	//===================================================================================
	// Abstract Methods
	//
	abstract public ProductGroup getCommitmentSpace();

	abstract public ProductGroup getResponseSpace();

	//===================================================================================
	// Service functions to create non-interactive SigmaChallengeGenerator and MultiChallengeGenerator
	//
	public static FiatShamirSigmaChallengeGenerator
		   createNonInteractiveSigmaChallengeGenerator(final int kc) {
		return createNonInteractiveSigmaChallengeGenerator(kc, null);
	}

	public static FiatShamirSigmaChallengeGenerator createNonInteractiveSigmaChallengeGenerator(
		   final ZMod challengeSpace) {
		return createNonInteractiveSigmaChallengeGenerator(challengeSpace, null);
	}

	public static FiatShamirSigmaChallengeGenerator createNonInteractiveSigmaChallengeGenerator(final int kc,
		   final Element proverId) {
		if (kc < 1) {
			throw new IllegalArgumentException();
		}
		return createNonInteractiveSigmaChallengeGenerator(createChallengeSpace(kc), proverId);
	}

	public static FiatShamirSigmaChallengeGenerator createNonInteractiveSigmaChallengeGenerator(
		   final ZMod challengeSpace, final Element proverId) {
		return FiatShamirSigmaChallengeGenerator.getInstance(challengeSpace, proverId);
	}

	public static <V> FiatShamirSigmaChallengeGenerator createNonInteractiveSigmaChallengeGenerator(
		   final ZMod challengeSpace, final Element proverId, final ConvertMethod<V> convertMethod,
		   final HashMethod<V> hashMethod, final Converter<ByteArray, BigInteger> converter) {
		if (challengeSpace == null || convertMethod == null || hashMethod == null || converter == null) {
			throw new IllegalArgumentException();
		}
		return FiatShamirSigmaChallengeGenerator.getInstance(challengeSpace, proverId, convertMethod, hashMethod, converter);
	}

	public static NonInteractiveChallengeGenerator createNonInteractiveEValuesGenerator(final int ke, final int size) {
		return createNonInteractiveEValuesGenerator(ke, size, null);
	}

	public static NonInteractiveChallengeGenerator createNonInteractiveEValuesGenerator(final int ke,
		   final int size, final Element proverId) {
		if (ke < 1) {
			throw new IllegalArgumentException();
		}
		return createNonInteractiveEValuesGenerator(createChallengeSpace(ke), size, proverId);
	}

	public static NonInteractiveChallengeGenerator createNonInteractiveEValuesGenerator(final ZMod challengeSpace,
		   final int size) {
		return createNonInteractiveEValuesGenerator(challengeSpace, size, (Element) null);
	}

	public static NonInteractiveChallengeGenerator createNonInteractiveEValuesGenerator(final ZMod challengeSpace,
		   final int size, final Element proverId) {
		ConvertMethod<ByteArray> convertMethod = ConvertMethod.getInstance();
		HashMethod<ByteArray> hashMethod = HashMethod.getInstance();
		int hashLength = hashMethod.getHashAlgorithm().getByteLength();
		Converter<ByteArray, BigInteger> converter = ByteArrayToBigInteger.getInstance(hashLength);
		Converter<BigInteger, ByteArray> indexConverter = BigIntegerToByteArray.getInstance();
		return createNonInteractiveEValuesGenerator(challengeSpace, size, proverId, convertMethod, hashMethod, converter, indexConverter);
	}

	public static <V> NonInteractiveChallengeGenerator createNonInteractiveEValuesGenerator(final ZMod eChallengeSpace,
		   final int size, final Element proverId, final ConvertMethod<V> convertMethod,
		   final HashMethod<V> hashMethod, final Converter<ByteArray, BigInteger> converter, final Converter<BigInteger, ByteArray> indexConverter) {

		if (size < 1 || eChallengeSpace == null || convertMethod == null || hashMethod == null || converter == null || indexConverter == null) {
			throw new IllegalArgumentException();
		}

		return MultiValuesNonInteractiveChallengeGenerator.getInstance(eChallengeSpace, size, proverId, convertMethod, hashMethod, converter, indexConverter);
	}

}

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
package ch.bfh.unicrypt.crypto.proofsystem.challengegenerator.classes;

import ch.bfh.unicrypt.crypto.proofsystem.challengegenerator.abstracts.AbstractNonInteractiveSigmaChallengeGenerator;
import ch.bfh.unicrypt.helper.aggregator.classes.ByteArrayAggregator;
import ch.bfh.unicrypt.helper.aggregator.interfaces.Aggregator;
import ch.bfh.unicrypt.helper.array.classes.ByteArray;
import ch.bfh.unicrypt.helper.converter.classes.ConvertMethod;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZMod;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZModElement;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import ch.bfh.unicrypt.math.function.interfaces.Function;
import ch.bfh.unicrypt.random.classes.PseudoRandomOracle;
import ch.bfh.unicrypt.random.classes.ReferenceRandomByteSequence;
import ch.bfh.unicrypt.random.interfaces.RandomOracle;

public class RandomOracleSigmaChallengeGenerator
	   extends AbstractNonInteractiveSigmaChallengeGenerator {

	protected final RandomOracle randomOracle;
	protected final ConvertMethod<ByteArray> convertMethod;
	protected final Aggregator<ByteArray> aggregator;

	protected RandomOracleSigmaChallengeGenerator(ZMod challengeSpace, Element proverId,
		   final RandomOracle randomOracle, ConvertMethod<ByteArray> convertMethod, Aggregator<ByteArray> aggregator) {
		super(challengeSpace, proverId);
		this.randomOracle = randomOracle;
		this.convertMethod = convertMethod;
		this.aggregator = aggregator;
	}

	public RandomOracle getRandomOracle() {
		return this.randomOracle;
	}

	@Override
	protected ZModElement abstractAbstractGenerate(Element<?> input) {
		ReferenceRandomByteSequence randomByteSequence
			   = this.randomOracle.getReferenceRandomByteSequence(input.convertTo(this.convertMethod, this.aggregator));
		return this.getChallengeSpace().getRandomElement(randomByteSequence);
	}

	public static RandomOracleSigmaChallengeGenerator getInstance(ZMod challengeSpace) {
		return RandomOracleSigmaChallengeGenerator.getInstance(challengeSpace, (Element) null,
															   PseudoRandomOracle.getInstance(), ConvertMethod.getInstance(ByteArray.class), ByteArrayAggregator.getInstance());
	}

	public static RandomOracleSigmaChallengeGenerator getInstance(ZMod challengeSpace, Element proverId) {
		return RandomOracleSigmaChallengeGenerator.getInstance(challengeSpace, proverId,
															   PseudoRandomOracle.getInstance(), ConvertMethod.getInstance(ByteArray.class), ByteArrayAggregator.getInstance());
	}

	public static RandomOracleSigmaChallengeGenerator getInstance(ZMod challengeSpace, RandomOracle randomOracle) {
		return RandomOracleSigmaChallengeGenerator.getInstance(challengeSpace, (Element) null,
															   randomOracle, ConvertMethod.getInstance(ByteArray.class), ByteArrayAggregator.getInstance());
	}

	public static RandomOracleSigmaChallengeGenerator getInstance(ZMod challengeSpace, Element proverId,
		   RandomOracle randomOracle) {
		return RandomOracleSigmaChallengeGenerator.getInstance(challengeSpace, proverId,
															   randomOracle, ConvertMethod.getInstance(ByteArray.class), ByteArrayAggregator.getInstance());
	}

	public static RandomOracleSigmaChallengeGenerator getInstance(ZMod challengeSpace,
		   ConvertMethod<ByteArray> convertMethod, Aggregator<ByteArray> aggregator) {
		return RandomOracleSigmaChallengeGenerator.getInstance(challengeSpace, (Element) null,
															   PseudoRandomOracle.getInstance(), convertMethod, aggregator);
	}

	public static RandomOracleSigmaChallengeGenerator getInstance(ZMod challengeSpace, Element proverId,
		   ConvertMethod<ByteArray> convertMethod, Aggregator<ByteArray> aggregator) {
		return RandomOracleSigmaChallengeGenerator.getInstance(challengeSpace, proverId,
															   PseudoRandomOracle.getInstance(), convertMethod, aggregator);
	}

	public static RandomOracleSigmaChallengeGenerator getInstance(ZMod challengeSpace, RandomOracle randomOracle,
		   ConvertMethod<ByteArray> convertMethod, Aggregator<ByteArray> aggregator) {
		return RandomOracleSigmaChallengeGenerator.getInstance(challengeSpace, (Element) null,
															   randomOracle, convertMethod, aggregator);
	}

	public static RandomOracleSigmaChallengeGenerator getInstance(ZMod challengeSpace, Element proverId,
		   RandomOracle randomOracle, ConvertMethod<ByteArray> convertMethod, Aggregator<ByteArray> aggregator) {
		if (challengeSpace == null || randomOracle == null || convertMethod == null || aggregator == null) {
			throw new IllegalArgumentException();
		}
		return new RandomOracleSigmaChallengeGenerator(challengeSpace, proverId, randomOracle, convertMethod, aggregator);
	}

	public static RandomOracleSigmaChallengeGenerator getInstance(Function function) {
		return RandomOracleSigmaChallengeGenerator.getInstance(function, (Element) null,
															   PseudoRandomOracle.getInstance(), ConvertMethod.getInstance(ByteArray.class), ByteArrayAggregator.getInstance());
	}

	public static RandomOracleSigmaChallengeGenerator getInstance(Function function, Element proverId) {
		return RandomOracleSigmaChallengeGenerator.getInstance(function, proverId, PseudoRandomOracle.getInstance(),
															   ConvertMethod.getInstance(ByteArray.class), ByteArrayAggregator.getInstance());
	}

	public static RandomOracleSigmaChallengeGenerator getInstance(Function function, RandomOracle randomOracle) {
		return RandomOracleSigmaChallengeGenerator.getInstance(function, (Element) null, randomOracle,
															   ConvertMethod.getInstance(ByteArray.class), ByteArrayAggregator.getInstance());
	}

	public static RandomOracleSigmaChallengeGenerator getInstance(Function function, Element proverId,
		   RandomOracle randomOracle) {
		return RandomOracleSigmaChallengeGenerator.getInstance(function, proverId, randomOracle,
															   ConvertMethod.getInstance(ByteArray.class), ByteArrayAggregator.getInstance());
	}

	public static RandomOracleSigmaChallengeGenerator getInstance(Function function,
		   ConvertMethod<ByteArray> convertMethod, Aggregator<ByteArray> aggregator) {
		return RandomOracleSigmaChallengeGenerator.getInstance(function, (Element) null,
															   PseudoRandomOracle.getInstance(), convertMethod, aggregator);
	}

	public static RandomOracleSigmaChallengeGenerator getInstance(Function function, Element proverId,
		   ConvertMethod<ByteArray> convertMethod, Aggregator<ByteArray> aggregator) {
		return RandomOracleSigmaChallengeGenerator.getInstance(function, proverId,
															   PseudoRandomOracle.getInstance(), convertMethod, aggregator);
	}

	public static RandomOracleSigmaChallengeGenerator getInstance(Function function, RandomOracle randomOracle,
		   ConvertMethod<ByteArray> convertMethod, Aggregator<ByteArray> aggregator) {
		return RandomOracleSigmaChallengeGenerator.getInstance(function, (Element) null, randomOracle, convertMethod, aggregator);

	}

	public static RandomOracleSigmaChallengeGenerator getInstance(Function function, Element proverId,
		   RandomOracle randomOracle, ConvertMethod<ByteArray> convertMethod, Aggregator<ByteArray> aggregator) {
		if (function == null || !function.getCoDomain().isSemiGroup() || randomOracle == null
			   || convertMethod == null || aggregator == null) {
			throw new IllegalArgumentException();
		}
		ZMod challengeSpace = ZMod.getInstance(function.getDomain().getMinimalOrder());
		return new RandomOracleSigmaChallengeGenerator(challengeSpace, proverId, randomOracle, convertMethod, aggregator);
	}

}

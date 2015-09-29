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
package ch.bfh.unicrypt.math.algebra.additive.interfaces;

import ch.bfh.unicrypt.helper.math.Point;
import ch.bfh.unicrypt.math.algebra.dualistic.interfaces.DualisticElement;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import java.math.BigInteger;

/**
 * Some return types are updated.
 * <p>
 * @param <V>  The generic type of the values stored in the elements of the underlying finite field
 * @param <DE> The generic type of the dualistic elements of the underlying finite field
 * <p>
 * @author C. Lutz
 * @author R. Haenni
 */
public interface ECElement<V, DE extends DualisticElement<V>>
	   extends AdditiveElement<Point<DE>> {

	/**
	 * Additional convenience getter method to handle to point of infinity
	 * <p>
	 * @return
	 */
	public DE getY();

	/**
	 * Additional convenience getter method to handle to point of infinity
	 * <p>
	 * @return
	 */
	public DE getX();

	/**
	 */
	public ECElement<V, DE> add(Element element);

	/**
	 */
	public ECElement<V, DE> times(BigInteger factor);

	/**
	 */
	public ECElement<V, DE> times(Element<BigInteger> factor);

	/**
	 */
	public ECElement<V, DE> times(long factor);

	/**
	 */
	public ECElement<V, DE> timesTwo();

	/**
	 */
	public boolean isZero();

	/**
	 */
	public ECElement<V, DE> negate();

	/**
	 */
	public ECElement<V, DE> subtract(Element element);

	@Override
	public ECElement<V, DE> apply(Element element);

	@Override
	public ECElement<V, DE> selfApply(long factor);

	@Override
	public ECElement<V, DE> selfApply(BigInteger factor);

	@Override
	public ECElement<V, DE> selfApply(Element<BigInteger> factor);

	@Override
	public ECElement<V, DE> selfApply();

	@Override
	public ECElement<V, DE> invert();

	@Override
	public ECElement<V, DE> applyInverse(Element element);

}
